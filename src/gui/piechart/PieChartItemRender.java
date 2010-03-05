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

package gui.piechart;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import plot.piechart.PieChartItem;

/**
 * Renderer used for pie chart items
 **/

@SuppressWarnings("serial")
public class PieChartItemRender extends JLabel implements ListCellRenderer {
	private static final Color HIGHLIGHT_COLOR = new Color(180, 180, 255);

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setOpaque(true);
		PieChartItem entry = (PieChartItem) value;
		setText(entry.toString());
		setForeground(entry.getColor());
		if (isSelected) {
			setBackground(HIGHLIGHT_COLOR);
		} else {
			setBackground(Color.WHITE);
		}
		return this;
	}

}
