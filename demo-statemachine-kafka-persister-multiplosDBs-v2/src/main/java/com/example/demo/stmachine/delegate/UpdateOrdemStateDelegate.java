package com.example.demo.stmachine.delegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import com.example.demo.dto.Ordem;
import com.example.demo.model.Offer;
import com.example.demo.model.Status;
import com.example.demo.stmachine.Events;
import com.example.demo.stmachine.States;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Qualifier("updateOrdemStateDelegate")
@Component
public class UpdateOrdemStateDelegate implements StateMachineDelegate {

	@Override
	public void execute(StateContext<States, Events> context) throws Exception{
		
		StateMachine<States, Events> machine = context.getStateMachine();
		Ordem ordem = (Ordem) context.getExtendedState().getVariables().get("ordem");
		
		List<Offer> offers = ordem.getOffers();

		for (Offer offer : offers) {
			offer.setStatus(Status.PROCESSED);
		}
		
		log.info("ID STATE " + context.getStateMachine().getId() + " | " + "STATE " + context.getStateMachine().getState().getId());
		machine.sendEvent(Events.SAVING_ODEM);
		
	}

}
