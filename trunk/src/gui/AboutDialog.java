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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import main.OpenPlotTool;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	private JTextArea licenseText;

	public AboutDialog() {
		super(OpenPlotTool.getMainFrame(), "About OpenPlot Tool");
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory
				.createTitledBorder(OpenPlotTool.fullProgramName + ":"));
		setContentPane(mainPanel);

		// create license text area
		licenseText = new JTextArea(16, 40);
		licenseText.setEditable(false);
		licenseText.setLineWrap(true);
		licenseText.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(licenseText);
		mainPanel.add(scroll, BorderLayout.CENTER);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// pack dialog
		pack();
		// set dialog location
		Point winLocation = new Point(
				OpenPlotTool.getMainFrame().getLocation().x + 20, OpenPlotTool
						.getMainFrame().getLocation().y + 20);
		setLocation(winLocation);
		showLicense();
	}

	// show license text
	public void showLicense() {
		String text = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"license/license.txt"));
			String str;
			while ((str = in.readLine()) != null) {
				// read each line of file into string
				text = text + str + "\n";
			}
			in.close();
		} catch (IOException e) {
		}
		licenseText.setText(text);
		licenseText.setCaretPosition(0);
	}
}
