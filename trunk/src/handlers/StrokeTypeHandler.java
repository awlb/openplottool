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

import gui.StrokeEditor;

import java.awt.BasicStroke;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import plot.StrokeType;
import xml.XMLFileHandler;

public class StrokeTypeHandler {
	private static boolean changed = false;
	private static Map<String, StrokeType> strokeTypeList = new HashMap<String, StrokeType>();

	public static void addStrokeType(StrokeType strokeType) {
		strokeTypeList.put(strokeType.getName(), strokeType);
		changed = true;
	}

	public static void showStrokeEditor() {
		StrokeEditor editor = new StrokeEditor();
		editor.setVisible(true);
		editor.dispose();
	}

	public static StrokeType getStrokeType(String name) {
		StrokeType strokeType = strokeTypeList.get(name);
		return strokeType;
	}

	public static String[] getStrokeTypeNames() {
		String[] strokeTypeArray = new String[strokeTypeList.size()];
		strokeTypeArray = strokeTypeList.keySet().toArray(strokeTypeArray);
		return strokeTypeArray;
	}

	public static boolean hasChanged() {
		return changed;
	}

	public static void loadStrokeTypes() throws SAXException, IOException,
			ParserConfigurationException {
		// parse stroke file into document
		File file = new File("settings/Strokes.xml");
		Document doc = XMLFileHandler.openPlainFile(file);
		// get document nodes
		NodeList strokeNodes = doc.getElementsByTagName("stroke");
		if (strokeNodes.getLength() > 0) {
			// get preferences from nodes
			for (int i = 0; i < strokeNodes.getLength(); i++) {
				Node curStrokeNode = strokeNodes.item(i);
				if (curStrokeNode != null) {
					NodeList strokeDataNodes = curStrokeNode.getChildNodes();
					// create default values
					String name = "New";
					float width = 1.0f;
					int cap = 2;
					int join = 2;
					float miterlimit = 10.0f;
					float[] dash = { 1.0f, 2.0f };
					float dashPhase = 0.0f;

					if (strokeDataNodes.getLength() > 0) {
						for (int j = 0; j < strokeDataNodes.getLength(); j++) {
							Node curDataNode = strokeDataNodes.item(j);
							if (curDataNode.getNodeName().equals("name")) {
								name = curDataNode.getTextContent();
							} else if (curDataNode.getNodeName()
									.equals("width")) {
								width = Float.parseFloat(curDataNode
										.getTextContent());
							} else if (curDataNode.getNodeName().equals("cap")) {
								cap = Integer.parseInt(curDataNode
										.getTextContent());
							} else if (curDataNode.getNodeName().equals("join")) {
								join = Integer.parseInt(curDataNode
										.getTextContent());
							} else if (curDataNode.getNodeName().equals(
									"miterlimit")) {
								miterlimit = Float.parseFloat(curDataNode
										.getTextContent());
							} else if (curDataNode.getNodeName().equals("dash")) {
								String[] splitLine = curDataNode
										.getTextContent().split(",");
								float[] tempDash = new float[splitLine.length];
								for (int k = 0; k < splitLine.length; k++) {
									tempDash[k] = Float
											.parseFloat(splitLine[k]);
								}
								dash = tempDash;
							} else if (curDataNode.getNodeName().equals(
									"dashphase")) {
								dashPhase = Float.parseFloat(curDataNode
										.getTextContent());
							}
						}
					}
					BasicStroke stroke = new BasicStroke(width, cap, join,
							miterlimit, dash, dashPhase);
					StrokeType strokeType = new StrokeType(name, stroke);
					strokeTypeList.put(name, strokeType);
				}
			}
		}
	}

	public static void removeStrokeType(StrokeType strokeType) {
		strokeTypeList.remove(strokeType);
		changed = true;
	}

	public static void saveStrokeTypes() throws IOException {
		// create file reference
		File file = new File("settings/Strokes.xml");
		// create string buffer to hold file content
		StringBuffer fileContentBuffer = new StringBuffer();
		// get list of strokes
		StrokeType[] strokeList = new StrokeType[strokeTypeList.size()];
		strokeList = strokeTypeList.values().toArray(strokeList);
		// add XML strings to buffer
		fileContentBuffer
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		fileContentBuffer.append("<strokes>\n");
		for (int i = 0; i < strokeList.length; i++) {
			StrokeType stroke = strokeList[i];
			fileContentBuffer.append("<stroke>\n");
			fileContentBuffer.append("<name>" + stroke.getName() + "</name>\n");
			fileContentBuffer.append("<width>"
					+ stroke.getStroke().getLineWidth() + "</width>\n");
			fileContentBuffer.append("<cap>" + stroke.getStroke().getEndCap()
					+ "</cap>\n");
			fileContentBuffer.append("<join>"
					+ stroke.getStroke().getLineJoin() + "</join>\n");
			fileContentBuffer.append("<miterlimit>"
					+ stroke.getStroke().getMiterLimit() + "</miterlimit>\n");
			fileContentBuffer.append("<dash>");
			float[] dashArray = stroke.getStroke().getDashArray();
			for (int j = 0; j < dashArray.length; j++) {
				fileContentBuffer.append(dashArray[i]);
				if (j != (dashArray.length - 1)) {
					fileContentBuffer.append(",");
				}
			}
			fileContentBuffer.append("</dash>\n");
			fileContentBuffer.append("<dashphase>"
					+ stroke.getStroke().getDashPhase() + "</dashphase>\n");
			fileContentBuffer.append("</stroke>\n");
		}
		fileContentBuffer.append("</strokes>\n");
		// write string buffer in plain format
		XMLFileHandler.savePlainFile(fileContentBuffer, file);
	}
}
