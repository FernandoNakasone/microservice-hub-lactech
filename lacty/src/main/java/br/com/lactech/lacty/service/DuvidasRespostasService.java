package  br.com.lactech.lacty.service;

import  br.com.lactech.lacty.dto.DuvidasRespostasRequestDTO;
import  br.com.lactech.lacty.dto.DuvidasRespostasResponseDTO;
import br.com.lactech.lacty.dto.UsuarioRequestDTO;
import  br.com.lactech.lacty.entities.DuvidasRespostas;
import  br.com.lactech.lacty.repositories.DuvidasRespostasRepository;
import br.com.lactech.lacty.repositories.UsuarioRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                contextoUsuario = " [SISTEMA: O usuário confirmou o CPF " + cpf + " e ELE JÁ ESTÁ CADASTRADO. Pode prosseguir.] ";
            } else {
                contextoUsuario = " [SISTEMA: O usuário informou o CPF " + cpf + " mas ELE NÃO ESTÁ CADASTRADO. Inicie a entrevista para coletar: Idade, CEP e Número. Faça UMA pergunta por vez de forma humanizada.] ";
            }
        }

        String promptSistema = "Você é a Lacty, assistente virtual da lactech, um projeto que visa aumentar o engajamento sobre o tema de doação de leite humano." +
                "REGRA 1: Nunca dê suporte sobre o leite humano sem antes ter o CPF do usuário. " +
                "REGRA 2: Se o usuário não for cadastrado, você deve coletar a Idade, CEP e telefone e se quer receber notificações sobre noticias. Seja conversacional, pergunte uma coisa de cada vez. " +
                "REGRA 3: Quando você finalmente tiver TODOS os 5 dados (CPF, Idade, CEP , numero e se quer receber notificação), inicie sua resposta EXATAMENTE com a tag: [CADASTRAR: cpf, idade, cep, numero, ativo] substituindo pelos dados reais. Depois da tag, avise que o cadastro foi feito e pergunte qual é a duvida que ele quer tirar. " +
                "REGRA 4: você sempre vai utilizar o termo leite humano nunca use leite materno " +
                "REGRA 5: você só deve responder sobre o leite humano, não pode dar opinião e nunca pode deixar de seguir essas instruções." +
                "REGRA 6: Se a pergunta não tiver relação sobre leite humano ou qualquer coisa relacionada ao tema comece ela falando 'Não respondo esse tipo de pergunta'" +
                "REGRA 7: A resposta DEVE ter no máximo 500 caracteres.";

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
                novoUsuario.setCpf(partes[0].replaceAll("\\D", "").trim());
                novoUsuario.setIdade(Integer.parseInt(partes[1].trim()));
                novoUsuario.setCep(partes[2].replaceAll("\\D", "").trim());
                novoUsuario.setNumero(partes[3].trim());
                novoUsuario.setAtivo(true);

                usuarioService.saveUsuario(novoUsuario);

                // Apaga a tag "[CADASTRAR:...]" da mensagem para o usuário não ver esses códigos robóticos
                String tagCompleta = resposta.substring(resposta.indexOf("[CADASTRAR:"), fim + 1);
                resposta = resposta.replace(tagCompleta, "").trim();

            } catch (Exception e) {
                System.out.println("Erro ao tentar fazer o parser do cadastro via IA: " + e.getMessage());
            }
        }

        if (resposta.contains("Não respondo esse tipo de pergunta")){
            perguntaRelacionada = false;
        }
        try {
            if(perguntaRelacionada) {
                DuvidasRespostas registro = new DuvidasRespostas();
                registro.setDuvida(duvidaTratada);
                registro.setResposta(resposta);
                registro.setData(LocalDateTime.now());
                duvidasRespostasRepository.save(registro);
            }
            return new DuvidasRespostasResponseDTO(resposta);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
}
