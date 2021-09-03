package com.sysmap.firstcall.stmachine.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sysmap.firstcall.entity.Offer;
import com.sysmap.firstcall.entity.Status;
import com.sysmap.firstcall.model.Ordem;
import com.sysmap.firstcall.repository.OfferRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Qualifier("recoveryOrdemDelegate")
@Component
public class RecoveryOfferTask implements StateMachineTask {

	private final OfferRepository offerRepository;

	@Override
	public void execute(Ordem ordem) throws Exception {

		log.info("RECUPERANDO OFFERS COM STATUS WAITING");
		List<Offer> offers = offerRepository.findOrdemByStatus(Status.WAITING);
		ordem.getOffers().addAll(offers);

	}

}
