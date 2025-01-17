package com.store.open.openStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan("com.store.open.openStore")
public class OpenStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenStoreApplication.class, args);
	}


}
