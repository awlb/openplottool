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

package data;

import main.OpenPlotTool;
import plot.PlotDataSet;
import plot.PlotPage;
import plot.piechart.PieChartDataSet;
import exceptions.NullDataValueException;
import gui.piechart.PieChartDataDialog;

public class PieChartDataManager {
	public static void addDataSet() throws NullDataValueException {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			PieChartDataDialog dialog = new PieChartDataDialog(null);
			dialog.setVisible(true);
			if (dialog.getPressed() == PieChartDataDialog.ADD_PRESSED) {
				PlotDataSet data = dialog.getDataItems();
				page.addData(data);
				page.repaint();
				OpenPlotTool.getMainFrame().getDataSetListModel().addElement(
						data);
			}
			dialog.dispose();
		}
	}

	public static void editDataSet(PlotPage page, int selectedIndex)
			throws NullDataValueException {
		// get selected data set
		PieChartDataSet currentDataSet = (PieChartDataSet) OpenPlotTool
				.getMainFrame().getDataSetList().getSelectedValue();
		if (currentDataSet != null) {
			// create and display a EditPieChartDataDialog
			PieChartDataDialog dialog = new PieChartDataDialog(currentDataSet);
			dialog.setVisible(true);
			if (dialog.getPressed() == PieChartDataDialog.ADD_PRESSED) {
				// get new pie chart data set
				PieChartDataSet dataSet = dialog.getDataItems();
				// update data set in lists
				page.getPlotData().set(selectedIndex, dataSet);
				OpenPlotTool.getMainFrame().getDataSetListModel()
						.removeElement(currentDataSet);
				OpenPlotTool.getMainFrame().getDataSetListModel().add(
						selectedIndex, dataSet);
				// repaint page
				page.repaint();
			}
			dialog.dispose();
		}
	}

	public static void removeDataSet(PlotPage page) {
		// get selected data set
		PlotDataSet data = (PlotDataSet) OpenPlotTool.getMainFrame()
				.getDataSetList().getSelectedValue();
		if (data != null) {
			// remove data set from lists
			OpenPlotTool.getMainFrame().getDataSetListModel().removeElement(
					data);
			page.removeData(data);
			// repaint page
			page.repaint();
		}
	}
}
