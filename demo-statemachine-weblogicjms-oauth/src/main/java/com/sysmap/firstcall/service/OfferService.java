package com.sysmap.firstcall.service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sysmap.firstcall.entity.Offer;
import com.sysmap.firstcall.model.Ordem;
import com.sysmap.firstcall.stmachine.Events;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("serial")
@RequiredArgsConstructor
@Service
public class OfferService {

	private final StMachineService stMachineService;
	
	public void executar() throws Exception {
		this.stMachineService.startAndSendEvent(UUID.randomUUID().toString(), Events.RECUPERANDO_OFFERS, new HashMap<String, Object>());
	}

	public void retry(List<Offer> offers) {
		if(offers != null) {
			String idStateMachine = UUID.randomUUID().toString();
			offers.forEach(o -> o.setRetry(true));
			Ordem ordem = Ordem.builder().idStateMachine(idStateMachine).offers(offers).build();
			this.stMachineService.startAndSendEvent(idStateMachine, Events.RETRY, new HashMap<String, Object>(){{put("ordem", ordem);}});
		}
	}
	
}
