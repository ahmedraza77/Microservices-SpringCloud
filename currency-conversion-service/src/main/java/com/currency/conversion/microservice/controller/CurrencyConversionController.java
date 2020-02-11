package com.currency.conversion.microservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.currency.conversion.microservice.feign.CurrencyExchangeServiceProxy;
import com.currency.conversion.microservice.model.CurrencyConversion;

@RestController
public class CurrencyConversionController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
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
				getForEntity("http://localhost:8001/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
		
		// getting response body
		CurrencyConversion response = forEntity.getBody();
				
		// returning with currency conversion
		return new CurrencyConversion(response.getId(), response.getFrom(), 
				                      response.getTo(), response.getConversionMultiple(), 
				                      quantity, quantity.multiply(response.getConversionMultiple()),
				                      response.getPort());
	}
	
	// communicating with external MS using feign client/proxy 
	
	@GetMapping("feign/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrencyFeignApproach(@PathVariable String from,
			                                  @PathVariable String to, 
			                                  @PathVariable BigDecimal quantity) {
		
		CurrencyConversion response = proxy.retrieveExchangeValue(from, to);
		
		logger.info("{}", response);

		return new CurrencyConversion(response.getId(), response.getFrom(), 
				                      response.getTo(), response.getConversionMultiple(), 
				                      quantity, quantity.multiply(response.getConversionMultiple()),
				                      response.getPort());
	}

}
