package org.etherpad.lite.client.admin.helper;

import java.io.IOException;
import java.net.URISyntaxException;

public class URLHelper {
	public static void openUrl(String url) throws IOException, URISyntaxException {
		if (java.awt.Desktop.isDesktopSupported()) {
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

			if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
				java.net.URI uri = new java.net.URI(url);
				desktop.browse(uri);
			}
		}
	}
}
