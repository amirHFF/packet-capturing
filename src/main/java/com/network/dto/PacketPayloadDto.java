package com.network.dto;

public class PacketPayloadDto {

	public String payload;

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return
				"\n\tpayload='" + payload ;
	}
}
