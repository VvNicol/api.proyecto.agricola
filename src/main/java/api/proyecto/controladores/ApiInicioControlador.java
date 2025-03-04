package api.proyecto.controladores;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

	/**
	 * Registra un nuevo usuario en el sistema.
	 * 
	 * @param usuario Objeto con los datos del usuario a registrar.
	 * @return Respuesta con mensaje de éxito o error.
	 */
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
			UsuarioModelo usuario = usuarioServicioApi.buscarPorToken(token);
			if (usuario == null) {
				respuesta.put("error", "Token no encontrado.");
				return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
			}

			if (usuario.getTokenExpiracionFecha() == null
					|| usuario.getTokenExpiracionFecha().isBefore(LocalDateTime.now())) {
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

	/**
	 * Actualiza el token de verificación de un usuario.
	 * 
	 * @param usuario Objeto con el correo del usuario y el nuevo token.
	 * @return Respuesta con mensaje de éxito o error.
	 */
	@PostMapping("/token-correo-actualizar")
	public ResponseEntity<Map<String, String>> actualizarToken(@RequestBody UsuarioModelo usuario) {
		Map<String, String> respuesta = new HashMap<>();
		try {

			UsuarioModelo usuarioExistente = usuarioServicioApi.buscarPorCorreo(usuario.getCorreo());
			if (usuarioExistente == null) {
				respuesta.put("error", "Correo no encontrado.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			usuarioExistente.setToken(usuario.getToken());
			usuarioExistente.setTokenExpiracionFecha(usuario.getTokenExpiracionFecha());
			usuarioServicioApi.actualizarUsuario(usuarioExistente);

			respuesta.put("mensaje", "Token actualizado correctamente.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
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

			//Se guarda el codigo despues de las verificaciones
			usuario.setCodigoRecuperacion(codigo);
			usuario.setCodigoExpiracionFecha(expiracion);
			usuarioServicioApi.actualizarUsuario(usuario);

			respuesta.put("mensaje", "Código de recuperación guardado correctamente.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
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

			if (Objects.isNull(usuario.getCodigoRecuperacion()) || usuario.getCodigoExpiracionFecha() == null) {
				respuesta.put("error", "No hay un código de recuperación activo.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}

			respuesta.put("codigo", String.valueOf(usuario.getCodigoRecuperacion()));
			respuesta.put("expiracion", usuario.getCodigoExpiracionFecha().toString());

			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
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

			int codigoRecuperacion = usuario.getCodigoRecuperacion();

			if (codigoIngresado != codigoRecuperacion) {
				respuesta.put("error", "Código incorrecto.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			if (usuario.getCodigoExpiracionFecha().isBefore(LocalDateTime.now())) {
				respuesta.put("error", "Código expirado.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}

			usuario.setCodigoVerificado(true);
			usuarioServicioApi.actualizarUsuario(usuario);

			respuesta.put("mensaje", "Código verificado correctamente.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
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

			// Desactivar el código de recuperación
			usuario.setCodigoRecuperacion(0);
			usuario.setCodigoVerificado(false);
			usuario.setCodigoExpiracionFecha(null);

			usuarioServicioApi.actualizarUsuario(usuario);

			respuesta.put("mensaje", "Contraseña cambiada con éxito.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			respuesta.put("error", "Error al cambiar la contraseña: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

}
