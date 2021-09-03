package com.sysmap.firstcall.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sysmap.firstcall.entity.Offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ordem implements Serializable{
	
	private static final long serialVersionUID = -3074545870450145015L;
	private String idStateMachine;
	@Builder.Default
	private List<Offer> offers = new ArrayList<Offer>();
	@Builder.Default
	private List<Offer> offersError = new ArrayList<Offer>();
	@Builder.Default
	private Map<String, Object> param = new HashMap<String, Object>();
}
