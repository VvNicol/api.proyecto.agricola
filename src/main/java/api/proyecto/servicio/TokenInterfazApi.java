package api.proyecto.servicio;

import api.proyecto.modelos.TokenModelo;
import api.proyecto.modelos.UsuarioModelo;

/**
 * Interfaz que define operaciones para gestionar tokens de verificación.
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
public interface TokenInterfazApi {
	
	/**
	 * 1. Busca un token por su valor.
	 * 
	 * @param token Valor del token
	 * @return Token encontrado o null si no existe
	 */
	public TokenModelo buscarToken(String token);

	/**
	 * 2. Guarda o actualiza un token en la base de datos.
	 * 
	 * @param tokenModelo Token a guardar
	 */
	public void guardar(TokenModelo tokenModelo);

	/**
	 * 3. Elimina un token existente.
	 * 
	 * @param token Token a eliminar
	 */
	public void eliminar(TokenModelo token);

	/**
	 * 4. Busca un token asociado a un usuario.
	 * 
	 * @param usuario Usuario al que pertenece el token
	 * @return Token asociado al usuario
	 */
	public TokenModelo buscarPorUsuario(UsuarioModelo usuario);

}
