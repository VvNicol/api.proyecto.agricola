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

	/**
	 * Verifica si ya existe un usuario registrado con el correo dado.
	 * 
	 * @param correo Correo electrónico a verificar
	 * @return true si existe, false si no
	 */
	boolean existsByCorreo(String correo); // Verifica si un correo ya está registrado

	/**
	 * Busca un usuario por su correo electrónico.
	 * 
	 * @param correo Correo del usuario
	 * @return Usuario encontrado o null si no existe
	 */
	UsuarioModelo findByCorreo(String correo); // Busca un usuario por su correo
}
