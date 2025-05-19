package api.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.UsuarioModelo;

/**
 * Repositorio para la gestión de usuarios en la base de datos.
 * 
 * @autor nrojlla 25022025
 */
@Repository
public interface UsuarioRepositorioApi extends JpaRepository<UsuarioModelo, Long> {
	
    boolean existsByCorreo(String correo); // Verifica si un correo ya está registrado

	UsuarioModelo findByCorreo(String correo); // Busca un usuario por su correo
}
