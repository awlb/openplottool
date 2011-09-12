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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.OpenPlotTool;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PointDialog extends JDialog implements ActionListener {
	public static int UPDATE_PRESSED = 1;
	private JButton cancelBtn;
	private Cartesian2DPoint newPoint;
	private int pressed = 0;
	private JButton updateBtn;
	private JTextField xField;
	private JTextField yField;

	public PointDialog(Cartesian2DPoint newPoint, boolean editMode) {
		super(OpenPlotTool.getInstance().getMainFrame(), "New Point", true);
		if (editMode) {
			setTitle("Edit Point");
		}
		this.newPoint = newPoint;
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		// create input panel
		JPanel inputPanel = new JPanel(new MigLayout("", "", "25px"));
		inputPanel.setBorder(BorderFactory
				.createTitledBorder("Point Details: "));
		mainPanel.add(inputPanel, BorderLayout.PAGE_START);
		// create x field
		inputPanel.add(new JLabel("X: "));
		xField = new JTextField(15);
		xField.setText(String.valueOf(newPoint.getX()));
		inputPanel.add(xField, "grow, span");
		// create y field
		inputPanel.add(new JLabel("Y: "));
		yField = new JTextField(15);
		yField.setText(String.valueOf(newPoint.getY()));
		inputPanel.add(yField, "grow, span");

		// create ok and cancel buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		updateBtn = new JButton("Update");
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

	public Cartesian2DPoint getPoint() {
		double x = Double.parseDouble(xField.getText());
		double y = Double.parseDouble(yField.getText());
		newPoint.setX(x);
		newPoint.setY(y);
		return newPoint;
	}

	public int getPressed() {
		return pressed;
	}
}
