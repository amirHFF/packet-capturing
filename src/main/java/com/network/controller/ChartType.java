package com.network.controller;

public enum ChartType {
	IP_ROUTE("ip route chart"),
	port("port chart"),
	protocol("protocol chart"),
	;

	ChartType(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
