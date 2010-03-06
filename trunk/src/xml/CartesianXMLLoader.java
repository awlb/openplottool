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

import net.smplmathparser.MathParserException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import plot.PlotPage;
import plot.cartesian.CartesianAxis;
import plot.cartesian.CartesianDataSet;
import plot.cartesian.CartesianFunction;
import plot.cartesian.CartesianPoint;
import plot.cartesian.CartesianSettings;

public class CartesianXMLLoader {
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
				} else if (curNode.getNodeName().equals("textcolor")) {
					String colorStr = curNode.getTextContent();
					newPage.getAxis().getPlotSettings().setTextColor(
							Color.decode(colorStr));
				} else if (curNode.getNodeName().equals("axiscolor")) {
					String colorStr = curNode.getTextContent();
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setAxisColor(Color.decode(colorStr));
				} else if (curNode.getNodeName().equals("axisdrawtype")) {
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setAxisDrawType(curNode.getTextContent());
				} else if (curNode.getNodeName().equals("maingridcolor")) {
					String colorStr = curNode.getTextContent();
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setMainGridColor(Color.decode(colorStr));
				} else if (curNode.getNodeName().equals("maingriddrawtype")) {
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setMainGridDrawType(curNode.getTextContent());
				} else if (curNode.getNodeName().equals("subgridcolor")) {
					String colorStr = curNode.getTextContent();
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setSubGridColor(Color.decode(colorStr));
				} else if (curNode.getNodeName().equals("subgriddrawtype")) {
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setSubGridType(curNode.getTextContent());
				} else if (curNode.getNodeName().equals("numxsplitsize")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setNumXSplitSize(nodeVal);
				} else if (curNode.getNodeName().equals("numysplitsize")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setNumYSplitSize(nodeVal);
				} else if (curNode.getNodeName().equals("xmax")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setxMax(nodeVal);
				} else if (curNode.getNodeName().equals("xmin")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setxMin(nodeVal);
				} else if (curNode.getNodeName().equals("xsplitsize")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setxSplitSize(nodeVal);
				} else if (curNode.getNodeName().equals("ymax")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setyMax(nodeVal);
				} else if (curNode.getNodeName().equals("ymin")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setyMin(nodeVal);
				} else if (curNode.getNodeName().equals("ysplitsize")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					((CartesianSettings) newPage.getAxis().getPlotSettings())
							.setySplitSize(nodeVal);
				}
			}
		}
	}

	private static void loadDataSets(PlotPage newPage) {
		// load data sets
		// get data set nodes
		NodeList dataSetNodes = doc.getElementsByTagName("dataset");
		for (int i = 0; i < dataSetNodes.getLength(); i++) {
			// loop through nodes creating new data set for each
			NodeList dataSetData = dataSetNodes.item(i).getChildNodes();
			ArrayList<double[]> plotData = null;
			Color drawColor = Color.RED;
			String title = "new";
			for (int j = 0; j < dataSetData.getLength(); j++) {
				// loop through nodes for each data set
				Node curNode = dataSetData.item(j);
				if (curNode.getNodeName().equals("drawcolor")) {
					String colorStr = curNode.getTextContent();
					drawColor = Color.decode(colorStr);
				} else if (curNode.getNodeName().equals("title")) {
					title = curNode.getTextContent();
				} else if (curNode.getNodeName().equals("data")) {
					// call function to get array list of data
					plotData = parseDataRow(curNode);
				}
			}
			// create data set object from loaded values
			if (plotData != null && plotData.size() > 0) {
				CartesianDataSet loadedDataSet = new CartesianDataSet();
				double[][] data = new double[plotData.size()][2];
				// convert arraylist into array of doubles
				for (int j = 0; j < plotData.size(); j++) {
					data[j][0] = plotData.get(j)[0];
					data[j][1] = plotData.get(j)[1];
				}
				loadedDataSet.setData(data);
				loadedDataSet.setDataTitle(title);
				loadedDataSet.setDrawColor(drawColor);
				// add data set to page
				newPage.addData(loadedDataSet);
			}
		}
	}

	public static PlotPage loadFile(Document inDoc) throws MathParserException {
		doc = inDoc;
		// create axis for loaded page
		CartesianAxis axis = new CartesianAxis(new CartesianSettings());
		// create new page
		PlotPage newPage = new PlotPage(axis);
		// load page settings and change new page settings to match
		loadPageSettings(newPage);
		loadAxisSettings(newPage);
		loadDataSets(newPage);
		loadPoints(newPage);
		loadFunctions(newPage);
		// return loaded page
		return newPage;
	}

	private static void loadFunctions(PlotPage newPage)
			throws MathParserException {
		// load functions
		// get function nodes
		NodeList functionNodes = doc.getElementsByTagName("function");
		for (int i = 0; i < functionNodes.getLength(); i++) {
			// loop through nodes creating new function for each
			NodeList functionData = functionNodes.item(i).getChildNodes();
			Color drawColor = null;
			int drawMethod = -1;
			int drawSize = -1;
			String functionString = "";
			double jumpSize = -1;
			for (int j = 0; j < functionData.getLength(); j++) {
				// loop through nodes for each function
				Node curNode = functionData.item(j);
				if (curNode.getNodeName().equals("drawcolor")) {
					String colorStr = curNode.getTextContent();
					drawColor = Color.decode(colorStr);
				} else if (curNode.getNodeName().equals("drawmethod")) {
					int nodeVal = Integer.parseInt(curNode.getTextContent());
					drawMethod = nodeVal;
				} else if (curNode.getNodeName().equals("drawsize")) {
					int nodeVal = Integer.parseInt(curNode.getTextContent());
					drawSize = nodeVal;
				} else if (curNode.getNodeName().equals("functionstring")) {
					functionString = curNode.getTextContent();
				} else if (curNode.getNodeName().equals("jumpsize")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					jumpSize = nodeVal;
				}
			}
			// create function object from loaded values
			CartesianFunction loadFunction = new CartesianFunction(
					functionString);
			loadFunction.setDrawColor(drawColor);
			loadFunction.setDrawMethod(drawMethod);
			loadFunction.setDrawSize(drawSize);
			loadFunction.setJumpSize(jumpSize);
			// add function to page
			newPage.addFunction(loadFunction);
		}
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

	private static void loadPoints(PlotPage newPage) {
		// load points
		// get point nodes
		NodeList pointNodes = doc.getElementsByTagName("point");
		for (int i = 0; i < pointNodes.getLength(); i++) {
			// loop through nodes creating new point for each
			NodeList pointData = pointNodes.item(i).getChildNodes();
			CartesianPoint loadPoint = new CartesianPoint();
			double xvalue = 0;
			double yvalue = 0;
			for (int j = 0; j < pointData.getLength(); j++) {
				// loop through nodes for each point
				Node curNode = pointData.item(j);
				if (curNode.getNodeName().equals("drawcolor")) {
					String colorStr = curNode.getTextContent();
					loadPoint.setDrawColor(Color.decode(colorStr));
				} else if (curNode.getNodeName().equals("xvalue")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					xvalue = nodeVal;
				} else if (curNode.getNodeName().equals("yvalue")) {
					double nodeVal = Double.parseDouble(curNode
							.getTextContent());
					yvalue = nodeVal;
				}
			}
			loadPoint.setPoint(xvalue, yvalue);
			// add point to page
			newPage.addPoint(loadPoint);
		}
	}

	private static ArrayList<double[]> parseDataRow(Node curNode) {
		// create arraylist to hold data
		ArrayList<double[]> plotData = new ArrayList<double[]>();
		NodeList rowNodes = curNode.getChildNodes();
		for (int k = 0; k < rowNodes.getLength(); k++) {
			// loop through row nodes
			Node curDataNode = rowNodes.item(k);
			if (curDataNode.getNodeName().equals("row")) {
				NodeList xyNodes = curDataNode.getChildNodes();
				double xValue = 0;
				double yValue = 0;
				for (int l = 0; l < xyNodes.getLength(); l++) {
					// loop through x & y values for each row
					Node curXYNode = xyNodes.item(l);
					if (curXYNode.getNodeName().equals("x")) {
						xValue = Double.parseDouble(curXYNode.getTextContent());
					} else if (curXYNode.getNodeName().equals("y")) {
						yValue = Double.parseDouble(curXYNode.getTextContent());
					}
				}
				// create array of doubles to store x & y value and add to
				// array list
				double[] rawData = { xValue, yValue };
				plotData.add(rawData);
			}
		}
		return plotData;
	}
}
