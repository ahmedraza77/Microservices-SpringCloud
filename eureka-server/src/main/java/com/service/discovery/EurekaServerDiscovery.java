package com.service.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class EurekaServerDiscovery {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerDiscovery.class, args);
	}

}
