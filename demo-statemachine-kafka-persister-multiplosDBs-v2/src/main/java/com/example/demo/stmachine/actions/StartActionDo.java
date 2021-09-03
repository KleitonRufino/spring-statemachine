package com.example.demo.stmachine.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.example.demo.stmachine.Events;
import com.example.demo.stmachine.States;
import com.example.demo.stmachine.delegate.StartDelegate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StartActionDo implements Action<States, Events>{
	

	private final StartDelegate delegate;
	
	@Override
	public void execute(StateContext<States, Events> context) {
		
		try {
			if(context.getMessage()!= null && context.getMessage().getHeaders() != null) context.getExtendedState().getVariables().putAll(context.getMessage().getHeaders());
			this.delegate.execute(context);
		}catch (Exception e) {
			context.getStateMachine().setStateMachineError(e);
		}
	
	}

}
