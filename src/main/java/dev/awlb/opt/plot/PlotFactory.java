package dev.awlb.opt.plot;

import dev.awlb.opt.gui.IconEntry;
import dev.awlb.opt.plot.cartesian2D.Cartesian2D;

import javax.swing.ImageIcon;

public class PlotFactory {
	public static final IconEntry plotList[] = {
			// plot types to appear in list
			new IconEntry(new ImageIcon("icon/plot/cartesian-2d.png"),
					"Cartesian 2D"),
			new IconEntry(new ImageIcon("icon/plot/pie-chart.png"), "Pie Chart") };

	public static Plot getPlot(String type) {
		// create and return plot object of required type
		if ("Cartesian 2D".equals(type)) {
			Cartesian2D cartesian2D = new Cartesian2D();
			return cartesian2D;
		} else {
			return null;
		}
	}
}
