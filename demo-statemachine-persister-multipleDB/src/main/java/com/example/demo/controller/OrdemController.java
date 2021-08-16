package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Ordem;
import com.example.demo.service.OrdemService;

import io.micrometer.core.annotation.Timed;

@Timed(histogram = true, value="ordem")
@RestController
@RequestMapping("/ordem")
public class OrdemController {

	@Autowired
	private OrdemService service;

	@GetMapping("/executar")
	public ResponseEntity<?> executar() throws Exception {
		service.executar();
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public List<Ordem> findAll() {
		return service.findAll();
	}

}
