package com.network.model;

public class TextPacket {
	private long id;
	private long packetData;

	public TextPacket(long id, long packetData) {
		this.id = id;
		this.packetData = packetData;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPacketData() {
		return packetData;
	}

	public void setPacketData(long packetData) {
		this.packetData = packetData;
	}
}
