package org.etherpad.lite.client.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.etherpad.lite.client.admin.model.EPLite;
import org.etherpad.lite.client.admin.view.AboutPanel;
import org.etherpad.lite.client.admin.view.MainView;
import org.etherpad_lite_client.EPLiteException;


public class MainViewController {
	private MainView mainView;
	private SettingsViewController settingsViewController;

	private String actualPadId = null;
	private String actualGroupId = null;
	
	private GroupPanelController groupPanelController = null;
	private PadPanelController padPanelController = null;

	private EPLite epLite = EPLite.getInstance();

	public MainViewController() {
		mainView = new MainView();
		if (epLite.hasServerConfig()) {
			mainView.enableAdministrationMenu();
		}
		addListener();
	}

	private void addListener() {
		mainView.setAboutMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutPanel();
			}
		});
		
		mainView.setServerConfigMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Show settings window
				settingsViewController = new SettingsViewController(mainView);
			}
		});

		mainView.setCreateGroupMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				epLite.createGroup();
			}
		});

		mainView.setOpenPadMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String padId = JOptionPane.showInputDialog("Pad ID");
				if (padId == null) {
					return;
				}
				actualPadId = padId;
				try {
					padPanelController = new PadPanelController(actualPadId);
					mainView.showPanel(padPanelController.getPadPanel());

					// epLite.isGroupPadPasswordProtected(padId);
				} catch (EPLiteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(
							mainView,
							"Error: "
									+ e.getMessage()
									+ ". Are you sure you entered the correct padId?",
							"Error loading pad", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		mainView.setCreatePadMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String padId = JOptionPane.showInputDialog("Pad ID");
				if (padId == null) {
					return;
				}
				try {
					if (actualGroupId != null) {
						actualPadId = actualGroupId + "$" + padId;
						epLite.createGroupPad(actualGroupId, padId);
						groupPanelController = new GroupPanelController(actualGroupId);

						mainView.showPanel(groupPanelController.getGroupPanel());
					} else {
						actualPadId = padId;
						epLite.createPad(padId);
						padPanelController = new PadPanelController(padId);
						mainView.showPanel(padPanelController.getPadPanel());
					}
				} catch (EPLiteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(mainView,
							"Error: " + e.getMessage(), "Error creating pad",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		mainView.setDeletePadMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (actualPadId == null) {
					String padId = JOptionPane.showInputDialog("Pad ID");
					if (padId == null) {
						return;
					}
					epLite.deletePad(padId);
				} else {
					epLite.deletePad(actualPadId);
				}
			}
		});

		mainView.setOpenGroupMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String groupId = JOptionPane.showInputDialog("Group ID");
				if (groupId == null) {
					return;
				}

				actualGroupId = groupId;
				groupPanelController = new GroupPanelController(actualGroupId);
				mainView.showPanel(groupPanelController.getGroupPanel());
			}
		});

		mainView.setCreateGroupMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actualGroupId = epLite.createGroup();
				groupPanelController = new GroupPanelController(actualGroupId);
				mainView.showPanel(groupPanelController.getGroupPanel());
			}
		});
	}
}
