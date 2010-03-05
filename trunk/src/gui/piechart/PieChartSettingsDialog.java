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

import gui.ColorSelector;
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
import plot.piechart.PieChartAxis;
import plot.piechart.PieChartSettings;

@SuppressWarnings("serial")
public class PieChartSettingsDialog extends JDialog implements ActionListener {
	public static int OK_PRESSED = 1;
	private JButton applyButton, cancelButton;
	private ColorSelector backgroundColorSelector, textColorSelector,
			outlineColorSelector;
	private int pressed = 0;
	private PieChartSettings settings;

	public PieChartSettingsDialog(PieChartAxis axis) {
		super(OpenPlotTool.getMainFrame(), "Axis Settings", true);
		// get current settings
		settings = (PieChartSettings) axis.getPlotSettings();
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// create settings panel
		JPanel settingsPanel = new JPanel();
		settingsPanel
				.setBorder(BorderFactory.createTitledBorder("Axis range:"));
		mainPanel.add(settingsPanel, BorderLayout.CENTER);
		// create layout
		double[][] settingsSize = { { 0.35, 0.65 }, { 25, 25, 25 } };
		settingsPanel.setLayout(new TableLayout(settingsSize));
		// create background color field
		settingsPanel.add(new JLabel("Background Color: "), "0, 0");
		backgroundColorSelector = new ColorSelector(settings
				.getBackgroundColor());
		settingsPanel.add(backgroundColorSelector, "1, 0");
		// create text color field
		settingsPanel.add(new JLabel("Text Color: "), "0, 1");
		textColorSelector = new ColorSelector(settings.getTextColor());
		settingsPanel.add(textColorSelector, "1, 1");
		// create outline color field
		settingsPanel.add(new JLabel("Outline Color: "), "0, 2");
		outlineColorSelector = new ColorSelector(settings.getOutlineDrawColor());
		settingsPanel.add(outlineColorSelector, "1, 2");

		// create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		applyButton = new JButton("Apply");
		applyButton.addActionListener(this);
		buttonPanel.add(applyButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

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
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == applyButton) {
			pressed = OK_PRESSED;
		}
		setVisible(false);
	}

	public PieChartSettings getNewSettings() {
		// build and return new pie chart axis settings
		settings.setBackgroundColor(backgroundColorSelector.getColor());
		settings.setTextColor(textColorSelector.getColor());
		settings.setOutlineDrawColor(outlineColorSelector.getColor());

		return settings;
	}

	public int getPressed() {
		return pressed;
	}
}
