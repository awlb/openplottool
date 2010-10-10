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

package plot;

import gui.NewPlotEntry;

import javax.swing.ImageIcon;

import plot.cartesian2D.Cartesian2D;

public class PlotFactory {
	public static final NewPlotEntry plotList[] = {
			// plot types to appear in list
			new NewPlotEntry("cartesian2D", "Cartesian 2D", new ImageIcon(
					"icon/xy.png")),
			new NewPlotEntry("piechart", "Pie Chart", new ImageIcon(
					"icon/pie.png")) };

	// string plot types
	public static final String[] plotTypes = { "cartesian2D", "piechart" };

	public static Plot getPlot(String type) {
		// create and return plot object of required type
		if ("cartesian2D".equals(type)) {
			Cartesian2D cartesian2D = new Cartesian2D();
			cartesian2D.setPlotType("cartesian2D");
			return cartesian2D;
		} else {
			return null;
		}
	}
}
