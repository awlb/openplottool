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

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.OpenPlotTool;
import plot.PlotPage;

@SuppressWarnings("serial")
public class PageInformationDialog extends JDialog implements ActionListener {
	private JButton closeButton;

	public PageInformationDialog(PlotPage page) {
		super(OpenPlotTool.getMainFrame(), "Plot Page Information", true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// create general information panel
		JPanel generalPanel = new JPanel();
		generalPanel.setBorder(BorderFactory
				.createTitledBorder("General Information: "));
		double[][] generalLayoutSize = { { 0.25, 0.75 }, { 25, 25 } };
		generalPanel.setLayout(new TableLayout(generalLayoutSize));
		mainPanel.add(generalPanel, BorderLayout.PAGE_START);
		generalPanel.add(new JLabel("Page Type: "), "0, 0");
		generalPanel.add(new JLabel(page.getType()), "1, 0");
		generalPanel.add(new JLabel("Save File: "), "0, 1");
		if (page.getPageFile() != null) {
			generalPanel.add(new JLabel(page.getPageFile().getAbsolutePath()),
					"1, 1");
		} else {
			generalPanel.add(new JLabel("null"), "1, 1");
		}

		// create data information panel
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(BorderFactory
				.createTitledBorder("Data Information: "));
		double[][] dataLayoutSize = { { 0.25, 0.75 }, { 25, 25, 25 } };
		dataPanel.setLayout(new TableLayout(dataLayoutSize));
		mainPanel.add(dataPanel, BorderLayout.CENTER);
		dataPanel.add(new JLabel("Data Sets: "), "0, 0");
		dataPanel.add(new JLabel("" + page.getPlotData().size()), "1, 0");
		dataPanel.add(new JLabel("Points: "), "0, 1");
		dataPanel.add(new JLabel("" + page.getPlotPoints().size()), "1, 1");
		dataPanel.add(new JLabel("Data Sets: "), "0, 2");
		dataPanel.add(new JLabel("" + page.getPlotFunctions().size()), "1, 2");

		// create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);

		// make dialog none Resizable
		setResizable(false);
		// pack dialog
		pack();
		// set dialog location
		Point winLocation = new Point(
				OpenPlotTool.getMainFrame().getLocation().x + 20, OpenPlotTool
						.getMainFrame().getLocation().y + 20);
		setLocation(winLocation);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		setVisible(false);
	}
}
