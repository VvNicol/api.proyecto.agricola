package api.proyecto.servicio;

import api.proyecto.modelos.UsuarioModelo;

/**
 * Servicio para la gestión de usuarios en el sistema.
 * 
 * @autor nrojlla 25022025
 */
public interface UsuarioInterfazApi {

	/**
	 * Registra un nuevo usuario en el sistema.
	 *
	 * @param usuario Objeto con los datos del usuario.
	 * @return Usuario registrado.
	 * @throws Exception Si el correo ya está registrado.
	 */
	public UsuarioModelo registrarUsuario(UsuarioModelo usuario) throws Exception;

	/**
	 * Busca un usuario por su token de verificación.
	 *
	 * @param token Token de verificación.
	 * @return Usuario encontrado o null si no existe.
	 */
	public UsuarioModelo buscarPorToken(String token);

	/**
	 * Actualiza los datos de un usuario en la base de datos.
	 *
	 * @param usuario Objeto con los datos actualizados del usuario.
	 * @return Usuario actualizado.
	 */
	public UsuarioModelo actualizarUsuario(UsuarioModelo usuario);

	/**
	 * Busca un usuario por su correo electrónico.
	 *
	 * @param correo Correo electrónico del usuario.
	 * @return Usuario encontrado o null si no existe.
	 */
	public UsuarioModelo buscarPorCorreo(String correo);

}
