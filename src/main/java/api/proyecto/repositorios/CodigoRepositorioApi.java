package api.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.CodigoModelo;
import api.proyecto.modelos.UsuarioModelo;

@Repository
public interface CodigoRepositorioApi extends  JpaRepository<CodigoModelo, Long> {

	void deleteByUsuario(UsuarioModelo usuario);

}
