package api.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import api.proyecto.modelos.CodigoModelo;
import api.proyecto.modelos.UsuarioModelo;

public interface CodigoRepositorioApi extends  JpaRepository<CodigoModelo, Long> {

	void deleteByUsuario(UsuarioModelo usuario);

}
