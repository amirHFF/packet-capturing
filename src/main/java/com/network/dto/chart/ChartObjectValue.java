package com.network.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartObjectValue implements ChartableValue {
	private long totalLength;
	private long packetCount = 1;

	public ChartObjectValue(long totalLength) {
		this.totalLength = totalLength;
	}

	@Override
	public long getCount() {
		return getPacketCount();
	}

	@Override
	public void CountPlus() {
		packetCount++;
	}

	@Override
	public long getTrafficUsage() {
		return totalLength;
	}

	@Override
	public void addTrafficUsage(long traffic) {
		totalLength+=traffic;
	}
}
