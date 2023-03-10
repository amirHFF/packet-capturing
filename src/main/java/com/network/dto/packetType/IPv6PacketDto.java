package com.network.dto.packetType;

import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.IpV6Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.IpNumber;

import java.net.Inet6Address;

@Setter
@Getter
public class IPv6PacketDto {

	private String version;
	private short payloadLength;
	private IpNumber nextHeader;
	private byte hopLimit;
	private String srcAddr;
	private String dstAddr;
	private boolean correctLengthAtBuild;

	public static IPv6PacketDto build(Packet packet){
		IPv6PacketDto iPv6PacketDto=new IPv6PacketDto();
		if (packet.get(IpV6Packet.class) !=null){
			IpV6Packet.IpV6Header ipV6Header= packet.get(IpV6Packet.class).getHeader();
			iPv6PacketDto.setSrcAddr(ipV6Header.getSrcAddr().getHostAddress());
			iPv6PacketDto.setDstAddr(ipV6Header.getDstAddr().getHostAddress());
			iPv6PacketDto.setHopLimit(ipV6Header.getHopLimit());
			iPv6PacketDto.setNextHeader(ipV6Header.getNextHeader());
			iPv6PacketDto.setPayloadLength(ipV6Header.getPayloadLength());
			iPv6PacketDto.setVersion(ipV6Header.getVersion().name());
			return iPv6PacketDto;
		}
		return null;

	}

	@Override
	public String toString() {
		return
				"  version=" + version  +
				"\n, payloadLength=" + payloadLength +
				"\n, nextHeader=" + nextHeader +
				"\n, hopLimit=" + hopLimit +
				"\n, srcAddr=" + srcAddr  +
				"\n, dstAddr=" + dstAddr  +
				"\n, correctLengthAtBuild=" + correctLengthAtBuild ;
	}
}
