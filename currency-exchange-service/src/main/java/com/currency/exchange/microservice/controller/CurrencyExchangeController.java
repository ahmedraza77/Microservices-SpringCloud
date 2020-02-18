package com.currency.exchange.microservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.currency.exchange.microservice.domain.Limits;
import com.currency.exchange.microservice.entity.ExchangeValue;
import com.currency.exchange.microservice.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment environment;  
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));       // return the port of running instance
		
		logger.info("{}", exchangeValue);
		
		return exchangeValue;
	}
	
	@GetMapping("/currency-exchange/limits")
	public Limits returnLimits(HttpServletRequest request) {
		
		Boolean gatewayEnabled= Boolean.valueOf(request.getHeader("gatewayEnabled"));
		
		if(gatewayEnabled==true) {
			// getting entire response from Limits Service
			ResponseEntity<Limits> forEntity = restTemplate
					.getForEntity("http://netflix-zuul-api-gateway-server/limits-service/limits", Limits.class);
			
			// getting response body
			Limits response = forEntity.getBody();
			logger.info("zuul-exchange-zuul-limit");
			logger.info("{}", response);

			// returning the response
			return new Limits(response.getMinimum(), 
					          response.getMaximum(),
					          Integer.sum(response.getMinimum(), response.getMaximum()), 
					          response.getPort());
		}
		else {
			ResponseEntity<Limits> forEntity = restTemplate.getForEntity("http://limits-service/limits", Limits.class);
			
			Limits response = forEntity.getBody();
			logger.info("no gateway");
			logger.info("{}", response);

			return new Limits(response.getMinimum(), 
			          response.getMaximum(),
			          Integer.sum(response.getMinimum(), response.getMaximum()), 
			          response.getPort());
		}
		
	}

}
