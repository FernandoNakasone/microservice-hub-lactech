package br.com.lactech.performance_chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceChatbotRequestDTO {

    @NotBlank(message = "O campo cpf não pode ser em branco,vazio ou nulo")
    @Size(min = 11,max = 11, message = "O campo cpf deve ter 11 caracteres")
    @Schema(example = "12345678910")
    private String cpf;

    @NotNull(message = "O campo idDuvida não pode ser vazio, em branco ou nulo")
    @Schema(example = "4")
    private Long idDuvida;

}
