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

package gui.cartesian;

import gui.ColorSelector;
import handlers.StrokeTypeHandler;
import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import main.OpenPlotTool;
import plot.StrokeType;
import plot.cartesian.CartesianAxis;
import plot.cartesian.CartesianSettings;

@SuppressWarnings("serial")
public class CartesianSettingsDialog extends JDialog implements ActionListener {
	public static int OK_PRESSED = 1;
	private JButton applyButton, cancelButton;
	private JComboBox axisDrawSelector, gridDrawSelector, subDrawGridSelector;
	private ColorSelector backgroundColorSelector, textColorSelector,
			axisColorSelector, gridColorSelector, subColorGridSelector;
	private int pressed = 0;
	private JTextField xMaxField, xMinField, yMaxField, yMinField,
			xSplitSizeField, xNumSplitSizeField, ySplitSizeField,
			yNumSplitSizeField;

	public CartesianSettingsDialog(CartesianAxis axis) {
		super(OpenPlotTool.getMainFrame(), "Axis Settings", true);
		// get current settings
		CartesianSettings settings = (CartesianSettings) axis.getPlotSettings();
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		// create settings tab pane
		JTabbedPane settingsPane = new JTabbedPane();
		mainPanel.add(settingsPane, BorderLayout.CENTER);

		// create grid settings panel
		JPanel gridSettingsPanel = new JPanel(new GridLayout(1, 2));
		settingsPane.addTab("Grid Settings", gridSettingsPanel);
		// create range panel
		JPanel rangePanel = new JPanel();
		rangePanel.setBorder(BorderFactory.createTitledBorder("Axis range:"));
		gridSettingsPanel.add(rangePanel);
		// create layout
		double[][] rangeSize = { { 0.35, 0.65 }, { 25, 25, 25, 25 } };
		rangePanel.setLayout(new TableLayout(rangeSize));
		// create xmax field
		rangePanel.add(new JLabel("X Max: "), "0, 0");
		xMaxField = new JTextField(4);
		xMaxField.setText("" + settings.getxMax());
		rangePanel.add(xMaxField, "1, 0");
		// create xmax field
		rangePanel.add(new JLabel("X Min: "), "0, 1");
		xMinField = new JTextField(4);
		xMinField.setText("" + settings.getxMin());
		rangePanel.add(xMinField, "1, 1");
		// create xmax field
		rangePanel.add(new JLabel("Y Max: "), "0, 2");
		yMaxField = new JTextField(4);
		yMaxField.setText("" + settings.getyMax());
		rangePanel.add(yMaxField, "1, 2");
		// create xmax field
		rangePanel.add(new JLabel("Y Min: "), "0, 3");
		yMinField = new JTextField(4);
		yMinField.setText("" + settings.getyMin());
		rangePanel.add(yMinField, "1, 3");
		// create range panel
		JPanel spacingPanel = new JPanel();
		spacingPanel.setBorder(BorderFactory.createTitledBorder("Spacing:"));
		gridSettingsPanel.add(spacingPanel);
		// create layout
		double[][] spacingSize = { { 0.35, 0.65 }, { 25, 25, 25, 25 } };
		spacingPanel.setLayout(new TableLayout(spacingSize));
		// create x split size field
		spacingPanel.add(new JLabel("X Grid: "), "0, 0");
		xSplitSizeField = new JTextField(4);
		xSplitSizeField.setText("" + settings.getxSplitSize());
		spacingPanel.add(xSplitSizeField, "1, 0");
		// create x num split size field
		spacingPanel.add(new JLabel("X Number: "), "0, 1");
		xNumSplitSizeField = new JTextField(4);
		xNumSplitSizeField.setText("" + settings.getNumXSplitSize());
		spacingPanel.add(xNumSplitSizeField, "1, 1");
		// create y split size field
		spacingPanel.add(new JLabel("Y Grid: "), "0, 2");
		ySplitSizeField = new JTextField(4);
		ySplitSizeField.setText("" + settings.getySplitSize());
		spacingPanel.add(ySplitSizeField, "1, 2");
		// create num split size field
		spacingPanel.add(new JLabel("Y Number: "), "0, 3");
		yNumSplitSizeField = new JTextField(4);
		yNumSplitSizeField.setText("" + settings.getNumYSplitSize());
		spacingPanel.add(yNumSplitSizeField, "1, 3");

		// create color settings panel
		JPanel drawSettingsPanel = new JPanel(new GridLayout(1, 2));
		settingsPane.addTab("Color Settings", drawSettingsPanel);
		// create color panel
		JPanel colorPanel = new JPanel();
		colorPanel.setBorder(BorderFactory
				.createTitledBorder("Color Settings:"));
		drawSettingsPanel.add(colorPanel);
		// create layout
		double[][] colorSize = { { 0.35, 0.65 }, { 25, 25, 25, 25, 25 } };
		colorPanel.setLayout(new TableLayout(colorSize));
		// create background color field
		colorPanel.add(new JLabel("Background: "), "0, 0");
		backgroundColorSelector = new ColorSelector(settings
				.getBackgroundColor());
		colorPanel.add(backgroundColorSelector, "1, 0");
		// create text color field
		colorPanel.add(new JLabel("Text: "), "0, 1");
		textColorSelector = new ColorSelector(settings.getTextColor());
		colorPanel.add(textColorSelector, "1, 1");
		// create axis color field
		colorPanel.add(new JLabel("Axis: "), "0, 2");
		axisColorSelector = new ColorSelector(settings.getAxisColor());
		colorPanel.add(axisColorSelector, "1, 2");
		// create grid color field
		colorPanel.add(new JLabel("Grid: "), "0, 3");
		gridColorSelector = new ColorSelector(settings.getMainGridColor());
		colorPanel.add(gridColorSelector, "1, 3");
		// create sub grid color field
		colorPanel.add(new JLabel("Sub Grid: "), "0, 4");
		subColorGridSelector = new ColorSelector(settings.getSubGridColor());
		colorPanel.add(subColorGridSelector, "1, 4");
		// create draw method panel
		JPanel drawMethodPanel = new JPanel();
		drawMethodPanel.setBorder(BorderFactory
				.createTitledBorder("Draw Settings:"));
		drawSettingsPanel.add(drawMethodPanel);
		// create layout
		double[][] drawSize = { { 0.35, 0.65 }, { 25, 25, 25 } };
		drawMethodPanel.setLayout(new TableLayout(drawSize));
		// create axis color field
		drawMethodPanel.add(new JLabel("Axis: "), "0, 0");
		axisDrawSelector = new JComboBox(StrokeTypeHandler.getStrokeTypeArray());
		drawMethodPanel.add(axisDrawSelector, "1, 0");
		axisDrawSelector.setSelectedItem(settings.getAxisDrawType());
		// create grid color field
		drawMethodPanel.add(new JLabel("Grid: "), "0, 1");
		gridDrawSelector = new JComboBox(StrokeTypeHandler.getStrokeTypeArray());
		drawMethodPanel.add(gridDrawSelector, "1, 1");
		gridDrawSelector.setSelectedItem(settings.getMainGridDrawType());
		// create sub grid color field
		drawMethodPanel.add(new JLabel("Sub Grid: "), "0, 2");
		subDrawGridSelector = new JComboBox(StrokeTypeHandler.getStrokeTypeArray());
		drawMethodPanel.add(subDrawGridSelector, "1, 2");
		subDrawGridSelector.setSelectedItem(settings.getSubGridType());

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

	public CartesianSettings getNewSettings() throws Exception {
		// build and return new cartesian axis settings
		double newXMax = Double.parseDouble(xMaxField.getText());
		double newXMin = Double.parseDouble(xMinField.getText());
		double newYMax = Double.parseDouble(yMaxField.getText());
		double newYMin = Double.parseDouble(yMinField.getText());
		double newXSplitSize = Double.parseDouble(xSplitSizeField.getText());
		double newNumXSplitSize = Double.parseDouble(xNumSplitSizeField
				.getText());
		double newYSplitSize = Double.parseDouble(ySplitSizeField.getText());
		double newNumYSplitSize = Double.parseDouble(yNumSplitSizeField
				.getText());
		Color backgroundColor = backgroundColorSelector.getColor();
		Color textColor = textColorSelector.getColor();
		Color axisColor = axisColorSelector.getColor();
		Color gridColor = gridColorSelector.getColor();
		Color subGridColor = subColorGridSelector.getColor();
		String axisDrawType = ((StrokeType) axisDrawSelector.getSelectedItem()).getName();
		String mainGridDrawType = ((StrokeType) gridDrawSelector.getSelectedItem()).getName();
		String subDrawType = ((StrokeType) subDrawGridSelector.getSelectedItem()).getName();
		CartesianSettings settings = new CartesianSettings();
		settings.setxMax(newXMax);
		settings.setxMin(newXMin);
		settings.setyMax(newYMax);
		settings.setyMin(newYMin);
		settings.setxSplitSize(newXSplitSize);
		settings.setNumXSplitSize(newNumXSplitSize);
		settings.setySplitSize(newYSplitSize);
		settings.setNumYSplitSize(newNumYSplitSize);
		settings.setBackgroundColor(backgroundColor);
		settings.setTextColor(textColor);
		settings.setAxisColor(axisColor);
		settings.setMainGridColor(gridColor);
		settings.setSubGridColor(subGridColor);
		settings.setAxisDrawType(axisDrawType);
		settings.setMainGridDrawType(mainGridDrawType);
		settings.setSubGridType(subDrawType);

		return settings;
	}

	public int getPressed() {
		return pressed;
	}
}
