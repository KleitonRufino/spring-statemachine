package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Offer;
import com.example.demo.model.Status;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
	
	List<Offer> findOrdemByStatus(Status status);
}
