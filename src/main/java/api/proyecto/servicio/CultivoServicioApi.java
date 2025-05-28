package api.proyecto.servicio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.proyecto.modelos.CultivoModelo;
import api.proyecto.repositorios.CultivoRepositorioApi;
import api.proyecto.repositorios.NotificacionRepositorioApi;
import api.proyectoAgricola.dto.CultivoDto;
import api.proyectoAgricola.dto.ParcelaDto;
import api.proyectoAgricola.dto.UsuarioDto;
import jakarta.transaction.Transactional;

/**
 * Servicio que implementa CultivoInterfazApi
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
@Service
public class CultivoServicioApi implements CultivoInterfazApi {

	@Autowired
	private CultivoRepositorioApi cultivoRepositorio;

	@Autowired
	private NotificacionRepositorioApi notificacionRepositorioApi;

	public List<CultivoDto> obtenerCultivosPorUsuario(Long idUsuario) {
		List<CultivoModelo> cultivos = cultivoRepositorio.buscarCultivosPorUsuario(idUsuario);

		return cultivos.stream().map(modelo -> {
			ParcelaDto parcelaDto = new ParcelaDto();

			// Asignar datos del usuario si existen
			if (modelo.getParcelaId().getUsuarioId() != null) {
				UsuarioDto usuarioDto = new UsuarioDto();
				usuarioDto.setUsuarioId(modelo.getParcelaId().getUsuarioId().getUsuarioId());
				parcelaDto.setUsuarioId(usuarioDto);
			}

			parcelaDto.setParcelaId(modelo.getParcelaId().getParcelaId());
			parcelaDto.setNombre(modelo.getParcelaId().getNombre());
			parcelaDto.setDescripcion(modelo.getParcelaId().getDescripcion());
			parcelaDto.setFechaRegistro(modelo.getParcelaId().getFechaRegistro());

			return new CultivoDto(modelo.getCultivoId(), parcelaDto, modelo.getNombre(), modelo.getDescripcion(),
					modelo.getCantidad(), modelo.getFechaSiembra(), modelo.getFechaRegistro());
		}).collect(Collectors.toList());

	}

	@Transactional
	public boolean eliminarCultivo(Long id) {
	    Optional<CultivoModelo> cultivoOpt = cultivoRepositorio.findById(id);

	    if (cultivoOpt.isPresent()) {
	        CultivoModelo cultivo = cultivoOpt.get();
	        notificacionRepositorioApi.deleteByCultivoId(cultivo); // Borrar notificaciones primero
	        cultivoRepositorio.delete(cultivo);                    // Luego el cultivo
	        return true;
	    }

	    return false;
	}

	public CultivoModelo buscarPorId(Long id) {
		Optional<CultivoModelo> resultado = cultivoRepositorio.findById(id);
		return resultado.orElse(null);
	}

}
