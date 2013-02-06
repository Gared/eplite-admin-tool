package org.etherpad.lite.client.admin.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class GroupListView extends JDialog {

	private JTable groupIdTable = null;
	private JButton openButton = null;

	public GroupListView() {
		super(null, JDialog.DEFAULT_MODALITY_TYPE);
		createGUI();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/icons/eplite.png")));

		setMinimumSize(new Dimension(300, 400));
		CenteredFrame.center(this, getWidth(), getHeight());
	}

	private void createGUI() {
		JPanel panel = new JPanel(new BorderLayout());

		groupIdTable = new JTable();
		groupIdTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		groupIdTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel.add(new JScrollPane(groupIdTable), BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new BorderLayout());
		openButton = new JButton("Open");
		buttonPanel.add(openButton, BorderLayout.WEST);
		JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(cancelButton, BorderLayout.EAST);

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		panel.add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(panel);
	}

	public JTable getGroupIdTable() {
		return groupIdTable;
	}

	public void setGroupIdTableModel(DefaultTableModel d) {
		groupIdTable.setModel(d);
	}

	public void setOpenButtonListener(ActionListener a) {
		openButton.addActionListener(a);
	}
}
