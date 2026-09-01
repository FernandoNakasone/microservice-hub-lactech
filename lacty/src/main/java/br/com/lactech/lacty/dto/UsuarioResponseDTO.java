package  br.com.lactech.lacty.dto;

import br.com.lactech.lacty.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioResponseDTO {

    private String cpf;
    private Integer idade;
    private String cep;
    private String numero;
    private Boolean ativo;

    public UsuarioResponseDTO(Usuario usuario) {
        cpf = usuario.getCpf();
        idade = usuario.getIdade();
        cep = usuario.getCep();
        numero = usuario.getNumero();
        ativo = usuario.getAtivo();
    }
}
