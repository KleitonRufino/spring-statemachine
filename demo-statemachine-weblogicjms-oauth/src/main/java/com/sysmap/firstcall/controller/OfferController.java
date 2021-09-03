package com.sysmap.firstcall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysmap.firstcall.proxy.ManagerProxy;
import com.sysmap.firstcall.service.OfferService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/offers")
public class OfferController {

	private final OfferService service;
	private final ManagerProxy managerProxy;

	@GetMapping("/executar")
	public ResponseEntity<?> executar() throws Exception {
		service.executar();
		return ResponseEntity.ok().build();
	}

	@GetMapping("/msisdn/{id}")
	public String msidn(@PathVariable Long id) {
		try {
			return this.managerProxy.proxyResourceCallGetMsisdn(id);
		} catch (Exception e) {
			return e.getMessage();
		}

	}

}
