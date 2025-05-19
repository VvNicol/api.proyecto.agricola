package api.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.proyecto.modelos.TokenModelo;
import api.proyecto.modelos.UsuarioModelo;
import api.proyecto.repositorios.TokenRepositorioApi;

@Service
public class TokenServicioApi {

	@Autowired
	private TokenRepositorioApi tokenRepositorioApi;

	public TokenModelo buscarToken(String token) {
		return tokenRepositorioApi.findByToken(token);
	}

	public void guardar(TokenModelo tokenModelo) {
		tokenRepositorioApi.save(tokenModelo);
		
	}

	public void eliminar(TokenModelo token) {
		 tokenRepositorioApi.delete(token);		
	}

	public TokenModelo buscarPorUsuario(UsuarioModelo usuario) {
	    return tokenRepositorioApi.findByUsuario(usuario);

	}


}
