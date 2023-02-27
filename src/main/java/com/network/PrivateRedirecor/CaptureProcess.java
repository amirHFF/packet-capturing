package com.network.PrivateRedirecor;

import com.network.controller.MainSwingController;
import com.network.controller.TableInitializer;
import com.network.model.PacketDataModel;
import com.network.model.PacketSummaryModel;
import org.pcap4j.core.*;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

import javax.swing.*;
import java.awt.*;
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
    private HashMap<Long,PacketSummaryModel> packetStore = new HashMap<>();
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
                            counter++;
                            packetSummaryResult.addTrafficUsage(packet.length());
                            if (packet.get(IpV4Packet.class) != null) {
                                packetSummaryResult.putOccurrence(packet.get(IpV4Packet.class).getHeader().getDstAddr().toString(), packet.get(IpV4Packet.class).getHeader().getSrcAddr().toString(), packet.length());
//							packetSummary.putOccurrence(packet.get(IpV4Packet.class).getHeader().getDstAddr().getCanonicalHostName());
//							outputConsole.append(packet.toString());
                                PacketSummaryModel packetSummaryModel = readPacket(packet);
                                packetSummaryModel.setCompleteData(packet.toString());
                                packetStore.put(counter,packetSummaryModel);
                                addRow(packetSummaryModel.getAsArray());

                            } else {
                                packetSummaryResult.putOccurrence(packet.get(EthernetPacket.class).getHeader().getDstAddr().toString(), packet.get(EthernetPacket.class).getHeader().getSrcAddr().toString(), packet.length());
                                PacketSummaryModel packetSummaryModel = readPacket(packet);
                                packetStore.put(counter,packetSummaryModel);
                                addRow(packetSummaryModel.getAsArray());
                                packetSummaryModel.setCompleteData(packet.toString());

                            }
                            table.getTable().scrollRectToVisible(new Rectangle(0,table.getTable().getPreferredSize().height,1,1));
                            MainSwingController.totalTrafficUsedLabel.setText(packetSummaryResult.getTrafficUsage().toString());
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
    private PacketSummaryModel readPacket(Packet packet) {
        PacketSummaryModel packetSummaryModel = new PacketSummaryModel();
        IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
        EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
        packetSummaryModel.setCounter(counter);
        packetSummaryModel.setEthernetHeaderSource(ethernetPacket.getHeader().getSrcAddr().toString());
        packetSummaryModel.setEthernetHeaderDestination(ethernetPacket.getHeader().getDstAddr().toString());
        packetSummaryModel.setEthernetHeaderType(ethernetPacket.getHeader().getType().toString());
        if (packet.get(IpV4Packet.class) != null) {
            packetSummaryModel.setiPv4HeaderSource(ipV4Packet.getHeader().getSrcAddr().toString().replace("/", ""));
            packetSummaryModel.setiPv4HeaderDestination(ipV4Packet.getHeader().getDstAddr().toString().replace("/", ""));
            packetSummaryModel.setiPv4HeaderProtocol(ipV4Packet.getHeader().getProtocol().name());
            packetSummaryModel.setiPv4HeaderTTL(String.valueOf(ipV4Packet.getHeader().getTtlAsInt()));
            packetSummaryModel.setiPv4HeaderTotalLength(String.valueOf(ipV4Packet.getHeader().getTotalLengthAsInt()));
            packetSummaryModel.setiPv4HeaderVersion(String.valueOf(ipV4Packet.getHeader().getVersion()));
            packetSummaryModel.setTotalLength(packet.length());
        }
        return packetSummaryModel;
    }

    private void addRow(String[] data) {
        table.getTableModel().addRow(data);
    }

    public HashMap<Long, PacketSummaryModel> getPacketStore() {
        return packetStore;
    }
}
