package  br.com.lactech.lacty.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioRequestDTO {

    @NotBlank(message = "O campo cpf não pode ser nulo, vazio ou em branco")
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 digitos")
    @Schema(example = "44444555555")
    private String cpf;

    @NotNull(message = "O campo idade não pode ser nulo, vazio ou em branco")
    @Positive(message = "O campo idade deve ser um valor positivo (maior que zero) ")
    @Schema(example = "32")
    private Integer idade;

    @NotBlank(message = "O campo cep não pode ser nulo, vazio ou em branco")
    @Size(min = 8, max = 8, message = "O campo cep deve conter 8 caracteres")
    @Schema(example = "03525020")
    private String cep;

    @NotNull(message = "O campo numero não pode ser nulo, vazio ou em branco")
    @Size(min = 11, max = 11, message = "O campo numero deve conter 11 digitos")
    @Schema(example = "11923459876")
    private String  numero;

    @NotNull(message = "O campo ativo nao pode ser vazio, nulo ou em branco")
    @Schema(example = "true")
    private Boolean ativo;
}
