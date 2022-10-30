package com.network.PrivateRedirecor;

import com.network.controller.MainSwingController;
import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CaptureProcess {
	private static int COUNT = 2000;
	private static int READ_TIMEOUT = 50; // [ms]
	private static int SNAPLEN = 65536; // [bytes]
	private List<PcapNetworkInterface> networkInterfaceList;
	private PcapNetworkInterface selectedInterface;
	private PcapHandle handler;

	private JTextArea outputConsole;

	public CaptureProcess() {
	}

	public CaptureProcess(JTextArea console) {
		outputConsole=console;
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
	public PcapNetworkInterface selectNIF(int num){
		try {
		selectedInterface = networkInterfaceList.get(num);
		}catch (ArrayIndexOutOfBoundsException exception){
			exception.printStackTrace();
		}
		return selectedInterface;
	}
	public void capturePacket(PcapNetworkInterface networkInterface){
		try {
			handler = networkInterface.openLive(SNAPLEN , PcapNetworkInterface.PromiscuousMode.PROMISCUOUS,READ_TIMEOUT);
			PacketListener listener =
					new PacketListener() {
						@Override
						public void gotPacket(Packet packet) {
							System.out.println(packet);
							outputConsole.append(packet.toString());
							outputConsole.append("\n");
						}
					};
			handler.loop(COUNT,listener);
		} catch (PcapNativeException | InterruptedException | NotOpenException e) {
			e.printStackTrace();
		}
	}
	public void stopCapture(){
		try {
			handler.breakLoop();
		} catch (NotOpenException e) {
			e.printStackTrace();
		}
	}

}
