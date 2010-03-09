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

import handlers.StrokeTypeHandler;
import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import main.OpenPlotTool;
import plot.PageSettings;

@SuppressWarnings("serial")
public class PageSettingsDialog extends JDialog implements ActionListener {
	public static int APPLY_PRESSED = 1;
	private JButton applyButton, cancelButton;
	private ColorSelector backgroundSelector;
	private JComboBox borderDrawCombo;
	private ColorSelector borderSelector;
	private JCheckBox drawBorderCheck;
	private int pressed = 0;
	private PageSettings settings;
	private ColorSelector titleColorSelector;
	private JTextField titleField;

	public PageSettingsDialog(PageSettings inSettings) {
		super(OpenPlotTool.getMainFrame(), "Page Settings", true);
		settings = inSettings;
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// create settings tab pane
		JTabbedPane settingsPane = new JTabbedPane();
		mainPanel.add(settingsPane, BorderLayout.CENTER);

		// create draw settings panel
		JPanel drawSettingPanel = new JPanel();
		settingsPane.addTab("Draw Settings", drawSettingPanel);
		double[][] drawSettingLayoutSize = { { 0.35, 0.65 }, { 25, 25, 25 } };
		drawSettingPanel.setLayout(new TableLayout(drawSettingLayoutSize));

		// create title field
		drawSettingPanel.add(new JLabel("Title: "), "0, 0");
		titleField = new JTextField(10);
		titleField.setText("" + settings.getTitle());
		drawSettingPanel.add(titleField, "1, 0");
		// create title color field
		drawSettingPanel.add(new JLabel("Title Color: "), "0, 1");
		titleColorSelector = new ColorSelector(settings.getTitleColor());
		drawSettingPanel.add(titleColorSelector, "1, 1");
		// create background color field
		drawSettingPanel.add(new JLabel("Background Color: "), "0, 2");
		backgroundSelector = new ColorSelector(settings.getBackgroundColor());
		drawSettingPanel.add(backgroundSelector, "1, 2");

		// create border settings panel
		JPanel borderSettingPanel = new JPanel();
		settingsPane.addTab("Border Settings", borderSettingPanel);
		double[][] borderSettingLayoutSize = { { 0.35, 0.65 }, { 25, 25, 25 } };
		borderSettingPanel.setLayout(new TableLayout(borderSettingLayoutSize));

		// create draw border field
		borderSettingPanel.add(new JLabel("Draw Border: "), "0, 0");
		drawBorderCheck = new JCheckBox();
		drawBorderCheck.setSelected(settings.getDrawBorder());
		borderSettingPanel.add(drawBorderCheck, "1, 0");
		// create border color field
		borderSettingPanel.add(new JLabel("Border Color: "), "0, 1");
		borderSelector = new ColorSelector(settings.getBorderColor());
		borderSettingPanel.add(borderSelector, "1, 1");
		// create border draw type field
		borderSettingPanel.add(new JLabel("Border Draw Type: "), "0, 2");
		borderDrawCombo = new JComboBox(StrokeTypeHandler.getStrokeTypeArray());
		borderDrawCombo.setSelectedItem(settings.getBorderDrawType());
		borderSettingPanel.add(borderDrawCombo, "1, 2");

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

	public PageSettings getNewSettings() {
		// build and return new page settings object
		settings.setTitle(titleField.getText());
		settings.setTitleColor(titleColorSelector.getColor());
		settings.setBackgroundColor(backgroundSelector.getColor());
		settings.setDrawBorder(drawBorderCheck.isSelected());
		settings.setBorderColor(borderSelector.getColor());
		settings.setBorderDrawType((String) borderDrawCombo.getSelectedItem());
		return settings;
	}

	public int getPressed() {
		return pressed;
	}
}
