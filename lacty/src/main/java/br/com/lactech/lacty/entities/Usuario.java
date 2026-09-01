package  br.com.lactech.lacty.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "cpf")
@Table(name = "lactech_usuario")
public class Usuario {

    @Id
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "idade", nullable = false)
    private Integer idade;

    @Column(name = "cep",nullable = false, length = 8)
    private String cep;

    @Column(name = "numero", nullable = false, length = 11, unique = true)
    private String numero;

    @Column(name = "ativo", nullable = false, length = 1)
    private Boolean ativo;

}
