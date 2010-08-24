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

package plot.piechart;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import plot.Axis;
import plot.PlotDataSet;

public class PieChartDataSet extends PlotDataSet {
	// plot data
	private ArrayList<PieChartItem> dataItems = new ArrayList<PieChartItem>();

	@Override
	public void draw(Graphics g, Axis axis) {
		Graphics2D gc = (Graphics2D) g;
		// get pie chart axis
		PieChartAxis pieAxis = (PieChartAxis) axis;
		PieChartSettings settings = (PieChartSettings) pieAxis
				.getPlotSettings();
		double total = 0;
		// loop through items to calculate total
		for (PieChartItem dataItem : dataItems) {
			total += dataItem.getValue();
		}
		// loop through items to find max string length
		int max = 0;
		for (PieChartItem dataItem : dataItems) {
			int strLength = gc.getFontMetrics().stringWidth(dataItem.getName());
			if (strLength > max) {
				max = strLength;
			}
		}
		// get font height
		int fontHeight = gc.getFontMetrics().getHeight();

		if (settings.isDrawKey()) {
			// draw key background
			gc.setColor(settings.getKeyBackgroundColor());
			gc.fillRect(pieAxis.getxIndent() + 5, pieAxis.getyIndent() + 5, max
					+ fontHeight + 10, dataItems.size() * fontHeight + 5);
			// draw key border
			gc.setColor(settings.getOutlineDrawColor());
			gc.drawRect(pieAxis.getxIndent() + 5, pieAxis.getyIndent() + 5, max
					+ fontHeight + 10, dataItems.size() * fontHeight + 5);
		}

		// loop through data items and draw pie chart sections
		int startAngle = 0;
		int count = 1;
		for (PieChartItem dataItem : dataItems) {
			gc.setColor(dataItem.getColor());
			// calculate draw angle
			int drawAngle = (int) Math
					.round((dataItem.getValue() / total) * 360);
			// draw arc
			gc.fillArc(pieAxis.getxPiePos(), pieAxis.getyPiePos(), pieAxis
					.getPieSize(), pieAxis.getPieSize(), startAngle + 90,
					-drawAngle);
			if (settings.isDrawKey()) {
				// draw key content
				gc.setColor(settings.getTextColor());
				int xItemTextPos = pieAxis.getxIndent() + 8;
				int yItemTextPos = pieAxis.getyIndent() + 8
						+ gc.getFontMetrics().getHeight() * count;
				gc.drawString(dataItem.getName(), xItemTextPos,
						yItemTextPos - 2);
				gc.setColor(dataItem.getColor());
				gc.fillRect(xItemTextPos + max + 5, yItemTextPos - fontHeight,
						fontHeight, fontHeight);
			}
			// increase start angle
			startAngle += -drawAngle;
			// increase count
			count++;
		}

		// draw pie outline
		gc.setColor(settings.getOutlineDrawColor());
		gc.setStroke(new BasicStroke(2));
		gc.drawOval(pieAxis.getxPiePos(), pieAxis.getyPiePos(), pieAxis
				.getPieSize(), pieAxis.getPieSize());
	}

	public ArrayList<PieChartItem> getDataItems() {
		return dataItems;
	}

	public void setItems(ArrayList<PieChartItem> dataItems) {
		this.dataItems = dataItems;
	}
}
