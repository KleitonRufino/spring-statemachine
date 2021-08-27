package com.example.demo.states;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.monitor.StateMachineMonitor;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.statemachine.state.State;

import com.example.demo.monitor.TestStateMachineMonitor;

@Configuration
@EnableStateMachineFactory(contextEvents = false)
public class StateMachineConfig extends StateMachineConfigurerAdapter<States, Events> { 
	
	@Autowired
	private JpaStateMachineRepository jpaStateMachineRepository;
		
//	@Autowired
//	private StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister;


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
	private StateMachineMonitor<States, Events> stateMachineMonitor() {
		// TODO Auto-generated method stub
		return new TestStateMachineMonitor();
	}

	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states.withStates()
		.initial(States.CREATED)
		.end(States.PROCESSED)
		.states(EnumSet.allOf(States.class));
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions.withExternal()
			.source(States.CREATED).target(States.PROCESSING).event(Events.UPDATING_STATUS)
		.and().withExternal()
			.source(States.PROCESSING).target(States.PROCESSED).event(Events.UPDATED_STATUS);
	}
  
    
	@Bean
	public StateMachineListener<States, Events> listener() {

		return new StateMachineListenerAdapter<States, Events>() {
			@Override
			public void stateChanged(State<States, Events> from, State<States, Events> to) {
				System.out.println("Listerner : In state chnaged");
				if (from == null) {
					System.out.println("State machine initialised in state " + to.getId());
				} else {
					System.out.println("State changed from " + from.getId() + " to " + to.getId());
				}
			}
		};
	}
	
	
//	@Bean
//	public StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister() {
//		return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
//	}

	@Bean
	public StateMachineService<States, Events> stateMachineService(StateMachineFactory<States, Events> stateMachineFactory, StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister){
		return new DefaultStateMachineService<States, Events>(stateMachineFactory, stateMachineRuntimePersister);
	}
}
