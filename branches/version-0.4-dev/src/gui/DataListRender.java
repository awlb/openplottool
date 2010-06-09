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

package gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import plot.PlotData;

/*
 * Renderer used for data in the console tab lists
 */

@SuppressWarnings("serial")
public class DataListRender extends JLabel implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setOpaque(true);
		PlotData entry = (PlotData) value;
		setText(entry.toString());
		setForeground(entry.getDrawColor());
		if (isSelected) {
			setBackground(list.getSelectionBackground());
		} else {
			setBackground(list.getBackground());
		}
		return this;
	}
}
