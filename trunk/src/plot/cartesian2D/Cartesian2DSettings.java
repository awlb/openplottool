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

import java.awt.Color;
import java.io.Serializable;

/*
 * Cartesian plot settings class
 */

public class Cartesian2DSettings implements Serializable {
	private static final long serialVersionUID = 6997652915341484605L;
	private Color axisColor = Color.BLACK;
	private String axisDrawType = "Line";
	private Color backgroundColor = Color.WHITE;
	private Color mainGridColor = Color.BLUE;
	private String mainGridDrawType = "Line";
	private double numXSplitSize = 2.0;
	private double numYSplitSize = 2.0;
	private Color subGridColor = Color.LIGHT_GRAY;
	private String subGridType = "Line";
	private Color textColor = Color.BLACK;
	private double xMax = 5.0;
	private double xMin = -5.0;
	private double xSplitSize = 1.0;
	private double yMax = 5.0;
	private double yMin = -5.0;
	private double ySplitSize = 1.0;

	public Color getAxisColor() {
		return axisColor;
	}

	public void setAxisColor(Color axisColor) {
		this.axisColor = axisColor;
	}

	public String getAxisDrawType() {
		return axisDrawType;
	}

	public void setAxisDrawType(String axisDrawType) {
		this.axisDrawType = axisDrawType;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getMainGridColor() {
		return mainGridColor;
	}

	public void setMainGridColor(Color mainGridColor) {
		this.mainGridColor = mainGridColor;
	}

	public String getMainGridDrawType() {
		return mainGridDrawType;
	}

	public void setMainGridDrawType(String mainGridDrawType) {
		this.mainGridDrawType = mainGridDrawType;
	}

	public double getNumXSplitSize() {
		return numXSplitSize;
	}

	public void setNumXSplitSize(double numXSplitSize) {
		this.numXSplitSize = numXSplitSize;
	}

	public double getNumYSplitSize() {
		return numYSplitSize;
	}

	public void setNumYSplitSize(double numYSplitSize) {
		this.numYSplitSize = numYSplitSize;
	}

	public Color getSubGridColor() {
		return subGridColor;
	}

	public void setSubGridColor(Color subGridColor) {
		this.subGridColor = subGridColor;
	}

	public String getSubGridType() {
		return subGridType;
	}

	public void setSubGridType(String subGridType) {
		this.subGridType = subGridType;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public double getxMax() {
		return xMax;
	}

	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	public double getxMin() {
		return xMin;
	}

	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	public double getxSplitSize() {
		return xSplitSize;
	}

	public void setxSplitSize(double xSplitSize) {
		this.xSplitSize = xSplitSize;
	}

	public double getyMax() {
		return yMax;
	}

	public void setyMax(double yMax) {
		this.yMax = yMax;
	}

	public double getyMin() {
		return yMin;
	}

	public void setyMin(double yMin) {
		this.yMin = yMin;
	}

	public double getySplitSize() {
		return ySplitSize;
	}

	public void setySplitSize(double ySplitSize) {
		this.ySplitSize = ySplitSize;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
