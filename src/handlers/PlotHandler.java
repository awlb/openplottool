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

package handlers;

import gui.AddDataDialog;

import javax.swing.JOptionPane;

import main.OpenPlotTool;
import plot.Plot;
import plot.PlotData;
import view.PlotView;
import view.ViewManager;

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
		PlotView selectedView = ViewManager.getSelectedView();
		if (selectedView != null) {
			Plot selectedPlot = ViewManager.getSelectedPlot();
			// display add data dialog
			AddDataDialog addDataDialog = new AddDataDialog(selectedPlot);
			addDataDialog.setVisible(true);
			if (addDataDialog.getPressed() == AddDataDialog.OK_PRESSED) {
				// create new data of selected type
				String dataType = addDataDialog.getSelected();
				try {
					PlotData newData = selectedPlot.createData(dataType);
					if (newData != null) {
						selectedView.addData(newData);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(OpenPlotTool.getInstance()
							.getMainFrame(), "Failed to create data of type: "
							+ dataType, "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
			addDataDialog.dispose();
		}
	}

	public void editData() {
		// get selected data
		PlotView selectedView = ViewManager.getSelectedView();
		if (selectedView != null) {
			PlotData selectedData = selectedView.getSelectedData();
			if (selectedData != null) {
				try {
					PlotData newPlotData = selectedView.getPlot().editData(
							selectedData);
					if (newPlotData != null) {
						// replace data in view
						selectedView.replaceData(selectedData, newPlotData);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(
							OpenPlotTool.getInstance().getMainFrame(),
							"Failed to edit plot data of type: "
									+ selectedData.getDataType(), "Error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
	}

	public void editPlotSettings() {
		// get selected page
		PlotView selectedView = ViewManager.getSelectedView();
		if (selectedView != null) {
			Plot selectedPlot = ViewManager.getSelectedPlot();
			selectedPlot.editPlotSettings();
		}
	}

	public void removeData() {
		PlotView selectedView = ViewManager.getSelectedView();
		if (selectedView != null) {
			// get selected data
			PlotData selectedData = selectedView.getSelectedData();
			if (selectedData != null) {
				// remove data from view
				selectedView.removeData(selectedData);
			}
		}
	}
}
