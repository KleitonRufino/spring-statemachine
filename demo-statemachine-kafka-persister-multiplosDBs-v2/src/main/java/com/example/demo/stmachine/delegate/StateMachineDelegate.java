package com.example.demo.stmachine.delegate;

import org.springframework.statemachine.StateContext;

import com.example.demo.stmachine.Events;
import com.example.demo.stmachine.States;

public interface StateMachineDelegate {

	void execute(StateContext<States, Events> context) throws Exception;
	
}
