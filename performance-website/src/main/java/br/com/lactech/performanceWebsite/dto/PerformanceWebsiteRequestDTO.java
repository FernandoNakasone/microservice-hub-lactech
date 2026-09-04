package br.com.lactech.performanceWebsite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PerformanceWebsiteRequestDTO {

    @NotBlank(message = "O campo url não pode ser vazio, nulo ou em branco")
    @Size(max = 500, message = "O campo url deve ter no maximo 500 caractesres")
    private String url;

    @NotNull(message = "O campo data não pode ser nulo, vazio ou em branco")
    private LocalDate data;

    @NotNull(message = "O campo tempoPermanenciaMS não pode ser vazio, nulo ou em branco")
    @Positive(message = "O campo tempoPermanenciaMs deve ser positovo (maior que zero)")
    private Double tempoPermanenciaMs;

    @NotNull(message = "O campo tempoCarregamentoMs não pode ser vazio, nulo ou em branco")
    @Positive(message = "O campo tempoCarregamentoMs deve ser positovo (maior que zero)")
    private Double TempoCarregamentoMs;

    @NotBlank(message = "O campo tipoDispositivo não pode ser vazio, nulo ou em branco")
    @Size(max = 30, message = "O campo tipoDispositivo deve ter no maximo 30 caractesres")
    private String tipoDispositivo;

    @NotBlank(message = "O campo navegador não pode ser vazio, nulo ou em branco")
    @Size(max = 100, message = "O campo navegador deve ter no maximo 100 caractesres")
    private String navegador;

    @NotBlank(message = "O campo sistemaOperacional não pode ser vazio, nulo ou em branco")
    @Size(max = 100, message = "O campo sistemaOperacional deve ter no maximo 100 caractesres")
    private String sistemaOperacional;

}
