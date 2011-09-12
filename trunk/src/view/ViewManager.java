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

package view;

import javax.swing.JTabbedPane;

import main.OpenPlotTool;
import plot.Plot;

public class ViewManager {
	// add view
	public static void addView(PlotView view) {
		JTabbedPane tabbedPane = OpenPlotTool.getInstance().getMainFrame()
				.getTabPane();
		// add tab for view
		tabbedPane.addTab("New", view);
		// goto newly made tab
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
	}

	// close all views
	public static void closeAllViews() {
		JTabbedPane tabbedPane = OpenPlotTool.getInstance().getMainFrame()
				.getTabPane();
		// remove all tabs
		tabbedPane.removeAll();
	}

	// close current view
	public static void closeView() {
		JTabbedPane tabbedPane = OpenPlotTool.getInstance().getMainFrame()
				.getTabPane();
		// get and close selected view
		int selectedIndex = tabbedPane.getSelectedIndex();
		tabbedPane.removeTabAt(selectedIndex);
	}

	// get the currently selected index
	public static int getSelectedIndex() {
		JTabbedPane tabbedPane = OpenPlotTool.getInstance().getMainFrame()
				.getTabPane();
		return tabbedPane.getSelectedIndex();
	}

	// get the currently selected plot
	public static Plot getSelectedPlot() {
		JTabbedPane tabbedPane = OpenPlotTool.getInstance().getMainFrame()
				.getTabPane();
		if (tabbedPane.getSelectedComponent() != null) {
			PlotView selectedView = (PlotView) tabbedPane
					.getSelectedComponent();
			Plot selectedPlot = selectedView.getPlot();
			return selectedPlot;
		} else {
			return null;
		}
	}

	public static PlotView getSelectedView() {
		JTabbedPane tabbedPane = OpenPlotTool.getInstance().getMainFrame()
				.getTabPane();
		return (PlotView) tabbedPane.getSelectedComponent();
	}

	// get view count
	public static int getViewCount() {
		JTabbedPane tabbedPane = OpenPlotTool.getInstance().getMainFrame()
				.getTabPane();
		return tabbedPane.getTabCount();
	}

	// update tab title
	public static void updateTabText() {
		JTabbedPane tabbedPane = OpenPlotTool.getInstance().getMainFrame()
				.getTabPane();
		// get selected plot and index
		Plot selectedPlot = getSelectedPlot();
		int selectedIndex = getSelectedIndex();
		if (selectedPlot.getPlotFile() != null) {
			tabbedPane.setTitleAt(selectedIndex, selectedPlot.getPlotFile()
					.getName());
		} else {
			tabbedPane.setTitleAt(selectedIndex, "New");
		}
	}
}
