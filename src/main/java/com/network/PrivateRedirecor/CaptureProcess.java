package com.network.PrivateRedirecor;

import com.network.controller.TableInitializer;
import com.network.model.PacketDataModel;
import com.network.model.PacketSummaryModel;
import org.pcap4j.core.*;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

import javax.swing.*;
import java.util.*;

public class CaptureProcess {
	private long totalPacketCount = 1;
	private long counter =1;
	private static int READ_TIMEOUT = 50; // [ms]
	private static int SNAPLEN = 65536; // [bytes]
	private List<PcapNetworkInterface> networkInterfaceList;
	private PcapNetworkInterface selectedInterface;
	private PcapHandle handler;
	private PacketSummaryResult packetSummaryResult = new PacketSummaryResult();
	private HashSet<PacketSummaryModel> packetStore = new HashSet<>();
	private TableInitializer table;
	private JLabel totalTrafficLabel;
	private JTextArea outputConsole;

	public CaptureProcess() {
	}

	public CaptureProcess(JTextArea console , TableInitializer table , JLabel totalTrafficLabel) {
		outputConsole = console;
		this.table=table;
		this.totalTrafficLabel=totalTrafficLabel;
	}

	public List<PcapNetworkInterface> getInterfaceList() {
		try {
			networkInterfaceList = Pcaps.findAllDevs();
			return networkInterfaceList;
		} catch (PcapNativeException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PcapNetworkInterface selectNIF(int num) {
		try {
			selectedInterface = networkInterfaceList.get(num);
		} catch (ArrayIndexOutOfBoundsException exception) {
			exception.printStackTrace();
		}
		return selectedInterface;
	}

	public void startCapturePacket(PcapNetworkInterface networkInterface) {
		try {
			handler = networkInterface.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
			PacketListener listener =
					new PacketListener() {
						@Override
						public void gotPacket(Packet packet) {
							System.out.println(packet);

							packetSummaryResult.addTrafficUsage(packet.length());
							packetSummaryResult.putOccurrence(packet.get(IpV4Packet.class).getHeader().getDstAddr().toString(), packet.get(IpV4Packet.class).getHeader().getSrcAddr().toString(), packet.length());
//							packetSummary.putOccurrence(packet.get(IpV4Packet.class).getHeader().getDstAddr().getCanonicalHostName());
//							outputConsole.append(packet.toString());
							PacketSummaryModel packetSummaryModel=readPacket(packet);
							packetStore.add(packetSummaryModel);
							addRow(packetSummaryModel.getAsArray());
							totalTrafficLabel.setText(String.valueOf(packetSummaryResult.getTrafficUsage()));
							totalPacketCount++;

//							outputConsole.append("\n-----------------------------------------\n");
						}
					};
			handler.loop(-1, listener);
		} catch (PcapNativeException | InterruptedException | NotOpenException e) {
			e.printStackTrace();
		}
	}

	public void stopCapturePacket() {
		try {
			handler.breakLoop();
		} catch (NotOpenException e) {
			e.printStackTrace();
		}
		outputConsole.append("----------------------------------  Total traffic usage : ");
		outputConsole.append(packetSummaryResult.getTrafficUsage().toString() + "byte");
		outputConsole.append("  ----------------------------------\n");
		outputConsole.append("----------------------------------  Total packet : ");
		outputConsole.append(String.valueOf(totalPacketCount));
		outputConsole.append("  ----------------------------------\n");

		outputConsole.append("Source Address\t\tDestination Address\t\tPacket Count\t\tRoute Type Total size\n");

		for (Map.Entry<PacketSummaryResult.PacketSummaryModel, PacketSummaryResult.packetInfo> occurrence : packetSummaryResult.getPacketOccurrence().entrySet()) {
			outputConsole.append(occurrence.getKey().toString() + "\t\t");
			outputConsole.append(occurrence.getValue().getCount() + "\t\t");
			outputConsole.append(occurrence.getValue().getLength() + "byte");
			outputConsole.append("\n");

		}
	}

	public void routePacket() {
	}

//	private PacketSummaryModel readPacket(long counter, String packetAsString) {
//		ArrayList<PacketDataModel> packetDataModelList = new ArrayList<>();
//		String packetType = "";
//		String[] firstSplit = packetAsString.split("\n(?=\\[)");
//		for (String statement : firstSplit) {
//			String[] secondSplit = statement.split("\n");
//			HashMap<String, String> infoMap = new HashMap<>();
//			for (String info : secondSplit) {
//				if (info.startsWith("[")) {
//					packetType = info;
//				} else {
//					String[] detailPacketData = info.split(": ");
//					infoMap.put(detailPacketData[0], detailPacketData[1]);
//				}
//			}
//			packetDataModelList.add(new PacketDataModel(packetType, infoMap));
//		}
//		return null;
//	}
	private PacketSummaryModel readPacket(Packet packet){
		PacketSummaryModel packetSummaryModel=new PacketSummaryModel();
		IpV4Packet ipV4Packet= packet.get(IpV4Packet.class);
		EthernetPacket ethernetPacket= packet.get(EthernetPacket.class);
		packetSummaryModel.setCounter(counter++);
		packetSummaryModel.setEthernetHeaderSource(ethernetPacket.getHeader().getSrcAddr().toString());
		packetSummaryModel.setEthernetHeaderDestination(ethernetPacket.getHeader().getDstAddr().toString());
		packetSummaryModel.setEthernetHeaderType(ethernetPacket.getHeader().getType().toString());
		packetSummaryModel.setiPv4HeaderSource(ipV4Packet.getHeader().getSrcAddr().toString().replace("/",""));
		packetSummaryModel.setiPv4HeaderDestination(ipV4Packet.getHeader().getDstAddr().toString().replace("/",""));
		packetSummaryModel.setiPv4HeaderProtocol(ipV4Packet.getHeader().getProtocol().name());
		packetSummaryModel.setiPv4HeaderTTL(String.valueOf(ipV4Packet.getHeader().getTtlAsInt()));
		packetSummaryModel.setiPv4HeaderTotalLength(String.valueOf(ipV4Packet.getHeader().getTotalLengthAsInt()));
		packetSummaryModel.setiPv4HeaderVersion(String.valueOf(ipV4Packet.getHeader().getVersion()));
		packetSummaryModel.setTotalLength(packet.length());

		return packetSummaryModel;
	}

	private void addRow(String[] data){
		table.getTableModel().addRow(data);
	}

}
//[Ethernet Header (14 bytes)]
//		Destination address: 5c:c7:d7:23:6b:32
//		Source address: d4:5d:64:25:6f:81
//		Type: 0x0800 (IPv4)
//		[IPv4 Header (20 bytes)]
//		Version: 4 (IPv4)
//		IHL: 5 (20 [bytes])
//		TOS: [precedence: 0 (Routine)] [tos: 0 (Default)] [mbz: 0]
//		Total length: 41 [bytes]
//		Identification: 9711
//		Flags: (Reserved, Don't Fragment, More Fragment) = (false, true, false)
//		FragmentFragment offset: 0 (0 [bytes])
//		TTL: 128
//		Protocol: 6 (TCP)
//		Header checksum: 0x67e2
//		Source address: /192.168.1.4
//		Destination address: /185.165.241.171
//		[TCP Header (20 bytes)]
//		Source port: 60090 (unknown)
//		Destination port: 443 (HTTPS)
//		Sequence Number: 21303096
//		Acknowledgment Number: 2886975891
//		Data Offset: 5 (20 [bytes])
//		Reserved: 0
//		URG: false
//		ACK: true
//		PSH: false
//		RST: false
//		SYN: false
//		FIN: false
//		Window: 512
//		Checksum: 0xd63b
//		Urgent Pointer: 0
//		[data (1 bytes)]
//		Hex stream: 00
