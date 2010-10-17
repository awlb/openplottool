package plot.cartesian2D;

import java.awt.Color;
import java.awt.Graphics;

import plot.Plot;
import plot.PlotData;

public class Cartesian2DPoint extends PlotData {
	private static final long serialVersionUID = 3727205095114361199L;
	int x = 0;
	int y = 0;

	public Cartesian2DPoint(Plot parentPlot) {
		super(parentPlot);
		this.setDataType("Point");
	}

	@Override
	public void draw(Graphics g) {
		Cartesian2D plot = (Cartesian2D) this.getParentPlot();
		// set draw color
		g.setColor(Color.RED);
		// calculate x draw point
		double drawXPoint = plot.getxIndent()
				+ plot.getxLowerSectionPad()
				+ ((x - plot.getPlotSettings().getxMin() - plot.getxBelow()) / plot
						.getPlotSettings().getxSplitSize())
				* plot.getxSectionSize();
		int iDrawXPoint = (int) Math.round(drawXPoint);

		// calculate y draw point
		double drawYPoint = plot.getyIndent()
				+ plot.getyUpperSectionPad()
				+ ((plot.getPlotSettings().getyMax() - plot.getyOver() - y) / plot
						.getPlotSettings().getySplitSize())
				* plot.getySectionSize();
		int iDrawYPoint = (int) Math.round(drawYPoint);

		// draw point
		g.fillRect(iDrawXPoint - 2, iDrawYPoint - 2, 5, 5);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
