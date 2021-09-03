package com.sysmap.firstcall.stmachine.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sysmap.firstcall.entity.Offer;
import com.sysmap.firstcall.model.Ordem;
import com.sysmap.firstcall.repository.OfferRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Qualifier("saveOrdemStateDelegate")
@Component
public class SaveOfferStateTask implements StateMachineTask {

	private final OfferRepository ordemRepository;

	@Override
	public void execute(Ordem ordem) throws Exception {

		List<Offer> offers = ordem.getOffers();
		List<Offer> offersError = ordem.getOffersError();
		for (Offer offer : offers) {
			try {
				if (offer.getId() % 2 == 0 && !offer.isRetry())
					throw new Exception();
				ordemRepository.save(offer);
			} catch (Exception e) {
				log.error("ERRO NA OFFER " + offer);
				ordem.getOffersError().add(offer);
			}
		}
		offers.removeAll(offersError);

	}
}
