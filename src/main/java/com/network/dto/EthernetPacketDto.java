package com.network.dto;

import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.EthernetPacket;

@Getter
@Setter
public class EthernetPacketDto {

	private String ethernetHeaderDestination;
	private String ethernetHeaderSource;
	private String ethernetHeaderType;

	public static EthernetPacketDto build(EthernetPacket packet){
		EthernetPacketDto ethernetPacketDto = new EthernetPacketDto();
		ethernetPacketDto.setEthernetHeaderDestination(packet.getHeader().getDstAddr().toString());
		ethernetPacketDto.setEthernetHeaderSource(packet.getHeader().getSrcAddr().toString());
		ethernetPacketDto.setEthernetHeaderType(packet.getHeader().getType().toString());
		return ethernetPacketDto;
	}

	@Override
	public String toString() {
		return
				"ethernetHeaderDestination='" + ethernetHeaderDestination + '\'' +
				"\n, ethernetHeaderSource='" + ethernetHeaderSource + '\'' +
				"\n, ethernetHeaderType='" + ethernetHeaderType + '\'';
	}
}
