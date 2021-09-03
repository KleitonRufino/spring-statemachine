package com.example.demo.stmachine.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

import com.example.demo.stmachine.Events;
import com.example.demo.stmachine.States;
import com.example.demo.stmachine.actions.FinishActionDo;
import com.example.demo.stmachine.actions.RecoveryOrdemActionDo;
import com.example.demo.stmachine.actions.SaveOrdemActionDo;
import com.example.demo.stmachine.actions.StartActionDo;
import com.example.demo.stmachine.actions.UpdateOrdemActionDo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableStateMachineFactory(contextEvents = false)
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<States, Events> { 

	private final StartActionDo startActionDo;
	private final RecoveryOrdemActionDo recoveryOrdemActionDo;
	private final UpdateOrdemActionDo updateOrdemActionDo;
	private final SaveOrdemActionDo saveOrdemActionDo;
	private final FinishActionDo finishActionDo;
	private final JpaStateMachineRepository jpaStateMachineRepository;

	@Bean
	public StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister(
			JpaStateMachineRepository jpaStateMachineRepository) {
	
		return new JpaPersistingStateMachineInterceptor<States, Events, String>(jpaStateMachineRepository);
	
	}
	
	@Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
     
		config
        .withMonitoring()
        	.monitor(stateMachineMonitor())
        .and()
		.withPersistence()
			.runtimePersister(stateMachineRuntimePersister(jpaStateMachineRepository));
    
	}
    
	@Bean
	public StateMachineMonitor stateMachineMonitor() {
	
		return new StateMachineMonitor();
	
	}
	
	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		
		states.withStates()
		.initial(States.START)
		.end(States.FINISH)
		.states(EnumSet.allOf(States.class))
			
			.state(States.START)
			
			.stateDo(States.START, this.startActionDo)
				
			.stateDo(States.RECOVERY_ORDEM, this.recoveryOrdemActionDo)
			
			.stateDo(States.UPDATE_ORDEM, this.updateOrdemActionDo)

			.stateDo(States.SAVE_ORDEM, this.saveOrdemActionDo)

			.stateDo(States.FINISH, this.finishActionDo);
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {	
		
		transitions.withExternal()
			.source(States.START).target(States.RECOVERY_ORDEM).event(Events.RECOVING_ORDEM)
		.and().withExternal()
			.source(States.RECOVERY_ORDEM).target(States.UPDATE_ORDEM).event(Events.UPDATING_ORDEM_STATUS)
		.and().withExternal()
			.source(States.UPDATE_ORDEM).target(States.SAVE_ORDEM).event(Events.SAVING_ODEM)
		.and().withExternal()
			.source(States.SAVE_ORDEM).target(States.FINISH).event(Events.FINISHING)
		//RETRY
		.and().withExternal()
			.source(States.START).target(States.UPDATE_ORDEM).event(Events.RETRY);

	}

	@Bean
	public StateMachineService<States, Events> stateMachineService(StateMachineFactory<States, Events> stateMachineFactory, StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister){
		return new DefaultStateMachineService<States, Events>(stateMachineFactory, stateMachineRuntimePersister);
	}
	
}
