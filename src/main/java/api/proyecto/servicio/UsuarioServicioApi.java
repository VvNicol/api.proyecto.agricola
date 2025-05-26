package api.proyecto.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.proyecto.modelos.CultivoModelo;
import api.proyecto.modelos.ParcelaModelo;
import api.proyecto.modelos.UsuarioModelo;
import api.proyecto.repositorios.CodigoRepositorioApi;
import api.proyecto.repositorios.CultivoRepositorioApi;
import api.proyecto.repositorios.NotificacionRepositorioApi;
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
	private CultivoRepositorioApi cultivoRepositorioApi;

	@Autowired
	private ParcelaRepositorioApi parcelaRepositorioApi;
	
	@Autowired
	private NotificacionRepositorioApi notificacionRepositorioApi;


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
	    // 1. Eliminar código y token si existen
	    if (usuario.getCodigo() != null) {
	        codigoRepositorioApi.delete(usuario.getCodigo());
	    }

	    if (usuario.getToken() != null) {
	        tokenRepositorioApi.delete(usuario.getToken());
	    }

	    // 2. Eliminar notificaciones de cada cultivo de cada parcela
	    if (usuario.getParcelas() != null && !usuario.getParcelas().isEmpty()) {
	        for (ParcelaModelo parcela : usuario.getParcelas()) {

	            List<CultivoModelo> cultivos = cultivoRepositorioApi.findByParcelaId(parcela);
	            for (CultivoModelo cultivo : cultivos) {
	                // Eliminar notificaciones del cultivo
	                if (cultivo.getNotificaciones() != null && !cultivo.getNotificaciones().isEmpty()) {
	                    notificacionRepositorioApi.deleteAll(cultivo.getNotificaciones());
	                }

	                // Eliminar el cultivo
	                cultivoRepositorioApi.delete(cultivo);
	            }

	            // 3. Eliminar la parcela
	            parcelaRepositorioApi.delete(parcela);
	        }
	    }

	    // 4. Eliminar el usuario
	    usuarioRepositorioApi.delete(usuario);
	}


	public List<UsuarioModelo> obtenerTodosLosUsuarios() {
		return usuarioRepositorioApi.findAll();
	}

	public UsuarioModelo buscarPorId(Long usuarioId) {
		return usuarioRepositorioApi.findById(usuarioId).orElse(null);

	}

}
