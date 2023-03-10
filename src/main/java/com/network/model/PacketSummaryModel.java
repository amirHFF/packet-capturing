package com.network.model;

import com.network.dto.PortDto;
import com.network.dto.packetType.PacketDto;
import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

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
	private String version;
	private String ttl;
	private String protocol;
	private long totalLength;
	private String completeData;

	public static String[] buildArray(PacketDto packetDto) {
		return new String[]{
				String.valueOf(packetDto.getPacketCount()),
				packetDto.getIPv4PacketDto() == null ? null : packetDto.getIPv4PacketDto().getSrcAddr(),
				packetDto.getIPv4PacketDto() == null ? null : packetDto.getIPv4PacketDto().getDstAddr(),
				getPort(packetDto, true),
				getPort(packetDto, false),
				packetDto.getEthernetPacketDto().getEthernetHeaderSource(),
				packetDto.getEthernetPacketDto().getEthernetHeaderDestination(),
				getProtocol(packetDto),
				packetDto.getEthernetPacketDto().getEthernetHeaderType(),
				getVersion(packetDto),
				getTtl(packetDto),
				String.valueOf(packetDto.getLength()),
		};
	}

	private static String getPort(PacketDto packetDto, boolean isSrc) {
		if (packetDto.getTcpPacketDto() != null) {
			return isSrc ? String.valueOf(packetDto.getTcpPacketDto().getSrcPort().port) : String.valueOf(packetDto.getTcpPacketDto().getDstPort().port);
		} else if (packetDto.getUdpPacketDto() != null) {
			return isSrc ? String.valueOf(packetDto.getUdpPacketDto().getSrcPort().port) : String.valueOf(packetDto.getUdpPacketDto().getDstPort().port);
		} else return null;
	}

	private static String getProtocol(PacketDto packetDto) {
		if (packetDto.getIPv4PacketDto() != null) {
			return packetDto.getIPv4PacketDto().getProtocol();
		} else if (packetDto.getIPv6PacketDto() != null) {
			return packetDto.getIPv6PacketDto().getNextHeader().valueAsString();
		} else return null;
	}

	private static String getVersion(PacketDto packetDto) {
		if (packetDto.getIPv4PacketDto() != null) {
			return packetDto.getIPv4PacketDto().getVersion();
		} else if (packetDto.getIPv6PacketDto() != null) {
			return packetDto.getIPv6PacketDto().getVersion();
		} else return null;
	}

	private static String getTtl(PacketDto packetDto) {
		if (packetDto.getIPv4PacketDto() != null) {
			return String.valueOf(packetDto.getIPv4PacketDto().getTtl());
		} else if (packetDto.getIPv6PacketDto() != null) {
			return Byte.toString(packetDto.getIPv6PacketDto().getHopLimit());
		} else return null;
	}
}
