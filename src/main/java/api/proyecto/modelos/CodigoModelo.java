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

@Entity
@Table(name = "codigos", schema = "agrilog")
public class CodigoModelo {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_recuperacion")
    private int codigoRecuperacion;

    @Column(name = "codigo_expiracion_fecha")
    private LocalDateTime codigoExpiracionFecha;

    @Column(name = "codigo_verificado", columnDefinition = "boolean default false")
    private boolean codigoVerificado;

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
	 * @return the codigoVerificado
	 */
	public boolean isCodigoVerificado() {
		return codigoVerificado;
	}

	/**
	 * @param codigoVerificado the codigoVerificado to set
	 */
	public void setCodigoVerificado(boolean codigoVerificado) {
		this.codigoVerificado = codigoVerificado;
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
