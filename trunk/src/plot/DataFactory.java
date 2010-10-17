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

import net.smplmathparser.MathParserException;
import plot.cartesian2D.Cartesian2D;
import plot.cartesian2D.Cartesian2DDataSet;
import plot.cartesian2D.Cartesian2DFunction;
import plot.cartesian2D.Cartesian2DPoint;

public class DataFactory {
	public static PlotData getData(Plot plot, String dataType)
			throws MathParserException {
		if (plot instanceof Cartesian2D) {
			if ("Data Set".equals(dataType)) {
				return new Cartesian2DDataSet(plot);
			} else if ("Function".equals(dataType)) {
				return new Cartesian2DFunction(plot, "y=x");
			} else if ("Point".equals(dataType)) {
				return new Cartesian2DPoint(plot);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
