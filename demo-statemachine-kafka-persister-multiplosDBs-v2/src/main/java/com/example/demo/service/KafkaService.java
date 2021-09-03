package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.JmsMessage;
import com.example.demo.kafka.ProducerKafka;

@Service
public class KafkaService {

	@Autowired
	private ProducerKafka producerKafka;

	public void sendMessageToKafkaTopic(@RequestBody JmsMessage myMessage) {
		this.producerKafka.sendMessage(myMessage);
	}
}
