package com.example.demo.states;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.monitor.StateMachineMonitor;

import com.example.demo.monitor.TestStateMachineMonitor;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events>{


	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
		config
			.withMonitoring()
				.monitor(stateMachineMonitor())
			.and()
			.withConfiguration()
				.autoStartup(true);
				//.listener(listener())
	}
	
	private StateMachineMonitor<States, Events> stateMachineMonitor() {
		return new TestStateMachineMonitor();
	}

//	private StateMachineListener<OrderStates, OrderEvents> listener() {
//		return new StateMachineListenerAdapter<OrderStates, OrderEvents>(){
//			@Override
//			public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
//				if(from != null && to != null)
//					System.out.println("Estado da Ordem mudando de " + from.getId() + " para " + to.getId());
//			}
//		};
//	}
	
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
}

