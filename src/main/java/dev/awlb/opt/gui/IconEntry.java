package dev.awlb.opt.gui;

import javax.swing.Icon;

public class IconEntry {
	private Icon icon;
	private String name;

	public IconEntry(Icon icon, String name) {
		super();
		this.icon = icon;
		this.name = name;
	}

	public Icon getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}
}
