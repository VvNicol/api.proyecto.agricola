package api.proyecto.controladores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "api", exclude = SecurityAutoConfiguration.class )
@EntityScan(basePackages = "api.proyecto")
@EnableJpaRepositories(basePackages = "api.proyecto.repositorios")
public class ApiSpring {
	public static void main(String[] args) {
		SpringApplication.run(ApiSpring.class, args);
	}

}
