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

import gui.MainFrame;
import main.OpenPlotTool;
import page.Page;
import plot.Plot;
import plot.PlotFactory;

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
		System.out.println("Close all plots");
	}

	public void closePlot() {
		System.out.println("Close plot");
	}

	public void exportPlot() {
		System.out.println("Export plot");
	}

	public void newPlot() {
		// create new plot and page
		Plot plot = PlotFactory.getPlot("Cartesian2D");
		Page page = new Page(plot);
		// add page to tab area
		MainFrame mainFrame = OpenPlotTool.getInstance().getMainFrame();
		mainFrame.getTabPane().addTab("Test", page);
	}

	public void openPlot() {
		System.out.println("Open plot");
	}

	public void printPlot() {
		System.out.println("Print plot");
	}

	public void revertPlot() {
		System.out.println("Revert Plot");
	}

	public void savePlot() {
		System.out.println("Save plot");
	}

	public void savePlotAs() {
		System.out.println("Save plot as");
	}
}
