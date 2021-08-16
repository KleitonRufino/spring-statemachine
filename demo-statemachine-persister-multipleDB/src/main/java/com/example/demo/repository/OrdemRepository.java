package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.demo.model.Ordem;
import com.example.demo.model.Status;

//@Repository
@EnableJpaRepositories(basePackages = "com.example.demo.repository",
entityManagerFactoryRef = "ordemEntityManagerFactory",
transactionManagerRef= "ordemTransactionManager")
public interface OrdemRepository extends JpaRepository<Ordem, Long> {
	
	List<Ordem> findOrdemByStatus(Status status);
}
