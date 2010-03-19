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

package xml;

import java.awt.Color;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import plot.PlotPage;
import plot.piechart.PieChartAxis;
import plot.piechart.PieChartDataSet;
import plot.piechart.PieChartItem;
import plot.piechart.PieChartSettings;

public class PieChartXMLLoader {
	// xml document to loaded
	private static Document doc;

	private static void loadAxisSettings(PlotPage newPage) {
		// load axis settings
		NodeList axisNodes = doc.getElementsByTagName("axis");
		if (axisNodes.getLength() > 0) {
			// get axis settings nodes
			NodeList axisInnerNodes = axisNodes.item(0).getChildNodes();
			for (int i = 0; i < axisInnerNodes.getLength(); i++) {
				// loop through axis settings nodes
				Node curNode = axisInnerNodes.item(i);
				if (curNode.getNodeName().equals("bgcolor")) {
					String colorStr = curNode.getTextContent();
					newPage.getAxis().getPlotSettings().setBackgroundColor(
							Color.decode(colorStr));
				} else if (curNode.getNodeName().equals("outlinedrawcolor")) {
					String colorStr = curNode.getTextContent();
					((PieChartSettings) newPage.getAxis().getPlotSettings())
							.setOutlineDrawColor(Color.decode(colorStr));
				} else if (curNode.getNodeName().equals("textcolor")) {
					String colorStr = curNode.getTextContent();
					newPage.getAxis().getPlotSettings().setTextColor(
							Color.decode(colorStr));
				}
			}
		}
	}

	private static void loadDataSets(PlotPage newPage) {
		// load data sets
		// get data set nodes
		NodeList dataSetNodes = doc.getElementsByTagName("dataset");
		if (dataSetNodes.getLength() > 0) {
			PieChartDataSet dataSet = new PieChartDataSet();
			ArrayList<PieChartItem> dataItems = new ArrayList<PieChartItem>();
			NodeList dataSetData = dataSetNodes.item(0).getChildNodes();
			for (int i = 0; i < dataSetData.getLength(); i++) {
				Node curNode = dataSetData.item(i);
				if (curNode.getNodeName().equals("title")) {
					dataSet.setDataTitle(curNode.getTextContent());
				} else if (curNode.getNodeName().equals("dataitems")) {
					NodeList itemNodes = curNode.getChildNodes();
					for (int j = 0; j < itemNodes.getLength(); j++) {
						Node itemNode = itemNodes.item(j);
						if (itemNode.getNodeName().equals("dataitem")) {
							PieChartItem dataItem = new PieChartItem();
							NodeList curItemNodes = itemNodes.item(j)
									.getChildNodes();
							for (int k = 0; k < curItemNodes.getLength(); k++) {
								Node curItemNode = curItemNodes.item(k);
								if (curItemNode.getNodeName().equals("color")) {
									String colorStr = curItemNode
											.getTextContent();
									dataItem.setColor(Color.decode(colorStr));
								} else if (curItemNode.getNodeName().equals(
										"name")) {
									dataItem.setName(curItemNode
											.getTextContent());
								} else if (curItemNode.getNodeName().equals(
										"value")) {
									double nodeVal = Double
											.parseDouble(curItemNode
													.getTextContent());
									dataItem.setValue(nodeVal);
								}
							}
							dataItems.add(dataItem);
						}
					}
				}
			}
			dataSet.setItems(dataItems);
			newPage.addData(dataSet);
		}
	}

	public static PlotPage loadFile(Document inDoc) {
		doc = inDoc;
		// create axis for loaded page
		PieChartAxis axis = new PieChartAxis(new PieChartSettings());
		// create new page
		PlotPage newPage = new PlotPage(axis);
		// load page settings and change new page settings to match
		loadPageSettings(newPage);
		loadAxisSettings(newPage);
		loadDataSets(newPage);

		// return loaded page
		return newPage;
	}

	private static void loadPageSettings(PlotPage newPage) {
		// load page settings
		NodeList settingsNodes = doc.getElementsByTagName("settings");
		if (settingsNodes.getLength() > 0) {
			NodeList settingsInnerNodes = settingsNodes.item(0).getChildNodes();
			for (int i = 0; i < settingsInnerNodes.getLength(); i++) {
				// loop through settings nodes
				Node curNode = settingsInnerNodes.item(i);
				if (curNode.getNodeName().equals("bgcolor")) {
					String colorStr = curNode.getTextContent();
					newPage.getSettings().setBackgroundColor(
							Color.decode(colorStr));
				} else if (curNode.getNodeName().equals("bordercolor")) {
					String colorStr = curNode.getTextContent();
					newPage.getSettings()
							.setBorderColor(Color.decode(colorStr));
				} else if (curNode.getNodeName().equals("drawborder")) {
					String booleanStr = curNode.getTextContent();
					newPage.getSettings().setDrawBorder(
							Boolean.parseBoolean(booleanStr));
				} else if (curNode.getNodeName().equals("borderdrawtype")) {
					String typeStr = curNode.getTextContent();
					newPage.getSettings().setBorderDrawType(typeStr);
				} else if (curNode.getNodeName().equals("title")) {
					String title = curNode.getTextContent();
					newPage.getSettings().setTitle(title);
				} else if (curNode.getNodeName().equals("titlecolor")) {
					String colorStr = curNode.getTextContent();
					newPage.getSettings().setTitleColor(Color.decode(colorStr));
				}
			}
		}
	}
}
