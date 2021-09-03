package com.sysmap.firstcall.stmachine.config;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.monitor.AbstractStateMachineMonitor;
import org.springframework.statemachine.transition.Transition;

import com.sysmap.firstcall.stmachine.Events;
import com.sysmap.firstcall.stmachine.States;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StateMachineMonitor extends AbstractStateMachineMonitor<States, Events> {

	@Override
	public void transition(StateMachine<States, Events> stateMachine, Transition<States, Events> transition,
			long duration) {
		if (transition != null && transition.getSource() != null && transition.getTarget() != null)
			log.info("ESTADO MUDANDO DE " + transition.getSource().getId() + " PARA "
					+ transition.getTarget().getId());
	}

	@Override
	public void action(StateMachine<States, Events> stateMachine, Action<States, Events> action, long duration) {
		super.action(stateMachine, action, duration);
	}

}
