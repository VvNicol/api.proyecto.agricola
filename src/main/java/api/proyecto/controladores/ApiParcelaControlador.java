package api.proyecto.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.proyecto.modelos.ParcelaModelo;
import api.proyecto.modelos.UsuarioModelo;
import api.proyecto.repositorios.ParcelaRepositorioApi;
import api.proyecto.repositorios.UsuarioRepositorioApi;
import api.proyecto.servicio.ParcelaServicioApi;
import api.proyectoAgricola.dto.ParcelaDto;

/**
 * Controlador API para gestionar parcelas.
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
@RestController
@RequestMapping("/api/parcela")
public class ApiParcelaControlador {

	@Autowired
	private ParcelaRepositorioApi parcelaRepositorioApi;

	@Autowired
	private UsuarioRepositorioApi usuarioRepositorioApi;
	
	@Autowired
	private ParcelaServicioApi parcelaServicioApi;
	
	/**
	 * 1. Obtiene las parcelas asociadas a un usuario específico.
	 * 
	 * @param id ID del usuario
	 * @return Lista de parcelas en formato DTO
	 */
	@GetMapping("/usuario/{id}")
	public ResponseEntity<List<ParcelaDto>> obtenerParcelasPorUsuario(@PathVariable Long id) {
	    List<ParcelaDto> parcelas = parcelaServicioApi.obtenerParcelasPorUsuario(id);
	    return ResponseEntity.ok(parcelas);
	}

	/**
	 * 2. Crea una nueva parcela a partir de los datos enviados en el cuerpo de la solicitud.
	 * 
	 * @param dto Objeto con los datos de la parcela
	 * @return Mapa con mensaje de éxito o error y el ID generado
	 */
	@PostMapping("/crear")
	public ResponseEntity<Map<String, Object>> crearParcela(@RequestBody ParcelaDto dto) {
		Map<String, Object> respuesta = new HashMap<>();

		try {
			// 3. Validación del ID de usuario
			if (dto.getUsuarioId() == null) {
				throw new Exception("El ID de usuario es obligatorio.");
			}

			// 4. Verificación del usuario existente
			UsuarioModelo usuario = usuarioRepositorioApi
				    .findById(dto.getUsuarioId().getUsuarioId()) 
				    .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + dto.getUsuarioId().getUsuarioId()));

			// 5. Se crea y guarda una nueva parcela en la base de datos
			ParcelaModelo parcela = new ParcelaModelo();
			parcela.setNombre(dto.getNombre());
			parcela.setDescripcion(dto.getDescripcion());
			parcela.setFechaRegistro(dto.getFechaRegistro());
			parcela.setUsuarioId(usuario);

			parcelaRepositorioApi.save(parcela);

			// 6. Se devuelve mensaje y ID de la parcela creada
			respuesta.put("mensaje", "Parcela creada correctamente");
			respuesta.put("parcelaId", parcela.getParcelaId());
			return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			// 7. Manejo de errores
			Map<String, Object> error = new HashMap<>();
			error.put("error", "No se pudo crear la parcela: " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}
}
