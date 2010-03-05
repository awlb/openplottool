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

import java.awt.BasicStroke;

/*
 * Class that defines a few basic draw types
 */

public class DrawTypes {
	private static float dashA[] = { 2.0f, 2.0f };
	private static BasicStroke dash = new BasicStroke(1.0f,
			BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 10.0f, dashA, 0.0f);

	private static float dotA[] = { 1.0f, 2.0f };
	private static BasicStroke dot = new BasicStroke(1.0f,
			BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 10.0f, dotA, 0.0f);

	private static BasicStroke line = new BasicStroke(1);

	public static BasicStroke getDrawType(String typeName) {
		if (typeName.equals("Dash")) {
			return dash;
		} else if (typeName.equals("Dot")) {
			return dot;
		} else {
			return line;
		}
	}
}
