package api.proyecto.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.proyecto.modelos.CodigoModelo;
import api.proyecto.repositorios.CodigoRepositorioApi;

@Service
public class CodigoServicioApi {
	
	
	@Autowired
    private CodigoRepositorioApi codigoRepositorioApi;

    public void guardar(CodigoModelo codigo) {
        codigoRepositorioApi.save(codigo);
    }
}
