package api.proyectoAgricola.dto;

public class CodigoDto {

	private Long usuario;
	private Long codigoId;
	private int codigoRecuperacion = 0;
	private String codigoExpiracionFecha;
	private boolean codigoVerificado;
	
	/**
	 * Constructor vacio
	 */
	public CodigoDto() {
		super();
	}
	
	/**
	 * @return the codigoId
	 */
	public Long getCodigoId() {
		return codigoId;
	}
	/**
	 * @param codigoId the codigoId to set
	 */
	public void setCodigoId(Long codigoId) {
		this.codigoId = codigoId;
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
	public String getCodigoExpiracionFecha() {
		return codigoExpiracionFecha;
	}
	/**
	 * @param codigoExpiracionFecha the codigoExpiracionFecha to set
	 */
	public void setCodigoExpiracionFecha(String codigoExpiracionFecha) {
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

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}
	
	

}
