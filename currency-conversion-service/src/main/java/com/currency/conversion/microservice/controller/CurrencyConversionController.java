package com.currency.conversion.microservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.currency.conversion.microservice.model.CurrencyConversion;
import com.currency.conversion.microservice.model.Limits;

@RestController
public class CurrencyConversionController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrency(@PathVariable String from,
			                                  @PathVariable String to, 
			                                  @PathVariable BigDecimal quantity) {
		
		// setting the path variables
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		// getting entire response from currency exchange
		ResponseEntity<CurrencyConversion> forEntity = restTemplate.
				getForEntity("http://currency-exchange-service/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
		
		// getting response body
		CurrencyConversion response = forEntity.getBody();
		
		logger.info("{}", response);				
		// returning with currency conversion
		return new CurrencyConversion(response.getId(), response.getFrom(), 
				                      response.getTo(), response.getConversionMultiple(), 
				                      quantity, quantity.multiply(response.getConversionMultiple()),
				                      response.getPort());
	}
	
	
	@GetMapping("/currency-converter/exchangelimits")
	public Limits getLimits(HttpServletRequest request) {
        
		//Set the header
        final HttpHeaders headers = new HttpHeaders();
        headers.set("gatewayEnabled", request.getHeader("gatewayEnabled"));

        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        //Execute the method writing your HttpEntity to the request
		ResponseEntity<Limits> forEntity = restTemplate
				.exchange("http://currency-exchange-service/currency-exchange/limits", HttpMethod.GET, entity, Limits.class);
		
		Limits response = forEntity.getBody();
		
		logger.info("{}", response);

		return new Limits(response.getMinimum(), response.getMaximum(),
				response.getSum(), Integer.sum(response.getSum(), response.getSum()),
				response.getPort());
	}
}
