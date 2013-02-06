package org.etherpad.lite.client.admin.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.etherpad.lite.client.admin.model.EPLite;
import org.etherpad.lite.client.admin.model.PadName;
import org.etherpad.lite.client.admin.model.PadTableModel;
import org.etherpad.lite.client.admin.view.GroupPanel;
import org.etherpad_lite_client.EPLiteException;

public class GroupPanelController {

	private GroupPanel groupPanel = null;
	private JTable padTable = null;
	private PadPanelController padPanelController = null;
	private String actualGroupId = null;
	private EPLite epLite = EPLite.getInstance();

	public GroupPanelController(String groupId) {
		actualGroupId = groupId;
		padTable = new JTable();
		padTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		padTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		groupPanel = new GroupPanel(padTable, actualGroupId);

		List padNames = epLite.getAllPads(actualGroupId);
		final ArrayList<Object[]> padList = new ArrayList<Object[]>(padNames.size());
		for (int i = 0; i < padNames.size(); i++) {
			padList.add(new Object[] { new PadName(padNames.get(i).toString()), epLite.getGroupPadPublicStatus(padNames.get(i).toString()) });
		}
		padTable.setModel(new PadTableModel(padList));

		padTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = padTable.getSelectedRow();

				if (e.getClickCount() == 2 && row != -1) {
					String padId = ((PadName) padList.get(row)[0]).getPadName();
					loadGroupPadPanel(padId);
				}
			}
		});
	}

	public void loadGroupPadPanel(String padId) {
		try {
			padPanelController = new PadPanelController(padId, true);
			groupPanel.setPadPanel(padPanelController.getPadPanel());
		} catch (EPLiteException e) {
			JOptionPane.showMessageDialog(groupPanel, "Error: " + e.getMessage(), "Error loading pad", JOptionPane.ERROR_MESSAGE);
		}
	}

	public GroupPanel getGroupPanel() {
		return groupPanel;
	}
}
