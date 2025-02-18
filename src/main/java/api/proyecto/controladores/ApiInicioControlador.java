package api.proyecto.controladores;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		Map<String, Object> respuesta = new HashMap<>();
		try {
			UsuarioModelo nuevoUsuario = usuarioServicioApi.registrarUsuario(usuario);

			respuesta.put("mensaje",
					"El usuario ha sido registrado con éxito. Verifique su correo para iniciar sesión.");
			respuesta.put("usuario", nuevoUsuario);
			return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
		} catch (Exception e) {

			respuesta.put("error", e.getMessage());
			System.out.print(e.getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/token-correo")
	public ResponseEntity<Map<String, Object>> tokenCorreo(@RequestParam("token") String token) {
	    Map<String, Object> respuesta = new HashMap<>();
	    try {
	        UsuarioModelo usuario = usuarioServicioApi.buscarPorToken(token);
	        if (usuario == null) {
	            respuesta.put("error", "Token no encontrado.");
	            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
	        }

	        if (usuario.getTokenExpiracionFecha() == null || usuario.getTokenExpiracionFecha().isBefore(LocalDateTime.now())) {
	            respuesta.put("error", "El token ha expirado.");
	            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
	        }

	        respuesta.put("token", usuario.getToken());
	        respuesta.put("caducidad", usuario.getTokenExpiracionFecha());
	        respuesta.put("correo", usuario.getCorreo());

	        return new ResponseEntity<>(respuesta, HttpStatus.OK);
	    } catch (Exception e) {
	        respuesta.put("error", "Error al buscar token: " + e.getMessage());
	        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@PostMapping("/validar-correo")
	public ResponseEntity<Map<String, String>> validarCorreo(@RequestBody UsuarioModelo usuario) {
	    Map<String, String> respuesta = new HashMap<>();
	    try {
	        // 1. Buscar usuario en la base de datos por su correo
	        UsuarioModelo usuarioExistente = usuarioServicioApi.buscarPorCorreo(usuario.getCorreo());
	        if (usuarioExistente == null) {
	            respuesta.put("error", "Correo no encontrado.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
	        }

	        // 2. Marcar el correo como validado y eliminar el token
	        usuarioExistente.setCorreoValidado(true);
	        usuarioExistente.setToken(null);
	        usuarioExistente.setTokenExpiracionFecha(null);

	        // 3. Guardar cambios en la base de datos
	        usuarioServicioApi.actualizarUsuario(usuarioExistente);

	        respuesta.put("mensaje", "Correo verificado exitosamente.");
	        return ResponseEntity.ok(respuesta);
	    } catch (Exception e) {
	        respuesta.put("error", "Error al validar correo: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	    }
	}





}
