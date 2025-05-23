package api.proyecto.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.proyecto.modelos.ParcelaModelo;
import api.proyecto.repositorios.ParcelaRepositorioApi;
import api.proyectoAgricola.dto.ParcelaDto;
import api.proyectoAgricola.dto.UsuarioDto;

@Service
public class ParcelaServicioApi {

    @Autowired
    private ParcelaRepositorioApi parcelaRepositorio;

    public List<ParcelaDto> obtenerParcelasPorUsuario(Long usuarioId) {
        List<ParcelaModelo> parcelas = parcelaRepositorio.findByUsuarioId_UsuarioId(usuarioId);

        return parcelas.stream().map(parcela -> {
            ParcelaDto dto = new ParcelaDto();
            dto.setParcelaId(parcela.getParcelaId());

            // Creamos el UsuarioDto y le asignamos el ID
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setUsuarioId(parcela.getUsuarioId().getUsuarioId());

            dto.setUsuarioId(usuarioDto); // Le pasamos el objeto UsuarioDto
            dto.setNombre(parcela.getNombre());
            dto.setDescripcion(parcela.getDescripcion());
            dto.setFechaRegistro(parcela.getFechaRegistro());

            return dto;
        }).collect(Collectors.toList());
    }
}
