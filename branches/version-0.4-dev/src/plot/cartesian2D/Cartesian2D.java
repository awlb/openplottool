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

import java.awt.Graphics;

import plot.Plot;

public class Cartesian2D extends Plot {
	// serial UID
	private static final long serialVersionUID = 4025965656042258986L;

	public Cartesian2D() {
		super();
		// set data types for Cartesian
		String[] dataTypes = { "Cartesian Function", "Cartesian Point",
				"Cartesian Data Set" };
		this.setDataTypes(dataTypes);
	}

	@Override
	public void drawAxis(Graphics g) {
		g.setColor(this.getPlotSettings().getBackgroundColor());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
