package api.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.proyecto.modelos.UsuarioModelo;
import api.proyecto.repositorios.UsuarioRepositorioApi;

/**
 * Servicio para la gestión de usuarios. Implementa la interfaz
 * UsuarioInterfazApi.
 * 
 * @autor nrojlla 25022025
 */
@Component
public class UsuarioServicioApi implements UsuarioInterfazApi {

	@Autowired
	private UsuarioRepositorioApi usuarioRepositorioApi;

	public UsuarioModelo registrarUsuario(UsuarioModelo usuario) throws Exception {

		if (usuarioRepositorioApi.existsByCorreo(usuario.getCorreo())) {
			throw new Exception("El correo ya está registrado.");
		}
		return usuarioRepositorioApi.save(usuario);
	}

	public UsuarioModelo buscarPorToken(String token) {
		return usuarioRepositorioApi.findByToken(token);
	}

	public UsuarioModelo actualizarUsuario(UsuarioModelo usuario) {
		return usuarioRepositorioApi.save(usuario);
	}

	public UsuarioModelo buscarPorCorreo(String correo) {

		return usuarioRepositorioApi.findByCorreo(correo);
	}

}
