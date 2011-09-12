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
import java.io.Serializable;

/*
 * Cartesian plot settings class
 */

public class Cartesian2DSettings implements Serializable {
	private static final long serialVersionUID = 6997652915341484605L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Color axisColor = Color.BLACK;
	private String axisDrawType = "Line";
	private Color bgColor = Color.WHITE;
	private Color gridBGColor = Color.WHITE;
	private Color mainGridColor = Color.BLUE;
	private String mainGridDrawType = "Line";
	private double numXSplitSize = 1;
	private double numYSplitSize = 1;
	private Color subGridColor = Color.LIGHT_GRAY;
	private String subGridType = "Dot";
	private Color textColor = Color.BLACK;
	private double xMax = 5.0;
	private double xMin = -5.0;
	private double xSplitSize = 0.5;
	private double yMax = 5.0;
	private double yMin = -5.0;
	private double ySplitSize = 0.5;

	public Color getAxisColor() {
		return axisColor;
	}

	public String getAxisDrawType() {
		return axisDrawType;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public Color getGridBGColor() {
		return gridBGColor;
	}

	public Color getMainGridColor() {
		return mainGridColor;
	}

	public String getMainGridDrawType() {
		return mainGridDrawType;
	}

	public double getNumXSplitSize() {
		return numXSplitSize;
	}

	public double getNumYSplitSize() {
		return numYSplitSize;
	}

	public Color getSubGridColor() {
		return subGridColor;
	}

	public String getSubGridType() {
		return subGridType;
	}

	public Color getTextColor() {
		return textColor;
	}

	public double getxMax() {
		return xMax;
	}

	public double getxMin() {
		return xMin;
	}

	public double getxSplitSize() {
		return xSplitSize;
	}

	public double getyMax() {
		return yMax;
	}

	public double getyMin() {
		return yMin;
	}

	public double getySplitSize() {
		return ySplitSize;
	}

	public void setAxisColor(Color axisColor) {
		this.axisColor = axisColor;
	}

	public void setAxisDrawType(String axisDrawType) {
		this.axisDrawType = axisDrawType;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public void setGridBGColor(Color gridBGColor) {
		this.gridBGColor = gridBGColor;
	}

	public void setMainGridColor(Color mainGridColor) {
		this.mainGridColor = mainGridColor;
	}

	public void setMainGridDrawType(String mainGridDrawType) {
		this.mainGridDrawType = mainGridDrawType;
	}

	public void setNumXSplitSize(double numXSplitSize) {
		this.numXSplitSize = numXSplitSize;
	}

	public void setNumYSplitSize(double numYSplitSize) {
		this.numYSplitSize = numYSplitSize;
	}

	public void setSubGridColor(Color subGridColor) {
		this.subGridColor = subGridColor;
	}

	public void setSubGridType(String subGridType) {
		this.subGridType = subGridType;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	public void setxSplitSize(double xSplitSize) {
		this.xSplitSize = xSplitSize;
	}

	public void setyMax(double yMax) {
		this.yMax = yMax;
	}

	public void setyMin(double yMin) {
		this.yMin = yMin;
	}

	public void setySplitSize(double ySplitSize) {
		this.ySplitSize = ySplitSize;
	}
}
