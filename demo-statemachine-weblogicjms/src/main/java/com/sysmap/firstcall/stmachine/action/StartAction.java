package com.sysmap.firstcall.stmachine.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.sysmap.firstcall.model.Ordem;
import com.sysmap.firstcall.stmachine.Events;
import com.sysmap.firstcall.stmachine.States;
import com.sysmap.firstcall.stmachine.config.ErrorStateMachine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class StartAction implements Action<States, Events> {

	
	@Override
	public void execute(StateContext<States, Events> context) {

		try {
			
			if (context.getMessage() != null && context.getMessage().getHeaders() != null)
				context.getExtendedState().getVariables().putAll(context.getMessage().getHeaders());
			
			StateMachine<States, Events> machine = context.getStateMachine();
			machine.getStateMachineAccessor().doWithAllRegions(new ErrorStateMachine());
			
			log.info("CRIANDO OBJETO ORDEM");
			Ordem ordem = Ordem.builder().idStateMachine(machine.getId()).build();
			context.getExtendedState().getVariables().put("ordem", ordem);
			
			log.info("ID STATE " + context.getStateMachine().getId() + " | " + "STATE " + context.getStateMachine().getState().getId());				

		} catch (Exception e) {
			context.getStateMachine().setStateMachineError(e);
		}

	}

}
