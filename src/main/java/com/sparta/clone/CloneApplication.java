package com.sparta.clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloneApplication.class, args);
	}

}
