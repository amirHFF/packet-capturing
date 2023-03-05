package com.network.dto.chart;

import java.util.Objects;

public class ProtocolChartObjectKey implements ChartableKey {

    private String protocolName;

    public ProtocolChartObjectKey(String protocolName) {
        this.protocolName = protocolName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProtocolChartObjectKey)) return false;
        ProtocolChartObjectKey that = (ProtocolChartObjectKey) o;
        return protocolName.equals(that.protocolName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocolName);
    }

    @Override
    public String getName() {
        return protocolName;
    }
}
