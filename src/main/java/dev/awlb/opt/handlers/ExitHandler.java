package dev.awlb.opt.handlers;

public class ExitHandler {

	private static ExitHandler instance = null;

	public static ExitHandler getInstance() {
		if (instance == null) {
			instance = new ExitHandler();
		}
		return instance;
	}

	protected ExitHandler() {

	}

	public void exitProgram() {
		System.exit(0);
	}
}
