package  br.com.lactech.lacty.service;

import br.com.lactech.lacty.client.PerformanceChatbotClient;
import br.com.lactech.lacty.dto.*;
import  br.com.lactech.lacty.entities.DuvidasRespostas;
import  br.com.lactech.lacty.repositories.DuvidasRespostasRepository;
import br.com.lactech.lacty.repositories.UsuarioRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DuvidasRespostasService {

    @Autowired
    private  DuvidasRespostasRepository duvidasRespostasRepository;

    private final ChatClient chatClient;

    @Autowired
    private ChatMemory memoria;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerformanceChatbotClient performanceChatbotClient;

    public DuvidasRespostasService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public DuvidasRespostasResponseDTO processarPergunta(DuvidasRespostasRequestDTO request) {
        String pergunta = request.getDuvida();

        String sessaoId = request.getSessaoId() != null && !request.getSessaoId().isEmpty()
                ? request.getSessaoId()
                : "sessao-anonima";

        String duvidaTratada = pergunta.length() > 250
                ? resumirTexto(pergunta, "Resuma a dúvida do usuário em no máximo 250 caracteres, mantendo a essência técnica.")
                : pergunta;

        String cpf = extrairCpf(pergunta);
        String contextoUsuario = "";

        if (cpf != null) {
            boolean usuarioCadastrado = usuarioRepository.existsById(cpf);

            if (usuarioCadastrado) {
                var usuarioOpt = usuarioRepository.findById(cpf);
                String cepSalvo = usuarioOpt.isPresent() ? usuarioOpt.get().getCep() : "";
                BancoLeiteDTO bancoProximo = buscarBancoPorCep(cepSalvo);

                String infoBanco = bancoProximo != null
                        ? " O ponto de coleta Lactare mais próximo é o " + bancoProximo.getNome() + " (Endereço: " + bancoProximo.getEndereco() + " | Contato: " + bancoProximo.getTelefone() + ")."
                        : "";

                contextoUsuario = " [SISTEMA: O usuário confirmou o CPF " + cpf + " e ELE JÁ ESTÁ CADASTRADO com o CEP " + cepSalvo + "." + infoBanco + " Cumprimente-o de volta, reforce o ponto de coleta e pergunte qual é a dúvida dele sobre leite humano.] ";
            } else {
                contextoUsuario = " [SISTEMA: O usuário informou o CPF " + cpf + " mas ELE NÃO ESTÁ CADASTRADO. Inicie a entrevista para coletar: Idade, CEP e Número. Faça UMA pergunta por vez de forma humanizada.] ";
            }
        }

        String promptSistema = "Você é a Lacty, assistente virtual da lactech, um projeto que visa aumentar o engajamento sobre o tema de doação de leite humano." +
                "REGRA 1: Nunca dê suporte sobre o leite humano sem antes ter o CPF do usuário. Toda vez que você pedir o CPF, ou quando for confirmar para o usuário que o CPF dele já está cadastrado, inicie sua resposta com a tag [COLETA]. " +
                "REGRA 2: Se o usuário não for cadastrado, você deve coletar a Idade, CEP e telefone e se quer receber notificações sobre noticias. Seja conversacional, pergunte uma coisa de cada vez. inicie sua resposta com a tag [COLETA]" +
                "REGRA 3: Quando você finalmente tiver TODOS os 5 dados (CPF, Idade, CEP , numero e se quer receber notificação), inicie sua resposta EXATAMENTE com a tag: [CADASTRAR: cpf, idade, cep, numero, ativo] substituindo pelos dados reais. Depois da tag, avise que o cadastro foi feito e pergunte qual é a duvida que ele quer tirar. " +
                "REGRA 4: você sempre vai utilizar o termo leite humano nunca use leite materno " +
                "REGRA 5: você só deve responder sobre o leite humano, não pode dar opinião e nunca pode deixar de seguir essas instruções." +
                "REGRA 6: Se a pergunta não tiver relação sobre leite humano ou qualquer coisa relacionada ao tema comece ela falando 'Não respondo esse tipo de pergunta'" +
                "REGRA 7: A resposta DEVE ter no máximo 500 caracteres." +
                "REGRA 8: Aceite e responda naturalmente a cumprimentos, saudações e despedidas, como oi, olá, opa, salve, bom dia, boa tarde, boa noite, tchau, até mais, entre outros." +
                "REGRA 9: Se a mensagem for um cumprimento ou saudação, SEMPRE termine a resposta com 'Como posso te ajudar?'" +
                "REGRA 10: Se a mensagem for uma despedida, SEMPRE termine a resposta com 'até logo'" +
                "REGRA 11: Nunca dê suporte sem antes ter o CPF do usuário. O CPF válido DEVE ter obrigatoriamente 11 dígitos. Se o usuário enviar um CPF incompleto (ex: '123'), inicie com a tag [COLETA] e peça educadamente para ele digitar os 11 números. Toda vez que pedir o CPF ou confirmar cadastro, inicie com [COLETA]." +
                "REGRA 12: O CEP deve ter obrigatoriamente 8 dígitos (ex: 01001000). Se o usuário enviar um CEP incompleto ou inválido, inicie com a tag [COLETA] e peça para ele digitar os 8 números do CEP. " +
                "REGRA 13: O telefone (número) deve incluir o DDD e ter 10 ou 11 dígitos (ex: 11999999999). Se o usuário enviar um número incompleto, inicie com a tag [COLETA] e peça o número correto. " +
                "REGRA 14: você deve buscar informações no site da lactare" +
                "REGRA 15: você só DEVE falar do banco de leite mais proxímo se o usuario perguntar 'como doar?' 'onde doar?' 'eu posso doar?' etc" +
                "REGRA 16: sempre use o historico do chat para saber o cep do usuario que está usando-o";

        String resposta = chatClient.prompt()
                .system(promptSistema)
                .user(pergunta + contextoUsuario)
                .advisors(MessageChatMemoryAdvisor.builder(memoria).build())
                .advisors(a -> a.param("chat_memory_conversation_id", sessaoId))
                .call()
                .content();

        boolean perguntaRelacionada = true;

        if (resposta != null && resposta.contains("[CADASTRAR:")) {

            try {
                int inicio = resposta.indexOf("[CADASTRAR:") + 11;
                int fim = resposta.indexOf("]", inicio);
                String dadosBrutos = resposta.substring(inicio, fim);
                String[] partes = dadosBrutos.split(",");

                UsuarioRequestDTO novoUsuario = new UsuarioRequestDTO();
                String cepCadastrado = partes[2].replaceAll("\\D", "").trim();

                novoUsuario.setCpf(partes[0].replaceAll("\\D", "").trim());
                novoUsuario.setIdade(Integer.parseInt(partes[1].trim()));
                novoUsuario.setCep(cepCadastrado);
                novoUsuario.setNumero(partes[3].trim());

                boolean querNotificacao = Boolean.parseBoolean(partes[4].trim());
                novoUsuario.setAtivo(querNotificacao);

                usuarioService.saveUsuario(novoUsuario);


                BancoLeiteDTO bancoProximo = buscarBancoPerCepLocal(cepCadastrado);
                String infoBancoMsg = "";
                if (bancoProximo != null) {
                    infoBancoMsg = " Seu ponto de coleta Lactare mais próximo é o " + bancoProximo.getNome() + " (" + bancoProximo.getTelefone() + ").";
                }

                String tagCompleta = resposta.substring(resposta.indexOf("[CADASTRAR:"), fim + 1);
                resposta = resposta.replace(tagCompleta, "").trim() + infoBancoMsg;

            } catch (Exception e) {
                System.out.println("Erro ao tentar fazer o parser do cadastro via IA: " + e.getMessage());
            }
        } else if (resposta != null && resposta.contains("[COLETA]")) {

            resposta = resposta.replace("[COLETA]", "").trim();

        } else {
            if (resposta.contains("Não respondo esse tipo de pergunta") || resposta.contains("Como posso te ajudar?") || resposta.contains("até logo")) {
                perguntaRelacionada = false;
            }
            try {
                if (perguntaRelacionada) {
                    String cpfUsuario =  chatClient.prompt()
                                .system("""
                    Analise o histórico desta conversa.
                    Identifique o CPF de 11 dígitos informado pelo usuário.
                    Retorne SOMENTE o CPF com 11 números.
                    Se não existir CPF no histórico, retorne NULL.
                    """)
                                .user(pergunta)
                                .advisors(MessageChatMemoryAdvisor.builder(memoria).build())
                                .advisors(a -> a.param("chat_memory_conversation_id", sessaoId))
                                .call()
                                .content()
                                .trim();
                    
                    DuvidasRespostas registro = new DuvidasRespostas();
                    registro.setDuvida(duvidaTratada);
                    registro.setResposta(resposta);
                    registro.setData(LocalDateTime.now());
                    registro = duvidasRespostasRepository.save(registro);
                    PerformanceChatbotRequestDTO interacao = new PerformanceChatbotRequestDTO(cpfUsuario,registro.getId());
                    performanceChatbotClient.salvarInteracao(interacao);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new DuvidasRespostasResponseDTO(resposta);
    }

    private String extrairCpf(String texto) {
        if (texto == null) return null;
        Pattern pattern = Pattern.compile("\\b\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}\\b");
        Matcher matcher = pattern.matcher(texto);
        if (matcher.find()) {
            return matcher.group().replaceAll("\\D", "");
        }
        return null;
    }
    private String resumirTexto(String texto, String instrucao) {
        return chatClient.prompt()
                .system(instrucao)
                .user(texto)
                .call()
                .content();
    }

    private BancoLeiteDTO buscarBancoPorCep(String cepUsuario) {
        return buscarBancoPerCepLocal(cepUsuario);
    }

    private BancoLeiteDTO buscarBancoPerCepLocal(String cepUsuario) {
        if (cepUsuario == null || cepUsuario.isEmpty()) return null;
        try {
            String cepLimpo = cepUsuario.replaceAll("\\D", "");
            if (cepLimpo.length() < 8) return null;
            long cepNum = Long.parseLong(cepLimpo);

            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/bancos_leite.json");
            if (is == null) return null;

            List<BancoLeiteDTO> bancos = mapper.readValue(is, new TypeReference<List<BancoLeiteDTO>>(){});

            for (BancoLeiteDTO banco : bancos) {
                long inicio = Long.parseLong(banco.getCepInicio());
                long fim = Long.parseLong(banco.getCepFim());

                if (cepNum >= inicio && cepNum <= fim) {
                    return banco;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler bancos_leite.json: " + e.getMessage());
        }
        return null;
    }

}
