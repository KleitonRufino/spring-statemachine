package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import com.example.demo.persist.InMemoryStateMachinePersist;
import com.example.demo.states.Events;
import com.example.demo.states.States;

@Service
public class StMachineService {

	@Autowired
	private StateMachineFactory<States, Events> factory;
	InMemoryStateMachinePersist stateMachinePersist = new InMemoryStateMachinePersist();
	StateMachinePersister<States, Events, String> persister = new DefaultStateMachinePersister<States, Events, String>(stateMachinePersist);
	

	public void save(StateMachine<States, Events> machine, String id) {
		try {
			persister.persist(machine, id);
		} catch (Exception e) {
			System.out.println("Nao foi possivel salvar STATE MACHINE " + id);
		}
	}
	
	public String find(String id) {
		StateMachine<States, Events> machine = factory.getStateMachine();
		try {
			machine = persister.restore(machine, id);
			String s = machine.isComplete()? " COMPLETADA: SIM":" COMPLETADA: NAO";
			System.out.println("STATEMACHINE:" + id + " STATUS: " + machine.getState().getId() + s);
			return "STATEMACHINE:" + id + " STATUS: " + machine.getState().getId() + s;
		} catch (Exception e) {
			System.out.println("Nao foi possivel recuperar STATE MACHINE " + id);
		}
		return "Nao foi possivel recuperar STATE MACHINE " + id;
	}
}
