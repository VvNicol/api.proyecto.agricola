package api.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.CultivoModelo;
import api.proyecto.modelos.NotificacionModelo;

/**
 * Repositorio para la gestión de notificaciones en la base de datos.
 * 
 * @author nrojlla
 * @date 25/02/2025
 */
@Repository
public interface NotificacionRepositorioApi extends JpaRepository<NotificacionModelo, Long> {

	/**
	 * 1. Obtiene todas las notificaciones asociadas a un usuario. 2. Se accede a
	 * través del cultivo → parcela → usuario.
	 * 
	 * @param usuarioId ID del usuario
	 * @return Lista de notificaciones del usuario
	 */
	@Query("SELECT n FROM NotificacionModelo n WHERE n.cultivoId.parcelaId.usuarioId.usuarioId = :usuarioId")
	List<NotificacionModelo> obtenerPorUsuario(@Param("usuarioId") Long usuarioId);

	/**
	 * 3. Cuenta cuántas notificaciones tiene un usuario. 4. Útil para verificar si
	 * hay nuevas notificaciones.
	 * 
	 * @param usuarioId ID del usuario
	 * @return Número total de notificaciones
	 */
	@Query("SELECT COUNT(n) FROM NotificacionModelo n WHERE n.cultivoId.parcelaId.usuarioId.usuarioId = :usuarioId")
	int countByUsuarioId(@Param("usuarioId") Long usuarioId);

	/**
	 * 5. Elimina todas las notificaciones asociadas a un cultivo.
	 * 
	 * @param cultivo Cultivo cuyas notificaciones se eliminarán
	 */
	void deleteByCultivoId(CultivoModelo cultivo);

}
