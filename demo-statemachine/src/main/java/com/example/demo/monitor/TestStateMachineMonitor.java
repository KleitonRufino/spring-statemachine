package com.example.demo.monitor;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.monitor.AbstractStateMachineMonitor;
import org.springframework.statemachine.transition.Transition;

import com.example.demo.states.Events;
import com.example.demo.states.States;

public class TestStateMachineMonitor extends AbstractStateMachineMonitor<States, Events>{

	@Override
	public void transition(StateMachine<States, Events> stateMachine,
			Transition<States, Events> transition, long duration) {
		// TODO Auto-generated method stub
		super.transition(stateMachine, transition, duration);
		if(transition != null && transition.getSource() != null && transition.getTarget() != null)
			System.out.println("Estado da Ordem mudando de " + transition.getSource().getId() + " para " + transition.getTarget().getId());
	}
	
	@Override
	public void action(StateMachine<States, Events> stateMachine, Action<States, Events> action,
			long duration) {
		super.action(stateMachine, action, duration);
	}
	
	
}
