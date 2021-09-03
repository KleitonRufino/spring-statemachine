package com.sysmap.firstcall.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.sysmap.firstcall.model.JmsMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProducerJMS {
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${spring.jms.template.default-destination}")
	private String queue;
	
	public void sendMessage(JmsMessage jmsMessage) {
		log.info(String.format("#### -> PRODUZINDO MENSAGEM -> %s", jmsMessage));
		log.info(String.format("#### -> ENVIANDO PARA QUEUE '%s'", queue));
		jmsTemplate.convertAndSend(queue, jmsMessage);
	}
}
