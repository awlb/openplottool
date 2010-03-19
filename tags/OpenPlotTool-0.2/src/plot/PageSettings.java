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
 * Abstract class that provides basic page settings
 */

public class PageSettings {
	private Color backgroundColor = Color.WHITE;
	private Color borderColor = Color.BLACK;
	private String borderDrawType = "Line";
	private Boolean drawBorder = false;
	private String title = "New";
	private Color titleColor = Color.BLACK;

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public String getBorderDrawType() {
		return borderDrawType;
	}

	public Boolean getDrawBorder() {
		return drawBorder;
	}

	public String getTitle() {
		return title;
	}

	public Color getTitleColor() {
		return titleColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setBorderDrawType(String borderDrawType) {
		this.borderDrawType = borderDrawType;
	}

	public void setDrawBorder(Boolean drawBorder) {
		this.drawBorder = drawBorder;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleColor(Color titleColor) {
		this.titleColor = titleColor;
	}
}
