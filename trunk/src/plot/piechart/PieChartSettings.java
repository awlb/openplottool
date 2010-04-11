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

package plot.piechart;

import java.awt.Color;

import plot.PlotSettings;

/*
 * Pie chart plot settings class
 */

public class PieChartSettings extends PlotSettings {
	private boolean drawKey = true;
	private Color keyBackgroundColor = Color.LIGHT_GRAY;
	private Color outlineDrawColor = Color.BLACK;

	public Color getKeyBackgroundColor() {
		return keyBackgroundColor;
	}

	public Color getOutlineDrawColor() {
		return outlineDrawColor;
	}

	public boolean isDrawKey() {
		return drawKey;
	}

	public void setDrawKey(boolean drawKey) {
		this.drawKey = drawKey;
	}

	public void setKeyBackgroundColor(Color keyBackgroundColor) {
		this.keyBackgroundColor = keyBackgroundColor;
	}

	public void setOutlineDrawColor(Color outlineDrawColor) {
		this.outlineDrawColor = outlineDrawColor;
	}
}
