package org.etherpad.lite.client.admin.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.etherpad.lite.client.admin.model.PadName;

public class ManagePadsView extends JDialog {

	private JComboBox groupComboBox = null;
	private JComboBox padComboBox = null;
	private JButton openButton = null;
	private JButton deleteButton = null;

	public ManagePadsView() {
		super(null, "Manage pads", JDialog.DEFAULT_MODALITY_TYPE);
		createGUI();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/icons/eplite.png")));

		setMinimumSize(new Dimension(300, 400));
		CenteredFrame.center(this, getWidth(), getHeight());
	}

	private void createGUI() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(3, 3, 3, 3);

		JLabel groupLabel = new JLabel("Groups: ");
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		panel.add(groupLabel, c);

		groupComboBox = new JComboBox();
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		panel.add(groupComboBox, c);

		JLabel padLabel = new JLabel("Pads: ");
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0;
		panel.add(padLabel, c);

		padComboBox = new JComboBox();
		padComboBox.setEnabled(false);
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 0;
		panel.add(padComboBox, c);

		JPanel buttonPanel = new JPanel(new BorderLayout());
		openButton = new JButton("Open");
		buttonPanel.add(openButton, BorderLayout.WEST);
		deleteButton = new JButton("Delete");
		buttonPanel.add(deleteButton, BorderLayout.CENTER);
		JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(cancelButton, BorderLayout.EAST);

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		c.gridx = 0;
		c.gridy = 5;
		c.weightx = 1;
		c.weighty = 0;
		panel.add(buttonPanel, c);
		getContentPane().add(panel);
	}

	public void setGroupComboBoxValues(List groupIds) {
		if (groupIds != null) {
			groupComboBox.addItem("");
			groupComboBox.addItem("Public pads");
			for (int i = 0; i < groupIds.size(); i++) {
				groupComboBox.addItem(groupIds.get(i));
			}
		}
	}

	public void setGroupComboBoxActionListener(ActionListener a) {
		groupComboBox.addActionListener(a);
	}

	public void setPadComboBoxValues(List padIds) {
		padComboBox.removeAllItems();
		if (padIds != null) {
			for (int i = 0; i < padIds.size(); i++) {
				padComboBox.addItem(new PadName(padIds.get(i).toString()));
			}
			padComboBox.setEnabled(true);
		} else {
			padComboBox.setEnabled(false);
		}
	}

	public void setOpenButtonListener(ActionListener a) {
		openButton.addActionListener(a);
	}

	public void setDeleteButtonListener(ActionListener a) {
		deleteButton.addActionListener(a);
	}

	public String getPadNamePadComboBox() {
		PadName pad = (PadName) padComboBox.getSelectedItem();
		if (pad != null) {
			return pad.getPadName();
		}
		return null;
	}
}
