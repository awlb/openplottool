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

/*
 * Abstract class that provides basic function functionality
 * All function types must extend this class 
 */

public abstract class PlotFunction extends PlotData {
	public static int LINE_DRAW = 0;
	public static int POINT_DRAW = 1;
	private int drawMethod = LINE_DRAW;
	private int drawSize = 1;
	private String FunctionString = null;

	public int getDrawMethod() {
		return drawMethod;
	}

	public int getDrawSize() {
		return drawSize;
	}

	public String getFunctionString() {
		return FunctionString;
	}

	public void setDrawMethod(int drawMethod) {
		this.drawMethod = drawMethod;
	}

	public void setDrawSize(int drawSize) {
		this.drawSize = drawSize;
	}

	public void setFunctionString(String functionString) {
		FunctionString = functionString;
	}

	@Override
	public String toString() {
		return FunctionString;
	}
}
