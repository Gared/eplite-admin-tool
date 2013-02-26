package org.etherpad.lite.client.admin.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		String url = epLite.getUrl();
		settingsView.setSslCheckBoxSelected(url.startsWith("https://"));
		String hostText = url.split("//")[1];
		
		settingsView.setHostTextFieldText(hostText);
		settingsView.setApiTextFieldText(epLite.getApiKey());
	}

	private void addListener() {
		settingsView.setSaveButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				epLite.setServer(settingsView.getProtocolLabelText() + settingsView.getHostTextFieldText(), settingsView.getApiTextFieldText());
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
		
		settingsView.setHostTextFieldFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
		        JTextField textField = (JTextField) e.getSource();
		        if (textField.getText().matches("^[a-zA-Z0-9-_\\.]*(:(6553[0-5]|655[0-2][0-9]|65[0-4][0-9][0-9]|6[0-4][0-9][0-9][0-9]|\\d{2,4}|[1-9]))?$")) {
		        	textField.setBorder(null);
		        } else {
		        	Border border = BorderFactory.createLineBorder(Color.red);
		        	textField.setBorder(border);
		        }
		    }
		});
		
		settingsView.setSslCheckBoxChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				JCheckBox checkBox = (JCheckBox) arg0.getSource();
				if (checkBox.isSelected()) {
					settingsView.setProtocolLabelText("https://");
				} else {
					settingsView.setProtocolLabelText("http://");
				}
			}
		});
	}
}
