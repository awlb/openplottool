/*	Copyright (C) 2009-2011  Alex Barfoot
 
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

package dev.awlb.opt.plot.cartesian2D;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import dev.awlb.opt.OpenPlotTool;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTable;

@SuppressWarnings("serial")
public class DataSetDialog extends JDialog implements ChangeListener,
		ActionListener {
	public static int UPDATE_PRESSED = 1;
	private JButton cancelBtn, updateBtn;
	private DefaultTableModel dataModel;
	private Cartesian2DDataSet dataSet = null;
	private JXTable dataTable;
	private JCheckBox linkPointsCheck;
	private JTextField nameField;
	private int pressed = 0;
	private JSpinner setSizeField;

	public DataSetDialog(Cartesian2DDataSet dataSet, boolean editMode) {
		super(OpenPlotTool.getInstance().getMainFrame(), "New Data Set", true);
		if (editMode) {
			setTitle("Edit Data Set");
		}
		this.dataSet = dataSet;
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		// create input panel
		JPanel inputPanel = new JPanel(new MigLayout("", "", "25px"));
		inputPanel
				.setBorder(BorderFactory.createTitledBorder("Set Settings: "));
		mainPanel.add(inputPanel, BorderLayout.PAGE_START);
		// create name field
		inputPanel.add(new JLabel("Name: "));
		nameField = new JTextField(15);
		nameField.setText(dataSet.getName());
		inputPanel.add(nameField, "grow, span");
		// create set size field
		inputPanel.add(new JLabel("Set Size: "));
		SpinnerModel sizeModel = new SpinnerNumberModel(1, 1, 200, 1);
		setSizeField = new JSpinner(sizeModel);
		setSizeField.setValue(dataSet.getData().length);
		setSizeField.addChangeListener(this);
		inputPanel.add(setSizeField, "grow, span");
		// create link points checkbox
		inputPanel.add(new JLabel("Link Points: "));
		linkPointsCheck = new JCheckBox();
		inputPanel.add(linkPointsCheck, "grow, span");

		// create data table
		dataModel = new DefaultTableModel();
		dataTable = new JXTable(dataModel);
		JScrollPane dataTableScroll = new JScrollPane(dataTable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dataTableScroll.setBorder(BorderFactory
				.createTitledBorder("Set Data: "));
		mainPanel.add(dataTableScroll, BorderLayout.CENTER);
		dataModel.addColumn("X");
		dataModel.addColumn("Y");
		dataModel.setRowCount(dataSet.getData().length);
		for (int i = 0; i < dataSet.getData().length; i++) {
			dataModel.setValueAt(String.valueOf(dataSet.getData()[i][0]), i, 0);
			dataModel.setValueAt(String.valueOf(dataSet.getData()[i][0]), i, 1);
		}

		// create ok and cancel buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		updateBtn = new JButton("Create");
		if (editMode) {
			updateBtn.setText("Update");
		}
		updateBtn.addActionListener(this);
		buttonPanel.add(updateBtn);
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		buttonPanel.add(cancelBtn);

		// set close action
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// set dialog location
		Point parentPoint = OpenPlotTool.getInstance().getMainFrame()
				.getLocation();
		Point winLocation = new Point(parentPoint.x + 20, parentPoint.y + 20);
		setLocation(winLocation);

		// pack dialog
		setSize(300, 300);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateBtn) {
			pressed = UPDATE_PRESSED;
		}
		setVisible(false);
	}

	public Cartesian2DDataSet getDataSet() throws Exception {
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
				throw new Exception("Null value");
			}
		}
		dataSet.setName(nameField.getText());
		dataSet.setLinkPoints(linkPointsCheck.isSelected());
		dataSet.setData(rawData);
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
