package com.sysmap.firstcall.stmachine.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sysmap.firstcall.entity.Offer;
import com.sysmap.firstcall.entity.Status;
import com.sysmap.firstcall.model.Ordem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Qualifier("updateOrdemStateDelegate")
@Component
public class UpdateOfferStateTask implements StateMachineTask {
	
	@Override
	public void execute(Ordem ordem) throws Exception {

		log.info("MUDANDO STATUS DAS OFFERS PARA PROCESSED");

		List<Offer> offers = ordem.getOffers();
		for (Offer offer : offers) {
			offer.setStatus(Status.PROCESSED);
		}
		
	}

}
