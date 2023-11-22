package dev.awlb.opt.handlers;

import dev.awlb.opt.gui.AboutDialog;

public class HelpHandler {

	private static HelpHandler instance = null;

	public static HelpHandler getInstance() {
		if (instance == null) {
			instance = new HelpHandler();
		}
		return instance;
	}

	protected HelpHandler() {

	}

	public void showAbout() {
		// display about dialog
		AboutDialog aboutDialog = new AboutDialog();
		aboutDialog.setVisible(true);
	}
}
