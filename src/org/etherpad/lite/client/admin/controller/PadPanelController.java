package org.etherpad.lite.client.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import org.etherpad.lite.client.admin.helper.URLHelper;
import org.etherpad.lite.client.admin.model.EPLite;
import org.etherpad.lite.client.admin.view.PadEditView;
import org.etherpad.lite.client.admin.view.PadPanel;

public class PadPanelController {

	private EPLite epLite = EPLite.getInstance();
	private PadPanel padPanel = null;
	private PadEditView padEditView = null;
	private String actualPadId = null;

	public PadPanelController(String padId) {
		actualPadId = padId;
		String padText = epLite.getPadText(actualPadId);
		final String roPadId = epLite.getReadOnlyPadId(actualPadId);
		String padRev = epLite.getPadRevisionCount(actualPadId);
		String[] padAuthors = epLite.listAuthorsOfPad(actualPadId);
		StringBuilder padAuthorsString = new StringBuilder();
		for (String author : padAuthors) {
			padAuthorsString.append(author).append(", ");
		}
		padPanel = new PadPanel(actualPadId, roPadId, padText, padRev,
				padAuthorsString.toString());

		padPanel.setEditTextButtonMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				padEditView = new PadEditView(epLite.getPadText(actualPadId));
				padEditView.setSaveButtonListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						epLite.setPadText(actualPadId, padEditView.getPadText());
						padEditView.dispose();
						padPanel.setPadText(epLite.getPadText(actualPadId));
					}
				});
				padEditView.setVisible(true);
			}
		});

		padPanel.setPadIdLabelMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					URLHelper.openUrl(epLite.getUrl().split("api")[0] + "p/" + actualPadId);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		padPanel.setPadRoLabelMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					URLHelper.openUrl(epLite.getUrl().split("api")[0] + "p/" + roPadId);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public PadPanel getPadPanel() {
		return padPanel;
	}
}
