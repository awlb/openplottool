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

package main;

import gui.ErrorBox;
import gui.MainFrame;
import handlers.AxisHandler;
import handlers.HelpHandler;
import handlers.PageFileHandler;
import handlers.PageHandler;
import handlers.PreferenceHandler;
import handlers.StrokeTypeHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import data.DataManager;

public class OpenPlotTool {

	public static String programName = "OpenPlot Tool 0.3";

	// about menu listener inner class
	private class AboutMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == mainFrame.getAboutMenuItem()) {
				HelpHandler.showAbout();
			}
		}
	}

	// edit menu listener inner class
	private class EditMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == mainFrame.getPrefMenuItem()) {
				PreferenceHandler.editPreferences();
			}
		}
	}

	// file menu listener inner class
	private class FileMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == mainFrame.getNewMenuItem()) {
				PageHandler.newPlotPage();
			} else if (event.getSource() == mainFrame.getOpenMenuItem()) {
				PageFileHandler.openPageFile();
			} else if (event.getSource() == mainFrame.getSaveMenuItem()) {
				PageFileHandler.savePageFile();
			} else if (event.getSource() == mainFrame.getSaveAsMenuItem()) {
				PageFileHandler.saveAsPageFile();
			} else if (event.getSource() == mainFrame.getSaveAllMenuItem()) {
				// unimplemented
			} else if (event.getSource() == mainFrame.getCloseMenuItem()) {
				PageHandler.closeSelectedPlot();
			} else if (event.getSource() == mainFrame.getCloseAllMenuItem()) {
				PageHandler.closeAllPlots();
			} else if (event.getSource() == mainFrame.getPrintMenuItem()) {
				PageHandler.printSelectedPlot();
			} else if (event.getSource() == mainFrame.getExportMenuItem()) {
				PageHandler.export();
			} else if (event.getSource() == mainFrame.getQuitMenuItem()) {
				closeProgram();
			}
		}
	}

	// window listener inner class
	private class MainWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent event) {
			closeProgram();
		}
	}

	// page menu listener inner class
	private class PageMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == mainFrame.getPageInformationItem()) {
				PageHandler.displayInformation();
			} else if (event.getSource() == mainFrame.getPageSettingsItem()) {
				PageHandler.editPage();
			} else if (event.getSource() == mainFrame.getAxisSettingsItem()) {
				AxisHandler.editAxis();
			}
		}
	}

	// plot menu listener inner class
	private class PlotMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == mainFrame.getAddDataItem()) {
				DataManager.addDataSet();
			} else if (event.getSource() == mainFrame.getAddFunctionItem()) {
				DataManager.addFunction();
			} else if (event.getSource() == mainFrame.getAddPointItem()) {
				DataManager.addPoint();
			} else if (event.getSource() == mainFrame.getEditSelectedItem()) {
				DataManager.editData();
			} else if (event.getSource() == mainFrame.getRemoveSelectedItem()) {
				DataManager.removeData();
			}
		}
	}

	// tab listener inner class
	private class TabListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			PageHandler.updatePageChange();

		}
	}

	// list click listener inner class
	private class ListClickListener extends MouseAdapter {
		public void mouseClicked(MouseEvent evt) {
			JList list = (JList) evt.getSource();
			if (evt.getClickCount() == 2) {
				int index = list.locationToIndex(evt.getPoint());
				DataManager.editData(index);
			}
		}
	}

	private static MainFrame mainFrame;

	public static MainFrame getMainFrame() {
		return mainFrame;
	}

	public static void main(String[] args) {
		new OpenPlotTool();
	}

	public OpenPlotTool() {
		// load preferences on main thread
		loadPreferences();
		loadStrokes();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// create gui and add action listeners on the EDT
				createGui();
				addActionListeners();
			}
		});
	}

	private void addActionListeners() {
		// add file menu action listeners
		ActionListener fileMenuListener = new FileMenuListener();
		mainFrame.getNewMenuItem().addActionListener(fileMenuListener);
		mainFrame.getOpenMenuItem().addActionListener(fileMenuListener);
		mainFrame.getSaveMenuItem().addActionListener(fileMenuListener);
		mainFrame.getSaveAsMenuItem().addActionListener(fileMenuListener);
		mainFrame.getSaveAllMenuItem().addActionListener(fileMenuListener);
		mainFrame.getCloseMenuItem().addActionListener(fileMenuListener);
		mainFrame.getCloseAllMenuItem().addActionListener(fileMenuListener);
		mainFrame.getPrintMenuItem().addActionListener(fileMenuListener);
		mainFrame.getExportMenuItem().addActionListener(fileMenuListener);
		mainFrame.getQuitMenuItem().addActionListener(fileMenuListener);
		// add edit menu action listeners
		ActionListener editMenuListener = new EditMenuListener();
		mainFrame.getPrefMenuItem().addActionListener(editMenuListener);
		// add page menu action listeners
		ActionListener pageMenuListener = new PageMenuListener();
		mainFrame.getPageInformationItem().addActionListener(pageMenuListener);
		mainFrame.getPageSettingsItem().addActionListener(pageMenuListener);
		mainFrame.getAxisSettingsItem().addActionListener(pageMenuListener);
		// add plot menu action listeners
		ActionListener plotMenuListener = new PlotMenuListener();
		mainFrame.getAddDataItem().addActionListener(plotMenuListener);
		mainFrame.getAddFunctionItem().addActionListener(plotMenuListener);
		mainFrame.getAddPointItem().addActionListener(plotMenuListener);
		mainFrame.getEditSelectedItem().addActionListener(plotMenuListener);
		mainFrame.getRemoveSelectedItem().addActionListener(plotMenuListener);
		// add help menu action listeners
		ActionListener helpMenuListener = new AboutMenuListener();
		mainFrame.getAboutMenuItem().addActionListener(helpMenuListener);
		// add listener to plot tabbed panel
		mainFrame.getPlotPanel().addChangeListener(new TabListener());
		// add click listeners to lists
		mainFrame.getDataSetList().addMouseListener(new ListClickListener());
		mainFrame.getFunctionList().addMouseListener(new ListClickListener());
		mainFrame.getPointList().addMouseListener(new ListClickListener());
	}

	private void closeProgram() {
		try {
			// save preferences before exit if they have been changed
			if(PreferenceHandler.hasChanged()) {
				PreferenceHandler.savePreferences();
			}
			// save strokes before exit if they have been edited
			if(StrokeTypeHandler.hasChanged()) {
				StrokeTypeHandler.saveStrokeTypes();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void createGui() {
		// create and show the main window
		mainFrame = new MainFrame();
		mainFrame.addWindowListener(new MainWindowListener());
		mainFrame.setVisible(true);
	}

	private void loadPreferences() {
		// load user preferences from xml file
		try {
			PreferenceHandler.loadPreferences();
		} catch (SAXException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to load preferences", e);
			errorDialog.setVisible(true);

		} catch (FileNotFoundException e) {
			// preference file not found so defaults will be used
		} catch (IOException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to load preferences", e);
			errorDialog.setVisible(true);
		} catch (ParserConfigurationException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to load preferences", e);
			errorDialog.setVisible(true);
		}
		// set gui look and feel
		String lookAndFeelString = "";
		if (PreferenceHandler.getSettings().getLookAndFeel().equals("System")) {
			lookAndFeelString = UIManager.getSystemLookAndFeelClassName();
		} else {
			lookAndFeelString = UIManager
					.getCrossPlatformLookAndFeelClassName();
		}
		try {
			UIManager.setLookAndFeel(lookAndFeelString);
		} catch (InstantiationException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to set look and feel", e);
			errorDialog.setVisible(true);
		} catch (ClassNotFoundException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to set look and feel", e);
			errorDialog.setVisible(true);
		} catch (UnsupportedLookAndFeelException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to set look and feel", e);
			errorDialog.setVisible(true);
		} catch (IllegalAccessException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to set look and feel", e);
			errorDialog.setVisible(true);
		}
	}
	
	private void loadStrokes() {
		try {
			StrokeTypeHandler.loadStrokeTypes();
		} catch (SAXException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to load strokes", e);
			errorDialog.setVisible(true);
		} catch (IOException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to load strokes", e);
			errorDialog.setVisible(true);
		} catch (ParserConfigurationException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to load strokes", e);
			errorDialog.setVisible(true);
		}
	}
}
