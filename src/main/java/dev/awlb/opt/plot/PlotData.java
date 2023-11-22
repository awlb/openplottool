package dev.awlb.opt.plot;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class PlotData implements Serializable {
	private static final long serialVersionUID = 5102951867313854626L;
	private String dataType = "default";
	private Plot parentPlot = null;

	public PlotData(Plot parentPlot) {
		this.parentPlot = parentPlot;
	}

	public abstract void draw(Graphics g);

	public String getDataType() {
		return dataType;
	}

	public Plot getParentPlot() {
		return parentPlot;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "PlotData [dataType=" + dataType + "]";
	}
}
