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

package handlers;

public class FileHandler {

	private static FileHandler instance = null;

	public static FileHandler getInstance() {
		if (instance == null) {
			instance = new FileHandler();
		}
		return instance;
	}

	protected FileHandler() {

	}

	public void closeAllPlots() {

	}

	public void closePlot() {

	}

	public void exportPlot() {

	}

	public void newPlot() {

	}

	public void openPlot() {

	}

	public void printPlot() {
		System.out.println("Print plot");
	}

	public void revertPlot() {
		System.out.println("Revert Plot");
	}

	public void savePlot() {

	}

	public void savePlotAs() {

	}
}
