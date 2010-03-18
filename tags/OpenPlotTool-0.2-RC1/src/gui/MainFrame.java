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
import info.clearthought.layout.TableLayoutConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import main.OpenPlotTool;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	// help menu items
	private JMenuItem aboutMenuItem;
	// plot menu items
	private JMenuItem addDataItem, addPointItem, addFunctionItem,
			editSelectedItem, removeSelectedItem;
	// tab panels
	private JTabbedPane consoleTabPane, plotPanel;
	private JMenuItem exportMenuItem, quitMenuItem;
	// function, points and data set lists models
	private JList functionList, pointList, dataSetList;
	// function, points and data set lists models
	private DefaultListModel functionListModel, pointListModel,
			dataSetListModel;
	// file menu items
	private JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem;
	private JMenuItem saveAllMenuItem, closeMenuItem, closeAllMenuItem,
			printMenuItem;
	// page menu items
	private JMenuItem pageInformationItem, pageSettingsItem, axisSettingsItem;
	// edit menu items
	private JMenuItem prefMenuItem;
	// progress bar
	private JProgressBar progressBar;

	public MainFrame() {
		super(OpenPlotTool.programName);
		setIconImage(new ImageIcon("icon/plot.png").getImage());
		// create and set main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		// create menu bar and menus
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// create file menu
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		fileMenu.add(new JSeparator());
		saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		saveAsMenuItem = new JMenuItem("Save As...");
		fileMenu.add(saveAsMenuItem);
		saveAllMenuItem = new JMenuItem("Save All");
		fileMenu.add(saveAllMenuItem);
		fileMenu.add(new JSeparator());
		closeMenuItem = new JMenuItem("Close");
		fileMenu.add(closeMenuItem);
		closeAllMenuItem = new JMenuItem("Close All");
		fileMenu.add(closeAllMenuItem);
		fileMenu.add(new JSeparator());
		printMenuItem = new JMenuItem("Print");
		fileMenu.add(printMenuItem);
		fileMenu.add(new JSeparator());
		exportMenuItem = new JMenuItem("Export");
		fileMenu.add(exportMenuItem);
		fileMenu.add(new JSeparator());
		quitMenuItem = new JMenuItem("Quit");
		fileMenu.add(quitMenuItem);

		// create edit menu
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		prefMenuItem = new JMenuItem("Preferences");
		editMenu.add(prefMenuItem);

		// create page menu
		JMenu pageMenu = new JMenu("Page");
		menuBar.add(pageMenu);
		pageInformationItem = new JMenuItem("Page Information");
		pageMenu.add(pageInformationItem);
		pageSettingsItem = new JMenuItem("Page Settings");
		pageMenu.add(pageSettingsItem);
		pageMenu.add(new JSeparator());
		axisSettingsItem = new JMenuItem("Axis Settings");
		pageMenu.add(axisSettingsItem);

		// create plot menu
		JMenu plotMenu = new JMenu("Plot");
		menuBar.add(plotMenu);
		addDataItem = new JMenuItem("Add Data Set");
		plotMenu.add(addDataItem);
		addFunctionItem = new JMenuItem("Add Function");
		plotMenu.add(addFunctionItem);
		addPointItem = new JMenuItem("Add Point");
		plotMenu.add(addPointItem);
		plotMenu.addSeparator();
		editSelectedItem = new JMenuItem("Edit Selected");
		plotMenu.add(editSelectedItem);
		removeSelectedItem = new JMenuItem("Remove Selected");
		plotMenu.add(removeSelectedItem);

		// create help menu
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);

		// create work area panel
		JPanel workAreaPanel = new JPanel();
		mainPanel.add(workAreaPanel, BorderLayout.CENTER);
		// create layout
		double size[][] = { { TableLayoutConstants.FILL },
				{ TableLayoutConstants.FILL, 150 } };
		workAreaPanel.setLayout(new TableLayout(size));

		// create plot pane
		plotPanel = new JTabbedPane();
		plotPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		workAreaPanel.add(plotPanel, "0, 0");

		// create console tab pane
		consoleTabPane = new JTabbedPane();
		consoleTabPane.setMinimumSize(new Dimension(15, 15));
		workAreaPanel.add(consoleTabPane, "0, 1");

		// create data set list panel
		JPanel dataSetListPanel = new JPanel(new BorderLayout());
		consoleTabPane.addTab("Data Sets", null, dataSetListPanel,
				"Plotted data sets");
		dataSetListModel = new DefaultListModel();
		dataSetList = new JList(dataSetListModel);
		dataSetList.setCellRenderer(new DataListRender());
		JScrollPane dataSetListScroll = new JScrollPane(dataSetList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		dataSetListPanel.add(dataSetListScroll, BorderLayout.CENTER);

		// create function list panel
		JPanel functionListPanel = new JPanel(new BorderLayout());
		consoleTabPane.addTab("Functions", null, functionListPanel,
				"Plotted functions");
		functionListModel = new DefaultListModel();
		functionList = new JList(functionListModel);
		functionList.setCellRenderer(new DataListRender());
		JScrollPane functionListScroll = new JScrollPane(functionList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		functionListPanel.add(functionListScroll, BorderLayout.CENTER);

		// create points list panel
		JPanel pointListPanel = new JPanel(new BorderLayout());
		consoleTabPane.addTab("Points", null, pointListPanel, "Plotted points");
		pointListModel = new DefaultListModel();
		pointList = new JList(pointListModel);
		pointList.setCellRenderer(new DataListRender());
		JScrollPane pointListScroll = new JScrollPane(pointList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pointListPanel.add(pointListScroll, BorderLayout.CENTER);

		// create progress bar
		int minimum = 0;
		int maximum = 100;
		progressBar = new JProgressBar(minimum, maximum);
		mainPanel.add(progressBar, BorderLayout.PAGE_END);

		// set size
		setSize(600, 600);

		// turn of the default window closing code
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		// Calculate the frame location
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;

		// Set the new frame location
		setLocation(x, y);
	}

	public JMenuItem getAboutMenuItem() {
		return aboutMenuItem;
	}

	public JMenuItem getAddDataItem() {
		return addDataItem;
	}

	public JMenuItem getAddPointItem() {
		return addPointItem;
	}

	public JMenuItem getAddFunctionItem() {
		return addFunctionItem;
	}

	public JMenuItem getEditSelectedItem() {
		return editSelectedItem;
	}

	public JMenuItem getRemoveSelectedItem() {
		return removeSelectedItem;
	}

	public JTabbedPane getConsoleTabPane() {
		return consoleTabPane;
	}

	public JTabbedPane getPlotPanel() {
		return plotPanel;
	}

	public JMenuItem getExportMenuItem() {
		return exportMenuItem;
	}

	public JMenuItem getQuitMenuItem() {
		return quitMenuItem;
	}

	public JList getFunctionList() {
		return functionList;
	}

	public JList getPointList() {
		return pointList;
	}

	public JList getDataSetList() {
		return dataSetList;
	}

	public DefaultListModel getFunctionListModel() {
		return functionListModel;
	}

	public DefaultListModel getPointListModel() {
		return pointListModel;
	}

	public DefaultListModel getDataSetListModel() {
		return dataSetListModel;
	}

	public JMenuItem getNewMenuItem() {
		return newMenuItem;
	}

	public JMenuItem getOpenMenuItem() {
		return openMenuItem;
	}

	public JMenuItem getSaveMenuItem() {
		return saveMenuItem;
	}

	public JMenuItem getSaveAsMenuItem() {
		return saveAsMenuItem;
	}

	public JMenuItem getSaveAllMenuItem() {
		return saveAllMenuItem;
	}

	public JMenuItem getCloseMenuItem() {
		return closeMenuItem;
	}

	public JMenuItem getCloseAllMenuItem() {
		return closeAllMenuItem;
	}

	public JMenuItem getPrintMenuItem() {
		return printMenuItem;
	}

	public JMenuItem getPageInformationItem() {
		return pageInformationItem;
	}

	public JMenuItem getPageSettingsItem() {
		return pageSettingsItem;
	}

	public JMenuItem getAxisSettingsItem() {
		return axisSettingsItem;
	}

	public JMenuItem getPrefMenuItem() {
		return prefMenuItem;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}
}