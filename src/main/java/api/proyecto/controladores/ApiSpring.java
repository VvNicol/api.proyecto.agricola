package api.proyecto.controladores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Clase principal que inicia la API con Spring Boot.
 * Excluye la configuración de seguridad predeterminada.
 * 
 * @autor nrojlla 25022025
 */
@SpringBootApplication(scanBasePackages = "api", exclude = SecurityAutoConfiguration.class )
@EntityScan(basePackages = "api.proyecto")
@EnableJpaRepositories(basePackages = "api.proyecto.repositorios")
public class ApiSpring {
	
	/**
	 * Método principal que inicia la API con Spring Boot.
	 * 
	 * @param args Argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiSpring.class, args);
	}

}
