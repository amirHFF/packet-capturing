package com.network.dto.packetType;

import com.network.dto.PortDto;
import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.UdpPacket;

@Getter
@Setter
public class UdpPacketDto {

	private PortDto srcPort;
	private PortDto dstPort;
	private short length;
	private short checksum;

	public static UdpPacketDto build(UdpPacket packet){
		UdpPacketDto udpPacketDto= new UdpPacketDto();
		udpPacketDto.setSrcPort(new PortDto(packet.getHeader().getSrcPort().value(),packet.getHeader().getSrcPort().name()));
		udpPacketDto.setDstPort(new PortDto(packet.getHeader().getDstPort().value(),packet.getHeader().getDstPort().name()));
		udpPacketDto.setChecksum(packet.getHeader().getChecksum());
		udpPacketDto.setLength(packet.getHeader().getLength());
		return udpPacketDto;
	}
	@Override
	public String toString() {
		return
				"\nsrcPort=" + srcPort.getPort() +
				"\n, dstPort=" + dstPort.getPort() +
				"\n, length=" + length +
				"\n, checksum=" + checksum ;
	}
}
