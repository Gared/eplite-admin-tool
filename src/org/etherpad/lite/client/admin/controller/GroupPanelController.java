package org.etherpad.lite.client.admin.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.etherpad.lite.client.admin.model.EPLite;
import org.etherpad.lite.client.admin.model.PadName;
import org.etherpad.lite.client.admin.model.PadTableModel;
import org.etherpad.lite.client.admin.view.GroupPanel;


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
		
		groupPanel = new GroupPanel(padTable);
		
		String[] padNames = epLite.getAllPads(actualGroupId);
		PadName[] padNameArr = new PadName[padNames.length];
		for (int i = 0; i < padNames.length; i++) {
			padNameArr[i] = new PadName(padNames[i]);
		}
		padTable.setModel(new PadTableModel(new PadName[][] { padNameArr }));

		padTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = padTable.getSelectedRow();

				if (e.getClickCount() == 2 && row != -1) {
					String padId = ((PadName)padTable.getValueAt(row, 0)).getPadName();
					padPanelController = new PadPanelController(padId);
					groupPanel.setPadPanel(padPanelController.getPadPanel());
				}
			}
		});
	}
	
	private void loadGroupPadPanel(String padId) {
		// epLite.getGroupPadPublicStatus(actualPadId);
		
		padPanelController = new PadPanelController(padId);
		groupPanel.setPadPanel(padPanelController.getPadPanel());
		
		/*if (padNames.length > 0) {
			groupPanel = new GroupPanel(padNames);
		} else {
			groupPanel = new GroupPanel(new String[] {"empty"});
		}
		mainView.showPanel(groupPanel);*/
		// loadPadPanel();
		// groupPanel.setPadPanel(padPanel);
	}
	
	public GroupPanel getGroupPanel() {
		return groupPanel;
	}
}
