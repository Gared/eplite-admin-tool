package org.etherpad.lite.client.admin.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainView extends CenteredFrame {
	private JMenuItem aboutMenuItem = null;
	private JMenuItem openPadMenuItem = null;
	private JMenuItem createPadMenuItem = null;
	private JMenuItem deletePadMenuItem = null;
	private JMenuItem openGroupMenuItem = null;
	private JMenuItem createGroupMenuItem = null;
	private JMenuItem deleteGroupMenuItem = null;
	private JMenu administrationMenu = null;
	
	private JMenuItem serverConfigMenuItem = null;

	public MainView() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set program icon
		// TODO: Not working if packed in jar-file
		Image image = Toolkit.getDefaultToolkit().getImage("res/icons/eplite.png");
		setIconImage(image);
		
		// Create JMenuBar
		createMenuBar();

		// Set window settings
		setTitle("EP Lite admin tool");
		setSize(300, 300);
		setMinimumSize(new Dimension(250, 200));
		center(this, getWidth(), getHeight());
		setLayout(new BorderLayout());
		setVisible(true);
	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu epliteMenu = new JMenu("EP Lite Admin Tool");
		aboutMenuItem = new JMenuItem("About");
		epliteMenu.add(aboutMenuItem);

		administrationMenu = new JMenu("Administration");
		administrationMenu.setEnabled(false);
		JMenu padsMenu = new JMenu("Pads");
		JMenu groupsMenu = new JMenu("Groups");
		openPadMenuItem = new JMenuItem("Open pad");
		createPadMenuItem = new JMenuItem("Create pad");
		deletePadMenuItem = new JMenuItem("Delete pad");
		openGroupMenuItem = new JMenuItem("Open group");
		createGroupMenuItem = new JMenuItem("Create group");
		deleteGroupMenuItem = new JMenuItem("Delete group");
		padsMenu.add(openPadMenuItem);
		padsMenu.add(createPadMenuItem);
		padsMenu.add(deletePadMenuItem);
		groupsMenu.add(openGroupMenuItem);
		groupsMenu.add(createGroupMenuItem);
		groupsMenu.add(deleteGroupMenuItem);
		administrationMenu.add(padsMenu);
		administrationMenu.add(groupsMenu);
		
		JMenu settingsMenu = new JMenu("Settings");
		serverConfigMenuItem = new JMenuItem("Server config");
		settingsMenu.add(serverConfigMenuItem);

		menuBar.add(epliteMenu);
		menuBar.add(administrationMenu);
		menuBar.add(settingsMenu);
	}
	
	public void showPanel(JPanel panel) {
		getContentPane().removeAll();
		getContentPane().add(panel, BorderLayout.CENTER);
		pack();
	}
	
	public void enableAdministrationMenu() {
		administrationMenu.setEnabled(true);
	}
	
	/***** EP Lite Admin Tool -> About *****/
	
	public void setAboutMenuListener(ActionListener a) {
		aboutMenuItem.addActionListener(a);
	}
	
	/***** Pads *****/
	
	public void setOpenPadMenuListener(ActionListener a) {
		openPadMenuItem.addActionListener(a);
	}
	
	public void setCreatePadMenuListener(ActionListener a) {
		createPadMenuItem.addActionListener(a);
	}
	
	public void setDeletePadMenuListener(ActionListener a) {
		deletePadMenuItem.addActionListener(a);
	}
	
	/***** Groups *****/

	public void setOpenGroupMenuListener(ActionListener a) {
		openGroupMenuItem.addActionListener(a);
	}
	
	public void setCreateGroupMenuListener(ActionListener a) {
		createGroupMenuItem.addActionListener(a);
	}
	
	public void setDeleteGroupMenuListener(ActionListener a) {
		deleteGroupMenuItem.addActionListener(a);
	}
	
	/***** Server configuration *****/
	
	public void setServerConfigMenuListener(ActionListener a) {
		serverConfigMenuItem.addActionListener(a);
	}
}
