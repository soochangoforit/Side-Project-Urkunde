package sideproject.urkunde;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UrkundeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrkundeApplication.class, args);
	}

}
