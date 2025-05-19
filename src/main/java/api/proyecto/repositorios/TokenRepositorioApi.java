package api.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.proyecto.modelos.TokenModelo;
import api.proyecto.modelos.UsuarioModelo;

@Repository
public interface TokenRepositorioApi extends  JpaRepository<TokenModelo, Long>{

	TokenModelo findByToken(String token); // Busca un usuario por su token

	TokenModelo findByUsuario(UsuarioModelo usuario);
	

	}
