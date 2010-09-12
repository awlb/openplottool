/*	Copyright (C) 2009-2010  Alex Barfoot
 
 	This file is part of OpenPlot Tool.

    OpenPlot Tool is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    OpenPlot Tool is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with OpenPlot Tool.  If not, see <http://www.gnu.org/licenses/>.
 */

package plot.cartesian2D;

import handlers.StrokeTypeHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import plot.Plot;
import plot.PlotData;

public class Cartesian2DDataSet extends PlotData {
	private static final long serialVersionUID = -826888282207732685L;
	private double data[][] = { { 0.0, 1.0 }, { 1.0, 0.0 }, { 0.0, -1.0 },
			{ -1.0, 0.0 }, { 0.0, 1.0 } };
	private boolean linkPoints = true;

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

	@Override
	public String toString() {
		return "Data Set";
	}
}
