package com.example.demo.stmachine.delegate;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import com.example.demo.service.StMachineService;
import com.example.demo.stmachine.Events;
import com.example.demo.stmachine.States;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Qualifier("finishDelegate")
@Component
public class FinishDelegate implements StateMachineDelegate {

	private final StMachineService machineService;
	
	@Override
	public void execute(StateContext<States, Events> context) throws Exception{
		log.info(context.getExtendedState().getVariables().entrySet().toString());
		
		context.getStateMachine().stop();
		machineService.release(context.getStateMachine().getId());
		
		log.info("ID STATE " + context.getStateMachine().getId() + " | " + "STATE " + context.getStateMachine().getState().getId());	
	}


}
