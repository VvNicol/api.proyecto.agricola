package api.proyecto.modelos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * 
 * Modelo que representa un Token
 * 
 * @autor nrojlla 25022025
 */
@Entity
@Table(name = "tokens", schema = "agrilog")
public class TokenModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", length = 255)
    private String token;

    @Column(name = "token_expiracion_fecha")
    private LocalDateTime tokenExpiracionFecha;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModelo usuario;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the usuario
	 */
	public UsuarioModelo getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioModelo usuario) {
		this.usuario = usuario;
	}

  
}
