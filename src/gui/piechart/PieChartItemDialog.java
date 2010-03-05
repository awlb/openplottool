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
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.OpenPlotTool;
import plot.piechart.PieChartItem;

@SuppressWarnings("serial")
public class PieChartItemDialog extends JDialog implements ActionListener {

	public static int ADD_PRESSED = 1;
	private ColorSelector colorSelector;
	private int pressed = 0;
	private JButton addButton, cancelButton;
	private JTextField nameField, valueField;

	public PieChartItemDialog(PieChartItem pieItem) {
		super(OpenPlotTool.getMainFrame(), "Add Data Item", true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// create data panel
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(BorderFactory.createTitledBorder("Item values: "));
		mainPanel.add(dataPanel, BorderLayout.CENTER);
		// create layout
		double[][] rangeSize = { { 0.25, 0.75 }, { 25, 25, 25 } };
		dataPanel.setLayout(new TableLayout(rangeSize));

		// create name field
		dataPanel.add(new JLabel("Name: "), "0, 0");
		nameField = new JTextField(8);
		dataPanel.add(nameField, "1, 0");
		// create value field
		dataPanel.add(new JLabel("Value: "), "0, 1");
		valueField = new JTextField(8);
		dataPanel.add(valueField, "1, 1");
		// create colour field
		dataPanel.add(new JLabel("Color: "), "0, 2");
		colorSelector = new ColorSelector(Color.RED);
		dataPanel.add(colorSelector, "1, 2");

		// create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		buttonPanel.add(addButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

		if (pieItem != null) {
			nameField.setText(pieItem.getName());
			valueField.setText("" + pieItem.getValue());
			colorSelector.setColor(pieItem.getColor());
			addButton.setText("Update");
			this.setTitle("Edit Data Item");
		}

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
		if (e.getSource() == addButton) {
			pressed = ADD_PRESSED;
		}
		setVisible(false);
	}

	public PieChartItem getPieChartItem() {
		// build and return new pie chart item
		PieChartItem newItem = new PieChartItem();
		newItem.setName(nameField.getText());
		double value = Double.parseDouble(valueField.getText());
		newItem.setValue(value);
		newItem.setColor(colorSelector.getColor());
		return newItem;
	}

	public int getPressed() {
		return pressed;
	}
}
