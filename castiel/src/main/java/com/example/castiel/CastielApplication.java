package com.example.castiel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CastielApplication {

	public static void main(String[] args) {
		SpringApplication.run(CastielApplication.class, args);
	}

}
