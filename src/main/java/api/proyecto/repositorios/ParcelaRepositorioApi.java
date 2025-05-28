package api.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.ParcelaModelo;

/**
 * Repositorio para la gestión de parcelas en la base de datos.
 * 
 * @author nrojlla
 * @date 25/02/2025
 */
@Repository
public interface ParcelaRepositorioApi extends JpaRepository<ParcelaModelo, Long> {

	/**
	 * 1. Busca todas las parcelas asociadas a un usuario por su ID. 2. Accede
	 * mediante la relación: parcela → usuarioId → usuarioId.
	 * 
	 * @param usuarioId ID del usuario propietario de las parcelas
	 * @return Lista de parcelas del usuario
	 */
	List<ParcelaModelo> findByUsuarioId_UsuarioId(Long usuarioId);

}
