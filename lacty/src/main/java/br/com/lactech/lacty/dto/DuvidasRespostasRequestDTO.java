package  br.com.lactech.lacty.dto;

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

    private String sessaoId;

    @NotBlank(message = "O campo resposta não pode ser nulo, vazio ou em branco")
    private String duvida;

}