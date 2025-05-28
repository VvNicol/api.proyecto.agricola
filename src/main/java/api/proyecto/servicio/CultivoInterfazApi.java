package api.proyecto.servicio;

import java.util.List;

import api.proyecto.modelos.CultivoModelo;
import api.proyectoAgricola.dto.CultivoDto;

/**
 * Interfaz de servicio para operaciones de cultivos en la API.
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
public interface CultivoInterfazApi {

	/**
	 * Obtiene los cultivos asociados a un usuario.
	 * 
	 * @param idUsuario ID del usuario
	 * @return Lista de cultivos en formato DTO
	 */
	public List<CultivoDto> obtenerCultivosPorUsuario(Long idUsuario);

	/**
	 * Elimina un cultivo por su ID.
	 * 
	 * @param id ID del cultivo a eliminar
	 * @return true si fue eliminado correctamente, false en caso contrario
	 */
	public boolean eliminarCultivo(Long id);

	/**
	 * Busca un cultivo por su ID.
	 * 
	 * @param id ID del cultivo
	 * @return El modelo del cultivo si existe, null si no
	 */
	public CultivoModelo buscarPorId(Long id);
}
