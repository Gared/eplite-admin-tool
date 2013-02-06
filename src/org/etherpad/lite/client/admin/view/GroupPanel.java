package org.etherpad.lite.client.admin.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

public class GroupPanel extends JPanel {

	PadPanel padPanel = new PadPanel();
	JSplitPane splitPane = new JSplitPane();

	public GroupPanel(JTable padTable, String groupId) {
		padTable.getTableHeader().setReorderingAllowed(false);
		createGUI(padTable, groupId);
	}

	private void createGUI(JTable padTable, String groupId) {
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0;
		c.weightx = 0;

		JLabel groupNameLabel = new JLabel("Group ID: " + groupId);
		add(groupNameLabel, c);

		JPanel padListPanel = new JPanel();
		padListPanel.setLayout(new BorderLayout());
		padListPanel.add(new JScrollPane(padTable), BorderLayout.CENTER);
		padListPanel.setMinimumSize(new Dimension(100, 20));

		splitPane = new JSplitPane();
		splitPane.setLeftComponent(padListPanel);
		splitPane.setRightComponent(padPanel);
		splitPane.setDividerLocation(200 + splitPane.getInsets().left);
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;

		add(splitPane, c);
	}

	public void setPadPanel(PadPanel padPanel) {
		splitPane.setRightComponent(padPanel);
		this.padPanel = padPanel;
	}
}
