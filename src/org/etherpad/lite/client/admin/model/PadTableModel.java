package org.etherpad.lite.client.admin.model;

import javax.swing.table.AbstractTableModel;

public class PadTableModel extends AbstractTableModel {
	private String[] columnNames = new String[] {"Pad"};
	private Object[][] data;
	
	public PadTableModel(Object[][] data) {
		this.data = data;
	}
	
	public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}