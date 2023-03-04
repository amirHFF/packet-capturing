package com.network.model;

import com.network.dto.PacketDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class PacketSummaryModel {

	private long counter;
	private String ethernetHeaderDestination;
	private String ethernetHeaderSource;
	private String ethernetHeaderType;
	private String iPv4HeaderSource;
	private String iPv4HeaderDestination;
	private String sourcePort;
	private String destinationPort;
	private String iPv4HeaderVersion;
	private String iPv4HeaderTotalLength;
	private String iPv4HeaderTTL;
	private String iPv4HeaderProtocol;
	private long totalLength;
	private String completeData;


	public static String[] buildArray(PacketDto packetDto) {
		return new String[]{
				String.valueOf(packetDto.getPacketCount()),
				packetDto.getIPv4PacketDto().getSrcAddr(),
				packetDto.getIPv4PacketDto().getDstAddr(),
				packetDto.getTcpPacketDto() == null ? String.valueOf(packetDto.getUdpPacketDto().getSrcPort().port) : String.valueOf(packetDto.getTcpPacketDto().getSrcPort()),
				packetDto.getTcpPacketDto() == null ? String.valueOf(packetDto.getUdpPacketDto().getDstPort()) : String.valueOf(packetDto.getTcpPacketDto().getDstPort()),
				packetDto.getEthernetPacketDto().getEthernetHeaderSource(),
				packetDto.getEthernetPacketDto().getEthernetHeaderDestination(),
				packetDto.getIPv4PacketDto().getProtocol(),
				packetDto.getEthernetPacketDto().getEthernetHeaderType(),
				packetDto.getIPv4PacketDto().getVersion(),
				String.valueOf(packetDto.getIPv4PacketDto().getTtl()),
				String.valueOf(packetDto.getLength()),
				 };
	}
}
