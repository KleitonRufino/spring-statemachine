package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Ordem;
import com.example.demo.model.Status;
import com.example.demo.repository.OrdemRepository;

@Configuration
public class IniciarDB implements CommandLineRunner{

	@Autowired
	private OrdemRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		repository.deleteAll();
		
		Ordem ordem1 = new Ordem();
		ordem1.setNome("ordem1");
		ordem1.setStatus(Status.WAITING);
		
		repository.save(ordem1);
		
		Ordem ordem2 = new Ordem();
		ordem2.setNome("ordem2");
		ordem2.setStatus(Status.WAITING);
		
		repository.save(ordem2);
	}

}