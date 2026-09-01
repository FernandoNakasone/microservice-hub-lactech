package  br.com.lactech.lacty.repositories;

import  br.com.lactech.lacty.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
