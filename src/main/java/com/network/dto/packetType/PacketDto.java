package com.network.dto.packetType;

import com.network.dto.PacketPayloadDto;
import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.LlcPacket;

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
	private DNSPacketDto dnsPacketDto;
	private ARPPacketDto arpPacketDto;
	private LLCPacketDto llcPacketDto;
	private IPv6PacketDto iPv6PacketDto;

	public PacketDto(long packetCount, long length) {
		PacketCount = packetCount;
		this.length = length;
	}

	@Override
	public String toString() {
		return "Complete Packet info{" + ((ethernetPacketDto != null) ? ("\n, ethernetPacketDto=" + ethernetPacketDto) : "")
				+ ((iPv4PacketDto != null) ? ("\n, ip v4 packet=" + iPv4PacketDto) : "")+
				((iPv6PacketDto != null) ? ("\n, ip v6 packet=" + iPv6PacketDto) : "")+
				((tcpPacketDto != null) ? ("\n, tcp packet=" + tcpPacketDto) : "")+
				((udpPacketDto != null) ? ("\n, udp packet=" + udpPacketDto) : "")+
				((dnsPacketDto != null) ? ("\n, dns packet=" + dnsPacketDto) : "")+
				((arpPacketDto != null) ? ("\n, arp packet=" + arpPacketDto) : "")+
				((llcPacketDto != null) ? ("\n, llc packet=" + llcPacketDto) : "")+
				"\n, PacketCount=" + PacketCount +
				"\n, length=" + length +
				"\n}";
	}
}
