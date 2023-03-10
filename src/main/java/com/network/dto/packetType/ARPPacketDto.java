package com.network.dto.packetType;

import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.Packet;

@Getter
@Setter
public class ARPPacketDto {

	private String hardwareType;
	private String protocolType;
	private String operation;
	private String srcHardwareAddr;
	private String srcProtocolAddr;
	private String dstHardwareAddr;
	private String dstProtocolAddr;

	public static ARPPacketDto build(Packet packet) {
		if (packet.get(ArpPacket.class) != null) {
			ArpPacket.ArpHeader arpPacket = packet.get(ArpPacket.class).getHeader();
			ARPPacketDto arpPacketDto = new ARPPacketDto();
			arpPacketDto.setProtocolType(arpPacket.getProtocolType().name());
			arpPacketDto.setOperation(arpPacket.getOperation().name());
			arpPacketDto.setHardwareType(arpPacket.getHardwareType().name());
			arpPacketDto.setHardwareType(arpPacket.getHardwareType().name());
			arpPacketDto.setDstHardwareAddr(arpPacket.getDstHardwareAddr().getOui().valueAsString());
			arpPacketDto.setSrcHardwareAddr(arpPacket.getSrcHardwareAddr().getOui().valueAsString());
			arpPacketDto.setDstProtocolAddr(arpPacket.getDstProtocolAddr().getHostAddress());
			arpPacketDto.setSrcProtocolAddr(arpPacket.getSrcProtocolAddr().getHostAddress());
			return arpPacketDto;
		}
		else return null;
	}

	@Override
	public String toString() {
		return
				"  hardwareType=" + hardwareType +
						"\n, protocolType=" + protocolType +
						"\n, operation=" + operation +
						"\n, srcHardwareAddr=" + srcHardwareAddr +
						"\n, srcProtocolAddr=" + srcProtocolAddr +
						"\n, dstHardwareAddr=" + dstHardwareAddr +
						"\n, dstProtocolAddr=" + dstProtocolAddr;
	}
}
