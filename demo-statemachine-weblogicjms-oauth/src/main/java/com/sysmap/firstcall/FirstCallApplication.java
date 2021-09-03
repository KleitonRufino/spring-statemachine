package com.sysmap.firstcall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableFeignClients
@EnableAdminServer
@SpringBootApplication
public class FirstCallApplication  {

	/**
	 * ADMIN SERVER http://localhost:8080 
	 * H2 CONSOLE http://localhost:8080/h2-console
	 * GET http://localhost:8080/offers/executar
	 * GET http://localhost:8080/offers/msisdn/{id}
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(FirstCallApplication.class, args);
	}

}
