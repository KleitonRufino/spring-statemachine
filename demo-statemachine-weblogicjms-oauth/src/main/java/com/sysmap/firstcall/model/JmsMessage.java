package com.sysmap.firstcall.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class JmsMessage implements Serializable{

	private static final long serialVersionUID = 625502782746197774L;
	@Builder.Default
	private List<Offer> offers = new ArrayList<Offer>();
	
}
