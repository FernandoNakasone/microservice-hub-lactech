package br.com.lactech.lacty.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancoLeiteDTO {
    private String nome;
    private String endereco;
    private String telefone;
    private String cepInicio;
    private String cepFim;
}
