import com.network.controller.MainSwingController;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.pcap4j.core.*;
import org.pcap4j.packet.IpPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.util.NifSelector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {
	public static void main(String[] args) {
		MainSwingController swingController=new MainSwingController();

	}

	public static String hexToString(Packet packet) {
		byte[] packetByte;
		try {
			packetByte = Hex.decodeHex(packet.toString().toCharArray());
			return new String(packetByte, "UTF-8");
		} catch (DecoderException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String byteToString(byte[] packetByte) {
		try {
			return new String(packetByte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
