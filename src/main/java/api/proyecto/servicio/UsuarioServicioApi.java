package api.proyecto.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.proyecto.modelos.ParcelaModelo;
import api.proyecto.modelos.UsuarioModelo;
import api.proyecto.repositorios.CodigoRepositorioApi;
import api.proyecto.repositorios.ParcelaRepositorioApi;
import api.proyecto.repositorios.TokenRepositorioApi;
import api.proyecto.repositorios.UsuarioRepositorioApi;
import jakarta.transaction.Transactional;

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

	@Autowired
	private CodigoRepositorioApi codigoRepositorioApi;

	@Autowired
	private TokenRepositorioApi tokenRepositorioApi;

	@Autowired
	private ParcelaRepositorioApi parcelaRepositorioApi;


	public UsuarioModelo registrarUsuario(UsuarioModelo usuario) throws Exception {

		if (usuarioRepositorioApi.existsByCorreo(usuario.getCorreo())) {
			throw new Exception("El correo ya está registrado.");
		}
		return usuarioRepositorioApi.save(usuario);
	}

	public UsuarioModelo actualizarUsuario(UsuarioModelo usuario) {
		return usuarioRepositorioApi.save(usuario);
	}

	public UsuarioModelo buscarPorCorreo(String correo) {

		return usuarioRepositorioApi.findByCorreo(correo);
	}

	@Transactional
	public void eliminarUsuario(UsuarioModelo usuario) {
	    // Eliminar código si existe
	    if (usuario.getCodigo() != null) {
	        codigoRepositorioApi.delete(usuario.getCodigo());
	    }

	    // Eliminar token si existe
	    if (usuario.getToken() != null) {
	        tokenRepositorioApi.delete(usuario.getToken());
	    }

	    // Eliminar parcelas si existen
	    if (usuario.getParcelas() != null && !usuario.getParcelas().isEmpty()) {
	        for (ParcelaModelo parcela : usuario.getParcelas()) {
	            parcelaRepositorioApi.delete(parcela);
	        }
	    }

	    // Finalmente eliminar el usuario
	    usuarioRepositorioApi.delete(usuario);
	}


	public List<UsuarioModelo> obtenerTodosLosUsuarios() {
		return usuarioRepositorioApi.findAll();
	}

	public UsuarioModelo buscarPorId(Long usuarioId) {
		return usuarioRepositorioApi.findById(usuarioId).orElse(null);

	}

}
