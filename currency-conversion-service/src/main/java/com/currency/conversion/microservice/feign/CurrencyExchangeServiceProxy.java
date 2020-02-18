package com.currency.conversion.microservice.feign;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.currency.conversion.microservice.model.CurrencyConversion;
import com.currency.conversion.microservice.model.Limits;

//@FeignClient(name="currency-exchange-service", url="localhost:8001")   // no naming server; need to give url
//@FeignClient(name="currency-exchange-service")               // with eureka naming server
@FeignClient(name="netflix-zuul-api-gateway-server")           // request going through gateway
@RibbonClient(name="currency-exchange-service")                // load balancing between multiple instance of this service
public interface CurrencyExchangeServiceProxy {
	
	//@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")    // naming convention, through gateway
	public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
	
	
	@GetMapping("/currency-exchange-service/currency-exchange/limits")
	public Limits returnLimits(@RequestHeader("gatewayEnabled") Boolean gatewayEnabled);
	
}
