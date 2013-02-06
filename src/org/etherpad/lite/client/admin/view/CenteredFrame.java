package org.etherpad.lite.client.admin.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class CenteredFrame extends JFrame {

	public static void center(Container cont, int width, int height) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		cont.setLocation((screenWidth - width) / 2, (screenHeight - height) / 2);
	}
}