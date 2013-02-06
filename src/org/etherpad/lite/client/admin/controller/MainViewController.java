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
		final MainViewController mainViewController = this;
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

		mainView.setOpenPadMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String padId = JOptionPane.showInputDialog("Pad ID");
				if (padId == null) {
					return;
				}
				try {
					openPad(padId);
				} catch (EPLiteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(mainView, "Error: " + e.getMessage() + ". Are you sure you entered the correct padId?",
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
					if (isGroupPad()) {
						int reply = JOptionPane.showConfirmDialog(null, "Create pad for this group?", "", JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							createGroupPad(padId);
						} else {
							createPublicPad(padId);
						}
					} else {
						createPublicPad(padId);
					}
				} catch (EPLiteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(mainView, "Error: " + e.getMessage(), "Error creating pad", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		mainView.setManagePadsMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ManagePadsController(epLite.getAllGroups(), mainViewController);
			}
		});

		mainView.setOpenGroupMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String groupId = JOptionPane.showInputDialog("Group ID");
				if (groupId == null) {
					return;
				}
				try {
					openGroup(groupId);
				} catch (EPLiteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(mainView, "Error: " + e.getMessage(), "Error opening group", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		mainView.setCreateGroupMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openGroup(epLite.createGroup());
			}
		});

		/*
		 * mainView.setDeleteGroupMenuListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) {
		 * epLite.deleteGroup(actualGroupId); } });
		 */

		mainView.setListAllGroupsMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new GroupListController(epLite.getAllGroups(), mainViewController);
				} catch (EPLiteException epEx) {
					epEx.printStackTrace();
					JOptionPane.showMessageDialog(mainView, "Error: " + epEx.getMessage(), "Error listing groups", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public void openGroup(String groupId) {
		actualGroupId = groupId;

		groupPanelController = new GroupPanelController(actualGroupId);
		mainView.showPanel(groupPanelController.getGroupPanel());
	}

	private void openGroupPad(String groupId, String padId) {
		openGroup(groupId);
		groupPanelController.loadGroupPadPanel(actualGroupId + "$" + padId);
	}

	private void createGroupPad(String padId) {
		epLite.createGroupPad(actualGroupId, padId);
		groupPanelController = new GroupPanelController(actualGroupId);

		mainView.showPanel(groupPanelController.getGroupPanel());
		openGroupPad(actualGroupId, padId);
	}

	private void createPublicPad(String padId) {
		epLite.createPad(padId);
		padPanelController = new PadPanelController(padId, false);
		mainView.showPanel(padPanelController.getPadPanel());
	}

	private boolean isGroupPad() {
		return actualGroupId != null;
	}

	public void openPad(String padId) {
		if (padId != null) {
			if (padId.contains("$")) {
				String[] padIdComponents = padId.split("\\$");
				openGroupPad(padIdComponents[0], padIdComponents[1]);
			} else {
				padPanelController = new PadPanelController(padId, false);
				mainView.showPanel(padPanelController.getPadPanel());
			}
		}
	}

	public void removePad() {
		mainView.showPanel(null);
	}
}
