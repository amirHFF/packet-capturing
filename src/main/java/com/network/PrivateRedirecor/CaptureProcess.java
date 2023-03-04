package com.network.PrivateRedirecor;

import com.network.controller.MainPanel;
import com.network.controller.MainSwingController;
import com.network.controller.TableInitializer;
import com.network.dto.*;
import com.network.dto.chart.ChartObjectMap;
import com.network.dto.chart.ChartObjectValue;
import com.network.dto.chart.IpRouteChartObjectKey;
import com.network.dto.chart.PortChartObjectKey;
import com.network.model.PacketSummaryModel;
import org.pcap4j.core.*;
import org.pcap4j.packet.*;

import javax.swing.*;
import java.awt.*;
import java.net.Inet4Address;
import java.util.*;
import java.util.List;

public class CaptureProcess {
	//    private long totalPacketCount = 0;
	private long counter = 0;
	private static int READ_TIMEOUT = 50; // [ms]
	private static int SNAPLEN = 65536; // [bytes]
	private List<PcapNetworkInterface> networkInterfaceList;
	private PcapNetworkInterface selectedInterface;
	private PcapHandle handler;
	private PacketSummaryResult packetSummaryResult = new PacketSummaryResult();
	private HashMap<Long, PacketDto> packetStore = new HashMap<>();
	public static ChartObjectMap<IpRouteChartObjectKey, ChartObjectValue> ipRouteChartMap = new ChartObjectMap();
	public static ChartObjectMap<PortChartObjectKey, ChartObjectValue> portChartMap = new ChartObjectMap();
	private TableInitializer table;
	private JTextArea outputConsole;

	public CaptureProcess() {
	}

	public CaptureProcess(JTextArea console, TableInitializer table) {
		outputConsole = console;
		this.table = table;
	}

	public List<PcapNetworkInterface> getInterfaceList() {
		try {
			networkInterfaceList = Pcaps.findAllDevs();
			return networkInterfaceList;
		} catch (PcapNativeException e) {
			e.printStackTrace();
			MainSwingController.throwWarning(e.getMessage());
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
							PacketDto packetDto = new PacketDto(counter++, packet.length());
							packetSummaryResult.addTrafficUsage(packet.length());

							if (packet.get(IpV4Packet.class) != null) {
								packetDto.setIPv4PacketDto(IPv4PacketDto.build(packet.get(IpV4Packet.class)));
							}
							if (packet.get(EthernetPacket.class) != null) {
								packetDto.setEthernetPacketDto(EthernetPacketDto.build(packet.get(EthernetPacket.class)));
							}
							if (packet.get(TcpPacket.class) != null) {
								packetDto.setTcpPacketDto(TcpPacketDto.build(packet.get(TcpPacket.class)));
							}
							if (packet.get(UdpPacket.class) != null) {
								packetDto.setUdpPacketDto(UdpPacketDto.build(packet.get(UdpPacket.class)));
							}
							addRow(PacketSummaryModel.buildArray(packetDto));
							packetStore.put(counter, packetDto);
							table.getTable().scrollRectToVisible(new Rectangle(0, table.getTable().getPreferredSize().height, 1, 1));
							MainPanel.totalTrafficUsedLabel.setText(packetSummaryResult.getTrafficUsage().toString());
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

		chartCalculator();
		outputConsole.append("----------------------------------  Total traffic usage : ");
		outputConsole.append(packetSummaryResult.getTrafficUsage().toString() + "byte");
		outputConsole.append("  ----------------------------------\n");
		outputConsole.append("----------------------------------  Total packet : ");
		outputConsole.append(String.valueOf(counter));
		outputConsole.append("  ----------------------------------\n");

		outputConsole.append("Source Address\t\tDestination Address\t\tPacket Count\t\tRoute Type Total size\n");

		for (Map.Entry<PacketSummaryResult.PacketSummaryModel, PacketSummaryResult.packetInfo> occurrence : packetSummaryResult.getPacketOccurrence().entrySet()) {
			outputConsole.append(occurrence.getKey().toString() + "\t\t");
			outputConsole.append(occurrence.getValue().getCount() + "\t\t");
			outputConsole.append(occurrence.getValue().getLength() + "byte");
			outputConsole.append("\n");

		}
	}

	private void addRow(String[] data) {
		table.getTableModel().addRow(data);
	}

	public HashMap<Long, PacketDto> getPacketStore() {
		return packetStore;
	}

	private String getIpv4AdapterAddress() {
		for (PcapAddress address : selectedInterface.getAddresses()) {
			if (address.getAddress() instanceof Inet4Address) {
				return address.getAddress().getHostAddress();
			}
		}
		return null;
	}

	private void chartCalculator() {
		String adapterIpV4Address = getIpv4AdapterAddress();
		for (Map.Entry<Long, PacketDto> packetDto : packetStore.entrySet()) {
			if (packetDto.getValue().getIPv4PacketDto() != null) {
				ipRouteChartMap.put(new IpRouteChartObjectKey(packetDto.getValue().getIPv4PacketDto().getSrcAddr(), packetDto.getValue().getIPv4PacketDto().getDstAddr()),
						new ChartObjectValue(packetDto.getValue().getLength()));

				if (packetDto.getValue().getTcpPacketDto() != null) {
					if (packetDto.getValue().getIPv4PacketDto().getSrcAddr().equals(adapterIpV4Address)) {
						portChartMap.put(new PortChartObjectKey(String.valueOf(packetDto.getValue().getTcpPacketDto().getSrcPort().getPort()))
								, new ChartObjectValue(packetDto.getValue().getLength()));

					} else if (packetDto.getValue().getIPv4PacketDto().getDstAddr().equals(adapterIpV4Address)) {
						portChartMap.put(new PortChartObjectKey(String.valueOf(packetDto.getValue().getTcpPacketDto().getDstPort().getPort()))
								, new ChartObjectValue(packetDto.getValue().getLength()));
					}
				}
			}
			else if (packetDto.getValue().getEthernetPacketDto() !=null){
				ipRouteChartMap.put(new IpRouteChartObjectKey(packetDto.getValue().getEthernetPacketDto().getEthernetHeaderSource()
								, packetDto.getValue().getEthernetPacketDto().getEthernetHeaderDestination()),
						new ChartObjectValue(packetDto.getValue().getLength()));
			}
		}
	}
}
