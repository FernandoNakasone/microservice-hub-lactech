package  br.com.lactech.lacty.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "lactech_duvidas_respostas")
public class DuvidasRespostas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "duvida", length = 250, nullable = false)
    private String duvida;

    @Column(name = "resposta", length = 500, nullable = false)
    private String resposta;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

}
