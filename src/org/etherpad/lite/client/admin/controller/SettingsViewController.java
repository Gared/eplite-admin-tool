package org.etherpad.lite.client.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.etherpad.lite.client.admin.model.EPLite;
import org.etherpad.lite.client.admin.view.MainView;
import org.etherpad.lite.client.admin.view.SettingsView;

public class SettingsViewController {
	private SettingsView settingsView = null;
	private MainView mainView = null;
	
	private EPLite epLite = EPLite.getInstance();

	public SettingsViewController(MainView mainView) {
		this.mainView = mainView;
		settingsView = new SettingsView();
		initData();
		addListener();
		settingsView.setVisible(true);
	}

	private void initData() {
		settingsView.setHostTextFieldText(epLite.getUrl());
		settingsView.setApiTextFieldText(epLite.getApiKey());
	}

	private void addListener() {
		settingsView.setSaveButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				epLite.setServer(settingsView.getHostTextFieldText(), settingsView.getApiTextFieldText());
				mainView.enableAdministrationMenu();
				settingsView.dispose();
			}
		});
		settingsView.setCancelButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settingsView.dispose();
			}
		});
	}
}
