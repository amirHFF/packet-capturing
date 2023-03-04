package com.network.dto;

import lombok.Getter;
import lombok.Setter;
import org.pcap4j.packet.TcpPacket;

@Getter
@Setter
public class TcpPacketDto {

	private PortDto srcPort;
	private PortDto dstPort;
	private int sequenceNumber;
	private int acknowledgmentNumber;
	private byte dataOffset;
	private byte reserved;
	private boolean urg;
	private boolean ack;
	private boolean psh;
	private boolean rst;
	private boolean syn;
	private boolean fin;
	private short window;
	private short checksum;
	private short urgentPointer;

	public static TcpPacketDto build(TcpPacket packet){
		TcpPacketDto tcpPacketDto=new TcpPacketDto();
		tcpPacketDto.setSrcPort(new PortDto(packet.getHeader().getSrcPort().valueAsInt(),packet.getHeader().getSrcPort().name()));
		tcpPacketDto.setDstPort(new PortDto(packet.getHeader().getDstPort().valueAsInt(),packet.getHeader().getDstPort().name()));
		tcpPacketDto.setAck(packet.getHeader().getAck());
		tcpPacketDto.setAcknowledgmentNumber(packet.getHeader().getAcknowledgmentNumber());
		tcpPacketDto.setChecksum(packet.getHeader().getChecksum());
		tcpPacketDto.setDataOffset(packet.getHeader().getDataOffset());
		tcpPacketDto.setFin(packet.getHeader().getFin());
		tcpPacketDto.setPsh(packet.getHeader().getPsh());
		tcpPacketDto.setReserved(packet.getHeader().getReserved());
		tcpPacketDto.setRst(packet.getHeader().getRst());
		tcpPacketDto.setSequenceNumber(packet.getHeader().getSequenceNumber());
		tcpPacketDto.setSyn(packet.getHeader().getSyn());
		tcpPacketDto.setUrg(packet.getHeader().getUrg());
		tcpPacketDto.setUrgentPointer(packet.getHeader().getUrgentPointer());
		tcpPacketDto.setWindow(packet.getHeader().getWindow());
		return tcpPacketDto;
	}

	@Override
	public String toString() {
		return
				"\nsrcPort= " + srcPort.getPort() +
				"\n, dstPort= " + dstPort.getPort() +
				"\n, sequenceNumber= " + sequenceNumber +
				"\n, acknowledgmentNumber= " + acknowledgmentNumber +
				"\n, dataOffset= " + dataOffset +
				"\n, reserved= " + reserved +
				"\n, urg= " + urg +
				"\n, ack= " + ack +
				"\n, psh= " + psh +
				"\n, rst= " + rst +
				"\n, syn= " + syn +
				"\n, fin= " + fin +
				"\n, window= " + window +
				"\n, checksum= " + checksum +
				"\n, urgentPointer= " + urgentPointer ;
	}
}
