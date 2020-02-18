package com.currency.conversion.microservice.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.currency.conversion.microservice.feign.CurrencyExchangeServiceProxy;
import com.currency.conversion.microservice.model.CurrencyConversion;
import com.currency.conversion.microservice.model.Limits;

@RestController
public class CurrencyConversionFeignController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	// communicating with external MS using feign client/proxy 
	
	@GetMapping("/feign/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
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
	

	
	@GetMapping("/feign/currency-converter/exchangeLimits")
	public Limits getExchangeLimits(@RequestHeader("gatewayEnabled") Boolean gatewayEnabled) {
		
		Limits response = proxy.returnLimits(gatewayEnabled);
		
		logger.info("{}", response);

		return new Limits(response.getMinimum(), response.getMaximum(),
				response.getSum(), Integer.sum(response.getSum(), response.getSum()),
				response.getPort());	}

}
