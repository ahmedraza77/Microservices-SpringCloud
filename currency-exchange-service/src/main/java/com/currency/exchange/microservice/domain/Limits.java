package com.currency.exchange.microservice.domain;


public class Limits {
	
	private int minimum;
	private int maximum;
	private int sum;
	private int limitsPort;
	
	public Limits() {
		
	}
	
	public Limits(int minimum, int maximum, int sum, int port) {
		super();
		this.minimum = minimum;
		this.maximum = maximum;
		this.sum = sum;
		this.limitsPort = port;
	}



	public int getMinimum() {
		return minimum;
	}
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	public int getPort() {
		return limitsPort;
	}
	public void setPort(int port) {
		this.limitsPort = port;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	

}
