package api.proyecto.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.proyecto.modelos.CultivoModelo;
import api.proyecto.modelos.ParcelaModelo;
import api.proyecto.repositorios.CultivoRepositorioApi;
import api.proyecto.repositorios.ParcelaRepositorioApi;
import api.proyecto.servicio.CultivoServicioApi;
import api.proyectoAgricola.dto.CultivoDto;

@RestController
@RequestMapping("/api/cultivo")
public class ApiCultivoControlador {

    @Autowired
    private CultivoRepositorioApi cultivoRepositorio;

    @Autowired
    private ParcelaRepositorioApi parcelaRepositorio;
    
    @Autowired
    private CultivoServicioApi cultivoServicioApi;
    
    @GetMapping("/usuario/{id}")
	public ResponseEntity<List<CultivoDto>> obtenerCultivosPorUsuario(@PathVariable Long id) {
	    List<CultivoDto> cultivos = cultivoServicioApi.obtenerCultivosPorUsuario(id);
	    return ResponseEntity.ok(cultivos);
	}

    @PostMapping("/crear")
    public ResponseEntity<Map<String, String>> crearCultivo(@RequestBody CultivoModelo cultivo) {
        Map<String, String> respuesta = new HashMap<>();

        try {
            if (cultivo.getParcelaId() == null || cultivo.getParcelaId().getParcelaId() == null) {
                respuesta.put("error", "La parcela asociada es obligatoria.");
                return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
            }

            Long parcelaId = cultivo.getParcelaId().getParcelaId();

            ParcelaModelo parcela = parcelaRepositorio.findById(parcelaId)
                .orElseThrow(() -> new RuntimeException("Parcela no encontrada con ID: " + parcelaId));

            cultivo.setParcelaId(parcela);

            cultivoRepositorio.save(cultivo);

            respuesta.put("mensaje", "Cultivo creado correctamente.");
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.put("error", "Error al crear el cultivo: " + e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }
    
    
}
