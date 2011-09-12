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

package plot.cartesian2D;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
import net.miginfocom.swing.MigLayout;
import net.smplmathparser.MathParserException;

@SuppressWarnings("serial")
public class FunctionDialog extends JDialog implements ActionListener {
	public static int UPDATE_PRESSED = 1;
	private JButton cancelBtn;
	private JComboBox drawTypeList;
	private JTextField functionField;
	private Cartesian2DFunction newFunction;
	private int pressed = 0;
	private JSpinner sizeField;
	private JButton updateBtn;

	public FunctionDialog(Cartesian2DFunction newFunction, boolean editMode) {
		super(OpenPlotTool.getInstance().getMainFrame(), "New Function", true);
		if (editMode) {
			setTitle("Edit Function");
		}
		this.newFunction = newFunction;
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		// create input panel
		JPanel inputPanel = new JPanel(new MigLayout("", "", "25px"));
		inputPanel.setBorder(BorderFactory
				.createTitledBorder("Function Details: "));
		mainPanel.add(inputPanel, BorderLayout.PAGE_START);
		// create function field
		inputPanel.add(new JLabel("Function: "));
		functionField = new JTextField(15);
		functionField.setText(newFunction.getFunctionString());
		inputPanel.add(functionField, "grow, span");
		// create set size field
		inputPanel.add(new JLabel("Draw Size: "));
		SpinnerModel sizeModel = new SpinnerNumberModel(1, 1, 10, 1);
		sizeField = new JSpinner(sizeModel);
		sizeField.setValue(newFunction.getDrawSize());
		inputPanel.add(sizeField, "grow, span");
		// draw type field
		inputPanel.add(new JLabel("Draw Type: "));
		String[] drawTypes = { "Line", "Point" };
		drawTypeList = new JComboBox(drawTypes);
		drawTypeList.setSelectedIndex(newFunction.getDrawMethod());
		inputPanel.add(drawTypeList, "grow, span");

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
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateBtn) {
			pressed = UPDATE_PRESSED;
		}
		setVisible(false);
	}

	public Cartesian2DFunction getFunction() throws MathParserException {
		newFunction.setFunctionString(functionField.getText());
		Integer value = (Integer) sizeField.getValue();
		newFunction.setDrawSize(value.intValue());
		String type = (String) drawTypeList.getSelectedItem();
		if ("Line".equals(type)) {
			newFunction.setDrawMethod(Cartesian2DFunction.LINE_DRAW);
		} else {
			newFunction.setDrawMethod(Cartesian2DFunction.POINT_DRAW);
		}
		return newFunction;
	}

	public int getPressed() {
		return pressed;
	}
}
