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

package main;

import gui.MainFrame;
import handlers.StrokeTypeHandler;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class OpenPlotTool {

	// openplot tool instance
	private static OpenPlotTool instance = null;

	public static OpenPlotTool getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		instance = new OpenPlotTool();
	}

	// main frame
	MainFrame mainFrame = null;

	protected OpenPlotTool() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// create gui
				mainFrame = new MainFrame();
				// add action listener

				mainFrame.setVisible(true);
			}
		});
		// load strokes from XML
		try {
			StrokeTypeHandler.loadStrokeTypes();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}
}
