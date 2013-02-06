package org.etherpad.lite.client.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.etherpad.lite.client.admin.helper.URLHelper;
import org.etherpad.lite.client.admin.model.EPLite;
import org.etherpad.lite.client.admin.view.PadEditView;
import org.etherpad.lite.client.admin.view.PadPanel;

public class PadPanelController {

	private EPLite epLite = EPLite.getInstance();
	private PadPanel padPanel = null;
	private PadEditView padEditView = null;
	private String actualPadId = null;

	public PadPanelController(String padId, boolean groupPad) {
		actualPadId = padId;
		Date lastEditedTime = epLite.getPadLastEditedTime(actualPadId);
		String padText = epLite.getPadText(actualPadId);
		final String roPadId = epLite.getReadOnlyPadId(actualPadId);
		String padRev = epLite.getPadRevisionCount(actualPadId);
		List padAuthorIds = epLite.listAuthorsOfPad(actualPadId);
		epLite.getActualPadUsers(actualPadId);
		if (groupPad) {
			epLite.isGroupPadPasswordProtected(actualPadId);
		}
		StringBuilder padAuthorsString = new StringBuilder();
		for (int i = 0; i < padAuthorIds.size(); i++) {
			// padAuthorsString.append(epLite.getAuthorName(padAuthorIds.get(i).toString())).append(", ");
			padAuthorsString.append(padAuthorIds.get(i).toString()).append(", ");
		}
		if (padAuthorIds.size() == 0) {
			padAuthorsString.append("No authors");
		}
		padPanel = new PadPanel(actualPadId, roPadId, padText, padRev, padAuthorsString.toString(), lastEditedTime);

		padPanel.setEditTextButtonListener(new ActionListener() {
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

		padPanel.setDeletePadButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int reply = JOptionPane.showConfirmDialog(null, "Do you really want to delete this pad?", "Delete Pad?", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					epLite.deletePad(actualPadId);
					padPanel.setVisible(false);
				}
			}
		});

		padPanel.setPadIdLabelMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					URLHelper.openUrl(epLite.getUrl().split("api")[0] + "/p/" + actualPadId);
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
					URLHelper.openUrl(epLite.getUrl().split("api")[0] + "/p/" + roPadId);
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
