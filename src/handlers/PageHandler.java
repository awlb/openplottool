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

import gui.ErrorBox;
import gui.ExportDialog;
import gui.NewPlotDialog;
import gui.PageInformationDialog;
import gui.PageSettingsDialog;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import main.OpenPlotTool;
import plot.PageSettings;
import plot.PlotPage;
import plot.cartesian.CartesianAxis;
import plot.cartesian.CartesianSettings;
import plot.piechart.PieChartAxis;
import plot.piechart.PieChartSettings;

public class PageHandler {
	// plot page list
	private static ArrayList<PlotPage> plotPages = new ArrayList<PlotPage>();

	// method to add plot page
	public static void addPlotPage(PlotPage page) {
		// add page to list
		plotPages.add(page);
		// add tab for page
		OpenPlotTool.getMainFrame().getPlotPanel().addTab(
				page.getPageFile().getName(), page);
		// set index to newly created tab
		OpenPlotTool.getMainFrame().getPlotPanel().setSelectedIndex(
				OpenPlotTool.getMainFrame().getPlotPanel().getTabCount() - 1);
	}

	// method to close all plots
	public static void closeAllPlots() {
		plotPages.clear();
		OpenPlotTool.getMainFrame().getPlotPanel().removeAll();
	}

	// method to close currently selected plot
	public static void closeSelectedPlot() {
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		int index = OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedIndex();
		if (page != null) {
			// removed selected page
			plotPages.remove(page);
			OpenPlotTool.getMainFrame().getPlotPanel().removeTabAt(index);
			updatePageChange();
		}
	}

	// method to show page information dialog
	public static void displayInformation() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			PageInformationDialog dialog = new PageInformationDialog(page);
			dialog.setVisible(true);
		}
	}

	// method to edit page settings
	public static void editPage() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			// create and display a PageSettingsDialog
			PageSettingsDialog dialog = new PageSettingsDialog(page
					.getSettings());
			dialog.setVisible(true);
			if (dialog.getPressed() == PageSettingsDialog.APPLY_PRESSED) {
				// update page settings
				PageSettings settings = dialog.getNewSettings();
				page.setSettings(settings);
				// repaint page
				page.repaint();
			}
			dialog.dispose();
		}
	}

	// method to export current page
	public static void export() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();

		if (page != null) {
			// create and display a ExportDialog
			ExportDialog dialog = new ExportDialog(page);
			dialog.setVisible(true);
			if (dialog.getPressed() == ExportDialog.EXPORT_PRESSED) {
				// get file and format
				File file = dialog.getFile();
				String format = dialog.getFormat();
				if (file != null) {
					// create image of page
					int width = page.getWidth();
					int height = page.getHeight();
					BufferedImage image = new BufferedImage(width, height,
							BufferedImage.TYPE_INT_RGB);
					Graphics2D g2 = image.createGraphics();
					page.paint(g2);
					g2.dispose();
					// write image to file
					try {
						ImageIO.write(image, format, file);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(OpenPlotTool
								.getMainFrame(),
								"Error while exporting to file.\n"
										+ e.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
							"Invalid export file location.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			dialog.dispose();
		}
	}

	// create new plot page
	public static void newPlotPage() {
		NewPlotDialog dialog = new NewPlotDialog();
		dialog.setVisible(true);
		if (dialog.getPressed() == NewPlotDialog.OK_PRESSED) {
			String ID = dialog.getSelected().getEntryPlotID();
			if (ID.equals("cartesianxyplot")) {
				// create new Cartesian plot page and add to list
				CartesianAxis axis = new CartesianAxis(new CartesianSettings());
				PlotPage newPage = new PlotPage(axis);
				newPage.setType("cartesianxyplot");
				plotPages.add(newPage);
				OpenPlotTool.getMainFrame().getPlotPanel().addTab(
						newPage.getSettings().getTitle(), newPage);

			} else if (ID.equals("piechart")) {
				// create new pie chart page and add to list
				PieChartAxis axis = new PieChartAxis(new PieChartSettings());
				PlotPage newPage = new PlotPage(axis);
				newPage.setType("piechart");
				plotPages.add(newPage);
				OpenPlotTool.getMainFrame().getPlotPanel().addTab(
						newPage.getSettings().getTitle(), newPage);
				OpenPlotTool.getMainFrame().getPlotPanel().setSelectedIndex(
						OpenPlotTool.getMainFrame().getPlotPanel()
								.getTabCount() - 1);
			}
		}
		dialog.dispose();
	}

	// method to print current plot page
	public static void printSelectedPlot() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setPrintable(page);
			if (printJob.printDialog()) {
				try {
					printJob.print();
				} catch (Exception e) {
					ErrorBox errorDialog = new ErrorBox(OpenPlotTool
							.getMainFrame(), "Failed to print page", e);
					errorDialog.setVisible(true);
				}
			}
		}
	}

	// method called when page changed
	public static void updatePageChange() {
		// clear current items from lists
		OpenPlotTool.getMainFrame().getFunctionListModel().clear();
		OpenPlotTool.getMainFrame().getPointListModel().clear();
		OpenPlotTool.getMainFrame().getDataSetListModel().clear();
		// get new selected page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			// add functions to function list
			Object[] functions = page.getPlotFunctions().toArray();
			for (int i = 0; i < functions.length; i++) {
				OpenPlotTool.getMainFrame().getFunctionListModel().addElement(
						functions[i]);
			}
			// add points to list
			Object[] points = page.getPlotPoints().toArray();
			for (int i = 0; i < points.length; i++) {
				OpenPlotTool.getMainFrame().getPointListModel().addElement(
						points[i]);
			}
			// add data sets to list
			Object[] data = page.getPlotData().toArray();
			for (int i = 0; i < data.length; i++) {
				OpenPlotTool.getMainFrame().getDataSetListModel().addElement(
						data[i]);
			}
			// update window title
			if (page.getPageFile() != null) {
				OpenPlotTool.getMainFrame().setTitle(
						OpenPlotTool.programName + " - "
								+ page.getPageFile().getAbsolutePath());
			} else {
				OpenPlotTool.getMainFrame().setTitle(
						OpenPlotTool.programName + " - "
								+ page.getSettings().getTitle());
			}
		} else {
			OpenPlotTool.getMainFrame().setTitle(OpenPlotTool.programName);
		}
	}
}
