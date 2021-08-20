package com.example.demo.kafka;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ordem;
import com.example.demo.service.OrdemService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerKafka {
	
	@Autowired
	private OrdemService ordemService;
	

	@KafkaListener(topics = "ordem", groupId = "group_id")
	public void consume(ConsumerRecord<String, Ordem> record) throws IOException {
		log.info(String.format("#### -> CONSUMINDO DO TOPICO ORDEM MENSAGEM-> %s", record.value()));
		ordemService.executar(record.value());
	}
}
