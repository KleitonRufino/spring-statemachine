package com.sysmap.firstcall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sysmap.firstcall.jms.ProducerJMS;
import com.sysmap.firstcall.model.JmsMessage;

@Service
public class JmsService {

	@Autowired
	private ProducerJMS producer;

	public void sendMessage(@RequestBody JmsMessage myMessage) {
		this.producer.sendMessage(myMessage);
	}
}
