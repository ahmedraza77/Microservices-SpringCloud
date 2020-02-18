package com.microservice.limitservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.limitservice.Configuration;
import com.microservice.limitservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitServiceController {

	@Autowired
	private Configuration configuration;       // can be wired anywhere I want to read the values
	
	@Autowired
	private Environment environment;  
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfiguration() {
		int port = Integer.parseInt(environment.getProperty("local.server.port"));
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum(), port);
	}
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")        // fallback, if this goes down
	public LimitConfiguration retrieveConfiguration() {
		throw new RuntimeException("not available");
	}
	
	
	public LimitConfiguration fallbackRetrieveConfiguration() {
		int port = Integer.parseInt(environment.getProperty("local.server.port"));
		return new LimitConfiguration(555, 5, port);
	}
	
}
