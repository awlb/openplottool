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
import java.awt.CardLayout;
import java.awt.Dimension;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.OpenPlotTool;
import main.Preferences;

@SuppressWarnings("serial")
public class PreferenceDialog extends JDialog implements ActionListener,
		ListSelectionListener {
	public static int APPLY_PRESSED = 1;
	private JComboBox aaTypeField, lookTypeField;
	private JButton applyButton, cancelButton;
	private CardLayout cardLayout;
	private JCheckBox compressFilesCheck, addExtensionCheck,
			checkExtensionCheck;
	private int pressed = 0;
	private JList sectionList;
	private JPanel settingsPanel;

	public PreferenceDialog(Preferences settings) {
		super(OpenPlotTool.getMainFrame(), "Preferences", true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		// create settings panel
		cardLayout = new CardLayout();
		settingsPanel = new JPanel(cardLayout);
		mainPanel.add(settingsPanel, BorderLayout.CENTER);

		// create section list
		String[] sections = { "Draw", "Files", "User Interface" };
		sectionList = new JList(sections);
		sectionList.setPreferredSize(new Dimension(115, 50));
		sectionList.addListSelectionListener(this);
		sectionList.setSelectedIndex(0);
		JScrollPane stopsTableScroll = new JScrollPane(sectionList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(stopsTableScroll, BorderLayout.LINE_START);

		// create draw settings panel
		JPanel drawSettingPanel = new JPanel();
		drawSettingPanel.setBorder(BorderFactory
				.createTitledBorder("Draw Settings: "));
		settingsPanel.add(drawSettingPanel, "Draw");
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
		settingsPanel.add(guiSettingPanel, "User Interface");
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
		settingsPanel.add(fileSettingsPanel, "Files");
		double[][] fileSettingsLayoutSize = { { 0.8, 0.2 }, { 25, 25, 25 } };
		fileSettingsPanel.setLayout(new TableLayout(fileSettingsLayoutSize));

		// create add extension check box
		fileSettingsPanel.add(new JLabel("Add file extensions: "), "0, 0");
		addExtensionCheck = new JCheckBox();
		addExtensionCheck.setSelected(settings.isAddFileExtensions());
		fileSettingsPanel.add(addExtensionCheck, "1, 0");

		// create check file extensions check box
		fileSettingsPanel.add(new JLabel("Check file extensions: "), "0, 1");
		checkExtensionCheck = new JCheckBox();
		checkExtensionCheck.setSelected(settings.isCheckFileExtensions());
		fileSettingsPanel.add(checkExtensionCheck, "1, 1");

		// create compress files check box
		fileSettingsPanel.add(new JLabel("Compress Files: "), "0, 2");
		compressFilesCheck = new JCheckBox();
		compressFilesCheck.setSelected(settings.useCompressedFiles());
		fileSettingsPanel.add(compressFilesCheck, "1, 2");

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
		newSettings.setAddFileExtensions(addExtensionCheck.isSelected());
		newSettings.setCheckFileExtensions(checkExtensionCheck.isSelected());
		return newSettings;
	}

	public int getPressed() {
		return pressed;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		String selection = (String) sectionList.getSelectedValue();
		cardLayout.show(settingsPanel, selection);
	}
}
