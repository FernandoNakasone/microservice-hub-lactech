package br.com.lactech.gestor.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "lactech_gestor")
public class Gestor {

    @Id
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "senha",nullable = false, length = 15)
    private String senha;


}
