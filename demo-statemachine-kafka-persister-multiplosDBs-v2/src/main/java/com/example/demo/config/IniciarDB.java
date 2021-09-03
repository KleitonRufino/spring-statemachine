package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Offer;
import com.example.demo.model.Status;
import com.example.demo.repository.OfferRepository;

@Configuration
@EnableTransactionManagement
public class IniciarDB implements CommandLineRunner{

	@Autowired
	private OfferRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		repository.deleteAll();
		
		Offer ordem1 = new Offer();
		ordem1.setNome("ordem1");
		ordem1.setStatus(Status.WAITING);
		
		repository.save(ordem1);
		
		Offer ordem2 = new Offer();
		ordem2.setNome("ordem2");
		ordem2.setStatus(Status.WAITING);
		
		repository.save(ordem2);
	
		Offer ordem3 = new Offer();
		ordem3.setNome("ordem3");
		ordem3.setStatus(Status.WAITING);
	
		repository.save(ordem3);
		
		Offer ordem4 = new Offer();
		ordem4.setNome("ordem4");
		ordem4.setStatus(Status.WAITING);
	
		repository.save(ordem4);
	}

}