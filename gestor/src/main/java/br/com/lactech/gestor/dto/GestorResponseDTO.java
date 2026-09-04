package br.com.lactech.gestor.dto;

import br.com.lactech.gestor.entities.Gestor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GestorResponseDTO {

    private String email;
    private String senha;

    public GestorResponseDTO(Gestor gestor){
        email = gestor.getEmail();
        senha = gestor.getSenha();
    }

}
