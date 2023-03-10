package com.network.dto.packetType;


import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.IpV4Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Getter
@Setter
public class IPv4PacketDto {
	static Logger logger= LoggerFactory.getLogger(IPv4PacketDto.class);
	private String srcAddr;
	private String dstAddr;
	private String version;
	private int ihl;
	private String tos;
	private short totalLength;
	private short identification;
	private boolean reservedFlag;
	private boolean dontFragmentFlag;
	private boolean moreFragmentFlag;
	private short fragmentOffset;
	private int ttl;
	private String protocol;
	private short headerChecksum;
	private byte[] padding;
	private boolean correctChecksumAtBuild;
	private boolean correctLengthAtBuild;

	public static IPv4PacketDto build(IpV4Packet packet){
		IPv4PacketDto iPv4PacketDto=new IPv4PacketDto();
		iPv4PacketDto.setSrcAddr(packet.getHeader().getSrcAddr().toString().replace("/",""));
		iPv4PacketDto.setDstAddr(packet.getHeader().getDstAddr().toString().replace("/",""));
		iPv4PacketDto.setDontFragmentFlag(packet.getHeader().getDontFragmentFlag());
		iPv4PacketDto.setFragmentOffset(packet.getHeader().getFragmentOffset());
		iPv4PacketDto.setHeaderChecksum(packet.getHeader().getHeaderChecksum());
		iPv4PacketDto.setIdentification(packet.getHeader().getIdentification());
		iPv4PacketDto.setIhl(packet.getHeader().getIhlAsInt());
		iPv4PacketDto.setMoreFragmentFlag(packet.getHeader().getMoreFragmentFlag());
		iPv4PacketDto.setPadding(packet.getHeader().getPadding());
		iPv4PacketDto.setProtocol(packet.getHeader().getProtocol().name());
		iPv4PacketDto.setReservedFlag(packet.getHeader().getReservedFlag());
		iPv4PacketDto.setTos(String.valueOf(packet.getHeader().getTos()));
		iPv4PacketDto.setTotalLength(packet.getHeader().getTotalLength());
		iPv4PacketDto.setTtl(packet.getHeader().getTtl());
		iPv4PacketDto.setVersion(packet.getHeader().getVersion().name());

		logger.info("ipv4Packet created successfully");
		return iPv4PacketDto;
	}
	@Override
	public String toString() {
		return
				"\nsrcAddr= " + srcAddr +
				"\n, dstAddr= " + dstAddr +
				"\n, version= '" + version + '\'' +
				"\n, ihl= " + ihl +
				"\n, tos= '" + tos + '\'' +
				"\n, totalLength= " + totalLength +
				"\n, identification= " + identification +
				"\n, reservedFlag= " + reservedFlag +
				"\n, dontFragmentFlag= " + dontFragmentFlag +
				"\n, moreFragmentFlag= " + moreFragmentFlag +
				"\n, fragmentOffset= " + fragmentOffset +
				"\n, ttl= " + ttl +
				"\n, protocol= '" + protocol + '\'' +
				"\n, headerChecksum= " + headerChecksum +
				"\n, padding= " + Arrays.toString(padding) +
				"\n, correctChecksumAtBuild= " + correctChecksumAtBuild +
				"\n, correctLengthAtBuild= " + correctLengthAtBuild ;
	}
}
