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

package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.OpenPlotTool;

import org.jdesktop.swingx.JXHeader;

import plot.PlotFactory;

@SuppressWarnings("serial")
public class NewPlotDialog extends JDialog implements ActionListener {
	// list click listener inner class
	private class ListClickListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent evt) {
			JList list = (JList) evt.getSource();
			if (evt.getClickCount() == 2) {
				int index = list.locationToIndex(evt.getPoint());
				if (index > -1) {
					selected = ((IconEntry) plotList.getSelectedValue())
							.getName();
					pressed = OK_PRESSED;
					setVisible(false);
				}
			}
		}
	}

	public static int OK_PRESSED = 1;
	private JButton okBtn, cancelBtn;
	private JList plotList;
	private int pressed = 0;

	private String selected = null;

	public NewPlotDialog() {
		super(OpenPlotTool.getInstance().getMainFrame(), "New Plot", true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		// create new header
		JXHeader aboutHeader = new JXHeader();
		mainPanel.add(aboutHeader, BorderLayout.PAGE_START);
		aboutHeader.setTitle("Create New Plot");
		aboutHeader.setDescription("Select type from list below");
		aboutHeader.setIcon(new ImageIcon("icon/new-large.png"));
		aboutHeader.setIconPosition(JXHeader.IconPosition.LEFT);
		// create type selection list
		plotList = new JList(PlotFactory.plotList);
		plotList.setSelectedIndex(0);
		plotList.setVisibleRowCount(4);
		plotList.setCellRenderer(new IconEntryRender());
		plotList.addMouseListener(new ListClickListener());
		JScrollPane scroll = new JScrollPane(plotList);
		mainPanel.add(scroll, BorderLayout.CENTER);
		// create ok and cancel buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		okBtn = new JButton("Ok");
		okBtn.addActionListener(this);
		buttonPanel.add(okBtn);
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		buttonPanel.add(cancelBtn);
		// make dialog none Resizable
		setResizable(false);

		// set close action
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// set dialog location
		Point parentPoint = OpenPlotTool.getInstance().getMainFrame()
				.getLocation();
		Point winLocation = new Point(parentPoint.x + 20, parentPoint.y + 20);
		setLocation(winLocation);

		// set dialog size
		setSize(320, 300);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtn) {
			if (plotList.getSelectedValue() != null) {
				selected = ((IconEntry) plotList.getSelectedValue()).getName();
				pressed = OK_PRESSED;
			}
		}
		setVisible(false);
	}

	public int getPressed() {
		return pressed;
	}

	public String getSelected() {
		// return selected plot type
		return selected;
	}
}
