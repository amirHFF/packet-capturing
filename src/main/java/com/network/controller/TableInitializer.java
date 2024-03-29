package com.network.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TableInitializer {
	JTable table;
	DefaultTableModel tableModel;

	public TableInitializer(JTable table) {
		this.table = table;
		tableModel = new DefaultTableModel();
		table.setModel(tableModel);
		setTableColumn();
	}

	public JTable getTable() {
		return table;
	}


	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
	private void setTableColumn(){
	tableModel.addColumn("num");
	tableModel.addColumn("IPv4 Source");
	tableModel.addColumn("IPv4 Destination");
	tableModel.addColumn("Source port");
	tableModel.addColumn("Destination port");
	tableModel.addColumn("Ethernet Source");
	tableModel.addColumn("Ethernet Destination");
	tableModel.addColumn("Protocol");
	tableModel.addColumn("Ethernet Type");
	tableModel.addColumn("Packet Version");
	tableModel.addColumn("Packet TTL");
	tableModel.addColumn("Total length");
	}
}
