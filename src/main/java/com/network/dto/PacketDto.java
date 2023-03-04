package com.network.dto;

import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.EthernetPacket;

@Getter
@Setter
public class PacketDto {
	private long PacketCount;
	private long length;
	private EthernetPacketDto ethernetPacketDto;
	private IPv4PacketDto iPv4PacketDto;
	private PacketPayloadDto packetPayloadDto;
	private TcpPacketDto tcpPacketDto;
	private UdpPacketDto udpPacketDto;

	public PacketDto(long packetCount, long length) {
		PacketCount = packetCount;
		this.length = length;
	}

	@Override
	public String toString() {
		return "Complete Packet info{" +
				"\n, ethernetPacketDto=" + ethernetPacketDto +
				"\n, iPv4PacketDto=" + iPv4PacketDto +
				"\n, packetPayloadDto=" + packetPayloadDto +
				"\n, tcpPacketDto=" + tcpPacketDto +
				"\n, udpPacketDto=" + udpPacketDto +
				"\n, PacketCount=" + PacketCount +
				"\n, length=" + length +
				"\n}";
	}
}
