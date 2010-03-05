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

import gui.cartesian.CartesianSettingsDialog;
import gui.piechart.PieChartSettingsDialog;

import javax.swing.JOptionPane;

import main.OpenPlotTool;
import plot.PlotPage;
import plot.cartesian.CartesianAxis;
import plot.cartesian.CartesianSettings;
import plot.piechart.PieChartAxis;
import plot.piechart.PieChartSettings;

public class AxisHandler {
	// method to edit axis
	public static void editAxis() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			// show dialog depending on axis type
			if (page.getAxis() instanceof CartesianAxis) {
				// get Cartesian axis
				CartesianAxis axis = (CartesianAxis) page.getAxis();
				// display edit dialog
				CartesianSettingsDialog dialog = new CartesianSettingsDialog(
						axis);
				dialog.setVisible(true);
				if (dialog.getPressed() == CartesianSettingsDialog.OK_PRESSED) {
					try {
						// get edited axis settings
						CartesianSettings settings = dialog.getNewSettings();
						// update axis settings
						axis.setPlotSettings(settings);
						// repaint page
						page.repaint();
					} catch (Exception e) {
						// show error as axis settings were invalid
						JOptionPane.showMessageDialog(OpenPlotTool
								.getMainFrame(), "Invalid settings.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				dialog.dispose();
			} else if (page.getAxis() instanceof PieChartAxis) {
				// get pie chart axis
				PieChartAxis axis = (PieChartAxis) page.getAxis();
				// display edit dialog
				PieChartSettingsDialog dialog = new PieChartSettingsDialog(axis);
				dialog.setVisible(true);
				if (dialog.getPressed() == CartesianSettingsDialog.OK_PRESSED) {
					// get edited axis settings
					PieChartSettings settings = dialog.getNewSettings();
					// update axis settings
					axis.setPlotSettings(settings);
					// repaint page
					page.repaint();
				}
			}
		}
	}
}
