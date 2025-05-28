package api.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.CultivoModelo;
import api.proyecto.modelos.ParcelaModelo;

/**
 * Repositorio para acceder y gestionar cultivos en la base de datos.
 * 
 * @author nrojlla
 * @date 25/02/2025
 */
@Repository
public interface CultivoRepositorioApi extends JpaRepository<CultivoModelo, Long> {

	/**
	 * Busca todos los cultivos asociados a un usuario específico. Se accede al ID
	 * del usuario a través de la relación: cultivo → parcela → usuario.
	 * 
	 * @param usuarioId ID del usuario
	 * @return Lista de cultivos del usuario
	 */
	@Query("SELECT c FROM CultivoModelo c WHERE c.parcelaId.usuarioId.usuarioId = :usuarioId")
	List<CultivoModelo> buscarCultivosPorUsuario(@Param("usuarioId") Long usuarioId);

	/**
	 * Busca cultivos que pertenecen a una parcela específica.
	 * 
	 * @param parcela Objeto parcela
	 * @return Lista de cultivos asociados a la parcela
	 */
	List<CultivoModelo> findByParcelaId(ParcelaModelo parcela);

}
