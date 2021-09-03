//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.service.KafkaService;
//import com.example.demo.states.MyMessage;
//
//@RestController
//@RequestMapping(value = "/kafka")
//public class KafkaController {
//
//	@Autowired
//	private KafkaService kafkaService;
//
//
//	@PostMapping(value = "/publish")
//	public void sendMessageToKafkaTopic(@RequestBody MyMessage myMessage) {
//		this.kafkaService.sendMessageToKafkaTopic(myMessage);
//	}
//}