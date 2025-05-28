package api.proyectoAgricola.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO que representa un cultivo para transferencia entre la API y el DWP.
 * 
 * @autor nrojlla 25022025
 */
public class CultivoDto {

	private Long cultivoId;
	private ParcelaDto parcelaId;
	private String nombre;
	private String descripcion;
	private Integer cantidad;
	private LocalDate fechaSiembra;
	private LocalDateTime fechaRegistro;

	// Constructores

	public CultivoDto() {
	}

	public CultivoDto(Long cultivoId, ParcelaDto parcelaId, String nombre, String descripcion, Integer cantidad,
			LocalDate fechaSiembra, LocalDateTime fechaRegistro) {
		this.cultivoId = cultivoId;
		this.parcelaId = parcelaId;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.fechaSiembra = fechaSiembra;
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the cultivoId
	 */
	public Long getCultivoId() {
		return cultivoId;
	}

	/**
	 * @param cultivoId the cultivoId to set
	 */
	public void setCultivoId(Long cultivoId) {
		this.cultivoId = cultivoId;
	}

	/**
	 * @return the parcelaId
	 */
	public ParcelaDto getParcelaId() {
		return parcelaId;
	}

	/**
	 * @param parcelaId the parcelaId to set
	 */
	public void setParcelaId(ParcelaDto parcelaId) {
		this.parcelaId = parcelaId;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the fechaSiembra
	 */
	public LocalDate getFechaSiembra() {
		return fechaSiembra;
	}

	/**
	 * @param fechaSiembra the fechaSiembra to set
	 */
	public void setFechaSiembra(LocalDate fechaSiembra) {
		this.fechaSiembra = fechaSiembra;
	}

	/**
	 * @return the fechaRegistro
	 */
	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
