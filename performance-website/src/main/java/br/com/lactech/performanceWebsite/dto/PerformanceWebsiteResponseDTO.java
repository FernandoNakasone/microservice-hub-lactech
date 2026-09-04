package br.com.lactech.performanceWebsite.dto;

import br.com.lactech.performanceWebsite.entities.PerformanceWebsite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PerformanceWebsiteResponseDTO {

    private Long id;
    private String url;
    private LocalDate data;
    private Double tempoPermanenciaMs;
    private Double TempoCarregamentoMs;
    private String tipoDispositivo;
    private String navegador;
    private String sistemaOperacional;

    public PerformanceWebsiteResponseDTO(PerformanceWebsite performanceWebsite){
        id = performanceWebsite.getId();
        url = performanceWebsite.getUrl();
        data = performanceWebsite.getData();
        tempoPermanenciaMs = performanceWebsite.getTempoPermanenciaMs();
        TempoCarregamentoMs = performanceWebsite.getTempoCarregamentoMs();
        tipoDispositivo = performanceWebsite.getTipoDispositivo();
        navegador = performanceWebsite.getNavegador();
        sistemaOperacional = performanceWebsite.getSistemaOperacional();
    }

}
