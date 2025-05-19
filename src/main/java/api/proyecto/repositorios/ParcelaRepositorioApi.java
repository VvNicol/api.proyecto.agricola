package api.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import api.proyecto.modelos.ParcelaModelo;

public interface ParcelaRepositorioApi extends JpaRepository<ParcelaModelo, Long> {

}
