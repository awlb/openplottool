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

import exceptions.NullDataValueException;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import main.OpenPlotTool;
import net.smplmathparser.MathParserException;
import plot.PlotFunction;
import plot.cartesian.CartesianFunction;

@SuppressWarnings("serial")
public class CartesianFunctionDialog extends JDialog implements ActionListener {
	public static int ADD_PRESSED = 1;
	private ColorSelector colorSelector;
	private JSpinner drawSizeField;
	private JComboBox drawTypeList, detailList;
	private JTextField functionField;
	private int pressed = 0;
	private JButton addButton, cancelButton;

	public CartesianFunctionDialog(CartesianFunction currentFunction) {
		super(OpenPlotTool.getMainFrame(), "Add Function", true);

		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// create function data panel
		JPanel functionDataPanel = new JPanel();
		functionDataPanel.setBorder(BorderFactory
				.createTitledBorder("Function Data:"));
		mainPanel.add(functionDataPanel, BorderLayout.PAGE_START);
		// create layout
		double[][] functionDataLayoutSize = { { 0.25, 0.75 }, { 25 } };
		functionDataPanel.setLayout(new TableLayout(functionDataLayoutSize));
		// create function field
		functionDataPanel.add(new JLabel("Function: "), "0, 0");
		functionField = new JTextField(15);
		functionDataPanel.add(functionField, "1, 0");

		// create function settings panel
		JPanel functionSettingsPanel = new JPanel();
		functionSettingsPanel.setBorder(BorderFactory
				.createTitledBorder("Function Settings:"));
		mainPanel.add(functionSettingsPanel, BorderLayout.CENTER);
		// create layout
		double[][] functionSettingsLayoutSize = { { 0.25, 0.75 },
				{ 25, 25, 25, 25 } };
		functionSettingsPanel.setLayout(new TableLayout(
				functionSettingsLayoutSize));

		// create draw type field
		functionSettingsPanel.add(new JLabel("Draw Type: "), "0, 0");
		String[] drawTypes = { "Line", "Point" };
		drawTypeList = new JComboBox(drawTypes);
		drawTypeList.addActionListener(this);
		functionSettingsPanel.add(drawTypeList, "1, 0");
		// create draw size field
		functionSettingsPanel.add(new JLabel("Draw Size: "), "0, 1");
		SpinnerModel sizeModel = new SpinnerNumberModel(1, 1, 10, 1);
		drawSizeField = new JSpinner(sizeModel);
		functionSettingsPanel.add(drawSizeField, "1, 1");
		// create detail field
		functionSettingsPanel.add(new JLabel("Detail: "), "0, 2");
		String[] detailTypes = { "Low", "Medium", "High" };
		detailList = new JComboBox(detailTypes);
		functionSettingsPanel.add(detailList, "1, 2");
		// create color field
		functionSettingsPanel.add(new JLabel("Color: "), "0, 3");
		colorSelector = new ColorSelector(Color.RED);
		functionSettingsPanel.add(colorSelector, "1, 3");

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

		// load values from current function
		if (currentFunction != null) {
			functionField.setText(currentFunction.getFunctionString());
			if (currentFunction.getDrawMethod() == PlotFunction.LINE_DRAW) {
				drawTypeList.setSelectedIndex(0);
			} else {
				drawTypeList.setSelectedIndex(1);
			}
			drawSizeField.setValue(new Integer(currentFunction.getDrawSize()));
			if (currentFunction.getJumpSize() == 10) {
				detailList.setSelectedIndex(0);
			} else if (currentFunction.getJumpSize() == 100) {
				detailList.setSelectedIndex(1);
			} else {
				detailList.setSelectedIndex(2);
			}
			colorSelector.setColor(currentFunction.getDrawColor());
			// set add button text to update
			addButton.setText("Update");
			// set window title for update
			this.setTitle("Update Function");
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
			setVisible(false);
		} else if (e.getSource() == drawTypeList) {
			String drawType = (String) drawTypeList.getSelectedItem();
			if (drawType.equals("Line")) {
				detailList.setEnabled(true);
				drawSizeField.setEnabled(true);
			} else {
				detailList.setEnabled(false);
				drawSizeField.setEnabled(false);
			}
		} else if (e.getSource() == cancelButton) {
			setVisible(false);
		}
	}

	public int getPressed() {
		return pressed;
	}

	public CartesianFunction getFunction() throws NullDataValueException,
			MathParserException {
		// build and return function
		String functionText = functionField.getText();
		if (functionText.equals("")) {
			throw new NullDataValueException();
		} else {
			CartesianFunction editFunction = new CartesianFunction(functionText);
			editFunction.setDrawColor(colorSelector.getColor());
			editFunction.setDrawSize(((Integer) drawSizeField.getValue())
					.intValue());
			String drawType = (String) drawTypeList.getSelectedItem();
			if (drawType.equals("Line")) {
				editFunction.setDrawMethod(PlotFunction.LINE_DRAW);
			} else {
				editFunction.setDrawMethod(PlotFunction.POINT_DRAW);
			}
			String detailLevel = (String) detailList.getSelectedItem();
			if (detailLevel.equals("Low")) {
				editFunction.setJumpSize(10);
			} else if (detailLevel.equals("Medium")) {
				editFunction.setJumpSize(100);
			} else {
				editFunction.setJumpSize(1000);
			}
			return editFunction;
		}
	}
}
