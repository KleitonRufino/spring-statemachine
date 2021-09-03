package com.sysmap.firstcall.stmachine.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sysmap.firstcall.entity.Offer;
import com.sysmap.firstcall.model.Ordem;
import com.sysmap.firstcall.proxy.ManagerProxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Qualifier("recoveryOfferMsisdnDelegate")
@Component
public class RecoveryOfferMsisdnTask implements StateMachineTask {

	private final ManagerProxy managerProxy;

	@Override
	public void execute(Ordem ordem) throws Exception {

		log.info("RECUPERANDO MSISDN DAS OFFERS");
		List<Offer> offers = ordem.getOffers();
		//List<Offer> offersError = ordem.getOffersError();
		for (Offer offer : offers) {
			try {
				String msisdn = this.managerProxy.proxyResourceCallGetMsisdn(offer.getId());
				offer.setMsisdn(msisdn);
			} catch (Exception e) {
				log.error("NAO FOI POSSIVEL RECUPERAR O MSISDN DA OFFER {}", offer.getId());
				log.error(e.getMessage());
				//offersError.add(offer);
			}
		}
		//offers.removeAll(offersError);
	}

}
