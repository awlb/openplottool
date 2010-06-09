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
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import main.OpenPlotTool;
import plot.cartesian.CartesianDataSet;

@SuppressWarnings("serial")
public class CartesianDataDialog extends JDialog implements ChangeListener,
		ActionListener {
	public static int ADD_PRESSED = 1;
	private JButton addButton, cancelButton;
	private ColorSelector colorSelector;
	private DefaultTableModel dataModel;
	private JTable dataTable;
	private JCheckBox linkPointsCheck;
	private JTextField nameField;
	private int pressed = 0;
	private JSpinner setSizeField;

	public CartesianDataDialog(CartesianDataSet currentDataSet) {
		super(OpenPlotTool.getMainFrame(), "Add Data Set", true);

		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// create settings panel
		JPanel settingsPanel = new JPanel();
		settingsPanel.setBorder(BorderFactory
				.createTitledBorder("Set Settings: "));
		mainPanel.add(settingsPanel, BorderLayout.PAGE_START);
		// create layout
		double[][] settingsSize = { { 0.4, 0.6 }, { 25, 25, 25, 25 } };
		settingsPanel.setLayout(new TableLayout(settingsSize));
		// create name field
		nameField = new JTextField();
		settingsPanel.add(new JLabel("Name: "), "0, 0");
		settingsPanel.add(nameField, "1, 0");
		// create set size field
		settingsPanel.add(new JLabel("Set Size: "), "0, 1");
		SpinnerModel sizeModel = new SpinnerNumberModel(4, 1, 200, 1);
		setSizeField = new JSpinner(sizeModel);
		setSizeField.addChangeListener(this);
		settingsPanel.add(setSizeField, "1, 1");
		// create color field
		settingsPanel.add(new JLabel("Color: "), "0, 2");
		colorSelector = new ColorSelector(Color.RED);
		settingsPanel.add(colorSelector, "1, 2");
		// create link point check box
		settingsPanel.add(new JLabel("Link Points: "), "0, 3");
		linkPointsCheck = new JCheckBox();
		settingsPanel.add(linkPointsCheck, "1, 3");

		// create data table
		dataModel = new DefaultTableModel();
		dataTable = new JTable(dataModel);
		JScrollPane dataTableScroll = new JScrollPane(dataTable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dataTableScroll.setBorder(BorderFactory
				.createTitledBorder("Set Data: "));
		mainPanel.add(dataTableScroll, BorderLayout.CENTER);
		dataModel.addColumn("X");
		dataModel.addColumn("Y");
		dataModel.setRowCount(4);

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

		// load values from current data set
		if (currentDataSet != null) {
			nameField.setText(currentDataSet.getDataTitle());
			sizeModel.setValue(new Integer(currentDataSet.getData().length));
			colorSelector.setColor(currentDataSet.getDrawColor());
			linkPointsCheck.setSelected(currentDataSet.isLinkPoints());
			dataModel.setRowCount(currentDataSet.getData().length);
			// add current data to table
			double[][] rawData = currentDataSet.getData();
			for (int i = 0; i < rawData.length; i++) {
				dataModel.setValueAt("" + rawData[i][0], i, 0);
				dataModel.setValueAt("" + rawData[i][1], i, 1);
			}
			// set add button text to update
			addButton.setText("Update");
			// set window title for update
			this.setTitle("Update Data Set");
		}
		// pack dialog
		setSize(300, 300);
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

	public CartesianDataSet getDataSet() throws NumberFormatException,
			NullDataValueException {
		// build and return data set
		double[][] rawData = new double[dataTable.getRowCount()][2];
		// loop through rows putting data into the 2D array
		for (int i = 0; i < dataTable.getRowCount(); i++) {
			String xStr = (String) dataModel.getValueAt(i, 0);
			String yStr = (String) dataModel.getValueAt(i, 1);
			if (xStr != null && yStr != null) {
				double xValue = Double.parseDouble(xStr);
				double yValue = Double.parseDouble(yStr);
				rawData[i][0] = xValue;
				rawData[i][1] = yValue;
			} else {
				// throw null data exception as some data cells where left empty
				throw new NullDataValueException();
			}
		}
		CartesianDataSet dataSet = new CartesianDataSet();
		dataSet.setDataTitle(nameField.getText());
		dataSet.setData(rawData);
		dataSet.setDrawColor(colorSelector.getColor());
		dataSet.setLinkPoints(linkPointsCheck.isSelected());
		return dataSet;
	}

	public int getPressed() {
		return pressed;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Integer value = (Integer) setSizeField.getValue();
		int num = value.intValue();
		dataModel.setRowCount(num);
	}
}