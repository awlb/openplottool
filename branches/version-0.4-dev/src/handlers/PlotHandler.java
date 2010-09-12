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

import gui.AddDataDialog;

import javax.swing.JOptionPane;

import main.OpenPlotTool;
import net.smplmathparser.MathParserException;
import page.Page;
import page.PageManager;
import plot.DataFactory;
import plot.Plot;
import plot.PlotData;

public class PlotHandler {

	private static PlotHandler instance = null;

	public static PlotHandler getInstance() {
		if (instance == null) {
			instance = new PlotHandler();
		}
		return instance;
	}

	protected PlotHandler() {

	}

	public void addData() {
		// get selected page
		Page selectedPage = PageManager.getSelected();
		if (selectedPage != null) {
			// get selected plot
			Plot selectedPlot = selectedPage.getPlot();
			// display add data dialog
			AddDataDialog addDataDialog = new AddDataDialog(selectedPlot);
			addDataDialog.setVisible(true);
			if (addDataDialog.getPressed() == AddDataDialog.OK_PRESSED) {
				// create new data of selected type
				String dataType = addDataDialog.getSelected();
				PlotData newData;
				try {
					newData = DataFactory.getData(selectedPlot, dataType);
					if (newData != null) {
						selectedPage.addData(newData);
					} else {
						// display error dialog if plot type is not recognised
						JOptionPane.showMessageDialog(OpenPlotTool
								.getInstance().getMainFrame(),
								"Failed to create data of type: " + dataType,
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (MathParserException e) {
					e.printStackTrace();
				}
			}
			addDataDialog.dispose();
		}
	}

	public void editPlotSettings() {

	}

	public void removeData() {
		Page selectedPage = PageManager.getSelected();
		selectedPage.removeData();
	}
}
