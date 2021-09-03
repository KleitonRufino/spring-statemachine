package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OfferService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/offer")
public class OfferController {

	private final OfferService service;

	@GetMapping("/executar")
	public ResponseEntity<?> executar() throws Exception {
		service.executar();
		return ResponseEntity.ok().build();
	}

}
