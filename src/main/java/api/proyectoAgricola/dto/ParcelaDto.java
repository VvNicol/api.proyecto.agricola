package api.proyectoAgricola.dto;

import java.time.LocalDateTime;

/**
 * DTO que representa una parcela para la comunicación entre el DWP y la API.
 * 
 * @author nrojlla
 */
public class ParcelaDto {

    private Long parcelaId;
    private UsuarioDto usuarioId;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaRegistro;

    // Constructor vacío
    public ParcelaDto() {
    }

    // Constructor sin ID de parcela
    public ParcelaDto(UsuarioDto usuarioId, String nombre, String descripcion, LocalDateTime fechaRegistro) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
    }

    public ParcelaDto(Long parcelaId, UsuarioDto usuarioId, String nombre, String descripcion,
                      LocalDateTime fechaRegistro) {
        this.parcelaId = parcelaId;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters

    public Long getParcelaId() {
        return parcelaId;
    }

    public void setParcelaId(Long parcelaId) {
        this.parcelaId = parcelaId;
    }

    public UsuarioDto getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UsuarioDto usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
