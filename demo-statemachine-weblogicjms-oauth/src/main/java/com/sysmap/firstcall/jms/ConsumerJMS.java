package com.sysmap.firstcall.jms;


import java.util.List;

import javax.ejb.TransactionManagement;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sysmap.firstcall.entity.Offer;
import com.sysmap.firstcall.model.JmsMessage;
import com.sysmap.firstcall.service.OfferService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@TransactionManagement
@EnableTransactionManagement
@RequiredArgsConstructor
@Slf4j
@Component
public class ConsumerJMS {

	@Value("${spring.jms.template.default-destination}")
	private String queue;
	private final OfferService offerService;
	
	@JmsListener(destination = "${spring.jms.template.default-destination}")
	public void listenToMessages(Message message, Session session,
			@Header(name = "JMSXDeliveryCount", defaultValue = "1") String redeliveryCount,
			@Header(name = JmsHeaders.MESSAGE_ID, defaultValue = "1") String messageId) throws JMSException{
	
		try {
			JmsMessage jmsMessage = message.getBody(JmsMessage.class);
			log.info("#### -> CONSUMINDO MENSAGEM mensagem {} NA QUEUE {} [RedeliveryCount={}, MessageID={}]", jmsMessage, queue,
					redeliveryCount, messageId);
			
			List<Offer> offers = jmsMessage.getOffers();
			offers.forEach(o -> o.setRetry(true));
			this.offerService.retry(offers);
			session.commit();
		} catch (Exception e) {
			session.rollback();
		}

	}

}
