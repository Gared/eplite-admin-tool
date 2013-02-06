package org.etherpad.lite.client.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.etherpad.lite.client.admin.model.EPLite;
import org.etherpad.lite.client.admin.view.ManagePadsView;
import org.etherpad_lite_client.EPLiteException;

public class ManagePadsController {
	public ManagePadsController(List groupIds, final MainViewController mainViewController) {
		final ManagePadsView managePadView = new ManagePadsView();
		managePadView.setGroupComboBoxValues(groupIds);

		managePadView.setGroupComboBoxActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String groupId = (String) cb.getSelectedItem();
				List<String> pads = null;
				try {
					if (cb.getSelectedIndex() == 1) {
						List<String> allPads = EPLite.getInstance().getAllPads();
						pads = new ArrayList<String>();
						for (int i = 0; i < allPads.size(); i++) {
							if (!allPads.get(i).toString().contains("$")) {
								pads.add(allPads.get(i));
							}
						}
					} else if (cb.getSelectedIndex() > 1) {
						pads = EPLite.getInstance().getAllPads(groupId);
					}
				} catch (EPLiteException epEx) {
					epEx.printStackTrace();
					JOptionPane.showMessageDialog(managePadView, "Error: " + epEx.getMessage(), "Error listing pads", JOptionPane.ERROR_MESSAGE);
				}
				managePadView.setPadComboBoxValues(pads);
			}
		});

		managePadView.setOpenButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String padName = managePadView.getPadNamePadComboBox();
				if (padName != null) {
					mainViewController.openPad(padName);
					managePadView.dispose();
				}
			}
		});

		managePadView.setDeleteButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String padName = managePadView.getPadNamePadComboBox();
				if (padName != null) {
					try {
						EPLite.getInstance().deletePad(padName);
						JOptionPane.showMessageDialog(managePadView, "Pad '" + padName + "' was deleted", "Pad deleted",
								JOptionPane.INFORMATION_MESSAGE);
						managePadView.dispose();
					} catch (EPLiteException epEx) {
						epEx.printStackTrace();
						JOptionPane.showMessageDialog(managePadView, "Error: " + epEx.getMessage(), "Error deleting pad", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		managePadView.setVisible(true);
	}
}
