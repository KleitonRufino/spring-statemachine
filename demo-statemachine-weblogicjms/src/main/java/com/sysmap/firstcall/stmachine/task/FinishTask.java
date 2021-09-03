package com.sysmap.firstcall.stmachine.task;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sysmap.firstcall.jms.ProducerJMS;
import com.sysmap.firstcall.model.JmsMessage;
import com.sysmap.firstcall.model.Ordem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Qualifier("finishDelegate")
@Component
public class FinishTask implements StateMachineTask {

	private final ProducerJMS producerJMS;

	@Override
	public void execute(Ordem ordem) throws Exception {

		log.info("FORAM PROCESSADAS {} OFFERS", ordem.getOffers() != null ? ordem.getOffers().size() : 0);
		log.info("NAO FORAM PROCESSADAS {} OFFERS", ordem.getOffers() != null ? ordem.getOffersError().size() : 0);
		if (ordem.getOffersError() != null && !ordem.getOffersError().isEmpty()) {
			JmsMessage jmsMessage = JmsMessage.builder().offers(ordem.getOffersError()).build();
			this.producerJMS.sendMessage(jmsMessage);
		}

	}

}
