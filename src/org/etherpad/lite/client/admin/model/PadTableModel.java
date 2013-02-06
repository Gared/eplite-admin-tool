package org.etherpad.lite.client.admin.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class PadTableModel extends AbstractTableModel {
	private String[] columnNames = new String[] { "Pads", "Private" };
	private ArrayList<Object[]> data;

	public PadTableModel(ArrayList<Object[]> data) {
		this.data = data;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		Object[] rowArr = (Object[]) data.get(row);
		switch (col) {
		case 0:
			return ((PadName) rowArr[0]).getPadNameShort();
		case 1:
			return (Boolean) rowArr[1];
		default:
			return "";
		}
	}

	public Class getColumnClass(int c) {
		Class retClass = null;

		switch (c) {
		case 0:
			retClass = String.class;
			break;
		case 1:
			retClass = Boolean.class;
			break;
		}
		return retClass;
	}
}