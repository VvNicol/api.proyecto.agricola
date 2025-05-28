package api.proyecto.servicio;

import api.proyecto.modelos.CodigoModelo;

/**
 * Interfaz para la gestión de códigos de recuperación en la API.
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
public interface CodigoInterfazApi {

	/**
	 * Guarda un nuevo código de recuperación.
	 * 
	 * @param codigo Objeto con los datos del código a guardar
	 */
	public void guardar(CodigoModelo codigo);
}
