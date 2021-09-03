package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;

import com.example.demo.model.Offer;

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
public class Ordem implements Serializable{
	
	private static final long serialVersionUID = -3074545870450145015L;
	private String idStateMachine;
	private List<Offer> offers;
}
