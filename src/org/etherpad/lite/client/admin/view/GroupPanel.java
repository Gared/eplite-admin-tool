package org.etherpad.lite.client.admin.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SplashScreen;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

public class GroupPanel extends JPanel {
	
	PadPanel padPanel = new PadPanel();
	JSplitPane splitPane = new JSplitPane();
	
	public GroupPanel(JTable padTable) {
		padTable.getTableHeader().setReorderingAllowed(false);
		createGUI(padTable);
	}

	private void createGUI(JTable padTable) {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 1;

		JPanel padListPanel = new JPanel();
		padListPanel.setLayout(new BorderLayout());
		padListPanel.add(new JScrollPane(padTable), BorderLayout.CENTER);
		padListPanel.setMinimumSize(new Dimension(100, 20));
//		padTable.setPreferredSize(new Dimension(200, 200));
//		padListPanel.setMinimumSize(new Dimension(100, 100));
		
		splitPane = new JSplitPane();
		splitPane.setLeftComponent(padListPanel);
		splitPane.setRightComponent(padPanel);
//		splitPane.setPreferredSize(new Dimension(300, 600));
//		this.pac
		splitPane.setDividerLocation(200 + splitPane.getInsets().left);
//		splitPane.validate();
		
		add(splitPane, c);
	}
	
	public void setPadPanel(PadPanel padPanel) {
		splitPane.setRightComponent(padPanel);
		this.padPanel = padPanel;
	}
}
