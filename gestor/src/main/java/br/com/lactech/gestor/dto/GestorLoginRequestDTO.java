package br.com.lactech.gestor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record  GestorLoginRequestDTO (
    String email,
    String senha
){
    
}
