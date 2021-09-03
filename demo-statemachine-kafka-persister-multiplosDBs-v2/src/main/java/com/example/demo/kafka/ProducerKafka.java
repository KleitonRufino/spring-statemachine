package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dto.JmsMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProducerKafka {

	private static final String TOPIC = "ordem";

	@Autowired
	private KafkaTemplate<String, JmsMessage> kafkaTemplate;

	public void sendMessage(JmsMessage myMessage) {
		log.info(String.format("#### -> PRODUZINDO MENSAGEM -> %s", myMessage));
		log.info(String.format("#### -> ENVIANDO PARA O TOPICO 'ORDEM'"));
		this.kafkaTemplate.send(TOPIC, myMessage);
	}
}