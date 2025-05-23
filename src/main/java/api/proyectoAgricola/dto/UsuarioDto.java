package api.proyectoAgricola.dto;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para representar los datos de un usuario.
 * 
 * @autor nrojlla 25022025
 */
public class UsuarioDto {

	private Long usuarioId; // Identificador único del usuario

	private String nombreCompleto; // Nombre completo del usuario

	private String telefono; // Número de teléfono del usuario

	private String correo; // Correo electrónico del usuario

	private String rol; // Rol del usuario (ej. ADMIN, USUARIO)

	private String contrasenia; // Contraseña encriptada del usuario

	private byte[] imagen; // Imagen de perfil del usuario en formato binario

	private LocalDateTime fechaRegistro; // Fecha y hora en que se registró el usuario

	private boolean correoValidado = false; // Indica si el correo del usuario ha sido validado


	/**
	 * Constructor vacío.
	 */
	public UsuarioDto() {
		super();
	}

	/**
	 * @param usuarioId
	 * @param correo
	 * @param rol
	 */
	public UsuarioDto(String correo, String rol, boolean esRol) {
		this.correo = correo;
		this.rol = rol;
	}

	/**
	 * Constructor con parámetros básicos para inicio de sesión.
	 * 
	 * @param correo      Correo del usuario.
	 * @param contrasenia Contraseña del usuario.
	 */
	public UsuarioDto(String correo, String contrasenia) {
		super();
		this.correo = correo;
		this.contrasenia = contrasenia;
	}

	/**
	 * @return the usuarioId
	 */
	public Long getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * @return the imagen
	 */
	public byte[] getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
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

	/**
	 * @return the correoValidado
	 */
	public boolean isCorreoValidado() {
		return correoValidado;
	}

	/**
	 * @param correoValidado the correoValidado to set
	 */
	public void setCorreoValidado(boolean correoValidado) {
		this.correoValidado = correoValidado;
	}

	// Métodos Getter y Setter
	


}
