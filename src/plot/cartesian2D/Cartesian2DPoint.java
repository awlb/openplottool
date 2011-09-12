/*	Copyright (C) 2009-2011  Alex Barfoot
 
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

import java.awt.Color;
import java.awt.Graphics;

import plot.Plot;
import plot.PlotData;

public class Cartesian2DPoint extends PlotData {
	private static final long serialVersionUID = 3727205095114361199L;
	double x = 0;
	double y = 0;

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

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "<html><font color='#ff0000'>(" + x + "," + y
				+ ")</font></html>";
	}
}
