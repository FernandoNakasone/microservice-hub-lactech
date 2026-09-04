package br.com.lactech.gestor.repository;

import br.com.lactech.gestor.entities.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GestorRepository extends JpaRepository<Gestor,String> {

    Optional<Gestor> findByEmailAndSenha(String email, String senha);

}
