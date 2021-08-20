package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ordem;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerKafka {

	private static final String TOPIC = "ordem";

	@Autowired
	private KafkaTemplate<String, Ordem> kafkaTemplate;

	public void sendMessage(Ordem ordem) {
		log.info(String.format("#### -> PRODUZINDO MENSAGEM -> %s", ordem));
		log.info(String.format("#### -> ENVIANDO PARA O TOPICO 'ORDEM'"));
		this.kafkaTemplate.send(TOPIC, ordem);
	}
}