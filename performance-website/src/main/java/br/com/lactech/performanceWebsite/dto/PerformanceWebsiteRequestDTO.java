package br.com.lactech.performanceWebsite.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "https://seusistema.com.br/home")
    private String url;

    @NotNull(message = "O campo data não pode ser nulo, vazio ou em branco")
    @Schema(example = "2026-09-04")
    private LocalDate data;

    @NotNull(message = "O campo tempoPermanenciaMS não pode ser vazio, nulo ou em branco")
    @Positive(message = "O campo tempoPermanenciaMs deve ser positovo (maior que zero)")
    @Schema(example = "45500.0")
    private Double tempoPermanenciaMs;

    @NotNull(message = "O campo tempoCarregamentoMs não pode ser vazio, nulo ou em branco")
    @Positive(message = "O campo tempoCarregamentoMs deve ser positovo (maior que zero)")
    @Schema(example = "1205.5")
    private Double tempoCarregamentoMs;

    @NotBlank(message = "O campo tipoDispositivo não pode ser vazio, nulo ou em branco")
    @Size(max = 30, message = "O campo tipoDispositivo deve ter no maximo 30 caractesres")
    @Schema(example = "Desktop")
    private String tipoDispositivo;

    @NotBlank(message = "O campo navegador não pode ser vazio, nulo ou em branco")
    @Size(max = 100, message = "O campo navegador deve ter no maximo 100 caractesres")
    @Schema(example = "Chrome")
    private String navegador;

    @NotBlank(message = "O campo sistemaOperacional não pode ser vazio, nulo ou em branco")
    @Size(max = 100, message = "O campo sistemaOperacional deve ter no maximo 100 caractesres")
    @Schema(example = "Windows")
    private String sistemaOperacional;

}
