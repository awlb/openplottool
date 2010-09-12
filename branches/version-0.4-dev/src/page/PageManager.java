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

package page;

import gui.MainFrame;

import java.util.ArrayList;

import main.OpenPlotTool;

public class PageManager {
	// list of pages
	private static ArrayList<Page> pageList = new ArrayList<Page>();

	public static void addPage(Page page) {
		// add page to list
		if (page != null) {
			pageList.add(page);
		}
	}

	public static Page getSelected() {
		// get the page currently selected
		MainFrame mainFrame = OpenPlotTool.getInstance().getMainFrame();
		Page selectedPage = (Page) mainFrame.getTabPane()
				.getSelectedComponent();
		return selectedPage;
	}

	public static int getSelectedIndex() {
		// get the index of the page selected
		MainFrame mainFrame = OpenPlotTool.getInstance().getMainFrame();
		int selectedIndex = mainFrame.getTabPane().getSelectedIndex();
		return selectedIndex;
	}

	public static void removeAll() {
		// clear list of all pages
		pageList.clear();
	}

	public static void removePage(Page page) {
		// remove page from list
		if (page != null) {
			pageList.remove(page);
		}
	}
}