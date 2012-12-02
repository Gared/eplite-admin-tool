package org.etherpad.lite.client.admin.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JDialog;
import javax.swing.JLabel;

import org.etherpad.lite.client.admin.helper.URLHelper;

public class AboutPanel extends JDialog {
	public AboutPanel() {
		super(null, JDialog.DEFAULT_MODALITY_TYPE);
		createGUI();
		
		setTitle("EP Lite Admin Tool");
		setSize(400, 300);
		setResizable(true);
		CenteredFrame.center(this, getWidth(), getHeight());
		setVisible(true);
	}

	private void createGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		c.insets = new Insets(3,3,3,3);
		JLabel versionLabel = new JLabel("Version 0.9");
		c.gridx = 0;
		c.gridy = 0;
		add(versionLabel, c);
		
		JLabel projectLabel = new JLabel("Project on github:");
		c.gridx = 0;
		c.gridy = 1;
		add(projectLabel, c);
		
		JLabel projectUrlLabel = new JLabel("<html><u>https://github.com/gared/eplite-admin-tool</u></html>");
		projectUrlLabel.setForeground(Color.BLUE);
		projectUrlLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		projectUrlLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					URLHelper.openUrl("https://github.com/gared/eplite-admin-tool");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		c.gridx = 1;
		c.gridy = 1;
		add(projectUrlLabel);		
	}
}
