package com.store.open.openStore.config;

import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class Webconfig {

	@Bean
	public WebMvcConfigurer corsMappingConfigurer() {
	   return new WebMvcConfigurer() {
	       @Override
	       public void addCorsMappings(CorsRegistry registry) {
	           
	           registry.addMapping("/**")
	             .allowedOrigins("http://localhost:4200")
	             
	             .allowCredentials(true);
	       }
	   };
	}
    
}