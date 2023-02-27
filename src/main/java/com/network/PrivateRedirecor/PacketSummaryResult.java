package com.network.PrivateRedirecor;

import java.math.BigDecimal;
import java.util.*;

public class PacketSummaryResult {
	private BigDecimal trafficUsage = new BigDecimal(0);
	private HashSet<PacketSummaryModel> allPackSet = new HashSet<>();
	private HashMap<PacketSummaryModel, PacketSummaryResult.packetInfo > packetOccurrence = new HashMap<>();

	public BigDecimal getTrafficUsage() {
		return trafficUsage;
	}

	public void setTrafficUsage(BigDecimal trafficUsage) {
		this.trafficUsage = trafficUsage;
	}

	public void addTrafficUsage(long traffic) {
		trafficUsage = trafficUsage.add(new BigDecimal(traffic));
	}

	public void putOccurrence(String source, String dest, long length) {
		packetInfo occurrence = packetOccurrence.get(new PacketSummaryModel(source, dest));
		if (occurrence == null) {
			packetOccurrence.put(new PacketSummaryModel(source, dest), new packetInfo(1, length));
		}
		else {
			occurrence.count++;
			occurrence.length+=length;
			packetOccurrence.put(new PacketSummaryModel(source, dest), occurrence);
		}
	}

	public void addPacket(String source, String dest) {
		allPackSet.add(new PacketSummaryModel(source, dest));
	}

//	public void calculateOccurrence() {
//		for (PacketSummaryModel summaryModel : allPackSet) {
//			putOccurrence(summaryModel.source, summaryModel.destination, summaryModel.thisSourceSize);
//		}
//	}

	public HashSet<PacketSummaryModel> getAllPackSet() {
		return allPackSet;
	}

	public HashMap<PacketSummaryModel, packetInfo> getPacketOccurrence() {
		return packetOccurrence;
	}

	class PacketSummaryModel {
		private String source;
		private String destination;

		public PacketSummaryModel(String source, String destination) {
			this.source = source;
			this.destination = destination;
		}

		public PacketSummaryModel(String source, String destination, long thisSourceSize) {
			this.source = source;
			this.destination = destination;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getDestination() {
			return destination;
		}



		public void setDestination(String destination) {
			this.destination = destination;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			PacketSummaryModel that = (PacketSummaryModel) o;
			return Objects.equals(source, that.source) &&
					Objects.equals(destination, that.destination);
		}

		@Override
		public int hashCode() {
			return Objects.hash(source, destination);
		}

		@Override
		public String toString() {
			return
					  source + "\t\t "+ destination ;
		}
	}
	class packetInfo{
		private int count;
		private long length;

		public packetInfo(int count, long length) {
			this.count = count;
			this.length = length;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public long getLength() {
			return length;
		}

		public void setLength(long length) {
			this.length = length;
		}
	}
}
