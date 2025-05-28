package api.proyecto.modelos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 * Modelo que representa las notificaciones
 * 
 * @autor nrojlla 25022025
 */
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
	private LocalDateTime  fechaMensaje; // Fecha de envío del mensaje
	
	@Column(name = "estado_mensaje", nullable = false)
	private boolean estadoMensaje = false; // false = no leído, true = leído

	@Column(name = "fecha_lectura")
	private LocalDateTime fechaLectura; // Fecha y hora en que se leyó

	
	

	public NotificacionModelo() {
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
	public LocalDateTime getFechaMensaje() {
		return fechaMensaje;
	}




	/**
	 * @param fechaMensaje the fechaMensaje to set
	 */
	public void setFechaMensaje(LocalDateTime fechaMensaje) {
		this.fechaMensaje = fechaMensaje;
	}




	/**
	 * @return the estadoMensaje
	 */
	public boolean isEstadoMensaje() {
		return estadoMensaje;
	}




	/**
	 * @param estadoMensaje the estadoMensaje to set
	 */
	public void setEstadoMensaje(boolean estadoMensaje) {
		this.estadoMensaje = estadoMensaje;
	}




	/**
	 * @return the fechaLectura
	 */
	public LocalDateTime getFechaLectura() {
		return fechaLectura;
	}




	/**
	 * @param fechaLectura the fechaLectura to set
	 */
	public void setFechaLectura(LocalDateTime fechaLectura) {
		this.fechaLectura = fechaLectura;
	}



}