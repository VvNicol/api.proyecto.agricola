package api.proyecto.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.proyecto.modelos.UsuarioModelo;
import api.proyecto.servicio.UsuarioServicioApi;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApiInicioControlador {

	@Autowired
	private UsuarioServicioApi usuarioServicioApi;
	
	@PostMapping("/registrarse")
	public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioModelo usuario) {
	    try {
	        UsuarioModelo nuevoUsuario = usuarioServicioApi.registrarUsuario(usuario);
	        Map<String, Object> response = new HashMap<>();
	        response.put("mensaje", "El usuario ha sido registrado con éxito. Verifique su correo para iniciar sesión.");
	        response.put("usuario", nuevoUsuario);
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    } catch (Exception e) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Error al registrar usuario: " + e.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	    }
	}

	
}
