package com.example.demo.stmachine.delegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import com.example.demo.dto.JmsMessage;
import com.example.demo.dto.Ordem;
import com.example.demo.model.Offer;
import com.example.demo.repository.OfferRepository;
import com.example.demo.service.KafkaService;
import com.example.demo.stmachine.Events;
import com.example.demo.stmachine.States;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Qualifier("saveOrdemStateDelegate")
@Component
public class SaveOrdemStateDelegate implements StateMachineDelegate {

	private final OfferRepository ordemRepository;
	private final KafkaService kafkaService;
	
	@Override
	public void execute(StateContext<States, Events> context) throws Exception{
		
		StateMachine<States, Events> machine = context.getStateMachine();
		Ordem ordem = (Ordem) context.getExtendedState().getVariables().get("ordem");
		
		List<Offer> offers = ordem.getOffers();
		
		for (Offer offer : offers) {
			try {
				if(offer.getId() == 2 && !offer.isRetry())
					throw new Exception();
				ordemRepository.save(offer);
			}catch (Exception e) {
				String id = context.getStateMachine().getId();
				States currentState = machine.getState().getId();
					this.kafkaService
						.sendMessageToKafkaTopic(
								JmsMessage.builder()
								.idStateMachine(id).currentState(currentState).offer(offer)
								.build());
			}
		}
		
		log.info("ID STATE " + context.getStateMachine().getId() + " | " + "STATE " + context.getStateMachine().getState().getId());
		machine.sendEvent(Events.FINISHING);

	}

}
