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
import plot.piechart.PieChartDataSet;
import plot.piechart.PieChartItem;
import plot.piechart.PieChartSettings;

public class PieChartXMLBuilder {

	private static void buildXMLAxisSettings(PlotPage page,
			StringBuffer xmlStringBuffer) {
		PieChartSettings settings = (PieChartSettings) page.getAxis()
				.getPlotSettings();
		xmlStringBuffer.append("<axis>\n");
		xmlStringBuffer.append("<bgcolor>"
				+ settings.getBackgroundColor().getRGB() + "</bgcolor>\n");
		xmlStringBuffer.append("<textcolor>" + settings.getTextColor().getRGB()
				+ "</textcolor>\n");
		xmlStringBuffer.append("<outlinedrawcolor>"
				+ settings.getOutlineDrawColor().getRGB()
				+ "</outlinedrawcolor>");
		xmlStringBuffer.append("</axis>\n");
	}

	private static void buildXMLDataSets(PlotPage page,
			StringBuffer xmlStringBuffer) {
		xmlStringBuffer.append("<datasets>\n");
		if (page.getPlotData().size() > 0) {
			xmlStringBuffer.append("<dataset>\n");
			PieChartDataSet dataSet = (PieChartDataSet) page.getPlotData().get(
					0);
			xmlStringBuffer.append("<title>" + dataSet.getDataTitle()
					+ "</title>\n");
			xmlStringBuffer.append("<dataitems>\n");
			for (int i = 0; i < dataSet.getDataItems().size(); i++) {
				xmlStringBuffer.append("<dataitem>\n");
				PieChartItem dataItem = dataSet.getDataItems().get(i);
				xmlStringBuffer.append("<color>" + dataItem.getColor().getRGB()
						+ "</color>\n");
				xmlStringBuffer.append("<name>" + dataItem.getName()
						+ "</name>\n");
				xmlStringBuffer.append("<value>" + dataItem.getValue()
						+ "</value>\n");
				xmlStringBuffer.append("</dataitem>\n");
			}
			xmlStringBuffer.append("</dataitems>\n");
			xmlStringBuffer.append("</dataset>\n");
		}
		xmlStringBuffer.append("</datasets>\n");
	}

	private static void buildXMLSettings(PlotPage page,
			StringBuffer xmlStringBuffer) {
		// create string of settings in required XML format
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
		xmlStringBuffer.append("<type>piechartplot</type>\n");
		xmlStringBuffer.append("<version>0.2</version>\n");
		buildXMLSettings(page, xmlStringBuffer);
		buildXMLAxisSettings(page, xmlStringBuffer);
		buildXMLDataSets(page, xmlStringBuffer);
		xmlStringBuffer.append("</page>\n");
		return xmlStringBuffer;
	}
}
