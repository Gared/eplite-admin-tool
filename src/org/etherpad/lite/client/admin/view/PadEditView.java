package org.etherpad.lite.client.admin.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PadEditView extends JDialog {
	
	private JTextArea padTextArea = null;
	private JButton saveButton = null;
	// dirty. PadEditController?
	//private String padId = null;

	public PadEditView(String padText) {
		super(null, JDialog.DEFAULT_MODALITY_TYPE);
		createGUI(padText);
		
		setMinimumSize(new Dimension(300, 400));
		CenteredFrame.center(this, getWidth(), getHeight());
	}

	private void createGUI(String padText) {
		JPanel panel = new JPanel(new BorderLayout());
		padTextArea = new JTextArea(padText);
		panel.add(padTextArea, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new BorderLayout());
		saveButton = new JButton("Save");
		buttonPanel.add(saveButton, BorderLayout.WEST);
		JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(panel);
	}
	
	public void setSaveButtonListener(ActionListener a) {
		saveButton.addActionListener(a);
	}
	
	public String getPadText() {
		return padTextArea.getText();
	}
}
