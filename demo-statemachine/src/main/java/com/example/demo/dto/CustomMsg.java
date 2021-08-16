package com.example.demo.dto;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomMsg {

	private Long id;

	@NotNull
	private String message;

	@Override
	public String toString() {
		return "CustomMsg{id=" + id + ", message='" + message + "'}";
	}

}