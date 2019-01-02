package com.kafka.poc.producerapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

	

	
	/**
	 * Used to send the request to Kafka Producer
	 * 
	 * @param request
	 */
	@RequestMapping(method=RequestMethod.GET, path="/getKafkaProducer")
	public Map getKafkaProducer(HttpServletRequest httpRequest){
		Map result = new HashMap<>();
		result.put("result", "Success");
		return result;
	}

}
