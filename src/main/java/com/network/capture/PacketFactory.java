package com.network.capture;

import com.network.dto.PacketPayloadDto;
import com.network.dto.packetType.*;
import com.network.model.PacketSummaryModel;
import org.pcap4j.packet.*;

public class PacketFactory {
    PacketDto packetDto;
    private PacketSummaryModel packetSummaryModel;

    public PacketFactory(PacketDto packetDto) {
        this.packetDto = packetDto;
        packetSummaryModel = new PacketSummaryModel();
    }

    public PacketDto createPacketDto(Packet packet) {
        packetSummaryModel.setTotalLength(packet.length());
        packetSummaryModel.setCounter(packetDto.getPacketCount());
        if (packet.get(IpV4Packet.class) != null) {
            packetDto.setIPv4PacketDto(IPv4PacketDto.build(packet.get(IpV4Packet.class)));
            packetSummaryModel.setIPv4HeaderSource(packetDto.getIPv4PacketDto().getSrcAddr());
            packetSummaryModel.setIPv4HeaderDestination(packetDto.getIPv4PacketDto().getDstAddr());
            packetSummaryModel.setProtocol(packetDto.getIPv4PacketDto().getProtocol());
            packetSummaryModel.setVersion(packetDto.getIPv4PacketDto().getVersion());
            packetSummaryModel.setTtl(String.valueOf(packetDto.getIPv4PacketDto().getTtl()));
        }
        if (packet.get(IpV6Packet.class) != null) {
            packetDto.setIPv6PacketDto(IPv6PacketDto.build(packet.get(IpV6Packet.class)));
            packetSummaryModel.setEthernetHeaderSource(packetDto.getIPv6PacketDto().getSrcAddr());
            packetSummaryModel.setEthernetHeaderDestination(packetDto.getIPv6PacketDto().getDstAddr());
            packetSummaryModel.setProtocol(packetDto.getIPv6PacketDto().getNextHeader().name());
            packetSummaryModel.setVersion(packetDto.getIPv6PacketDto().getVersion());
            packetSummaryModel.setTtl(Byte.toString(packetDto.getIPv6PacketDto().getHopLimit()));
        }
        if (packet.get(EthernetPacket.class) != null) {
            packetDto.setEthernetPacketDto(EthernetPacketDto.build(packet.get(EthernetPacket.class)));
            packetSummaryModel.setEthernetHeaderType(packetDto.getEthernetPacketDto().getEthernetHeaderType());
            packetSummaryModel.setEthernetHeaderSource(packetDto.getEthernetPacketDto().getEthernetHeaderSource());
            packetSummaryModel.setEthernetHeaderDestination(packetDto.getEthernetPacketDto().getEthernetHeaderDestination());
        }
        if (packet.get(TcpPacket.class) != null) {
            packetDto.setTcpPacketDto(TcpPacketDto.build(packet.get(TcpPacket.class)));
            packetSummaryModel.setSourcePort(String.valueOf(packetDto.getTcpPacketDto().getSrcPort().port));
            packetSummaryModel.setDestinationPort(String.valueOf(packetDto.getTcpPacketDto().getDstPort().port));
            if (packet.get(TcpPacket.class).getPayload() != null)
                packetDto.setPacketPayloadDto(new PacketPayloadDto(packet.get(TcpPacket.class).getPayload().toString()));
        }
        if (packet.get(UdpPacket.class) != null) {
            packetDto.setUdpPacketDto(UdpPacketDto.build(packet.get(UdpPacket.class)));
            packetSummaryModel.setSourcePort(String.valueOf(packetDto.getUdpPacketDto().getSrcPort().port));
            packetSummaryModel.setDestinationPort(String.valueOf(packetDto.getUdpPacketDto().getDstPort().port));
            if (packet.get(UdpPacket.class).getPayload() != null)
                packetDto.setPacketPayloadDto(new PacketPayloadDto(packet.get(UdpPacket.class).getPayload().toString()));
        }
        packetDto.setDnsPacketDto(DNSPacketDto.build(packet));
        if (packet.get(ArpPacket.class) != null) {
            packetDto.setArpPacketDto(ARPPacketDto.build(packet));
            packetSummaryModel.setProtocol("ARP");

        }
        if (packet.get(LlcPacket.class) != null) {
            packetDto.setLlcPacketDto(LLCPacketDto.build(packet));
            packetSummaryModel.setProtocol("LLC");

        }
        return packetDto;
    }

    public PacketSummaryModel getPacketSummaryModel() {
        return packetSummaryModel;
    }
}

