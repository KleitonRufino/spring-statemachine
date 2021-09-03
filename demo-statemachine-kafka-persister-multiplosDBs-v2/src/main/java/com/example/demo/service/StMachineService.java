package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.stmachine.Events;
import com.example.demo.stmachine.States;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StMachineService {
	@Autowired
	private StateMachineService<States, Events> stateMachineService;
	private StateMachine<States, Events> machine;

//	public StateMachine<States, Events> createStateMachine(StateMachine<States, Events> stateMachine, String machineId){
//		try {
//			return persister.restore(stateMachine, machineId);
//		} catch (Exception e) {
//		return null;
//		}
//	}

	public void startAndSendEvent(String idMachine, Events nextEvent, Map<String, Object> param) {
		machine = getStateMachine(idMachine);
		if(machine.hasStateMachineError()) machine.setStateMachineError(null);
		machine.start();
		sendEvent(machine, nextEvent, param);
	}

	public static void sendEvent(StateMachine<States, Events> machine, Events nextEvent, Map<String, Object> param) {
		machine.sendEvent(new Message<Events>() {
			@Override
			public Events getPayload() {
				return nextEvent;
			}

			@Override
			public MessageHeaders getHeaders() {
				final Map<String, Object> params = new HashMap<>(param);
				return new MessageHeaders(params);
			}
		});
	}

	public StateMachine<States, Events> getStateMachine(String machineId) {
		try {
			return stateMachineService.acquireStateMachine(machineId);
		} catch (Exception e) {
			return null;
		}
	}

	public void release(String machineId) {
		stateMachineService.releaseStateMachine(machineId, true);
	}

	public String find(String id) {
		try {
			StateMachine<States, Events> machine = createStateMachine(id);
			String s = machine.isComplete() ? " - COMPLETADA:SIM" : " - COMPLETADA:NAO";
			log.info("STATEMACHINE:" + id + " - STATUS:" + machine.getState().getId() + s);
			return "STATEMACHINE:" + id + " - STATUS:" + machine.getState().getId() + s;
		} catch (Exception e) {
			log.error("NAO FOI POSSIVEL RECUPERAR STATE MACHINE " + id);
		}
		return "NAO FOI POSSIVEL RECUPERAR STATE MACHINE " + id;
	}

	// Synchronized method to obtain persisted SM from Database.
	private synchronized StateMachine<States, Events> createStateMachine(String machineId) throws Exception {

		if (machine != null)
			stateMachineService.releaseStateMachine(machine.getId(), true);

		if (machine == null) {
			machine = stateMachineService.acquireStateMachine(machineId);

			// stateMachine.startReactively();
			machine.start();
		} else if (!ObjectUtils.nullSafeEquals(machine.getId(), machineId)) {
			// stateMachine.stopReactively();
			machine.stop();
			machine = stateMachineService.acquireStateMachine(machineId);
			// stateMachine.startReactively();
			machine.start();
		}
		return machine;
	}
}
