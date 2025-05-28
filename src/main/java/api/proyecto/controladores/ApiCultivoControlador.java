package api.proyecto.controladores;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api.proyecto.modelos.CultivoModelo;
import api.proyecto.modelos.NotificacionModelo;
import api.proyecto.modelos.ParcelaModelo;
import api.proyecto.repositorios.CultivoRepositorioApi;
import api.proyecto.repositorios.NotificacionRepositorioApi;
import api.proyecto.repositorios.ParcelaRepositorioApi;
import api.proyecto.servicio.CultivoServicioApi;
import api.proyectoAgricola.dto.CultivoDto;
import api.proyectoAgricola.dto.ParcelaDto;

/**
 * Controlador REST para gestionar cultivos.
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
@RestController
@RequestMapping("/api/cultivo")
public class ApiCultivoControlador {

	@Autowired
	private CultivoRepositorioApi cultivoRepositorio;

	@Autowired
	private ParcelaRepositorioApi parcelaRepositorio;

	@Autowired
	private CultivoServicioApi cultivoServicioApi;

	@Autowired
	private NotificacionRepositorioApi notificacionRepositorioApi;

	/**
	 * 1. Obtiene todos los cultivos del usuario.
	 * 
	 * @param id ID del usuario
	 * @return Lista de cultivos en formato DTO
	 */
	@GetMapping("/usuario/{id}")
	public ResponseEntity<List<CultivoDto>> obtenerCultivosPorUsuario(@PathVariable Long id) {
		List<CultivoDto> cultivos = cultivoServicioApi.obtenerCultivosPorUsuario(id);
		return ResponseEntity.ok(cultivos);
	}

	/**
	 * 2. Devuelve los datos de un cultivo específico.
	 * 
	 * @param id ID del cultivo
	 * @return Objeto DTO del cultivo o error si no existe
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerCultivoPorId(@PathVariable Long id) {
		try {
			CultivoModelo cultivo = cultivoServicioApi.buscarPorId(id);
			if (cultivo == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cultivo no encontrado."));
			}

			CultivoDto dto = new CultivoDto();
			dto.setCultivoId(cultivo.getCultivoId());
			dto.setNombre(cultivo.getNombre());
			dto.setCantidad(cultivo.getCantidad());
			dto.setFechaSiembra(cultivo.getFechaSiembra());
			dto.setDescripcion(cultivo.getDescripcion());

			if (cultivo.getParcelaId() != null) {
				ParcelaDto parcelaDto = new ParcelaDto();
				parcelaDto.setParcelaId(cultivo.getParcelaId().getParcelaId());
				dto.setParcelaId(parcelaDto);
			}

			return ResponseEntity.ok(dto);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error al obtener cultivo por ID"));
		}
	}

	/**
	 * 3. Elimina un cultivo si existe.
	 * 
	 * @param id ID del cultivo
	 * @return Mensaje indicando éxito o error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Map<String, String>> eliminarCultivo(@PathVariable Long id) {
		Map<String, String> respuesta = new HashMap<>();
		try {
			boolean eliminado = cultivoServicioApi.eliminarCultivo(id);
			if (!eliminado) {
				respuesta.put("error", "Cultivo no encontrado.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}
			respuesta.put("mensaje", "Cultivo eliminado correctamente.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.put("error", "Error al eliminar el cultivo.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	/**
	 * 4. Crea un nuevo cultivo con su parcela.
	 * Si es el primer cultivo del usuario, genera una notificación.
	 * 
	 * @param cultivo Objeto CultivoModelo recibido desde el cliente
	 * @return Mensaje de éxito o error con estado correspondiente
	 */
	@PostMapping("/crear")
	public ResponseEntity<Map<String, String>> crearCultivo(@RequestBody CultivoModelo cultivo) {
		Map<String, String> respuesta = new HashMap<>();

		try {
			if (cultivo.getParcelaId() == null || cultivo.getParcelaId().getParcelaId() == null) {
				respuesta.put("error", "La parcela asociada es obligatoria.");
				return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
			}

			Long parcelaId = cultivo.getParcelaId().getParcelaId();

			ParcelaModelo parcela = parcelaRepositorio.findById(parcelaId)
					.orElseThrow(() -> new RuntimeException("Parcela no encontrada con ID: " + parcelaId));

			cultivo.setParcelaId(parcela);

			CultivoModelo cultivoGuardado = cultivoRepositorio.save(cultivo);

			// 5. Si es el primer cultivo del usuario, se genera notificación
			Long usuarioId = parcela.getUsuarioId().getUsuarioId();
			if (notificacionRepositorioApi.countByUsuarioId(usuarioId) == 0) {
				NotificacionModelo noti = new NotificacionModelo();
				noti.setCultivoId(cultivoGuardado);
				noti.setMensaje("¡Has creado tu primer cultivo: " + cultivo.getNombre() + "!");
				noti.setCantidad(cultivoGuardado.getCantidad());
				noti.setFechaMensaje(LocalDateTime.now());
				notificacionRepositorioApi.save(noti);
			}

			respuesta.put("mensaje", "Cultivo creado correctamente.");
			return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			respuesta.put("error", "Error al crear el cultivo: " + e.getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 6. Actualiza un cultivo existente.
	 * 
	 * @param id  ID del cultivo
	 * @param dto DTO con los nuevos datos del cultivo
	 * @return Mensaje de éxito o error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<Map<String, String>> actualizarCultivo(@PathVariable Long id, @RequestBody CultivoDto dto) {
		Map<String, String> respuesta = new HashMap<>();
		try {
			CultivoModelo cultivo = cultivoServicioApi.buscarPorId(id);
			if (cultivo == null) {
				respuesta.put("error", "Cultivo no encontrado.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}

			cultivo.setNombre(dto.getNombre());
			cultivo.setCantidad(dto.getCantidad());
			cultivo.setDescripcion(dto.getDescripcion());
			cultivo.setFechaSiembra(dto.getFechaSiembra());

			if (dto.getParcelaId() != null) {
				ParcelaModelo parcela = parcelaRepositorio.findById(dto.getParcelaId().getParcelaId())
						.orElseThrow(() -> new RuntimeException("Parcela no encontrada."));
				cultivo.setParcelaId(parcela);
			}

			cultivoRepositorio.save(cultivo);
			respuesta.put("mensaje", "Cultivo actualizado correctamente.");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.put("error", "Error al actualizar el cultivo.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
}
