package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.states.Events;
import com.example.demo.states.States;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StMachineService {

	@Autowired
	private StateMachineService<States, Events> stateMachineService;
	private StateMachine<States, Events> stateMachine;

//	public StateMachine<States, Events> createStateMachine(String machineId){
//		try {
//			return stateMachineService.acquireStateMachine(machineId, true);
//		} catch (Exception e) {
//		return null;
//		}
//	}
//	
//	public synchronized StateMachine<States, Events> getStateMachine(String machineId){
//		try {
//			return stateMachineService.acquireStateMachine(machineId);
//		} catch (Exception e) {
//		return null;
//		}
//	}
//	
//	public void release(String machineId) {
//		stateMachineService.releaseStateMachine(machineId, true);
//	}
	
	public String find(String id) {
		try {
			StateMachine<States, Events> machine = createStateMachine(id);
			String s = machine.isComplete()? " - COMPLETADA:SIM":" - COMPLETADA:NAO";
			log.info("STATEMACHINE:" + id + " - STATUS:" + machine.getState().getId() + s);
			return "STATEMACHINE:" + id + " - STATUS:" + machine.getState().getId() + s;
		} catch (Exception e) {
			System.out.println("NAO FOI POSSIVEL RECUPERAR STATE MACHINE " + id);
		}
		return "NAO FOI POSSIVEL RECUPERAR STATE MACHINE " + id;
	}
	
	// Synchronized method to obtain persisted SM from Database.
	public synchronized StateMachine<States, Events> createStateMachine(String machineId) throws Exception {
		
		if(stateMachine != null)
			stateMachineService.releaseStateMachine(stateMachine.getId(), true);
		
		if (stateMachine == null) {
			stateMachine = stateMachineService.acquireStateMachine(machineId);
			//stateMachine.startReactively();
			stateMachine.start();
		} else if (!ObjectUtils.nullSafeEquals(stateMachine.getId(), machineId)) {
			//stateMachine.stopReactively();
			stateMachine.stop();
			stateMachine = stateMachineService.acquireStateMachine(machineId);
			//stateMachine.startReactively();
			stateMachine.start();
		}
		return stateMachine;
	}
}
