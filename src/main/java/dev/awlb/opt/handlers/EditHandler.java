package dev.awlb.opt.handlers;

public class EditHandler {

	private static EditHandler instance = null;

	public static EditHandler getInstance() {
		if (instance == null) {
			instance = new EditHandler();
		}
		return instance;
	}

	protected EditHandler() {

	}

	public void preferencesEdit() {
		// TODO edit preferences implement
	}

	public void redoEdit() {
		// TODO redo implement
	}

	public void undoEdit() {
		//
	}
}
