package br.com.lactech.lacty.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PerformanceChatbotRequestDTO {
    private String cpf;
    private Long idDuvida;
}
