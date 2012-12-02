package org.etherpad.lite.client.admin.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.etherpad.lite.client.admin.controller.MainViewController;


public class Main {
	static MainViewController mainViewController;

	public static void main(String[] args) {
		try {
			// Set platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		mainViewController = new MainViewController();
	}
}
