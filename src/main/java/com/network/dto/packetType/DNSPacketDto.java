package com.network.dto.packetType;

import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.DnsPacket;
import org.pcap4j.packet.DnsQuestion;
import org.pcap4j.packet.DnsResourceRecord;
import org.pcap4j.packet.Packet;

import java.util.List;

@Getter
@Setter
public class DNSPacketDto {
	private short id;
	private boolean response;
	private String opCode;
	private boolean authoritativeAnswer;
	private boolean truncated;
	private boolean recursionDesired;
	private boolean recursionAvailable;
	private boolean reserved;
	private boolean authenticData;
	private boolean checkingDisabled;
	private String rCode;
	private short qdCount;
	private short anCount;
	private short nsCount;
	private short arCount;
	private List<DnsQuestion> questions;
	private List<DnsResourceRecord> answers;
	private List<DnsResourceRecord> authorities;
	private List<DnsResourceRecord> additionalInfo;

	public static DNSPacketDto build(Packet packet){
		DNSPacketDto dnsPacketDto=new DNSPacketDto();
		if (packet.get(DnsPacket.class) !=null) {
			DnsPacket.DnsHeader dnsHeader=packet.get(DnsPacket.class).getHeader();
			dnsPacketDto.setResponse(dnsHeader.isResponse());
			dnsPacketDto.setOpCode(dnsHeader.getOpCode().name());
			dnsPacketDto.setAuthoritativeAnswer(dnsHeader.isAuthoritativeAnswer());
			dnsPacketDto.setTruncated(dnsHeader.isTruncated());
			dnsPacketDto.setRecursionDesired(dnsHeader.isRecursionDesired());
			dnsPacketDto.setRecursionAvailable(dnsHeader.isRecursionAvailable());
			dnsPacketDto.setReserved(dnsHeader.getReservedBit());
			dnsPacketDto.setAuthenticData(dnsHeader.isAuthenticData());
			dnsPacketDto.setCheckingDisabled(dnsHeader.isCheckingDisabled());
			dnsPacketDto.setRCode(dnsHeader.getrCode().name());
			dnsPacketDto.setQdCount(dnsHeader.getQdCount());
			dnsPacketDto.setAnCount(dnsHeader.getAnCount());
			dnsPacketDto.setNsCount(dnsHeader.getNsCount());
			dnsPacketDto.setArCount(dnsHeader.getArCount());
			dnsPacketDto.setQuestions(dnsHeader.getQuestions());
			dnsPacketDto.setAnswers(dnsHeader.getAnswers());
			dnsPacketDto.setAuthorities(dnsHeader.getAuthorities());
			dnsPacketDto.setAdditionalInfo(dnsHeader.getAdditionalInfo());
			return dnsPacketDto;
		}
		else return null;
	}

	@Override
	public String toString() {
		return
				"  id=" + id +
				"\n, response=" + response +
				"\n, opCode=" + opCode  +
				"\n, authoritativeAnswer=" + authoritativeAnswer +
				"\n, truncated=" + truncated +
				"\n, recursionDesired=" + recursionDesired +
				"\n, recursionAvailable=" + recursionAvailable +
				"\n, reserved=" + reserved +
				"\n, authenticData=" + authenticData +
				"\n, checkingDisabled=" + checkingDisabled +
				"\n, rCode=" + rCode  +
				"\n, qdCount=" + qdCount +
				"\n, anCount=" + anCount +
				"\n, nsCount=" + nsCount +
				"\n, arCount=" + arCount +
				"\n, questions=" + questions +
				"\n, answers=" + answers +
				"\n, authorities=" + authorities +
				"\n, additionalInfo=" + additionalInfo ;
	}
}
