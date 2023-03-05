package com.network.dto.chart;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class PortChartObjectKey implements ChartableKey {
	private String port;

	@Override
	public String getName() {
		return port;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PortChartObjectKey)) return false;
		PortChartObjectKey that = (PortChartObjectKey) o;
		return port.equals(that.port);
	}

	@Override
	public int hashCode() {
		return Objects.hash(port);
	}

}
