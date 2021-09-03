package com.example.demo.service;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.stmachine.Events;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableTransactionManagement
@Service
public class OfferService {

	@Autowired
	private StMachineService machineService;

	public void executar() throws Exception {
		
		this.machineService.startAndSendEvent(UUID.randomUUID().toString(), Events.RECOVING_ORDEM, new HashMap<String, Object>());

	}

}
