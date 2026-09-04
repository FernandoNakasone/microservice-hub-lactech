package br.com.lactech.performanceWebsite.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "lactech_performance_website")
public class PerformanceWebsite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "data_acesso", nullable = false)
    private LocalDate data;

    @Column(name = "tempo_permanencia_ms", nullable = false)
    private Double tempoPermanenciaMs;

    @Column(name = "tempo_carregamento_ms", nullable = false)
    private Double TempoCarregamentoMs;

    @Column(name = "tipo_dispositivo", length = 20)
    private String tipoDispositivo;

    @Column(name = "navegador", length = 100)
    private String navegador;

    @Column(name = "sistema_operacional",length = 100)
    private String sistemaOperacional;
}
