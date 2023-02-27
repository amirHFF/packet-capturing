package com.network.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomTable extends JTable {

    public CustomTable(DefaultTableModel tableModel) {
        super(tableModel);
    }

    @Override
    public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
        super.changeSelection(rowIndex, columnIndex, toggle, extend);
        if (rowIndex >= 0) {
            firePropertyChange("rowSelected", -1, rowIndex);
        }
    }
}
