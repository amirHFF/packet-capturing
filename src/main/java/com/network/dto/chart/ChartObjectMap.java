package com.network.dto.chart;

import java.util.HashMap;

public class ChartObjectMap<K,V> extends HashMap<ChartableKey,ChartableValue> {


	@Override
	public ChartableValue put(ChartableKey key, ChartableValue value) {
		if (key != null && value != null) {
			ChartableValue existedValue =  get(key);
			if (existedValue == null) {
				return super.put(key, value);
			} else {
				existedValue.addTrafficUsage(value.getTrafficUsage());
				existedValue.CountPlus();
				return super.replace(key, existedValue);
			}
		} else {
			throw new RuntimeException("wrong map creation");
		}
	}
}
