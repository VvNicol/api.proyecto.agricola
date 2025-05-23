package api.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.ParcelaModelo;

@Repository
public interface ParcelaRepositorioApi extends JpaRepository<ParcelaModelo, Long> {

	List<ParcelaModelo> findByUsuarioId_UsuarioId(Long usuarioId);

}
