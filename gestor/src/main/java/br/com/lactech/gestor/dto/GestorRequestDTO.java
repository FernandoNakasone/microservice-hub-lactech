package br.com.lactech.gestor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GestorRequestDTO {

    @NotBlank(message = "O campo email não pode ser vazio,nulo ou em branco")
    @Email(message = "O campo email deve ser um email (exemplo@email,com)")
    @Size(min = 3, max = 50, message = "O campo email deve ter entre 3 a 50 caracteres")
    private String email;

    @NotBlank(message = "O campo senha não pode ser nulo, em branco ou vazio")
    @Size(min = 6,max = 15, message = "O campo senha deve ter entre 6 e 15 caracteres")
    private String senha;

}
