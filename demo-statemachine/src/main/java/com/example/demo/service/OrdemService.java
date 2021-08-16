package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ordem;
import com.example.demo.model.Status;
import com.example.demo.repository.OrdemRepository;
import com.example.demo.states.Events;
import com.example.demo.states.States;

@Service
public class OrdemService {

	@Autowired
	private OrdemRepository repository;

	@Autowired
	private StMachineService machineService;

	@Autowired
	private StateMachineFactory<States, Events> factory;

	public void executar() throws Exception {

		List<Ordem> list = findAllByStatus(Status.WAITING);
		for (Ordem ordem : list) {
			StateMachine<States, Events> machine = factory.getStateMachine(String.valueOf(ordem.getId()));
			machine.start();

			machine.sendEvent(Events.UPDATING_STATUS);

			ordem.setStatus(Status.PROCESSED);
			save(ordem);

			machine.sendEvent(Events.UPDATED_STATUS);
			machine.stop();

			machineService.save(machine, String.valueOf(ordem.getId()));
		}
	}

	public void save(Ordem ordem) {
		var res = repository.save(ordem);
		System.out.println("SAVE ORDEM " + res);
	}

	public List<Ordem> findAllByStatus(Status status) {
		return repository.findOrdemByStatus(status);
	}

	public List<Ordem> findAll() {
		return repository.findAll();
	}
}
