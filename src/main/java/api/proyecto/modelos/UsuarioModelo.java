package api.proyecto.modelos;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


/**
 * 
 * Modelo que representa un usuario
 * 
 * @autor nrojlla 25022025
 */
@Entity
@Table(name = "usuarios", schema = "agrilog")
public class UsuarioModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long usuarioId;

	@NotBlank(message = "El nombre completo no puede estar vacío")
	@Column(name = "nombre_completo", nullable = false, length = 100)
	private String nombreCompleto;

	@NotBlank(message = "El teléfono no puede estar vacío")
	@Column(name = "telefono", nullable = false, length = 15)
	private String telefono;

	@Email(message = "Correo electrónico no válido")
	@NotBlank(message = "El correo no puede estar vacío")
	@Column(name = "correo", nullable = false, unique = true, length = 100)
	private String correo;

	@Column(name = "codigo_recuperacion")
	private int codigoRecuperacion = 0;

	@Column(name = "codigo_fecha_expiracion")
	private LocalDateTime codigoExpiracionFecha;

	@Column(name = "rol")
	private String rol;

	@Column(name = "contrasenia")
	private String contrasenia;

	@Column(name = "token", length = 255)
	private String token;

	@Column(name = "token_expiracion_fecha")
	private LocalDateTime tokenExpiracionFecha;

	@Column(name = "correo_validado", columnDefinition = "boolean default false")
	private boolean correoValidado = false;

	@Lob
	@Column(name = "imagen")
	private byte[] imagen;

	@Column(name = "fecha_registro")
	private LocalDateTime fechaRegistro;

	@Column(name = "autenticacion_externa", nullable = false, columnDefinition = "boolean default false")
	private Boolean autenticacionExterna = false;

	@Column(name = "proveedor", length = 50)
	private String proveedor = "ninguno";

	@Column(name = "externo_id", length = 100)
	private String externoId = "ninguno";

	// Relación de uno a muchos con ParcelaModelo
	@OneToMany(mappedBy = "usuarioId") // Relación de un usuario a muchas parcelas
	private List<ParcelaModelo> parcelas; // Lista de parcelas asociadas a este usuario

	// Constructores

	public UsuarioModelo() {

	}

	/**
	 * Getters y Setters
	 */

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
	 * @return the codigoRecuperacion
	 */
	public int getCodigoRecuperacion() {
		return codigoRecuperacion;
	}

	/**
	 * @param codigoRecuperacion the codigoRecuperacion to set
	 */
	public void setCodigoRecuperacion(int codigoRecuperacion) {
		this.codigoRecuperacion = codigoRecuperacion;
	}

	/**
	 * @return the codigoExpiracionFecha
	 */
	public LocalDateTime getCodigoExpiracionFecha() {
		return codigoExpiracionFecha;
	}

	/**
	 * @param codigoExpiracionFecha the codigoExpiracionFecha to set
	 */
	public void setCodigoExpiracionFecha(LocalDateTime codigoExpiracionFecha) {
		this.codigoExpiracionFecha = codigoExpiracionFecha;
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the tokenExpiracionFecha
	 */
	public LocalDateTime getTokenExpiracionFecha() {
		return tokenExpiracionFecha;
	}

	/**
	 * @param tokenExpiracionFecha the tokenExpiracionFecha to set
	 */
	public void setTokenExpiracionFecha(LocalDateTime tokenExpiracionFecha) {
		this.tokenExpiracionFecha = tokenExpiracionFecha;
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
	 * @return the autenticacionExterna
	 */
	public Boolean getAutenticacionExterna() {
		return autenticacionExterna;
	}

	/**
	 * @param autenticacionExterna the autenticacionExterna to set
	 */
	public void setAutenticacionExterna(Boolean autenticacionExterna) {
		this.autenticacionExterna = autenticacionExterna;
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

	/**
	 * @return the proveedor
	 */
	public String getProveedor() {
		return proveedor;
	}

	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	/**
	 * @return the externoId
	 */
	public String getExternoId() {
		return externoId;
	}

	/**
	 * @param externoId the externoId to set
	 */
	public void setExternoId(String externoId) {
		this.externoId = externoId;
	}

	/**
	 * @return the parcelas
	 */
	public List<ParcelaModelo> getParcelas() {
		return parcelas;
	}

	/**
	 * @param parcelas the parcelas to set
	 */
	public void setParcelas(List<ParcelaModelo> parcelas) {
		this.parcelas = parcelas;
	}
}
