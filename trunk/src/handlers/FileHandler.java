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
import gui.NewPlotDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import main.OpenPlotTool;
import page.Page;
import page.PageManager;
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
		// remove all pages from list
		PageManager.removeAll();
		// remove all tabs
		MainFrame mainFrame = OpenPlotTool.getInstance().getMainFrame();
		mainFrame.getTabPane().removeAll();
	}

	public void closePlot() {
		// get tab index
		int index = PageManager.getSelectedIndex();
		if (index >= 0) {
			// get selected page
			Page selectedPage = PageManager.getSelected();
			// remove from list
			PageManager.removePage(selectedPage);
			// remove tab
			MainFrame mainFrame = OpenPlotTool.getInstance().getMainFrame();
			mainFrame.getTabPane().removeTabAt(index);
		}

	}

	public void exportPlot() {
		System.out.println("Export plot");
	}

	public void newPlot() {
		// display new plot dialog
		NewPlotDialog newPlotDialog = new NewPlotDialog();
		newPlotDialog.setVisible(true);
		if (newPlotDialog.getPressed() == NewPlotDialog.OK_PRESSED) {
			String plotTypeName = newPlotDialog.getSelected();
			// create new plot and page
			Plot plot = PlotFactory.getPlot(plotTypeName);
			if (plot != null) {
				Page page = new Page(plot);
				// add to page list
				PageManager.addPage(page);
				// add page to tab area
				MainFrame mainFrame = OpenPlotTool.getInstance().getMainFrame();
				mainFrame.getTabPane().addTab("Test", page);
			} else {
				// display error dialog if plot type is not recognised
				JOptionPane.showMessageDialog(OpenPlotTool.getInstance()
						.getMainFrame(), "Unknown plot type " + plotTypeName
						+ " selected.\n" + "Plese report this problem.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void openPlot() {
		// create and display file chooser
		JFileChooser fileChooser = new JFileChooser();
		int btnInt = fileChooser.showOpenDialog(OpenPlotTool.getInstance()
				.getMainFrame());
		if (btnInt == JFileChooser.APPROVE_OPTION) {
			// get selected file
			File selectedFile = fileChooser.getSelectedFile();
			try {
				FileInputStream fileInputStream = new FileInputStream(
						selectedFile);
				ObjectInputStream objectInputStream = new ObjectInputStream(
						fileInputStream);
				Plot loadedPlot = (Plot) objectInputStream.readObject();
				Page loadedPage = new Page(loadedPlot);
				// add to page list
				PageManager.addPage(loadedPage);
				// add page to tab area
				MainFrame mainFrame = OpenPlotTool.getInstance().getMainFrame();
				mainFrame.getTabPane().addTab("Test", loadedPage);
			} catch (FileNotFoundException e) {
				// display error dialog if plot could not be saved
				JOptionPane.showMessageDialog(OpenPlotTool.getInstance()
						.getMainFrame(), "Failed to open plot:\n"
						+ e.getMessage(), "File Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				// display error dialog if plot could not be saved
				JOptionPane
						.showMessageDialog(OpenPlotTool.getInstance()
								.getMainFrame(), "F ailed to open plot:\n"
								+ e.getMessage(), "IO Error",
								JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e) {
				// display error dialog if plot could not be saved
				JOptionPane.showMessageDialog(OpenPlotTool.getInstance()
						.getMainFrame(), "Failed to open plot:\n"
						+ e.getMessage(), "Class Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void printPlot() {
		System.out.println("Print plot");
	}

	public void revertPlot() {
		System.out.println("Revert Plot");
	}

	public void savePlot() {
		if (PageManager.getSelected() != null) {
			// get selected plot
			Plot selectedPlot = PageManager.getSelected().getPlot();
			if (selectedPlot.getPlotFile() != null) {
				// write plot object to file
				FileOutputStream fileOutputStream;
				try {
					fileOutputStream = new FileOutputStream(selectedPlot
							.getPlotFile());
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(
							fileOutputStream);
					objectOutputStream.writeObject(selectedPlot);
				} catch (FileNotFoundException e) {
					// display error dialog if plot could not be saved
					JOptionPane.showMessageDialog(OpenPlotTool.getInstance()
							.getMainFrame(), "Failed to save plot:\n"
							+ e.getMessage(), "File Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					// display error dialog if plot could not be saved
					JOptionPane.showMessageDialog(OpenPlotTool.getInstance()
							.getMainFrame(), "Failed to save plot:\n"
							+ e.getMessage(), "IO Error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			} else {
				this.savePlotAs();
			}
		}
	}

	public void savePlotAs() {
		if (PageManager.getSelected() != null) {
			// get selected plot
			Plot selectedPlot = PageManager.getSelected().getPlot();
			// create and display file chooser
			JFileChooser fileChooser = new JFileChooser();
			int btnInt = fileChooser.showSaveDialog(OpenPlotTool.getInstance()
					.getMainFrame());
			if (btnInt == JFileChooser.APPROVE_OPTION) {
				// get selected file
				File selectedFile = fileChooser.getSelectedFile();
				try {
					// write plot object to file
					FileOutputStream fileOutputStream = new FileOutputStream(
							selectedFile);
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(
							fileOutputStream);
					objectOutputStream.writeObject(selectedPlot);
					// update plot save file
					selectedPlot.setPlotFile(selectedFile);
				} catch (FileNotFoundException e) {
					// display error dialog if plot could not be saved
					JOptionPane.showMessageDialog(OpenPlotTool.getInstance()
							.getMainFrame(), "Failed to save plot:\n"
							+ e.getMessage(), "File Error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (IOException e) {
					// display error dialog if plot could not be saved
					JOptionPane.showMessageDialog(OpenPlotTool.getInstance()
							.getMainFrame(), "Failed to save plot:\n"
							+ e.getMessage(), "IO Error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
	}
}
