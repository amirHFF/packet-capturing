package com.network.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PacketSummaryModel {

	private long counter;
	private String ethernetHeaderDestination;
	private String ethernetHeaderSource;
	private String ethernetHeaderType;
	private String iPv4HeaderSource;
	private String iPv4HeaderDestination;
	private String iPv4HeaderVersion;
	private String iPv4HeaderTotalLength;
	private String iPv4HeaderTTL;
	private String iPv4HeaderProtocol;
	private long totalLength;

	public PacketSummaryModel() {
	}

	public PacketSummaryModel(String ethernetHeaderDestination, String ethernetHeaderSource, String ethernetHeaderType, String iPv4HeaderSource, String iPv4HeaderDestination,
							  String iPv4HeaderVersion, String iPv4HeaderTotalLength, String iPv4HeaderTTL , long totalLength,
							  String iPv4HeaderProtocol) {
		this.ethernetHeaderDestination = ethernetHeaderDestination;
		this.ethernetHeaderSource = ethernetHeaderSource;
		this.ethernetHeaderType = ethernetHeaderType;
		this.iPv4HeaderSource = iPv4HeaderSource;
		this.iPv4HeaderDestination = iPv4HeaderDestination;
		this.iPv4HeaderVersion = iPv4HeaderVersion;
		this.iPv4HeaderTotalLength = iPv4HeaderTotalLength;
		this.iPv4HeaderTTL = iPv4HeaderTTL;
		this.iPv4HeaderProtocol = iPv4HeaderProtocol;
		this.totalLength = totalLength;
	}

	public long getCounter() {
		return counter;
	}

	public void setCounter(long counter) {
		this.counter = counter;
	}

	public long getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(long totalLength) {
		this.totalLength = totalLength;
	}

	public String getEthernetHeaderDestination() {
		return ethernetHeaderDestination;
	}

	public void setEthernetHeaderDestination(String ethernetHeaderDestination) {
		this.ethernetHeaderDestination = ethernetHeaderDestination;
	}

	public String getEthernetHeaderSource() {
		return ethernetHeaderSource;
	}

	public void setEthernetHeaderSource(String ethernetHeaderSource) {
		this.ethernetHeaderSource = ethernetHeaderSource;
	}

	public String getEthernetHeaderType() {
		return ethernetHeaderType;
	}

	public void setEthernetHeaderType(String ethernetHeaderType) {
		this.ethernetHeaderType = ethernetHeaderType;
	}

	public String getiPv4HeaderSource() {
		return iPv4HeaderSource;
	}

	public void setiPv4HeaderSource(String iPv4HeaderSource) {
		this.iPv4HeaderSource = iPv4HeaderSource;
	}

	public String getiPv4HeaderDestination() {
		return iPv4HeaderDestination;
	}

	public void setiPv4HeaderDestination(String iPv4HeaderDestination) {
		this.iPv4HeaderDestination = iPv4HeaderDestination;
	}

	public String getiPv4HeaderVersion() {
		return iPv4HeaderVersion;
	}

	public void setiPv4HeaderVersion(String iPv4HeaderVersion) {
		this.iPv4HeaderVersion = iPv4HeaderVersion;
	}

	public String getiPv4HeaderTotalLength() {
		return iPv4HeaderTotalLength;
	}

	public void setiPv4HeaderTotalLength(String iPv4HeaderTotalLength) {
		this.iPv4HeaderTotalLength = iPv4HeaderTotalLength;
	}

	public String getiPv4HeaderTTL() {
		return iPv4HeaderTTL;
	}

	public void setiPv4HeaderTTL(String iPv4HeaderTTL) {
		this.iPv4HeaderTTL = iPv4HeaderTTL;
	}

	public String getiPv4HeaderProtocol() {
		return iPv4HeaderProtocol;
	}

	public void setiPv4HeaderProtocol(String iPv4HeaderProtocol) {
		this.iPv4HeaderProtocol = iPv4HeaderProtocol;
	}

	public String[] getAsArray() {
		return new String[]{
				String.valueOf(counter),
				ethernetHeaderDestination,
				ethernetHeaderSource,
				ethernetHeaderType,
				iPv4HeaderSource,
				iPv4HeaderDestination,
				iPv4HeaderVersion,
				iPv4HeaderProtocol,
				iPv4HeaderTTL,
				String.valueOf(totalLength)
				 };
	}
}
