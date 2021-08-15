package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@EnableAdminServer
@EntityScan(basePackages = "com.example.demo")
@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan(basePackages = { "com.example.demo" })
//@EnableJpaRepositories(basePackages = "com.example.demo")
//@EnableTransactionManagement
public class DemoApplication {

	/**
	 * 
	 * GET http://localhost:8080/ordem GET http://localhost:8080/ordem/executar
	 * 
	 * actuator http://localhost:8080/actuator
	 * 
	 * prometheus CMD prometheus.exe
	 * -–config.file=C:\Users\kleit\eclipse-workspace\git\spring-statemachine\demo-statemachine-persister\src\main\resources\prometheus.yml
	 * GET http://localhost:9090/
	 * 
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
