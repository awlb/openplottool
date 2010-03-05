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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.xml.parsers.ParserConfigurationException;

import net.smplmathparser.MathParserException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import data.cartesian.CartesianXMLLoader;
import data.cartesian.CartesianXMLBuilder;

import plot.PlotPage;

import main.OpenPlotTool;

public class PageFileHandler {
	public static void openPageFile() {
		// show file chooser
		JFileChooser filechooser = new JFileChooser();
		filechooser.showOpenDialog(OpenPlotTool.getMainFrame());
		// get selected file
		File selectedFile = filechooser.getSelectedFile();
		if (selectedFile != null) {
			// Initialise String plotType = getPlotType(xmlDocument);XML
			// document for loaded file
			Document xmlDocument = null;
			// get extension of file
			String fileExtension = getFileExtension(selectedFile);
			try {
				if (fileExtension.equals("opc")) {
					// extension indicates compressed file
					xmlDocument = XMLFileHandler
							.openCompressedFile(selectedFile);
				} else if (fileExtension.equals("opp")) {
					// extension indicates plain file
					xmlDocument = XMLFileHandler.openPlainFile(selectedFile);
				}
				if (XMLFileHandler.getFileVersion(xmlDocument) == 0.1) {
					String plotType = XMLFileHandler.getPlotType(xmlDocument);
					PlotPage loadedPage = null;
					if (plotType.equals("cartesianxyplot")) {
						loadedPage = CartesianXMLLoader.loadFile(xmlDocument);
					} else if (plotType.equals("piechartplot")) {
						loadedPage = CartesianXMLLoader.loadFile(xmlDocument);
					} else {
						// unknown plot type -- probably from newer version
						// TODO add code to handle unknown plot types
					}
					if (loadedPage != null) {
						// set page file
						loadedPage.setPageFile(selectedFile);
						// set page type
						loadedPage.setType(plotType);
						// add in loaded page
						PageHandler.addPlotPage(loadedPage);
					}
				} else {
					// old or new file version
					// TODO add code to handle older and newer file versions
				}
			} catch (IOException e) {
				ErrorBox errorDialog = new ErrorBox(
						OpenPlotTool.getMainFrame(), "Failed to open file", e);
				errorDialog.setVisible(true);
			} catch (SAXException e) {
				ErrorBox errorDialog = new ErrorBox(
						OpenPlotTool.getMainFrame(), "Failed to open file", e);
				errorDialog.setVisible(true);
			} catch (ParserConfigurationException e) {
				ErrorBox errorDialog = new ErrorBox(
						OpenPlotTool.getMainFrame(), "Failed to open file", e);
				errorDialog.setVisible(true);
			} catch (MathParserException e) {
				ErrorBox errorDialog = new ErrorBox(
						OpenPlotTool.getMainFrame(),
						"Failed when building page", e);
				errorDialog.setVisible(true);
			}
		}
	}

	public static void savePageFile() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			// get page save file
			File saveFile = page.getPageFile();
			if (saveFile == null) {
				// file the page has no save file call save as method instead
				saveAsPageFile();
			} else {
				performSave(page, saveFile);
			}
		}
	}

	public static void saveAsPageFile() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		if (page != null) {
			JFileChooser fc = new JFileChooser();
			fc.showSaveDialog(OpenPlotTool.getMainFrame());
			// get selected file
			File saveFile = fc.getSelectedFile();
			if (saveFile != null) {
				performSave(page, saveFile);
			}
		}
	}
	
	private static void performSave(PlotPage page, File saveFile) {
		if (page.getType().equals("cartesianxyplot")) {
			// create string buffer of XML file content
			StringBuffer xmlDocumentBuffer = CartesianXMLBuilder
					.buildXML(page);
			try {
				// check what format the file should be saved in
				if (PreferenceHandler.getSettings()
						.useCompressedFiles()) {
					// save XML content in compressed format
					XMLFileHandler.saveCompressedFile(
							xmlDocumentBuffer, saveFile);
				} else {
					// save XML content in plain format
					XMLFileHandler.savePlainFile(xmlDocumentBuffer,
							saveFile);
				}
			} catch (FileNotFoundException e) {
				ErrorBox errorDialog = new ErrorBox(OpenPlotTool
						.getMainFrame(), "Failed to save page", e);
				errorDialog.setVisible(true);
			} catch (IOException e) {
				ErrorBox errorDialog = new ErrorBox(OpenPlotTool
						.getMainFrame(), "Failed to save page", e);
				errorDialog.setVisible(true);
			}
		} else if (page.getType().equals("piechartplot")) {

		}
	}

	private static String getFileExtension(File file) {
		// get the file extension of the given file
		String fileName = file.getName();
		String extension = (fileName.lastIndexOf(".") == -1) ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		return extension;
	}
}
