package com.sysmap.firstcall.stmachine.config;

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

import com.sysmap.firstcall.stmachine.Events;
import com.sysmap.firstcall.stmachine.States;
import com.sysmap.firstcall.stmachine.action.FinishAction;
import com.sysmap.firstcall.stmachine.action.RecoveryOfferAction;
import com.sysmap.firstcall.stmachine.action.RecoveryOfferMsisdnAction;
import com.sysmap.firstcall.stmachine.action.SaveOfferAction;
import com.sysmap.firstcall.stmachine.action.StartAction;
import com.sysmap.firstcall.stmachine.action.UpdateOfferAction;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableStateMachineFactory(contextEvents = false)
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<States, Events> { 

	private final StartAction startAction;
	private final RecoveryOfferAction recoveryOfferAction;
	private final RecoveryOfferMsisdnAction recoveryOfferMsisdnAction;
	private final UpdateOfferAction updateOfferAction;
	private final SaveOfferAction saveOfferAction;
	private final FinishAction finishAction;
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
			
			.stateDo(States.START, this.startAction)
				
			.stateDo(States.RECOVERY_OFFERS, this.recoveryOfferAction)
			
			.stateDo(States.RECOVERY_OFFERS_MSISDN, this.recoveryOfferMsisdnAction)
			
			.stateDo(States.UPDATE_OFFERS, this.updateOfferAction)

			.stateDo(States.SAVE_OFFERS, this.saveOfferAction)

			.stateDo(States.FINISH, this.finishAction);
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {	
		
		transitions.withExternal()
			.source(States.START).target(States.RECOVERY_OFFERS).event(Events.RECOVERING_OFFERS)
		.and().withExternal()
			.source(States.RECOVERY_OFFERS).target(States.RECOVERY_OFFERS_MSISDN).event(Events.RECOVERING_OFFERS_MSISDN)
		.and().withExternal()
			.source(States.RECOVERY_OFFERS_MSISDN).target(States.UPDATE_OFFERS).event(Events.UPDATING_OFFERS_STATUS)	
		.and().withExternal()
			.source(States.UPDATE_OFFERS).target(States.SAVE_OFFERS).event(Events.SAVING_OFFERS)
		.and().withExternal()
			.source(States.SAVE_OFFERS).target(States.FINISH).event(Events.FINISHING)
		//RETRY
		.and().withExternal()
			.source(States.START).target(States.RECOVERY_OFFERS_MSISDN).event(Events.RETRY);

	}

	@Bean
	public StateMachineService<States, Events> stateMachineService(StateMachineFactory<States, Events> stateMachineFactory, StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister){
		return new DefaultStateMachineService<States, Events>(stateMachineFactory, stateMachineRuntimePersister);
	}
	
}
