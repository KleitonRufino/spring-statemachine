package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class DemoApplication {

	
	/**
	 * GET http://localhost:8080/offer
	 * GET http://localhost:8080/offer/executar
	 * GET http://localhost:8080/state-machine/{id}
	 * KAFKA docker-compose up -d
	 * ADMIN SERVER http://localhost:8080
	 * H2 CONSOLE http://localhost:8080/h2-console
	 * 
	 * */
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
