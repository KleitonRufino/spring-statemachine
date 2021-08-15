package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.states.Events;
import com.example.demo.states.States;

@Service
public class StMachineService {

	@Autowired
	private StateMachineService<States, Events> stateMachineService;
	private StateMachine<States, Events> stateMachine;

	public StateMachine<States, Events> getStateMachine(String machineId) throws Exception{
		return createStateMachine(machineId);
	}
	
	// Synchronized method to obtain persisted SM from Database.
	private synchronized StateMachine<States, Events> createStateMachine(String machineId) throws Exception {
		if (stateMachine == null) {
			stateMachine = stateMachineService.acquireStateMachine(machineId);
			//stateMachine.startReactively();
			stateMachine.start();
		} else if (!ObjectUtils.nullSafeEquals(stateMachine.getId(), machineId)) {
			stateMachineService.releaseStateMachine(stateMachine.getId());
			//stateMachine.stopReactively();
			stateMachine.stop();
			stateMachine = stateMachineService.acquireStateMachine(machineId);
			//stateMachine.startReactively();
			stateMachine.start();
		}
		return stateMachine;
	}
}
