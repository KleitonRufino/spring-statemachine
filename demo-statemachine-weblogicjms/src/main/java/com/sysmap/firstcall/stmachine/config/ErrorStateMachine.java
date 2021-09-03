package com.sysmap.firstcall.stmachine.config;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineFunction;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;

import com.sysmap.firstcall.stmachine.Events;
import com.sysmap.firstcall.stmachine.States;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorStateMachine implements StateMachineFunction<StateMachineAccess<States, Events>> {

	@Override
	public void apply(StateMachineAccess<States, Events> function) {
		function.addStateMachineInterceptor(new StateMachineInterceptorAdapter<States, Events>() {
			@Override
			public Exception stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
				log.error("ERRO NA STATE MACHINE -> " + exception.getMessage());
				log.error(exception.getMessage());
				return exception;
			}
		});
	}
}