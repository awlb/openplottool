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
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import main.OpenPlotTool;
import net.smplmathparser.MathParserException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import plot.PlotPage;
import xml.CartesianXMLBuilder;
import xml.CartesianXMLLoader;
import xml.PieChartXMLBuilder;
import xml.PieChartXMLLoader;
import xml.XMLFileHandler;

public class PageFileHandler {
	private static String getFileExtension(File file) {
		// get the file extension of the given file
		String fileName = file.getName();
		String extension = (fileName.lastIndexOf(".") == -1) ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		return extension;
	}

	public static void openPageFile() {
		// show file chooser
		JFileChooser filechooser = new JFileChooser();
		filechooser.showOpenDialog(OpenPlotTool.getMainFrame());
		// get selected file
		File selectedFile = filechooser.getSelectedFile();
		if (selectedFile != null) {
			PlotPage loadedPage = performOpen(selectedFile);
			// if page is null there was an error while loading
			if (loadedPage != null) {
				PageHandler.addPlotPage(loadedPage);
			}
		}
	}

	private static PlotPage performOpen(File file) {
		// Initialise String XML document for loaded file
		Document xmlDocument = null;
		// boolean indicating if file should tried to be opened as plain
		boolean tryPlain = false;
		// try open file as compressed one
		try {
			xmlDocument = XMLFileHandler.openCompressedFile(file);
		} catch (IOException e) {
			if (e.getMessage().equals("Not in GZIP format")) {
				// file was not in GZIP format so attempt plain
				tryPlain = true;
			} else {
				JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
						"IO error while reading file.", "Failed to open file",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
		} catch (SAXException e) {
			// file was in GZIP format but did not contain XML
			JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
					"SAX error while reading file.\n"
							+ "GZIP compressed non XML file",
					"Failed to open file", JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (ParserConfigurationException e) {
			// error with parser occurred
			JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
					"XML Parser error while reading file.",
					"Failed to open file", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		// try open plain if required
		if (xmlDocument == null && tryPlain) {
			try {
				xmlDocument = XMLFileHandler.openPlainFile(file);
			} catch (SAXException e) {
				// file was not of XML type
				JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
						"SAX error while reading file.\n" + "Non XML file",
						"Failed to open file", JOptionPane.ERROR_MESSAGE);
				return null;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
						"IO error while reading file.", "Failed to open file",
						JOptionPane.ERROR_MESSAGE);
				return null;
			} catch (ParserConfigurationException e) {
				// error with parser occurred
				JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
						"XML Parser error while reading file.",
						"Failed to open file", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}

		if (xmlDocument != null) {
			// XML document was loaded successfully -- may still be non open
			// plot tool XML though
			if (XMLFileHandler.getFileVersion(xmlDocument) == -1) {
				// indicates non open plot tool XML file
				JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
						"Document format error while reading file.\n"
								+ "XML document is not in correct format.",
						"Failed to open file", JOptionPane.ERROR_MESSAGE);
				return null;
			} else if (XMLFileHandler.getFileVersion(xmlDocument) != 0.3) {
				// file version is old ask if they want to load anyway
				int chosen = JOptionPane.showConfirmDialog(OpenPlotTool
						.getMainFrame(),
						"The file you are trying to open is in an old format.\n"
								+ "Do you want to try open the file anyway?",
						"Old file version", JOptionPane.YES_NO_OPTION);
				if (chosen == JOptionPane.NO_OPTION) {
					return null;
				}
			}
			// get plot type
			String plotType = XMLFileHandler.getPlotType(xmlDocument);
			// Declare plot page
			PlotPage loadedPage = null;
			if (plotType.equals("cartesianxyplot")) {
				try {
					loadedPage = CartesianXMLLoader.loadFile(xmlDocument);
				} catch (MathParserException e) {
					// error with saved function
					JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
							"Math Parser error while creating page.",
							"Failed to create page", JOptionPane.ERROR_MESSAGE);
					return loadedPage;
				}
			} else if (plotType.equals("piechartplot")) {
				loadedPage = PieChartXMLLoader.loadFile(xmlDocument);
			}

			if (loadedPage != null) {
				loadedPage.setPageFile(file);
				loadedPage.setType(plotType);
				return loadedPage;
			} else {
				// error with saved function
				JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
						"Error while creating page.", "Failed to create page",
						JOptionPane.ERROR_MESSAGE);
				return loadedPage;
			}
		} else {
			// possible uncaught error could cause xmlDocument to be null
			JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
					"Unknown error while reading file.", "Failed to open file",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	private static void performSave(PlotPage page, File saveFile) {
		StringBuffer xmlDocumentBuffer = null;
		if (page.getType().equals("cartesianxyplot")) {
			// create string buffer of XML file content for cartesian
			xmlDocumentBuffer = CartesianXMLBuilder.buildXML(page);
		} else if (page.getType().equals("piechartplot")) {
			// create string buffer of XML file content for pie chart
			xmlDocumentBuffer = PieChartXMLBuilder.buildXML(page);
		}

		try {
			// check what format the file should be saved in
			if (getFileExtension(saveFile).equals("opc")) {
				// save XML content in compressed format
				XMLFileHandler.saveCompressedFile(xmlDocumentBuffer, saveFile);
			} else if (getFileExtension(saveFile).equals("opp")) {
				// save XML content in plain format
				XMLFileHandler.savePlainFile(xmlDocumentBuffer, saveFile);
			} else if (PreferenceHandler.getSettings().useCompressedFiles()) {
				// save XML content in compressed format
				XMLFileHandler.saveCompressedFile(xmlDocumentBuffer, saveFile);
			} else {
				// save XML content in plain format
				XMLFileHandler.savePlainFile(xmlDocumentBuffer, saveFile);
			}
		} catch (FileNotFoundException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to save page", e);
			errorDialog.setVisible(true);
		} catch (IOException e) {
			ErrorBox errorDialog = new ErrorBox(OpenPlotTool.getMainFrame(),
					"Failed to save page", e);
			errorDialog.setVisible(true);
		}
	}

	public static void revertPageFile() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		int index = OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedIndex();
		if (page != null) {
			if (page.getPageFile() != null) {
				PlotPage loadedPage = performOpen(page.getPageFile());
				if (loadedPage != null) {
					PageHandler.replacePage(loadedPage, index);
				} else {

				}
			} else {
				JOptionPane.showMessageDialog(OpenPlotTool.getMainFrame(),
						"Page has not been saved to a file.",
						"Failed to revert page", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void saveAsPageFile() {
		// get selected plot page
		PlotPage page = (PlotPage) OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedComponent();
		int index = OpenPlotTool.getMainFrame().getPlotPanel()
				.getSelectedIndex();
		if (page != null) {
			JFileChooser fc = new JFileChooser();
			fc.showSaveDialog(OpenPlotTool.getMainFrame());
			// get selected file
			File saveFile = fc.getSelectedFile();
			if (saveFile != null) {
				// get required extension
				String fileExtension = PreferenceHandler.getSettings()
						.useCompressedFiles() ? "opc" : "opp";
				if (fileExtension.equals(getFileExtension(saveFile))) {
					// file extension is ok
				} else if (getFileExtension(saveFile).equals("")
						&& PreferenceHandler.getSettings()
								.isAddFileExtensions()) {
					saveFile = new File(saveFile.getAbsoluteFile() + "."
							+ fileExtension);
				} else if (PreferenceHandler.getSettings()
						.isCheckFileExtensions()) {
					int chosen = JOptionPane
							.showConfirmDialog(
									OpenPlotTool.getMainFrame(),
									"The file extension you have used is not the recommened one.\n"
											+ "Do you want to save with that extension anyway?",
									"Wrong File Extension",
									JOptionPane.YES_NO_OPTION);
					if (chosen == JOptionPane.NO_OPTION) {
						return;
					}
				}
				// check if saving will overwrite another file
				if (saveFile.exists() && !(page.getPageFile().equals(saveFile))) {
					int chosen = JOptionPane.showConfirmDialog(OpenPlotTool
							.getMainFrame(),
							"A file with the name you have given alreadt exists.\n"
									+ "Do you wish to overwrite this file?",
							"Overwrite File", JOptionPane.YES_NO_OPTION);
					if (chosen == JOptionPane.NO_OPTION) {
						return;
					}
				}
				// perform save
				performSave(page, saveFile);
				// set page file
				page.setPageFile(saveFile);
				// update tab title and tool tip
				OpenPlotTool.getMainFrame().getPlotPanel().setTitleAt(index,
						saveFile.getName());
				OpenPlotTool.getMainFrame().getPlotPanel().setToolTipTextAt(
						index, saveFile.getAbsolutePath());
				PageHandler.updatePageChange();
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
}
