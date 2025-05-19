package api.proyecto.controladores;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.proyecto.modelos.CodigoModelo;
import api.proyecto.modelos.TokenModelo;
import api.proyecto.modelos.UsuarioModelo;
import api.proyecto.servicio.CodigoServicioApi;
import api.proyecto.servicio.TokenServicioApi;
import api.proyecto.servicio.UsuarioServicioApi;
import dwp.agrilog.dto.TokenDto;

/**
 * Controlador para gestionar las operaciones de autenticación y registro de
 * usuarios en la API.
 * 
 * @autor nrojlla 25022025
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApiInicioControlador {

	@Autowired
	private UsuarioServicioApi usuarioServicioApi;

	@Autowired
	private TokenServicioApi tokenServicioApi;

	@Autowired
	private CodigoServicioApi codigoServicioApi;
	

	/**
	 * Registra un nuevo usuario en el sistema.
	 * 
	 * @param usuario Objeto con los datos del usuario a registrar.
	 * @return Respuesta con mensaje de éxito o error.
	 */
	@PostMapping("/registrarse")
	public ResponseEntity<UsuarioModelo> registrarUsuario(@RequestBody UsuarioModelo usuario) {
	    try {
	        UsuarioModelo nuevoUsuario = usuarioServicioApi.registrarUsuario(usuario);
	        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}

	/**
	 * Obtiene la información del token de verificación de un usuario.
	 * 
	 * @param token Token de verificación enviado al usuario.
	 * @return Información del token y correo asociado.
	 */
	@GetMapping("/token-correo")
	public ResponseEntity<Map<String, Object>> tokenCorreo(@RequestParam("token") String token) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			// Ahora buscas el token directamente en la tabla TokenModelo
			TokenModelo tokenModelo = tokenServicioApi.buscarToken(token);

			if (tokenModelo == null || tokenModelo.getUsuario() == null) {
				respuesta.put("error", "Token no encontrado.");
				return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
			}

			if (tokenModelo.getTokenExpiracionFecha() == null
					|| tokenModelo.getTokenExpiracionFecha().isBefore(LocalDateTime.now())) {
				respuesta.put("error", "El token ha expirado.");
				return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
			}

			UsuarioModelo usuario = tokenModelo.getUsuario();

			respuesta.put("token", tokenModelo.getToken());
			respuesta.put("caducidad", tokenModelo.getTokenExpiracionFecha());
			respuesta.put("correo", usuario.getCorreo());

			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} catch (Exception e) {
			respuesta.put("error", "Error al buscar token: " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Actualiza el token de verificación de un usuario.
	 * 
	 * @param usuario Objeto con el correo del usuario y el nuevo token.
	 * @return Respuesta con mensaje de éxito o error.
	 */
	@PostMapping("/token-correo-actualizar")
	public ResponseEntity<Map<String, String>> actualizarToken(@RequestBody TokenDto tokenDto) {
	    Map<String, String> respuesta = new HashMap<>();
	    try {
	        UsuarioModelo usuario = usuarioServicioApi.buscarPorId(tokenDto.getUsuarioId());
	        if (usuario == null) {
	            respuesta.put("error", "Usuario no encontrado.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
	        }

	        TokenModelo token = tokenServicioApi.buscarPorUsuario(usuario);

	        if (token == null) {
	            token = new TokenModelo();
	            token.setUsuario(usuario); // Relación obligatoria
	        }

	        token.setToken(tokenDto.getToken());
	        token.setTokenExpiracionFecha(tokenDto.getTokenExpiracionFecha());
	    
	        tokenServicioApi.guardar(token);

	        respuesta.put("mensaje", "Token actualizado correctamente.");
	        return ResponseEntity.ok(respuesta);
	    } catch (Exception e) {
	        e.printStackTrace(); // 🔍 Esto faltaba para mostrar el error exacto en consola
	        respuesta.put("error", "Error al actualizar el token: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	    }
	}


	/**
	 * Valida el correo electrónico de un usuario.
	 * 
	 * @param usuario Objeto con el correo del usuario a validar.
	 * @return Respuesta con mensaje de éxito o error.
	 */
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

			usuarioExistente.setCorreoValidado(true);

			TokenModelo token = usuario.getToken();
			if (token != null) {
				tokenServicioApi.eliminar(token);
			}

			// 3. Guardar cambios en la base de datos
			usuarioServicioApi.actualizarUsuario(usuarioExistente);

			respuesta.put("mensaje", "Correo verificado exitosamente.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			respuesta.put("error", "Error al validar correo: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	/**
	 * Obtiene la información de un usuario por su correo electrónico.
	 * 
	 * @param usuario Objeto con el correo del usuario.
	 * @return Respuesta con la contraseña, estado de validación del correo y rol
	 *         del usuario.
	 */
	@PostMapping("/contrasenia")
	public ResponseEntity<Map<String, Object>> obtenerUsuario(@RequestBody UsuarioModelo usuario) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			UsuarioModelo usuarioExistente = usuarioServicioApi.buscarPorCorreo(usuario.getCorreo());
			if (usuarioExistente == null) {
				respuesta.put("error", "Correo no encontrado");
				return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
			}

			respuesta.put("contrasenia", usuarioExistente.getContrasenia());
			respuesta.put("correoValidado", usuarioExistente.isCorreoValidado());
			respuesta.put("rol", usuarioExistente.getRol());

			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} catch (Exception e) {
			respuesta.put("error", "Error al obtener usuario: " + e.getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Guarda un codigo de verificacion con su caducidad
	 * 
	 * @param request un correo para verificar el usuarioS
	 * @return Mensaje de exito o error
	 */
	@PostMapping("/guardar-codigo")
	public ResponseEntity<Map<String, String>> guardarCodigoRecuperacion(@RequestBody Map<String, String> request) {
		Map<String, String> respuesta = new HashMap<>();
		try {
			String correo = request.get("correo");
			int codigo = Integer.parseInt(request.get("codigo"));
			LocalDateTime expiracion = LocalDateTime.parse(request.get("expiracion"));

			UsuarioModelo usuario = usuarioServicioApi.buscarPorCorreo(correo);
			if (usuario == null) {
				respuesta.put("error", "Correo no encontrado.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			CodigoModelo codigoModelo = usuario.getCodigo();

			if (codigoModelo == null) {
				codigoModelo = new CodigoModelo();
				codigoModelo.setUsuario(usuario);
			}

			codigoModelo.setCodigoRecuperacion(codigo);
			codigoModelo.setCodigoExpiracionFecha(expiracion);
			codigoModelo.setCodigoVerificado(false);

			codigoServicioApi.guardar(codigoModelo);

			respuesta.put("mensaje", "Código de recuperación guardado correctamente.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.put("error", "Error al guardar el código: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	/**
	 * 
	 * Envia los datos del codigo de recuperacion
	 * 
	 * @param request correo del usuio
	 * @return Codigo String del codigo y un String de la fecha de expiracion
	 */
	@PostMapping("/obtener-codigo")
	public ResponseEntity<Map<String, String>> obtenerCodigo(@RequestBody Map<String, String> request) {
		Map<String, String> respuesta = new HashMap<>();
		try {
			String correo = request.get("correo");

			UsuarioModelo usuario = usuarioServicioApi.buscarPorCorreo(correo);
			if (usuario == null) {
				respuesta.put("error", "Correo no encontrado.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}

			CodigoModelo codigo = usuario.getCodigo();
			if (codigo == null || codigo.getCodigoRecuperacion() == 0 || codigo.getCodigoExpiracionFecha() == null) {
				respuesta.put("error", "No hay un código de recuperación activo.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}

			respuesta.put("codigo", String.valueOf(codigo.getCodigoRecuperacion()));
			respuesta.put("expiracion", codigo.getCodigoExpiracionFecha().toString());

			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			e.printStackTrace();
			respuesta.put("error", "Error al obtener el código: " + e.getMessage());			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	/**
	 * Valida codigo de recuperacion de un usuario.
	 * 
	 * @param usuario Datos del usuario con el correo a validar.
	 * @return Mensaje de éxito o error en la validación.
	 */
	@PostMapping("/codigo-verificar")
	public ResponseEntity<Map<String, String>> verificarCodigo(@RequestBody Map<String, String> request) {
		Map<String, String> respuesta = new HashMap<>();
		try {
			String correo = request.get("correo");
			int codigoIngresado = Integer.parseInt(request.get("codigo"));

			UsuarioModelo usuario = usuarioServicioApi.buscarPorCorreo(correo);
			if (usuario == null) {
				respuesta.put("error", "Correo no encontrado.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}

			CodigoModelo codigo = usuario.getCodigo();
			if (codigo == null) {
				respuesta.put("error", "No hay código registrado.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}

			if (codigo.getCodigoRecuperacion() != codigoIngresado) {
				respuesta.put("error", "Código incorrecto.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			if (codigo.getCodigoExpiracionFecha() == null
					|| codigo.getCodigoExpiracionFecha().isBefore(LocalDateTime.now())) {
				respuesta.put("error", "Código expirado.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			// Código válido: marcarlo como verificado
			codigo.setCodigoVerificado(true);
			codigoServicioApi.guardar(codigo);
			respuesta.put("mensaje", "Código verificado correctamente.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.put("error", "Error al verificar el código: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	/**
	 * Cambia la contraseña de un usuario después de verificar el código de
	 * recuperación.
	 * 
	 * @param request Datos con el correo y la nueva contraseña encriptada.
	 * @return Mensaje de éxito o error en el cambio de contraseña.
	 */
	@PostMapping("/cambiar-contrasenia")
	public ResponseEntity<Map<String, String>> cambiarContrasenia(@RequestBody Map<String, String> request) {
		Map<String, String> respuesta = new HashMap<>();
		try {
			String correo = request.get("correo");
			String nuevaContrasenia = request.get("nuevaContrasenia");

			UsuarioModelo usuario = usuarioServicioApi.buscarPorCorreo(correo);
			if (usuario == null) {
				respuesta.put("error", "Correo no encontrado.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}

			// Guardar directamente la contraseña encriptada
			usuario.setContrasenia(nuevaContrasenia);

			 // Desactivar código de recuperación
	        CodigoModelo codigo = usuario.getCodigo();
	        if (codigo != null) {
	            codigo.setCodigoRecuperacion(0);
	            codigo.setCodigoVerificado(false);
	            codigo.setCodigoExpiracionFecha(null);
	            codigoServicioApi.guardar(codigo);
	        }
			usuarioServicioApi.actualizarUsuario(usuario);

			respuesta.put("mensaje", "Contraseña cambiada con éxito.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.put("error", "Error al cambiar la contraseña: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	/**
	 * Obtiene la lista de todos los usuarios registrados.
	 * 
	 * @return Lista de usuarios o error en caso de fallo.
	 */
	@GetMapping("/lista-usuarios")
	public ResponseEntity<?> obtenerListaUsuarios() {
	    try {
	        List<UsuarioModelo> usuarios = usuarioServicioApi.obtenerTodosLosUsuarios();

	        List<Map<String, Object>> lista = new ArrayList<>();
	        for (UsuarioModelo u : usuarios) {
	            Map<String, Object> datos = new HashMap<>();
	            datos.put("correo", u.getCorreo());
	            datos.put("rol", u.getRol());
	            lista.add(datos);
	        }

	        return ResponseEntity.ok(lista);

	    } catch (Exception e) {
	        Map<String, String> error = new HashMap<>();
	        e.printStackTrace();
	        error.put("mensaje", "Error al obtener la lista de usuarios.");
	        error.put("error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	    }
	}


	/**
	 * Elimina un usuario por su correo.
	 * 
	 * @param correo Correo del usuario a eliminar.
	 * @return Mensaje de confirmación o error.
	 */
	@DeleteMapping("/eliminar-usuario")
	public ResponseEntity<Map<String, String>> eliminarUsuario(@RequestParam String correo) {
		Map<String, String> respuesta = new HashMap<>();
		try {
			UsuarioModelo usuario = usuarioServicioApi.buscarPorCorreo(correo);
			if (usuario == null) {
				
				respuesta.put("error", "Usuario no encontrado.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}

			usuarioServicioApi.eliminarUsuario(usuario);
			
			respuesta.put("mensaje", "Usuario eliminado correctamente.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.put("error", "Error al eliminar usuario: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
}
