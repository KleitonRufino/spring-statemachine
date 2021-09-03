package com.example.demo.kafka;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.dto.JmsMessage;
import com.example.demo.dto.Ordem;
import com.example.demo.model.Offer;
import com.example.demo.service.StMachineService;
import com.example.demo.stmachine.Events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ConsumerKafka {
	
	private final StMachineService stMachineService;
	
	@KafkaListener(topics = "ordem", groupId = "group_id")
	public void consume(ConsumerRecord<String, JmsMessage> record) throws Exception {
	
		JmsMessage myMessage = record.value();
		log.info(String.format("#### -> CONSUMINDO DO TOPICO ORDEM MENSAGEM-> %s", myMessage));

		JmsMessage message = record.value();
		Offer offer = message.getOffer();
		offer.setRetry(true);
		Ordem ordem = Ordem.builder().idStateMachine(message.getIdStateMachine()).offers(new ArrayList<Offer>(){{add(offer);}}).build();
		this.stMachineService.startAndSendEvent(UUID.randomUUID().toString(), Events.RETRY, Map.of("ordem", ordem));
	
	}
}
