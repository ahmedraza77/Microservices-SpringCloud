package com.currency.exchange.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.currency.exchange.microservice.entity.ExchangeValue;



public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long>{
	
	// query method for custom
	ExchangeValue findByFromAndTo(String from, String to);
}
