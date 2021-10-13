package br.com.artur.offnance;

import br.com.artur.offnance.config.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(ConfigProperties.class)
@SpringBootApplication
public class OffnanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OffnanceApplication.class, args);
	}

}
