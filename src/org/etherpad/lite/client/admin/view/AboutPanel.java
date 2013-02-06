package org.etherpad.lite.client.admin.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
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
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/icons/eplite.png")));

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
		c.insets = new Insets(3, 3, 3, 3);
		JLabel versionLabel = new JLabel("Version:");
		c.gridx = 0;
		c.gridy = 0;
		add(versionLabel, c);

		JLabel versionLabelText = new JLabel(" 0.9.1 (06.02.2013)");
		c.gridx = 1;
		c.gridy = 0;
		add(versionLabelText, c);

		JLabel apiLabel = new JLabel("Needed API version:");
		c.gridx = 0;
		c.gridy = 1;
		add(apiLabel, c);

		JLabel apiLabelText = new JLabel("1.2.1");
		c.gridx = 1;
		c.gridy = 1;
		add(apiLabelText, c);

		JLabel projectLabel = new JLabel("Project on github:");
		c.gridx = 0;
		c.gridy = 2;
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
		c.gridy = 2;
		add(projectUrlLabel, c);
	}
}
