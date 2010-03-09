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

import plot.PlotPage;
import plot.cartesian.CartesianDataSet;
import plot.cartesian.CartesianFunction;
import plot.cartesian.CartesianPoint;
import plot.cartesian.CartesianSettings;

public class CartesianXMLBuilder {
	private static void buildXMLAxisSettings(PlotPage page,
			StringBuffer xmlStringBuffer) {
		// create string of axis settings in required XML format
		CartesianSettings settings = (CartesianSettings) page.getAxis()
				.getPlotSettings();
		xmlStringBuffer.append("<axis>\n");
		xmlStringBuffer.append("<bgcolor>"
				+ settings.getBackgroundColor().getRGB() + "</bgcolor>\n");
		xmlStringBuffer.append("<textcolor>" + settings.getTextColor().getRGB()
				+ "</textcolor>\n");
		xmlStringBuffer.append("<axiscolor>" + settings.getAxisColor().getRGB()
				+ "</axiscolor>\n");
		xmlStringBuffer.append("<axisdrawtype>" + settings.getAxisDrawType()
				+ "</axisdrawtype>\n");
		xmlStringBuffer.append("<maingridcolor>"
				+ settings.getMainGridColor().getRGB() + "</maingridcolor>\n");
		xmlStringBuffer.append("<maingriddrawtype>"
				+ settings.getMainGridDrawType() + "</maingriddrawtype>\n");
		xmlStringBuffer.append("<subgridcolor>"
				+ settings.getSubGridColor().getRGB() + "</subgridcolor>\n");
		xmlStringBuffer.append("<subgriddrawtype>" + settings.getSubGridType()
				+ "</subgriddrawtype>\n");
		xmlStringBuffer.append("<numxsplitsize>" + settings.getNumXSplitSize()
				+ "</numxsplitsize>\n");
		xmlStringBuffer.append("<numysplitsize>" + settings.getNumYSplitSize()
				+ "</numysplitsize>\n");
		xmlStringBuffer.append("<xmax>" + settings.getxMax() + "</xmax>\n");
		xmlStringBuffer.append("<xmin>" + settings.getxMin() + "</xmin>\n");
		xmlStringBuffer.append("<xsplitsize>" + settings.getxSplitSize()
				+ "</xsplitsize>\n");
		xmlStringBuffer.append("<ymax>" + settings.getyMax() + "</ymax>\n");
		xmlStringBuffer.append("<ymin>" + settings.getyMin() + "</ymin>\n");
		xmlStringBuffer.append("<ysplitsize>" + settings.getySplitSize()
				+ "</ysplitsize>\n");
		xmlStringBuffer.append("</axis>\n");
	}

	private static void buildXMLDataSets(PlotPage page,
			StringBuffer xmlStringBuffer) {
		// create string of data sets in required XML format
		xmlStringBuffer.append("<datasets>\n");
		for (int i = 0; i < page.getPlotData().size(); i++) {
			CartesianDataSet dataSet = (CartesianDataSet) page.getPlotData()
					.get(i);
			xmlStringBuffer.append("<dataset>\n");
			xmlStringBuffer.append("<title>" + dataSet.getDataTitle()
					+ "</title>\n");
			xmlStringBuffer.append("<drawcolor>"
					+ dataSet.getDrawColor().getRGB() + "</drawcolor>\n");
			xmlStringBuffer.append("<linkpoints>"+dataSet.isLinkPoints()+"</linkpoints>\n");
			xmlStringBuffer.append("<data>\n");
			double[][] rawData = dataSet.getData();
			for (int j = 0; j < rawData.length; j++) {
				xmlStringBuffer.append("<row>\n");
				xmlStringBuffer.append("<x>" + rawData[j][0] + "</x>\n");
				xmlStringBuffer.append("<y>" + rawData[j][1] + "</y>\n");
				xmlStringBuffer.append("</row>\n");
			}
			xmlStringBuffer.append("</data>\n");
			xmlStringBuffer.append("</dataset>\n");
		}
		xmlStringBuffer.append("</datasets>\n");
	}

	private static void buildXMLFunction(PlotPage page,
			StringBuffer xmlStringBuffer) {
		// create string of functions in required XML format
		xmlStringBuffer.append("<functions>\n");
		for (int i = 0; i < page.getPlotFunctions().size(); i++) {
			CartesianFunction function = (CartesianFunction) page
					.getPlotFunctions().get(i);
			xmlStringBuffer.append("<function>\n");
			xmlStringBuffer.append("<drawcolor>"
					+ function.getDrawColor().getRGB() + "</drawcolor>\n");
			xmlStringBuffer.append("<drawmethod>" + function.getDrawMethod()
					+ "</drawmethod>\n");
			xmlStringBuffer.append("<drawsize>" + function.getDrawSize()
					+ "</drawsize>\n");
			xmlStringBuffer.append("<functionstring>"
					+ function.getFunctionString() + "</functionstring>\n");
			xmlStringBuffer.append("<jumpsize>" + function.getJumpSize()
					+ "</jumpsize>\n");
			xmlStringBuffer.append("</function>\n");
		}
		xmlStringBuffer.append("</functions>\n");
	}

	private static void buildXMLPoints(PlotPage page,
			StringBuffer xmlStringBuffer) {
		// create string of points in required XML format
		xmlStringBuffer.append("<points>\n");
		for (int i = 0; i < page.getPlotPoints().size(); i++) {
			CartesianPoint point = (CartesianPoint) page.getPlotPoints().get(i);
			xmlStringBuffer.append("<point>\n");
			xmlStringBuffer.append("<drawcolor>"
					+ point.getDrawColor().getRGB() + "</drawcolor>\n");
			xmlStringBuffer.append("<xvalue>" + point.getXValue()
					+ "</xvalue>\n");
			xmlStringBuffer.append("<yvalue>" + point.getYValue()
					+ "</yvalue>\n");
			xmlStringBuffer.append("</point>\n");
		}
		xmlStringBuffer.append("</points>\n");
	}

	private static void buildXMLSettings(PlotPage page,
			StringBuffer xmlStringBuffer) {
		// create string of points in required XML format
		xmlStringBuffer.append("<settings>\n");
		xmlStringBuffer.append("<bgcolor>"
				+ page.getSettings().getBackgroundColor().getRGB()
				+ "</bgcolor>\n");
		xmlStringBuffer.append("<bordercolor>"
				+ page.getSettings().getBorderColor().getRGB()
				+ "</bordercolor>\n");
		xmlStringBuffer.append("<drawborder>"
				+ page.getSettings().getDrawBorder() + "</drawborder>\n");
		xmlStringBuffer.append("<borderdrawtype>"
				+ page.getSettings().getBorderDrawType()
				+ "</borderdrawtype>\n");
		xmlStringBuffer.append("<title>" + page.getSettings().getTitle()
				+ "</title>\n");
		xmlStringBuffer.append("<titlecolor>"
				+ page.getSettings().getTitleColor().getRGB()
				+ "</titlecolor>\n");
		xmlStringBuffer.append("</settings>\n");
	}

	public static StringBuffer buildXML(PlotPage page) {
		// create string buffer for xml
		StringBuffer xmlStringBuffer = new StringBuffer();
		// write xml strings to file
		xmlStringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xmlStringBuffer.append("<page>\n");
		xmlStringBuffer.append("<type>cartesianxyplot</type>\n");
		xmlStringBuffer.append("<version>0.2</version>\n");
		buildXMLSettings(page, xmlStringBuffer);
		buildXMLAxisSettings(page, xmlStringBuffer);
		buildXMLDataSets(page, xmlStringBuffer);
		buildXMLPoints(page, xmlStringBuffer);
		buildXMLFunction(page, xmlStringBuffer);
		xmlStringBuffer.append("</page>\n");
		return xmlStringBuffer;
	}
}
