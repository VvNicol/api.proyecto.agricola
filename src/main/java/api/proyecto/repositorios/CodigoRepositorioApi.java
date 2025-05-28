package api.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.CodigoModelo;
import api.proyecto.modelos.UsuarioModelo;

/**
 * Repositorio para gestionar los códigos de recuperación en la base de datos.
 * @autor nrojlla 25022025
 */
@Repository
public interface CodigoRepositorioApi extends  JpaRepository<CodigoModelo, Long> {

	/**
	 * Elimina el código de recuperación asociado a un usuario.
	 * 
	 * @param usuario Usuario al que pertenece el código
	 */
	void deleteByUsuario(UsuarioModelo usuario);

}
