package com.example.demo.states;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class EntryProcessingAction implements Action<States, Events> {

	@Override
	public void execute(StateContext<States, Events> context) {
		System.out.println("ENTRY PROCESSING");
		context.getExtendedState().getVariables().put("key", "value");
	}

}
