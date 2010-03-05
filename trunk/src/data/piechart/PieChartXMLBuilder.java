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

package data.piechart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import plot.PlotPage;
import plot.piechart.PieChartDataSet;
import plot.piechart.PieChartItem;
import plot.piechart.PieChartSettings;

public class PieChartXMLBuilder {

	private static String buildXMLAxisSettings(PlotPage page) {
		PieChartSettings settings = (PieChartSettings) page.getAxis()
				.getPlotSettings();
		String s = "<axis>\n";
		s += "<bgcolor>" + settings.getBackgroundColor().getRGB()
				+ "</bgcolor>\n";
		s += "<textcolor>" + settings.getTextColor().getRGB()
				+ "</textcolor>\n";
		s += "<outlinedrawcolor>" + settings.getOutlineDrawColor().getRGB()
				+ "</outlinedrawcolor>";
		s += "</axis>\n";
		return s;
	}

	private static String buildXMLDataSets(PlotPage page) {
		String s = "<datasets>\n";
		if (page.getPlotData().size() > 0) {
			s += "<dataset>\n";
			PieChartDataSet dataSet = (PieChartDataSet) page.getPlotData().get(
					0);
			s += "<title>" + dataSet.getDataTitle() + "</title>\n";
			s += "<dataitems>\n";
			for (int i = 0; i < dataSet.getDataItems().size(); i++) {
				s += "<dataitem>\n";
				PieChartItem dataItem = dataSet.getDataItems().get(i);
				s += "<color>" + dataItem.getColor().getRGB() + "</color>\n";
				s += "<name>" + dataItem.getName() + "</name>\n";
				s += "<value>" + dataItem.getValue() + "</value>\n";
				s += "</dataitem>\n";
			}
			s += "</dataitems>\n";
			s += "</dataset>\n";
		}
		s += "</datasets>\n";
		return s;
	}

	private static String buildXMLSettings(PlotPage page) {
		// create string of settings in required XML format
		String s = "<settings>\n";
		s += "<bgcolor>" + page.getSettings().getBackgroundColor().getRGB()
				+ "</bgcolor>\n";
		s += "<bordercolor>" + page.getSettings().getBorderColor().getRGB()
				+ "</bordercolor>\n";
		s += "<drawborder>" + page.getSettings().getDrawBorder()
				+ "</drawborder>\n";
		s += "<borderdrawtype>" + page.getSettings().getBorderDrawType()
				+ "</borderdrawtype>\n";
		s += "<title>" + page.getSettings().getTitle() + "</title>\n";
		s += "<titlecolor>" + page.getSettings().getTitleColor().getRGB()
				+ "</titlecolor>\n";
		s += "</settings>\n";
		return s;
	}

	public static StringBuffer buildXML(PlotPage page) throws IOException {
		// create string buffer for xml
		StringBuffer xmlStringBuffer = new StringBuffer();
		// write xml strings to file
		xmlStringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xmlStringBuffer.append("<page>\n");
		xmlStringBuffer.append("<type>piechartplot</type>\n");
		xmlStringBuffer.append("<version>0.1</version>\n");
		xmlStringBuffer.append(buildXMLSettings(page));
		xmlStringBuffer.append(buildXMLAxisSettings(page));
		xmlStringBuffer.append(buildXMLDataSets(page));
		xmlStringBuffer.append("</page>\n");		
		return xmlStringBuffer;
	}
}
