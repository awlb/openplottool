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

package plot.cartesian;

import java.awt.Graphics;

import plot.Axis;
import plot.PlotDataSet;

public class CartesianDataSet extends PlotDataSet {
	private double data[][] = null;

	@Override
	public void draw(Graphics g, Axis axis) {
		// get Cartesian axis
		CartesianAxis caxis = (CartesianAxis) axis;
		// get plot settings
		CartesianSettings settings = (CartesianSettings) axis.getPlotSettings();
		// set draw color
		g.setColor(this.getDrawColor());
		int i = 0;
		while (i < data.length) {
			// get x and y values
			double x = data[i][0];
			double y = data[i][1];

			// calculate x draw point
			double drawXPoint = caxis.getxIndent()
					+ caxis.getxLowerSectionPad()
					+ ((x - settings.getxMin() - caxis.getxBelow()) / settings
							.getxSplitSize()) * caxis.getxSectionSize();
			int iDrawXPoint = (int) Math.round(drawXPoint);

			// calculate y draw point
			double drawYPoint = caxis.getyIndent()
					+ caxis.getyUpperSectionPad()
					+ ((settings.getyMax() - caxis.getyOver() - y) / settings
							.getySplitSize()) * caxis.getySectionSize();
			int iDrawYPoint = (int) Math.round(drawYPoint);

			// draw point
			g.fillRect(iDrawXPoint - 2, iDrawYPoint - 2, 5, 5);
			i++;
		}
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double data[][]) {
		this.data = data;
	}
}
