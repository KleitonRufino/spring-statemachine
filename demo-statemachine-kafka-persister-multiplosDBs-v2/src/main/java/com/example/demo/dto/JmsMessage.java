package com.example.demo.dto;

import java.io.Serializable;

import com.example.demo.model.Offer;
import com.example.demo.stmachine.States;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JmsMessage implements Serializable{

	private static final long serialVersionUID = 625502782746197774L;
	private String idStateMachine;
	private States currentState;
	private Offer offer;
	
}
