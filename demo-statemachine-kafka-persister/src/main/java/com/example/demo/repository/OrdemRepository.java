package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Ordem;
import com.example.demo.model.Status;

@Repository
public interface OrdemRepository extends JpaRepository<Ordem, Long> {
	
	List<Ordem> findOrdemByStatus(Status status);
}
