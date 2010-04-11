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

import gui.PreferenceDialog;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import main.OpenPlotTool;
import main.Preferences;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import plot.PlotPage;
import xml.XMLFileHandler;

public class PreferenceHandler {
	private static boolean changed = false;
	private static Preferences settings = new Preferences();

	public static void editPreferences() {
		// show preference dialog
		PreferenceDialog dialog = new PreferenceDialog(settings);
		dialog.setVisible(true);
		if (dialog.getPressed() == PreferenceDialog.APPLY_PRESSED) {
			// get new settings
			settings = dialog.getNewSettings();
			changed = true;
			PlotPage page = (PlotPage) OpenPlotTool.getMainFrame()
					.getPlotPanel().getSelectedComponent();

			// reset gui look and feel
			String lookAndFeelString = "";
			if (settings.getLookAndFeel().equals("System")) {
				lookAndFeelString = UIManager.getSystemLookAndFeelClassName();
			} else {
				lookAndFeelString = UIManager
						.getCrossPlatformLookAndFeelClassName();
			}
			try {
				UIManager.setLookAndFeel(lookAndFeelString);
				SwingUtilities.updateComponentTreeUI(OpenPlotTool
						.getMainFrame());
			} catch (InstantiationException e) {
			} catch (ClassNotFoundException e) {
			} catch (UnsupportedLookAndFeelException e) {
			} catch (IllegalAccessException e) {
			}
			if (page != null) {
				// repaint page
				page.repaint();
			}
		}
		dialog.dispose();
	}

	public static Preferences getSettings() {
		// return settings
		return settings;
	}

	public static boolean hasChanged() {
		return changed;
	}

	public static void loadPreferences() throws SAXException, IOException,
			ParserConfigurationException {
		// parse preferences XML file into document
		File file = new File("settings/Preferences.xml");
		Document doc = XMLFileHandler.openPlainFile(file);
		// get document nodes
		NodeList preferenceNodes = doc.getElementsByTagName("preferences");
		if (preferenceNodes.getLength() > 0) {
			// get preferences from nodes
			NodeList preferenceData = preferenceNodes.item(0).getChildNodes();
			for (int i = 0; i < preferenceData.getLength(); i++) {
				Node curNode = preferenceData.item(i);
				if (curNode != null) {
					if (curNode.getNodeName().equals("antialiasingstring")) {
						settings.setAntiAliasing(curNode.getTextContent());
					} else if (curNode.getNodeName().equals("lookandfeel")) {
						settings.setLookAndFeel(curNode.getTextContent());
					} else if (curNode.getNodeName().equals("compressfiles")) {
						String booleanStr = curNode.getTextContent();
						settings.setCompressFiles(Boolean
								.parseBoolean(booleanStr));
					} else if (curNode.getNodeName().equals(
							"checkfileextensions")) {
						String booleanStr = curNode.getTextContent();
						settings.setCheckFileExtensions(Boolean
								.parseBoolean(booleanStr));
					} else if (curNode.getNodeName()
							.equals("addfileextensions")) {
						String booleanStr = curNode.getTextContent();
						settings.setAddFileExtensions(Boolean
								.parseBoolean(booleanStr));
					}
				}
			}
		}
	}

	public static void savePreferences() throws IOException {
		// create file reference
		File file = new File("settings/Preferences.xml");
		// create string buffer to hold file content
		StringBuffer fileContentBuffer = new StringBuffer();
		// add XML strings to buffer
		fileContentBuffer
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		fileContentBuffer.append("<preferences>\n");
		fileContentBuffer.append("<antialiasingstring>"
				+ settings.getAntiAliasing() + "</antialiasingstring>\n");
		fileContentBuffer.append("<lookandfeel>" + settings.getLookAndFeel()
				+ "</lookandfeel>\n");
		fileContentBuffer.append("<compressfiles>"
				+ settings.useCompressedFiles() + "</compressfiles>\n");
		fileContentBuffer.append("<compressfiles>"
				+ settings.useCompressedFiles() + "</compressfiles>\n");
		fileContentBuffer
				.append("<checkfileextensions>"
						+ settings.isCheckFileExtensions()
						+ "</checkfileextensions>\n");
		fileContentBuffer.append("<addfileextensions>"
				+ settings.isAddFileExtensions() + "</addfileextensions>\n");
		fileContentBuffer.append("</preferences>\n");
		// write string buffer in plain format
		XMLFileHandler.savePlainFile(fileContentBuffer, file);
	}
}
