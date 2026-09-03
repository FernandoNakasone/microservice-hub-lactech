package br.com.lactech.performance_chatbot.dto;

import br.com.lactech.performance_chatbot.entities.PerformanceChatbot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceChatbotResponseDTO {

    private Long id;
    private String cpf;
    private Long idDuvida;

    public PerformanceChatbotResponseDTO(PerformanceChatbot performanceChatbot){
        id = performanceChatbot.getId();
        cpf = performanceChatbot.getCpf();
        idDuvida = performanceChatbot.getIdDuvida();
    }

}
