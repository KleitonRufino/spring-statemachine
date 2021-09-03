package com.sysmap.firstcall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sysmap.firstcall.entity.Offer;
import com.sysmap.firstcall.entity.Status;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
	
	List<Offer> findOrdemByStatus(Status status);
}
