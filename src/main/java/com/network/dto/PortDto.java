package com.network.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortDto {
	public int port;
	public String name;
	public String owner;

	public PortDto(int port, String name) {
		this.port = port;
		this.name = name;
	}

	@Override
	public String toString() {
		return String.valueOf(port);
	}
}
