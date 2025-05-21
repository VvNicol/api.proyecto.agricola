package api.proyecto.modelos;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * 
 * Modelo que representa una parcela
 * 
 * @autor nrojlla 25022025
 */
@Entity
@Table(name = "parcelas", schema = "agrilog")
public class ParcelaModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parcela_id")
	private Long parcelaId; // Identificador de la parcela

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false) // Relación con UsuarioModelo (parcela pertenece a un usuario)
	private UsuarioModelo usuarioId; // Relación con UsuarioModelo

	@Column(nullable = false)
	private String nombre; // Nombre de la parcela

	@Column(nullable = true)
	private String descripcion; // Descripción de la parcela

	@Column(name = "fecha_registro", nullable = false)
	private LocalDateTime fechaRegistro; // Fecha de registro de la parcela

	@OneToMany(mappedBy = "parcelaId") // Relación de una parcela a muchos cultivos
	private List<CultivoModelo> cultivos; // Lista de cultivos asociados a esta parcela

	// Constructores
	public ParcelaModelo() {
	}

	// Constructor con parámetros
	public ParcelaModelo(Long usuarioId, String nombre, String descripcion, LocalDateTime fechaRegistro) {
		this.usuarioId = new UsuarioModelo(); // Aquí se asocia el usuario por medio de su ID
		this.usuarioId.setUsuarioId(usuarioId); // Se asigna el ID del usuario
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaRegistro = fechaRegistro;
	}

	// Getters y Setters

	/**
	 * @return the parcelaId
	 */
	public Long getParcelaId() {
		return parcelaId;
	}

	/**
	 * @param parcelaId the parcelaId to set
	 */
	public void setParcelaId(Long parcelaId) {
		this.parcelaId = parcelaId;
	}

	/**
	 * @return the usuarioId
	 */
	public UsuarioModelo getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(UsuarioModelo usuarioId) {
		this.usuarioId = usuarioId;
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