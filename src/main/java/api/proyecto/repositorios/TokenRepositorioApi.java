package api.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.TokenModelo;
import api.proyecto.modelos.UsuarioModelo;

/**
 * Repositorio para gestionar tokens de verificación en la base de datos.
 * 
 * @author nrojlla
 * @date 25/02/2025
 */
@Repository
public interface TokenRepositorioApi extends JpaRepository<TokenModelo, Long> {

	/**
	 * Busca un token por su valor.
	 * 
	 * @param token Cadena del token
	 * @return Token encontrado o null si no existe
	 */
	TokenModelo findByToken(String token); // Busca un usuario por su token

	/**
	 * Busca el token asociado a un usuario.
	 * 
	 * @param usuario Usuario al que pertenece el token
	 * @return Token correspondiente o null si no existe
	 */
	TokenModelo findByUsuario(UsuarioModelo usuario);

}
