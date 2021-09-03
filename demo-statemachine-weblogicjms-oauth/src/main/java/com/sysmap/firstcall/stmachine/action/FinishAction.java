package com.sysmap.firstcall.stmachine.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.sysmap.firstcall.model.Ordem;
import com.sysmap.firstcall.service.StMachineService;
import com.sysmap.firstcall.stmachine.Events;
import com.sysmap.firstcall.stmachine.States;
import com.sysmap.firstcall.stmachine.task.FinishTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class FinishAction implements Action<States, Events> {
	
	private final FinishTask delegate;
	private final StMachineService machineService;
	
	@Override
	public void execute(StateContext<States, Events> context) {

		try {
			
			if (context.getMessage() != null && context.getMessage().getHeaders() != null)
				context.getExtendedState().getVariables().putAll(context.getMessage().getHeaders());
			
			StateMachine<States, Events> machine = context.getStateMachine();
			Ordem ordem = (Ordem) context.getExtendedState().getVariables().get("ordem");
			
			machine.stop();
			machineService.release(machine.getId());
			
			this.delegate.execute(ordem);
			
			log.info("ID STATE " + context.getStateMachine().getId() + " | " + "STATE " + context.getStateMachine().getState().getId());	

		} catch (Exception e) {
			context.getStateMachine().setStateMachineError(e);
		}

	}

}
