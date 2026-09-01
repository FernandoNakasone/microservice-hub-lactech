package  br.com.lactech.lacty.dto;

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
    private String cpf;

    @NotNull(message = "O campo idade não pode ser nulo, vazio ou em branco")
    @Positive(message = "O campo idade deve ser um valor positivo (maior que zero) ")
    private Integer idade;

    @NotBlank(message = "O campo cep não pode ser nulo, vazio ou em branco")
    @Size(min = 8, max = 8, message = "O campo cep deve conter 8 caracteres")
    private String cep;

    @NotNull(message = "O campo numero não pode ser nulo, vazio ou em branco")
    @Size(min = 11, max = 11, message = "O campo numero deve conter 11 digitos")
    private String  numero;

    @NotNull(message = "O campo ativo nao pode ser vazio, nulo ou em branco")
    private Boolean ativo;
}
