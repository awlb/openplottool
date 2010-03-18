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

import java.awt.Color;

/*
 * Abstract class that provides basic plot settings functionality
 * All plot setting types must extend this class 
 */

public class PlotSettings {
	private Color backgroundColor = Color.WHITE;
	private Color textColor = Color.BLACK;

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
}
