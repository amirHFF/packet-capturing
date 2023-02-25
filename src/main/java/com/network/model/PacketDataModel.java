package com.network.model;

import java.util.HashMap;

public class PacketDataModel {
	private String packetType;
	private HashMap<String , String> packetDetailInfo=new HashMap<>();

	public PacketDataModel() {
	}

	public PacketDataModel(String packetType, HashMap<String, String> packetDetailInfo) {
		this.packetType = packetType;
		this.packetDetailInfo = packetDetailInfo;
	}

	public String getPacketType() {
		return packetType;
	}

	public void setPacketType(String packetType) {
		this.packetType = packetType;
	}

	public HashMap<String, String> getPacketDetailInfo() {
		return packetDetailInfo;
	}

	public void setPacketDetailInfo(HashMap<String, String> packetDetailInfo) {
		this.packetDetailInfo = packetDetailInfo;
	}
}
