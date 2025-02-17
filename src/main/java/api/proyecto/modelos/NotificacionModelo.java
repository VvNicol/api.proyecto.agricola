package api.proyecto.modelos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notificaciones", schema = "agrilog")
public class NotificacionModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notificacion_id")
	private Long notificacionId; // Identificador de la notificación

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cultivo_id", nullable = false) // Relación con CultivoModelo
	private CultivoModelo cultivoId; // Relación con CultivoModelo

	@Column(nullable = false, length = 255)
	private String mensaje; // Mensaje de la notificación

	@Column(nullable = false)
	private int cantidad = 0; // Cantidad asociada a la notificación

	@Column(name = "fecha_mensaje", nullable = false)
	private LocalDate fechaMensaje; // Fecha de envío del mensaje

	public NotificacionModelo() {
	}

	// Constructor con parámetros
	public NotificacionModelo(Long cultivoId, String mensaje, int cantidad, LocalDate fechaMensaje) {
		this.cultivoId = new CultivoModelo(); // Asociar cultivo por su ID
		this.cultivoId.setCultivoId(cultivoId); // Asignar el ID del cultivo
		this.mensaje = mensaje;
		this.cantidad = cantidad;
		this.fechaMensaje = fechaMensaje;
	}

	/**
	 * @return the notificacionId
	 */
	public Long getNotificacionId() {
		return notificacionId;
	}

	/**
	 * @param notificacionId the notificacionId to set
	 */
	public void setNotificacionId(Long notificacionId) {
		this.notificacionId = notificacionId;
	}

	/**
	 * @return the cultivoId
	 */
	public CultivoModelo getCultivoId() {
		return cultivoId;
	}

	/**
	 * @param cultivoId the cultivoId to set
	 */
	public void setCultivoId(CultivoModelo cultivoId) {
		this.cultivoId = cultivoId;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the fechaMensaje
	 */
	public LocalDate getFechaMensaje() {
		return fechaMensaje;
	}

	/**
	 * @param fechaMensaje the fechaMensaje to set
	 */
	public void setFechaMensaje(LocalDate fechaMensaje) {
		this.fechaMensaje = fechaMensaje;
	}

}