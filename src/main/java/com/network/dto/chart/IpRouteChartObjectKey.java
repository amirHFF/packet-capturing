package com.network.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class IpRouteChartObjectKey implements ChartableKey {
	private String sourceAddress;
	private String destinationAddress;

	@Override
	public String getName() {
		return sourceAddress+" - "+destinationAddress;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof IpRouteChartObjectKey)) return false;
		IpRouteChartObjectKey that = (IpRouteChartObjectKey) o;
		return sourceAddress.equals(that.sourceAddress) &&
				destinationAddress.equals(that.destinationAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sourceAddress, destinationAddress);
	}
}
