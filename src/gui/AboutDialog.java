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

package gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import main.OpenPlotTool;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog implements ActionListener {

	private JButton closeBtn;
	private JButton licenseBtn;

	public AboutDialog() {
		super(OpenPlotTool.getMainFrame(), "About", true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		// create information panel
		JPanel informationPanel = new JPanel(new BorderLayout());
		informationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.add(informationPanel, BorderLayout.CENTER);
		// create icon label
		JLabel iconLabel = new JLabel("<html><b>" + OpenPlotTool.programName
				+ "</b></html>", new ImageIcon("icon/plot.png"),
				SwingConstants.CENTER);
		iconLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		iconLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		informationPanel.add(iconLabel, BorderLayout.PAGE_START);
		JLabel webLabel = new JLabel("http://openplottool.sourceforge.net/",
				SwingConstants.CENTER);
		informationPanel.add(webLabel, BorderLayout.CENTER);
		JLabel copyLabel = new JLabel("© 2009 - 2010 Alex Barfoot",
				SwingConstants.CENTER);
		informationPanel.add(copyLabel, BorderLayout.PAGE_END);

		// create ok and cancel buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		licenseBtn = new JButton("License");
		licenseBtn.addActionListener(this);
		buttonPanel.add(licenseBtn);
		closeBtn = new JButton("Close");
		closeBtn.addActionListener(this);
		buttonPanel.add(closeBtn);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
		if (e.getSource() == closeBtn) {
			setVisible(false);
		} else if (e.getSource() == licenseBtn) {
			LicenseDialog licenseDialog = new LicenseDialog();
			licenseDialog.setVisible(true);
		}
	}
}
