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
		return usuarioRepositorioApi.save(usuario);

	}
	
	public UsuarioModelo buscarPorToken(String token) {
        return usuarioRepositorioApi.findByToken(token);
    }
    
    public UsuarioModelo actualizarUsuario(UsuarioModelo usuario) {
        return usuarioRepositorioApi.save(usuario);
    }

	public UsuarioModelo buscarPorCorreo(String correo) {
		// TODO Auto-generated method stub
		return usuarioRepositorioApi.findByCorreo(correo);
	}

}
