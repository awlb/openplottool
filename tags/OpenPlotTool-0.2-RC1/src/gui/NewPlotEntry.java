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

package gui;

import javax.swing.Icon;

/*
 * Class used to represent plot types in the new plot dialog list
 */

public class NewPlotEntry {
	private String entryPlotID = null;
	private Icon icon = null;
	private String name = null;

	public NewPlotEntry(String entryPlotID, String name, Icon icon) {
		this.entryPlotID = entryPlotID;
		this.name = name;
		this.icon = icon;
	}

	public String getEntryPlotID() {
		return entryPlotID;
	}

	public Icon getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}
}
