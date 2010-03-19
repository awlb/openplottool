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

import gui.ErrorBox;

import javax.swing.JOptionPane;

import main.OpenPlotTool;
import net.smplmathparser.MathParserException;
import plot.PlotPage;
import plot.cartesian.CartesianAxis;
import plot.piechart.PieChartAxis;
import exceptions.NullDataValueException;

public class DataManager {

	public static void addDataSet() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			// show dialog depending on axis type
			if (page.getAxis() instanceof CartesianAxis) {
				try {
					// show dialog for Cartesian axis type
					CartesianDataManager.addDataSet(page);
				} catch (NumberFormatException e) {
					ErrorBox errorDialog = new ErrorBox(OpenPlotTool
							.getMainFrame(), "Failed to create data set", e);
					errorDialog.setVisible(true);
				} catch (NullDataValueException e) {
					ErrorBox errorDialog = new ErrorBox(OpenPlotTool
							.getMainFrame(), "Failed to create data set", e);
					errorDialog.setVisible(true);
				}
			} else if (page.getAxis() instanceof PieChartAxis) {
				// show dialog for pie chart axis type
				if (page.getPlotData().size() == 0) {
					try {
						PieChartDataManager.addDataSet();
					} catch (NullDataValueException e) {
						ErrorBox errorDialog = new ErrorBox(OpenPlotTool
								.getMainFrame(), "Failed to create data set", e);
						errorDialog.setVisible(true);
					}
				} else {
					// show message to user as pie charts only support one data
					// set
					JOptionPane
							.showMessageDialog(
									OpenPlotTool.getMainFrame(),
									"This plot type does not support multiple data sets.",
									"Information",
									JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	public static void addFunction() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			// show dialog depending on axis type
			if (page.getAxis() instanceof CartesianAxis) {
				try {
					// show dialog for Cartesian axis type
					CartesianDataManager.addFunction(page);
				} catch (NullDataValueException e) {
					ErrorBox errorDialog = new ErrorBox(OpenPlotTool
							.getMainFrame(), "Failed to create function", e);
					errorDialog.setVisible(true);
				} catch (MathParserException e) {
					ErrorBox errorDialog = new ErrorBox(OpenPlotTool
							.getMainFrame(), "Failed to create function", e);
					errorDialog.setVisible(true);
				}
			} else if (page.getAxis() instanceof PieChartAxis) {
				// show message to user as pie charts don't support functions
				JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
						"This plot type does not support functions.",
						"Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public static void addPoint() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			// show dialog depending on axis type
			if (page.getAxis() instanceof CartesianAxis) {
				// show dialog for Cartesian axis type
				try {
					CartesianDataManager.addPoint(page);
				} catch (NumberFormatException e) {
					ErrorBox errorDialog = new ErrorBox(OpenPlotTool
							.getMainFrame(), "Failed to create point", e);
					errorDialog.setVisible(true);
				}
			} else if (page.getAxis() instanceof PieChartAxis) {
				// show message to user as pie charts don't support points
				JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
						"This plot type does not support points.",
						"Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public static void editData() {
		// get selected console tab index
		int tabIndex = OpenPlotTool.getMainFrame().getConsoleTabPane()
				.getSelectedIndex();
		int selectedIndex = -1;
		// get selected item in console lists -- depends on selected console tab
		if (tabIndex == 0) {
			selectedIndex = OpenPlotTool.getMainFrame().getDataSetList()
					.getSelectedIndex();
		} else if (tabIndex == 1) {
			selectedIndex = OpenPlotTool.getMainFrame().getFunctionList()
					.getSelectedIndex();
		} else if (tabIndex == 2) {
			selectedIndex = OpenPlotTool.getMainFrame().getPointList()
					.getSelectedIndex();
		}
		// edit this selected point
		editData(selectedIndex);
	}

	public static void editData(int selectedIndex) {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null && selectedIndex > -1) {
			// get selected console tab index
			int tabIndex = OpenPlotTool.getMainFrame().getConsoleTabPane()
					.getSelectedIndex();
			// show dialog depending on axis type
			if (page.getAxis() instanceof CartesianAxis) {
				// show dialog depending on type of selected data
				if (tabIndex == 0) {
					try {
						// show edit dialog for data sets
						CartesianDataManager.editDataSet(page, selectedIndex);
					} catch (NumberFormatException e) {
						ErrorBox errorDialog = new ErrorBox(OpenPlotTool
								.getMainFrame(), "Failed to update data set", e);
						errorDialog.setVisible(true);
					} catch (NullDataValueException e) {
						ErrorBox errorDialog = new ErrorBox(OpenPlotTool
								.getMainFrame(), "Failed to update data set", e);
						errorDialog.setVisible(true);
					}
				} else if (tabIndex == 1) {
					try {
						// show edit dialog for functions
						CartesianDataManager.editFunction(page, selectedIndex);
					} catch (MathParserException e) {
						ErrorBox errorDialog = new ErrorBox(OpenPlotTool
								.getMainFrame(), "Failed to update function", e);
						errorDialog.setVisible(true);
					} catch (NullDataValueException e) {
						ErrorBox errorDialog = new ErrorBox(OpenPlotTool
								.getMainFrame(), "Failed to update function", e);
						errorDialog.setVisible(true);
					}
				} else if (tabIndex == 2) {
					try {
						// show edit dialog for points
						CartesianDataManager.editPoint(page, selectedIndex);
					} catch (NumberFormatException e) {
						ErrorBox errorDialog = new ErrorBox(OpenPlotTool
								.getMainFrame(), "Failed to update point", e);
						errorDialog.setVisible(true);
					}
				}
			} else if (page.getAxis() instanceof PieChartAxis) {
				// show dialog depending on type of selected data
				if (tabIndex == 0) {
					try {
						// show edit dialog for data sets
						PieChartDataManager.editDataSet(page, selectedIndex);
					} catch (NullDataValueException e) {
						ErrorBox errorDialog = new ErrorBox(OpenPlotTool
								.getMainFrame(), "Failed to update data set", e);
						errorDialog.setVisible(true);
					}
				}
			}
		}
	}

	public static void removeData() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			// remove data depending on the page type and data type selected
			int index = OpenPlotTool.getMainFrame().getConsoleTabPane()
					.getSelectedIndex();
			if (page.getAxis() instanceof CartesianAxis) {
				if (index == 0) {
					CartesianDataManager.removeDataSet(page);
				} else if (index == 1) {
					CartesianDataManager.removeFunction(page);
				} else if (index == 2) {
					CartesianDataManager.removePoint(page);
				}
			} else if (page.getAxis() instanceof PieChartAxis) {
				if (index == 0) {
					PieChartDataManager.removeDataSet(page);
				}
			}
		}
	}
}
