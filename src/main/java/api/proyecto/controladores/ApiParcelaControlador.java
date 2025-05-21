package api.proyecto.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.proyecto.modelos.ParcelaModelo;
import api.proyecto.modelos.UsuarioModelo;
import api.proyecto.repositorios.ParcelaRepositorioApi;
import api.proyecto.repositorios.UsuarioRepositorioApi;


@RestController
@RequestMapping("/api/parcela")
public class ApiParcelaControlador {

	@Autowired
	private ParcelaRepositorioApi parcelaRepositorioApi;

	@Autowired
	private UsuarioRepositorioApi usuarioRepositorioApi;

	@PostMapping("/crear")
	public ResponseEntity<Map<String, String>> crearParcela(@RequestBody ParcelaModelo parcela) {
	    Map<String, String> respuesta = new HashMap<>();

	    System.out.println("API recibió:");
	    System.out.println("usuarioId: " + (parcela.getUsuarioId() != null ? parcela.getUsuarioId().getUsuarioId() : "null"));
	    System.out.println("nombre: " + parcela.getNombre());
	    System.out.println("descripcion: " + parcela.getDescripcion());
	    System.out.println("fechaRegistro: " + parcela.getFechaRegistro());

	    try {
	        Long idUsuario = parcela.getUsuarioId() != null ? parcela.getUsuarioId().getUsuarioId() : null;

	        if (idUsuario == null) {
	            throw new Exception("El ID de usuario es obligatorio.");
	        }

	        UsuarioModelo usuario = usuarioRepositorioApi.findById(idUsuario)
	                .orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + idUsuario));

	        parcela.setUsuarioId(usuario); // Relacionar el modelo completo

	        parcelaRepositorioApi.save(parcela);

	        respuesta.put("mensaje", "Parcela creada correctamente");
	        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

	    } catch (Exception e) {
	        e.printStackTrace();
	        respuesta.put("error", "No se pudo crear la parcela: " + e.getMessage());
	        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
	    }
	}


}
