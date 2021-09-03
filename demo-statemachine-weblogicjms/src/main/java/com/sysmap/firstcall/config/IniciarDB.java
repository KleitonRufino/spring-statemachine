package com.sysmap.firstcall.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sysmap.firstcall.entity.Offer;
import com.sysmap.firstcall.entity.Status;
import com.sysmap.firstcall.repository.OfferRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableTransactionManagement
@Configuration
public class IniciarDB implements CommandLineRunner{

	private final OfferRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		
		repository.deleteAll();
		
		
		for (int i = 0; i < 100; i++) {
			Offer ordem1 = new Offer();
			ordem1.setNome("ordem " + (i + 1));
			ordem1.setStatus(Status.WAITING);			
			repository.save(ordem1);
		}
		
	}

}