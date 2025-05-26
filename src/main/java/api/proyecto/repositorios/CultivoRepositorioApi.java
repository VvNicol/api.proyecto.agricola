package api.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.CultivoModelo;
import api.proyecto.modelos.ParcelaModelo;

@Repository
public interface CultivoRepositorioApi extends JpaRepository<CultivoModelo, Long> {

	@Query("SELECT c FROM CultivoModelo c WHERE c.parcelaId.usuarioId.usuarioId = :usuarioId")
	List<CultivoModelo> buscarCultivosPorUsuario(@Param("usuarioId") Long usuarioId);

	List<CultivoModelo> findByParcelaId(ParcelaModelo parcela);


}
