package api.proyecto.controladores;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.proyecto.modelos.NotificacionModelo;
import api.proyecto.repositorios.NotificacionRepositorioApi;
import api.proyectoAgricola.dto.NotificacionDto;
import jakarta.transaction.Transactional;

/**
 * Controlador API para la gestión de notificaciones.
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
@RestController
@RequestMapping("/api/notificacion")
public class ApiNotificacionControlador {

	@Autowired
	private NotificacionRepositorioApi notificacionRepositorioApi;

	/**
	 * 1. Devuelve todas las notificaciones asociadas al usuario.
	 * 
	 * @param id ID del usuario
	 * @return Lista de notificaciones en formato DTO
	 */
	@GetMapping("/usuario/{id}")
	public ResponseEntity<List<NotificacionDto>> obtenerPorUsuario(@PathVariable Long id) {
		List<NotificacionModelo> notificaciones = notificacionRepositorioApi.obtenerPorUsuario(id);

		// 2. Se transforma cada entidad en su DTO correspondiente
		List<NotificacionDto> dtoList = notificaciones.stream().map(n -> {
			NotificacionDto dto = new NotificacionDto();
			dto.setNotificacionId(n.getNotificacionId());
			dto.setMensaje(n.getMensaje());
			dto.setCantidad(n.getCantidad());
			dto.setFechaMensaje(n.getFechaMensaje());
			dto.setEstadoMensaje(n.isEstadoMensaje());
			return dto;
		}).toList();

		return ResponseEntity.ok(dtoList);
	}

	/**
	 * 3. Indica si el usuario tiene notificaciones sin leer.
	 * 
	 * @param id ID del usuario
	 * @return true si hay notificaciones nuevas, false si todas fueron leídas
	 */
	@GetMapping("/usuario/{id}/nuevas")
	public ResponseEntity<Boolean> tieneNotificacionesNuevas(@PathVariable Long id) {
		boolean hayNuevas = notificacionRepositorioApi.obtenerPorUsuario(id).stream()
				.anyMatch(n -> !n.isEstadoMensaje());

		return ResponseEntity.ok(hayNuevas);
	}

	/**
	 * 4. Marca una notificación como leída, actualizando su estado y fecha de lectura.
	 * 
	 * @param id ID de la notificación
	 * @return Respuesta indicando éxito o error
	 */
	@PostMapping("/marcar-leida/{id}")
	@Transactional
	public ResponseEntity<?> marcarLeida(@PathVariable Long id) {
	    try {
	        Optional<NotificacionModelo> opt = notificacionRepositorioApi.findById(id);

	        // 5. Si existe, se actualiza su estado a leída
	        if (opt.isPresent()) {
	            NotificacionModelo n = opt.get();
	            n.setEstadoMensaje(true);
	            n.setFechaLectura(LocalDateTime.now());
	            notificacionRepositorioApi.save(n);
	            return ResponseEntity.ok().build();
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificación no encontrada.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al marcar como leída.");
	    }
	}
}
