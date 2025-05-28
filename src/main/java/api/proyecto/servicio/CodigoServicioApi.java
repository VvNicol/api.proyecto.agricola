package api.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.proyecto.modelos.CodigoModelo;
import api.proyecto.repositorios.CodigoRepositorioApi;

/**
 * Servicio que implementa CodigoInterfazApi.
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
@Service
public class CodigoServicioApi implements CodigoInterfazApi {

	@Autowired
	private CodigoRepositorioApi codigoRepositorioApi;

	public void guardar(CodigoModelo codigo) {
		codigoRepositorioApi.save(codigo);
	}
}
