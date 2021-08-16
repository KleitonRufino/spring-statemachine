package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.kafka.ProducerKafka;
import com.example.demo.model.Ordem;

@Service
public class KafkaService {

	@Autowired
	private ProducerKafka producerKafka;

	public void sendMessageToKafkaTopic(@RequestBody Ordem ordem) {
		this.producerKafka.sendMessage(ordem);
	}
}
