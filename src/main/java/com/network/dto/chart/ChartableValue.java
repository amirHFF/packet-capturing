package com.network.dto.chart;

public interface ChartableValue {
	long getCount();
	void CountPlus();
	long getTrafficUsage();
	void addTrafficUsage(long traffic);
}
