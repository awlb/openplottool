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

import handlers.PreferenceHandler;
import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.OpenPlotTool;
import main.Preferences;

@SuppressWarnings("serial")
public class PreferenceDialog extends JDialog implements ActionListener {
	public static int APPLY_PRESSED = 1;
	private JCheckBox compressFilesCheck;
	private JComboBox aaTypeField, lookTypeField;
	private JButton applyButton, cancelButton;
	private int pressed = 0;

	public PreferenceDialog(Preferences settings) {
		super(OpenPlotTool.getMainFrame(), "Preferences", true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// create settings panel
		JPanel settingsPanel = new JPanel(new GridLayout(3, 1));
		mainPanel.add(settingsPanel, BorderLayout.CENTER);

		// create draw settings panel
		JPanel drawSettingPanel = new JPanel();
		drawSettingPanel.setBorder(BorderFactory
				.createTitledBorder("Draw Settings: "));
		settingsPanel.add(drawSettingPanel);
		double[][] drawSettingLayoutSize = { { 0.4, 0.6 }, { 25 } };
		drawSettingPanel.setLayout(new TableLayout(drawSettingLayoutSize));

		// create anti aliasing field
		drawSettingPanel.add(new JLabel("Anti aliasing: "), "0, 0");
		String[] aaTypes = { "Full", "None" };
		aaTypeField = new JComboBox(aaTypes);
		aaTypeField.setSelectedItem(PreferenceHandler.getSettings()
				.getAntiAliasing());
		drawSettingPanel.add(aaTypeField, "1, 0");

		// create gui settings panel
		JPanel guiSettingPanel = new JPanel();
		guiSettingPanel.setBorder(BorderFactory
				.createTitledBorder("User Interface Settings: "));
		settingsPanel.add(guiSettingPanel);
		double[][] guiSettingLayoutSize = { { 0.4, 0.6 }, { 25 } };
		guiSettingPanel.setLayout(new TableLayout(guiSettingLayoutSize));

		// create look and feel field
		guiSettingPanel.add(new JLabel("Look and Feel: "), "0, 0");
		String[] lookType = { "System", "Cross Platform" };
		lookTypeField = new JComboBox(lookType);
		lookTypeField.setSelectedItem(settings.getLookAndFeel());
		guiSettingPanel.add(lookTypeField, "1, 0");

		// create file settings panel
		JPanel fileSettingsPanel = new JPanel();
		fileSettingsPanel.setBorder(BorderFactory
				.createTitledBorder("File Settings: "));
		settingsPanel.add(fileSettingsPanel);
		double[][] fileSettingsLayoutSize = { { 0.4, 0.6 }, { 25 } };
		fileSettingsPanel.setLayout(new TableLayout(fileSettingsLayoutSize));

		// create compress files check box
		fileSettingsPanel.add(new JLabel("Compress Files: "), "0, 0");
		compressFilesCheck = new JCheckBox();
		compressFilesCheck.setSelected(settings.useCompressedFiles());
		fileSettingsPanel.add(compressFilesCheck, "1, 0");

		// create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		applyButton = new JButton("Apply");
		applyButton.addActionListener(this);
		buttonPanel.add(applyButton);
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
		if (e.getSource() == applyButton) {
			pressed = APPLY_PRESSED;
		}
		setVisible(false);
	}

	public Preferences getNewSettings() {
		// build and return new preferences object
		Preferences newSettings = new Preferences();
		newSettings.setAntiAliasing((String) aaTypeField.getSelectedItem());
		newSettings.setLookAndFeel((String) lookTypeField.getSelectedItem());
		newSettings.setCompressFiles(compressFilesCheck.isSelected());
		return newSettings;
	}

	public int getPressed() {
		return pressed;
	}
}
