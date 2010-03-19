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

import java.awt.Graphics;

/*
 * Abstract class that provides basic axis functionality
 * All axis types must extend this class 
 */

public abstract class Axis {
	// page axis is drawn on
	private PlotPage parentPage = null;
	// axis settings
	private PlotSettings plotSettings;

	public abstract void draw(Graphics g, int width, int height);

	public PlotPage getParentPage() {
		return parentPage;
	}

	public PlotSettings getPlotSettings() {
		return plotSettings;
	}

	public void setParentPage(PlotPage parentPage) {
		this.parentPage = parentPage;
	}

	public void setPlotSettings(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}
}
