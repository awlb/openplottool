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

import handlers.EditHandler;
import handlers.ExitHandler;
import handlers.FileHandler;
import handlers.HelpHandler;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.action.ActionContainerFactory;
import org.jdesktop.swingx.action.ActionFactory;
import org.jdesktop.swingx.action.ActionManager;
import org.jdesktop.swingx.action.BoundAction;

@SuppressWarnings("serial")
public class MainFrame extends JXFrame {
	// menu bar
	private JMenuBar menuBar;
	// status bar
	private JXStatusBar statusBar;
	// program tool bar
	private JToolBar toolBar;
	// loading bar
	private JProgressBar loadingBar;
	// tab area
	private JTabbedPane tabPane;

	public MainFrame() {
		super("OpenPlotTool 0.4");
		// create action manager
		ActionManager actionManager = ActionManager.getInstance();

		// create file action
		BoundAction fileAction = ActionFactory.createBoundAction("file-action",
				"File", null);
		ActionFactory.decorateAction(fileAction, null, null, null, null, null);
		actionManager.addAction(fileAction);

		// create new plot action
		BoundAction newAction = ActionFactory.createBoundAction("new-action",
				"New", null);
		ActionFactory.decorateAction(newAction, "Create New Plot",
				"Create New Plot", new ImageIcon("icon/document-new.png"),
				null, null);
		// add and register new plot action action
		actionManager.addAction(newAction);
		actionManager.registerCallback("new-action", FileHandler.getInstance(),
				"newPlot");

		// create open plot action
		BoundAction openAction = ActionFactory.createBoundAction("open-action",
				"Open", null);
		ActionFactory.decorateAction(openAction, "Open Plot File",
				"Open Plot File", new ImageIcon("icon/document-open.png"),
				null, null);
		// add and register new plot action action
		actionManager.addAction(openAction);
		actionManager.registerCallback("open-action",
				FileHandler.getInstance(), "openPlot");

		// create save plot action
		BoundAction saveAction = ActionFactory.createBoundAction("save-action",
				"Save", null);
		ActionFactory.decorateAction(saveAction, "Save Plot", "Save Plot",
				new ImageIcon("icon/document-save.png"), null, null);
		// add and register new plot action action
		actionManager.addAction(saveAction);
		actionManager.registerCallback("save-action",
				FileHandler.getInstance(), "savePlot");

		// create save as plot action
		BoundAction saveAsAction = ActionFactory.createBoundAction(
				"save-as-action", "Save As...", null);
		ActionFactory.decorateAction(saveAsAction, "Save Plot As...",
				"Save Plot As...", new ImageIcon("icon/document-save-as.png"),
				null, null);
		// add and register new plot action action
		actionManager.addAction(saveAsAction);
		actionManager.registerCallback("save-as-action", FileHandler
				.getInstance(), "savePlotAs");

		// create revert plot action
		BoundAction revertAction = ActionFactory.createBoundAction(
				"revert-action", "Revert", null);
		ActionFactory.decorateAction(revertAction, "Revert to saved state",
				"Revert to saved state", new ImageIcon(
						"icon/document-revert.png"), null, null);
		// add and register new plot action action
		actionManager.addAction(revertAction);
		actionManager.registerCallback("revert-action", FileHandler
				.getInstance(), "savePlotAs");

		// create close plot action
		BoundAction closeAction = ActionFactory.createBoundAction(
				"close-action", "Close", null);
		ActionFactory.decorateAction(closeAction, "Close Plot", "Close Plot",
				new ImageIcon("icon/document-close.png"), null, null);
		// add and register close plot action
		actionManager.addAction(closeAction);
		actionManager.registerCallback("close-action", FileHandler
				.getInstance(), "closePlot");

		// create close all plot action
		BoundAction closeAllAction = ActionFactory.createBoundAction(
				"close-all-action", "Close All", null);
		ActionFactory.decorateAction(closeAllAction, "Close All Plots",
				"Close All Plots",
				new ImageIcon("icon/document-close-all.png"), null, null);
		// add and register close all plot action
		actionManager.addAction(closeAllAction);
		actionManager.registerCallback("close-all-action", FileHandler
				.getInstance(), "closeAllPlots");

		// create print plot action
		BoundAction printAction = ActionFactory.createBoundAction(
				"print-action", "Print", null);
		ActionFactory.decorateAction(printAction, "Print Plot", "Print Plot",
				new ImageIcon("icon/document-print.png"), null, null);
		// add and register print plot action
		actionManager.addAction(printAction);
		actionManager.registerCallback("print-action", FileHandler
				.getInstance(), "printPlot");

		// create export plot action
		BoundAction exportAction = ActionFactory.createBoundAction(
				"export-action", "Export", null);
		ActionFactory.decorateAction(exportAction, "Export Plot",
				"Export Plot", new ImageIcon("icon/document-export.png"), null,
				null);
		// add and register export plot action
		actionManager.addAction(exportAction);
		actionManager.registerCallback("export-action", FileHandler
				.getInstance(), "exportPlot");

		// create export plot action
		BoundAction exitAction = ActionFactory.createBoundAction("exit-action",
				"Exit", null);
		ActionFactory.decorateAction(exitAction, "Exit Program",
				"Exit Program", new ImageIcon("icon/exit.png"), null, null);
		// add and register exit action
		actionManager.addAction(exitAction);
		actionManager.registerCallback("exit-action",
				ExitHandler.getInstance(), "exitProgram");

		// create edit action
		BoundAction editAction = ActionFactory.createBoundAction("edit-action",
				"Edit", null);
		ActionFactory.decorateAction(editAction, null, null, null, null, null);
		actionManager.addAction(editAction);

		// create undo action
		BoundAction undoAction = ActionFactory.createBoundAction("undo-action",
				"Undo", null);
		ActionFactory.decorateAction(undoAction, "Undo Plot Edits",
				"Undo Plot Edits", new ImageIcon("icon/edit-undo.png"), null,
				null);
		// add and register undo action
		actionManager.addAction(undoAction);
		actionManager.registerCallback("undo-action",
				EditHandler.getInstance(), "undoEdit");

		// create redo action
		BoundAction redoAction = ActionFactory.createBoundAction("redo-action",
				"Redo", null);
		ActionFactory.decorateAction(redoAction, "Redo Plot Edits",
				"Redo Plot Edits", new ImageIcon("icon/edit-redo.png"), null,
				null);
		// add and register redo action
		actionManager.addAction(redoAction);
		actionManager.registerCallback("redo-action",
				EditHandler.getInstance(), "redoEdit");

		// create preferences action
		BoundAction preferencesAction = ActionFactory.createBoundAction(
				"preferences-action", "Preferences", null);
		ActionFactory.decorateAction(preferencesAction, "Edit Preferences",
				"Edit Preferences", new ImageIcon("icon/edit-preferences.png"),
				null, null);
		// add and register preferences action
		actionManager.addAction(preferencesAction);
		actionManager.registerCallback("preferences-action", EditHandler
				.getInstance(), "preferencesEdit");

		// create help action
		BoundAction helpAction = ActionFactory.createBoundAction("help-action",
				"Help", null);
		ActionFactory.decorateAction(helpAction, null, null, null, null, null);
		actionManager.addAction(helpAction);

		// create about action
		BoundAction aboutAction = ActionFactory.createBoundAction(
				"about-action", "About", null);
		ActionFactory
				.decorateAction(aboutAction, "View About", "Edit Preferences",
						new ImageIcon("icon/about.png"), null, null);
		// add and register preferences action
		actionManager.addAction(aboutAction);
		actionManager.registerCallback("about-action", HelpHandler
				.getInstance(), "showAbout");

		// create action container factory
		ActionContainerFactory factory = new ActionContainerFactory(
				actionManager);

		// create menu bar
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		// create file menu
		String[] fileMenuItems = { "file-action", "new-action", "open-action",
				null, "save-action", "save-as-action", "revert-action", null,
				"close-action", "close-all-action", null, "print-action", null,
				"export-action", null, "exit-action" };
		JMenu fileMenu = factory.createMenu(fileMenuItems);
		menuBar.add(fileMenu);
		// create edit menu
		String[] editMenuItems = { "edit-action", "undo-action", "redo-action",
				null, "preferences-action" };
		JMenu editMenu = factory.createMenu(editMenuItems);
		menuBar.add(editMenu);
		// create help menu
		String[] helpMenuItems = { "help-action", "about-action" };
		JMenu helpMenu = factory.createMenu(helpMenuItems);
		menuBar.add(helpMenu);

		// create tool bar
		String[] toolBarItems = { "new-action", "open-action", "save-action",
				null, "print-action" };
		toolBar = factory.createToolBar(toolBarItems);
		toolBar.setFloatable(false);
		this.setToolBar(toolBar);

		// create status bar
		statusBar = new JXStatusBar();
		this.setStatusBar(statusBar);

		// create loading bar
		loadingBar = new JProgressBar();
		statusBar.add(loadingBar);

		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		// create tab pane
		tabPane = new JTabbedPane();
		mainPanel.add(tabPane, BorderLayout.CENTER);

		// pack main frame
		pack();

		// turn of the default window closing code
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Set the new frame location
		setLocation(100, 100);
	}

	public JProgressBar getLoadingBar() {
		return loadingBar;
	}

	public JTabbedPane getTabPane() {
		return tabPane;
	}
}
