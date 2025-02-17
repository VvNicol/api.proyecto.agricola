package api.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.proyecto.modelos.UsuarioModelo;
import api.proyecto.repositorios.UsuarioRepositorioApi;

@Component
public class UsuarioServicioApi {

	@Autowired
	private UsuarioRepositorioApi usuarioRepositorioApi;

	public UsuarioModelo registrarUsuario(UsuarioModelo usuario) throws Exception {

		if (usuarioRepositorioApi.existsByCorreo(usuario.getCorreo())) {
			throw new Exception("El correo ya está registrado.");
		}

		usuario.setRol("usuario");

		usuario.setFechaRegistro(java.time.LocalDateTime.now());
		return usuarioRepositorioApi.save(usuario);

	}

}
