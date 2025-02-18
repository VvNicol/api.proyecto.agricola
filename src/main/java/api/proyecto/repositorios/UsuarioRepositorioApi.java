package api.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.UsuarioModelo;

@Repository
public interface UsuarioRepositorioApi extends JpaRepository<UsuarioModelo, Long> {
	
    boolean existsByCorreo(String correo);

	UsuarioModelo findByToken(String token);

	UsuarioModelo findByCorreo(String correo);
}
