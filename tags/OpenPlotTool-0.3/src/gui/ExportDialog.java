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

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.OpenPlotTool;
import plot.PlotPage;

@SuppressWarnings("serial")
public class ExportDialog extends JDialog implements ActionListener {

	public static int EXPORT_PRESSED = 1;
	private JButton exportButton, cancelButton, fileButton;
	private JComboBox formatBox;
	private JTextField locationField, nameField;
	private int pressed = 0;

	public ExportDialog(PlotPage page) {
		super(OpenPlotTool.getMainFrame(), "Export", true);

		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// create export panel
		JPanel exportPanel = new JPanel();
		mainPanel.add(exportPanel, BorderLayout.CENTER);
		double[][] settingLayoutSize = { { 0.25, 0.75 }, { 25, 25, 25 } };
		exportPanel.setLayout(new TableLayout(settingLayoutSize));
		exportPanel
				.setBorder(BorderFactory.createTitledBorder("Export File: "));

		// create name field
		exportPanel.add(new JLabel("Name: "), "0, 0");
		nameField = new JTextField(10);
		nameField.setText(page.getSettings().getTitle());
		exportPanel.add(nameField, "1, 0");
		// create location field
		exportPanel.add(new JLabel("Location: "), "0, 1");
		JPanel filePanel = new JPanel(new BorderLayout());
		locationField = new JTextField(10);
		locationField.setText(System.getProperty("user.home"));
		filePanel.add(locationField, BorderLayout.CENTER);
		fileButton = new JButton("...");
		fileButton.addActionListener(this);
		filePanel.add(fileButton, BorderLayout.LINE_END);
		exportPanel.add(filePanel, "1, 1");
		// create image type field
		exportPanel.add(new JLabel("Format: "), "0, 2");
		String[] formatNames = ImageIO.getReaderFormatNames();
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < formatNames.length; i++) {
			// only add formats with extension of length three
			if(formatNames[i].length()==3) {
				String name = formatNames[i].toLowerCase();
				set.add(name);
			}
		}
		formatNames = set.toArray(new String[0]);
		formatBox = new JComboBox(formatNames);
		exportPanel.add(formatBox, "1, 2");

		// create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		exportButton = new JButton("Export");
		exportButton.addActionListener(this);
		buttonPanel.add(exportButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

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
		if (e.getSource() == exportButton) {
			pressed = EXPORT_PRESSED;
			setVisible(false);
		} else if (e.getSource() == fileButton) {
			// show file chooser so user can select directory
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.showOpenDialog(this);
			// get selected directory
			File selFile = fileChooser.getSelectedFile();
			if (selFile != null) {
				locationField.setText(selFile.getAbsolutePath());
			}
		} else {
			setVisible(false);
		}
	}

	public File getFile() {
		if (nameField.getText().equals("")
				|| locationField.getText().equals("")) {
			return null;
		} else {
			// return file to be exported to
			File file = new File(locationField.getText() + File.separator
					+ nameField.getText() + "."
					+ (String) formatBox.getSelectedItem());
			return file;
		}
	}

	public String getFormat() {
		// return export format
		return (String) formatBox.getSelectedItem();
	}

	public int getPressed() {
		return pressed;
	}
}
