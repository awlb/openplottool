package dev.awlb.opt.plot;

import java.awt.BasicStroke;

public class StrokeType {
	// name of the stroke
	String name;
	// Basic stroke object for this stroke
	BasicStroke stroke;

	public StrokeType(String name, BasicStroke stroke) {
		this.name = name;
		this.stroke = stroke;
	}

	public String getName() {
		return name;
	}

	public BasicStroke getStroke() {
		return stroke;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}

	@Override
	public String toString() {
		return name;
	}
}
