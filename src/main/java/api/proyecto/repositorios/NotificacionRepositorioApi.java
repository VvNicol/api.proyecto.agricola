package api.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import api.proyecto.modelos.NotificacionModelo;

public interface NotificacionRepositorioApi extends JpaRepository<NotificacionModelo, Long> {

}
