package com.network.dto.packetType;

import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.LlcPacket;
import org.pcap4j.packet.Packet;

import java.util.Arrays;

@Getter
@Setter
public class LLCPacketDto {
	private String dsap;
	private String ssap ;
	private String control;

	public static LLCPacketDto build(Packet packet){
		LLCPacketDto llcPacketDto=new LLCPacketDto();
		if (packet.get(LlcPacket.class) !=null){
			LlcPacket.LlcHeader llcHeader=packet.get(LlcPacket.class).getHeader();
			llcPacketDto.setControl(Arrays.toString(llcHeader.getControl().getRawData()));
			llcPacketDto.setDsap(llcHeader.getDsap().name());
			llcPacketDto.setSsap(llcHeader.getSsap().name());
			return llcPacketDto;
		}

		return null;
	}

	@Override
	public String toString() {
		return "  dsap= " + dsap +
				"\n, ssap=" + ssap +
				"\n, control='" + control;
	}
}
