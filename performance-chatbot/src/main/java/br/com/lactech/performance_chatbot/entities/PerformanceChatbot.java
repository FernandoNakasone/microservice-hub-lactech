package br.com.lactech.performance_chatbot.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "lactech_performance_chatbot")
public class PerformanceChatbot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario", nullable = false, length = 11)
    private String cpf;

    @Column(name = "duvidas", nullable = false)
    private Long idDuvida;
}
