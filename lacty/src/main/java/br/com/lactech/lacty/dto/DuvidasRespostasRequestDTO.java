package  br.com.lactech.lacty.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DuvidasRespostasRequestDTO {

    @NotBlank(message = "O campo sessaoId nao pode ser nulo, vazio ou em branco")
    @Schema(example = "sessão-atendimento-1")
    private String sessaoId;

    @NotBlank(message = "O campo resposta não pode ser nulo, vazio ou em branco")
    @Schema(example = "o que é lactech")
    private String duvida;

}