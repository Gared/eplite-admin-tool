package org.etherpad.lite.client.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.etherpad.lite.client.admin.view.GroupListView;

public class GroupListController {
	GroupListView groupListView = null;

	public GroupListController(List groupIds, final MainViewController mainViewController) {
		groupListView = new GroupListView();
		DefaultTableModel tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableModel.addColumn("Group IDs");
		for (int i = 0; i < groupIds.size(); i++) {
			tableModel.addRow(new String[] { groupIds.get(i).toString() });
		}
		groupListView.setGroupIdTableModel(tableModel);

		groupListView.getGroupIdTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = groupListView.getGroupIdTable().getSelectedRow();

				if (e.getClickCount() == 2 && row != -1) {
					String groupId = groupListView.getGroupIdTable().getValueAt(row, 0).toString();
					mainViewController.openGroup(groupId);
					groupListView.dispose();
				}
			}
		});

		groupListView.setOpenButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = groupListView.getGroupIdTable().getSelectedRow();
				String groupId = groupListView.getGroupIdTable().getValueAt(row, 0).toString();
				mainViewController.openGroup(groupId);
				groupListView.dispose();
			}
		});

		groupListView.setVisible(true);
	}
}
