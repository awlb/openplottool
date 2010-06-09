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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import main.OpenPlotTool;

@SuppressWarnings("serial")
public class ColorSelector extends JPanel implements ActionListener {
	private static ColorSelectorDialog dialog = new ColorSelectorDialog(
			OpenPlotTool.getMainFrame());
	private Color color;
	private JPanel colorPanel;
	private JButton selectBtn;

	public ColorSelector(Color inColor) {
		// create gui control
		super();
		color = inColor;
		this.setLayout(new GridLayout(1, 1));
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		this.add(mainPanel);
		colorPanel = new JPanel();
		colorPanel.setBackground(color);
		mainPanel.add(colorPanel, BorderLayout.CENTER);
		selectBtn = new JButton("+");
		selectBtn.addActionListener(this);
		mainPanel.add(selectBtn, BorderLayout.LINE_END);

		// call performSelectColor() when panel is clicked
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				performSelectColor();
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectBtn) {
			performSelectColor();
		}
	}

	public Color getColor() {
		return color;
	}

	private void performSelectColor() {
		// show color chooser dialog and get new color
		dialog.showDialog(color);
		Color newColor = dialog.getSelectedColor();
		if (newColor != null) {
			// update color
			color = newColor;
			colorPanel.setBackground(color);
		}
	}

	public void setColor(Color color) {
		this.color = color;
		colorPanel.setBackground(color);
	}
}
