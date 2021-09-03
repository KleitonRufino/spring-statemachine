package com.example.demo.stmachine.delegate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import com.example.demo.dto.Ordem;
import com.example.demo.model.Offer;
import com.example.demo.stmachine.Events;
import com.example.demo.stmachine.States;
import com.example.demo.stmachine.config.ErrorStateMachine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Qualifier("startDelegate")
@Component
public class StartDelegate implements StateMachineDelegate {

	@Override
	public void execute(StateContext<States, Events> context) throws Exception{
		
		StateMachine<States, Events> machine = context.getStateMachine();
		machine.getStateMachineAccessor().doWithAllRegions(new ErrorStateMachine());
		
		Ordem ordem = Ordem.builder().idStateMachine(machine.getId()).offers(new ArrayList<Offer>()).build();
		context.getExtendedState().getVariables().put("ordem", ordem);
		
		log.info("ID STATE " + context.getStateMachine().getId() + " | " + "STATE " + context.getStateMachine().getState().getId());				

	}

}
