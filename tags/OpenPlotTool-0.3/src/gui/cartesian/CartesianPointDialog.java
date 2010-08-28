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
import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.OpenPlotTool;
import plot.cartesian.CartesianPoint;

@SuppressWarnings("serial")
public class CartesianPointDialog extends JDialog implements ActionListener {
	public static int ADD_PRESSED = 1;
	private JButton addButton, cancelButton;
	private ColorSelector colorSelector;
	private int pressed = 0;
	private JTextField xField, yField;

	public CartesianPointDialog(CartesianPoint currentPoint) {
		super(OpenPlotTool.getMainFrame(), "Add Point", true);

		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// create point data panel
		JPanel pointDataPanel = new JPanel();
		pointDataPanel.setBorder(BorderFactory
				.createTitledBorder("Point Data: "));
		mainPanel.add(pointDataPanel, BorderLayout.PAGE_START);
		// create layout
		double[][] pointSize = { { 0.4, 0.6 }, { 25, 25 } };
		pointDataPanel.setLayout(new TableLayout(pointSize));
		// create x field
		pointDataPanel.add(new JLabel("X: "), "0, 0");
		xField = new JTextField(8);
		pointDataPanel.add(xField, "1, 0");
		// create y field
		pointDataPanel.add(new JLabel("Y: "), "0, 1");
		yField = new JTextField(8);
		pointDataPanel.add(yField, "1, 1");

		// create point settings panel
		JPanel pointSettingsPanel = new JPanel();
		pointSettingsPanel.setBorder(BorderFactory
				.createTitledBorder("Point Settings: "));
		mainPanel.add(pointSettingsPanel, BorderLayout.CENTER);
		// create layout
		double[][] pointSettingsSize = { { 0.25, 0.75 }, { 25 } };
		pointSettingsPanel.setLayout(new TableLayout(pointSettingsSize));

		// create color field
		pointSettingsPanel.add(new JLabel("Color: "), "0, 0");
		colorSelector = new ColorSelector(Color.RED);
		pointSettingsPanel.add(colorSelector, "1, 0");

		// create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		buttonPanel.add(addButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

		// load values from current point
		if (currentPoint != null) {
			xField.setText("" + currentPoint.getXValue());
			yField.setText("" + currentPoint.getYValue());
			colorSelector.setColor(currentPoint.getDrawColor());
		}
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
		if (e.getSource() == addButton) {
			pressed = ADD_PRESSED;
		}
		setVisible(false);
	}

	public CartesianPoint getPoint() throws NumberFormatException {
		// build and return edited point
		double x = Double.parseDouble(xField.getText());
		double y = Double.parseDouble(yField.getText());
		CartesianPoint point = new CartesianPoint();
		point.setPoint(x, y);
		point.setDrawColor(colorSelector.getColor());
		return point;
	}

	public int getPressed() {
		return pressed;
	}
}
