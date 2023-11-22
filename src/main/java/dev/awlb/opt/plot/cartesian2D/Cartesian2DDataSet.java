package dev.awlb.opt.plot.cartesian2D;

import dev.awlb.opt.handlers.StrokeTypeHandler;
import dev.awlb.opt.plot.Plot;
import dev.awlb.opt.plot.PlotData;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cartesian2DDataSet extends PlotData {
	private static final long serialVersionUID = -826888282207732685L;
	private double data[][] = { { 0.0, 1.0 }, { 1.0, 0.0 } };
	// if the points of the dataset should be linked or not
	private boolean linkPoints = false;
	// the name of the dataset
	private String name = "Data Set";

	public Cartesian2DDataSet(Plot parentPlot) {
		super(parentPlot);
		this.setDataType("Data Set");
	}

	@Override
	public void draw(Graphics g) {
		Cartesian2D plot = (Cartesian2D) this.getParentPlot();
		Graphics2D gc = (Graphics2D) g;
		// set draw color
		gc.setColor(Color.RED);
		// set draw stroke
		gc.setStroke(StrokeTypeHandler.getStrokeType("Line").getStroke());
		// last draw points -- used for drawing linked points
		int lastXPoint = -1;
		int lastYPoint = -1;
		for (int i = 0; i < data.length; i++) {
			// get x and y values
			double x = data[i][0];
			double y = data[i][1];

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

			if (linkPoints && lastXPoint > -1 && lastYPoint > -1) {
				gc.drawLine(lastXPoint, lastYPoint, iDrawXPoint, iDrawYPoint);
			}
			// draw point
			gc.fillRect(iDrawXPoint - 2, iDrawYPoint - 2, 5, 5);
			// update last draw point
			lastXPoint = iDrawXPoint;
			lastYPoint = iDrawYPoint;
		}
	}

	public double[][] getData() {
		return data;
	}

	public String getName() {
		return name;
	}

	public boolean isLinkPoints() {
		return linkPoints;
	}

	public void setData(double[][] data) {
		this.data = data;
	}

	public void setLinkPoints(boolean linkPoints) {
		this.linkPoints = linkPoints;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "<html><font color='#ff0000'>" + name + "</font></html>";
	}
}
