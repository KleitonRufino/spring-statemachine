package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Ordem;
import com.example.demo.model.Status;
import com.example.demo.repository.OrdemRepository;
import com.example.demo.states.Events;
import com.example.demo.states.States;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableTransactionManagement
public class OrdemService {

	@Autowired
	private OrdemRepository repository;

	@Autowired
	private StMachineService machineService;

	@Autowired
	private KafkaService kafkaService;
	
	
	public void executar() throws Exception {
		List<Ordem> list = findAllByStatus(Status.WAITING);
		for (Ordem ordem : list) {
			try {
				StateMachine<States, Events> machine = this.machineService.createStateMachine(String.valueOf(ordem.getId()));
				log.info("START MAQUINA DE ESTADO DE ID " + machine.getId() + " NO ESTADO " + machine.getState().getId());
				
				atualizarStatus(ordem, machine);
				salvarOrdem(ordem, machine);
				//this.machineService.release(String.valueOf(ordem.getId()));
				
				log.info("MAQUINA DE ESTADO DE ID " + machine.getId() + (machine.isComplete()? " COMPLETADA": "NAO COMPLETADA"));
			} catch (Exception e) {
				log.error("ERRO PROCESSANDO ORDEM " + ordem);
				log.info("ENVIANDO PARA TOPIC KAFKA 'ORDEM'");
				this.kafkaService.sendMessageToKafkaTopic(ordem);
			}
		}
	}

	public void executarComErro()  {

		List<Ordem> list = findAllByStatus(Status.WAITING);
		for (Ordem ordem : list) {
			try {
				StateMachine<States, Events> machine = this.machineService.createStateMachine(String.valueOf(ordem.getId()));
				log.info("START MAQUINA DE ESTADO DE ID " + machine.getId() + " NO ESTADO " + machine.getState().getId());
				
				atualizarStatus(ordem, machine);
				if (ordem.getId() % 2 == 0) {
					throw new Exception();
				}
				salvarOrdem(ordem, machine);
				//this.machineService.release(String.valueOf(ordem.getId()));
				
				log.info("MAQUINA DE ESTADO DE ID " + machine.getId() + (machine.isComplete()? " COMPLETADA": "NAO COMPLETADA"));
			} catch (Exception e) {
				log.error("ERRO PROCESSANDO ORDEM " + ordem);
				log.info("ENVIANDO PARA TOPIC KAFKA 'ORDEM'");
				this.kafkaService.sendMessageToKafkaTopic(ordem);
			}
		}
	}
	
	public void executar(Ordem ordem) {
		try {
			StateMachine<States, Events> machine = this.machineService.createStateMachine(String.valueOf(ordem.getId()));
			log.info("START MAQUINA DE ESTADO DE ID " + machine.getId() + " NO ESTADO " + machine.getState().getId());
			if(States.CREATED.equals(machine.getState().getId())) {
				atualizarStatus(ordem, machine);
				salvarOrdem(ordem, machine);
			}else if(States.PROCESSING.equals(machine.getState().getId())) {
				salvarOrdem(ordem, machine);
			}
			//this.machineService.release(String.valueOf(ordem.getId()));
			
			log.info("MAQUINA DE ESTADO DE ID " + machine.getId() + (machine.isComplete()? " COMPLETADA": "NAO COMPLETADA"));
		} catch (Exception e) {
			log.error("ERRO PROCESSANDO ORDEM " + ordem);
			log.info("ENVIANDO PARA TOPIC KAFKA 'ORDEM'");
			this.kafkaService.sendMessageToKafkaTopic(ordem);
		}
	}

	
	private void atualizarStatus(Ordem ordem, StateMachine<States, Events> machine) {
		machine.sendEvent(Events.UPDATING_STATUS);
		String old = ordem.getStatus().name();
		ordem.setStatus(Status.FINISHED);
		log.info("STATUS DA ORDEM " + ordem.getId() + " ATUALIZADO DE " + old + " PARA " + ordem.getStatus());
	}
	
	private void salvarOrdem(Ordem ordem, StateMachine<States, Events> machine) {
		save(ordem);
		machine.sendEvent(Events.UPDATED_STATUS);
		log.info("ORDEM " + ordem + " SALVA NO DATABASE");
	}
	
	@Transactional("ordemTransactionManager")
	public void save(Ordem ordem) {
		var res = repository.save(ordem);
		log.info("SAVE ORDEM " + res);
	}
	
	@Transactional("ordemTransactionManager")
	public List<Ordem> findAllByStatus(Status status) {
		return repository.findOrdemByStatus(status);
	}

	@Transactional("ordemTransactionManager")
	public List<Ordem> findAll() {
		return repository.findAll();
	}
	
}
