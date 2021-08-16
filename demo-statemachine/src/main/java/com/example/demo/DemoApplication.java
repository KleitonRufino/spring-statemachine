package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories(basePackageClasses = {OrdemRepository.class, JpaStateMachineRepository.class})
//@EnableJpaRepositories("org.springframework.statemachine.data.jpa")
//@EntityScan("org.springframework.statemachine.data.jpa")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
