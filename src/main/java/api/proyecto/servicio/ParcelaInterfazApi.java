package api.proyecto.servicio;

import java.util.List;

import api.proyectoAgricola.dto.ParcelaDto;

/**
 * Interfaz de servicio para operaciones relacionadas con ParcelaServicioApi.
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
public interface ParcelaInterfazApi {

	/**
	 * Obtiene todas las parcelas asociadas a un usuario.
	 * 
	 * @param usuarioId ID del usuario
	 * @return Lista de parcelas en formato DTO
	 */
	public List<ParcelaDto> obtenerParcelasPorUsuario(Long usuarioId);

}
