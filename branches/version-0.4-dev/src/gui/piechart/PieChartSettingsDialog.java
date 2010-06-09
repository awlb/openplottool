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

package gui.piechart;

import gui.ColorSelector;
import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.OpenPlotTool;
import plot.piechart.PieChartAxis;
import plot.piechart.PieChartSettings;

@SuppressWarnings("serial")
public class PieChartSettingsDialog extends JDialog implements ActionListener,
		ListSelectionListener {
	public static int OK_PRESSED = 1;
	private JButton applyButton, cancelButton;
	private ColorSelector backgroundColorSelector, textColorSelector,
			outlineColorSelector;
	private CardLayout cardLayout;
	private JCheckBox drawKeyCheck;
	private ColorSelector keyColorSelector;
	private int pressed = 0;
	private JList sectionList;
	private PieChartSettings settings;
	private JPanel settingsPanel;

	public PieChartSettingsDialog(PieChartAxis axis) {
		super(OpenPlotTool.getMainFrame(), "Axis Settings", true);
		// get current settings
		settings = (PieChartSettings) axis.getPlotSettings();

		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		// create settings panel
		cardLayout = new CardLayout();
		settingsPanel = new JPanel(cardLayout);
		mainPanel.add(settingsPanel, BorderLayout.CENTER);

		// create section list
		String[] sections = { "Draw", "Key" };
		sectionList = new JList(sections);
		sectionList.setPreferredSize(new Dimension(115, 50));
		sectionList.addListSelectionListener(this);
		sectionList.setSelectedIndex(0);
		JScrollPane stopsTableScroll = new JScrollPane(sectionList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(stopsTableScroll, BorderLayout.LINE_START);

		// create draw settings panel
		JPanel drawSettingsPanel = new JPanel();
		drawSettingsPanel.setBorder(BorderFactory
				.createTitledBorder("Draw Settings:"));
		settingsPanel.add(drawSettingsPanel, "Draw");
		// create layout
		double[][] drawSettingsSize = { { 0.4, 0.6 }, { 25, 25, 25 } };
		drawSettingsPanel.setLayout(new TableLayout(drawSettingsSize));
		// create background color field
		drawSettingsPanel.add(new JLabel("Background: "), "0, 0");
		backgroundColorSelector = new ColorSelector(settings
				.getBackgroundColor());
		drawSettingsPanel.add(backgroundColorSelector, "1, 0");
		// create text color field
		drawSettingsPanel.add(new JLabel("Text Color: "), "0, 1");
		textColorSelector = new ColorSelector(settings.getTextColor());
		drawSettingsPanel.add(textColorSelector, "1, 1");
		// create outline color field
		drawSettingsPanel.add(new JLabel("Outline Color: "), "0, 2");
		outlineColorSelector = new ColorSelector(settings.getOutlineDrawColor());
		drawSettingsPanel.add(outlineColorSelector, "1, 2");

		// create key settings panel
		JPanel keySettingsPanel = new JPanel();
		keySettingsPanel.setBorder(BorderFactory
				.createTitledBorder("Key Settings:"));
		settingsPanel.add(keySettingsPanel, "Key");
		// create layout
		double[][] keyettingsSize = { { 0.4, 0.6 }, { 25, 25 } };
		keySettingsPanel.setLayout(new TableLayout(keyettingsSize));
		// create draw key check box
		keySettingsPanel.add(new JLabel("Draw Key: "), "0, 0");
		drawKeyCheck = new JCheckBox();
		drawKeyCheck.setSelected(settings.isDrawKey());
		keySettingsPanel.add(drawKeyCheck, "1, 0");
		// create background color field
		keySettingsPanel.add(new JLabel("Background: "), "0, 1");
		keyColorSelector = new ColorSelector(settings.getKeyBackgroundColor());
		keySettingsPanel.add(keyColorSelector, "1, 1");

		// create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		applyButton = new JButton("Apply");
		applyButton.addActionListener(this);
		buttonPanel.add(applyButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

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
			pressed = OK_PRESSED;
		}
		setVisible(false);
	}

	public PieChartSettings getNewSettings() {
		// build and return new pie chart axis settings
		settings.setBackgroundColor(backgroundColorSelector.getColor());
		settings.setTextColor(textColorSelector.getColor());
		settings.setOutlineDrawColor(outlineColorSelector.getColor());
		settings.setDrawKey(drawKeyCheck.isSelected());
		settings.setKeyBackgroundColor(keyColorSelector.getColor());

		return settings;
	}

	public int getPressed() {
		return pressed;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		String selection = (String) sectionList.getSelectedValue();
		cardLayout.show(settingsPanel, selection);
	}
}
