package com.microservice.limitservice.bean;

public class LimitConfiguration {

	private int maximum;
	private int minimum;
	private int limitsPort;	
	
	public LimitConfiguration() {
	}
	
	public LimitConfiguration(int maximum, int minimum, int limitPort) {
		super();
		this.maximum = maximum;
		this.minimum = minimum;
		this.limitsPort = limitPort;
	}



	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	public int getMinimum() {
		return minimum;
	}
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	public int getPort() {
		return limitsPort;
	}
	public void setPort(int limitPort) {
		this.limitsPort = limitPort;
	}

	@Override
	public String toString() {
		return String.format("LimitConfiguration [maximum=%s, minimum=%s, limitsPort=%s]", maximum, minimum, limitsPort);
	}
	
	
	
	
}
