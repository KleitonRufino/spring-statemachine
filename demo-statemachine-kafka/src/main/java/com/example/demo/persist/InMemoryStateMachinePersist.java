package com.example.demo.persist;

import java.util.HashMap;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

import com.example.demo.states.Events;
import com.example.demo.states.States;


public class InMemoryStateMachinePersist  implements StateMachinePersist<States, Events, String>{

	private final HashMap<String, StateMachineContext<States, Events>> contexts = new HashMap<String, StateMachineContext<States,Events>>();
	@Override
	public void write(StateMachineContext<States, Events> context, String contextObj) throws Exception {
		// TODO Auto-generated method stub
		contexts.put(contextObj, context);
	}

	@Override
	public StateMachineContext<States, Events> read(String contextObj) throws Exception {
		// TODO Auto-generated method stub
		return contexts.get(contextObj);
	}

}
