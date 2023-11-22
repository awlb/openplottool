package dev.awlb.opt.gui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dev.awlb.opt.handlers.*;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.action.ActionContainerFactory;
import org.jdesktop.swingx.action.ActionFactory;
import org.jdesktop.swingx.action.ActionManager;
import org.jdesktop.swingx.action.BoundAction;

@SuppressWarnings("serial")
public class MainFrame extends JXFrame {
	// tab listener inner class
	private class TabListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent event) {
			if (tabPane.getSelectedIndex() > -1) {
				// enable menu items because tab is shown
				actionManager.setEnabled("save-action", true);
				actionManager.setEnabled("save-as-action", true);
				actionManager.setEnabled("close-action", true);
				actionManager.setEnabled("close-all-action", true);
				actionManager.setEnabled("print-action", true);
				actionManager.setEnabled("import-action", true);
				actionManager.setEnabled("export-action", true);
				actionManager.setEnabled("undo-action", true);
				actionManager.setEnabled("redo-action", true);
				actionManager.setEnabled("plot-action", true);
			} else {
				// disable menu items as there are no tabs
				actionManager.setEnabled("save-action", false);
				actionManager.setEnabled("save-as-action", false);
				actionManager.setEnabled("close-action", false);
				actionManager.setEnabled("close-all-action", false);
				actionManager.setEnabled("print-action", false);
				actionManager.setEnabled("import-action", false);
				actionManager.setEnabled("export-action", false);
				actionManager.setEnabled("undo-action", false);
				actionManager.setEnabled("redo-action", false);
				actionManager.setEnabled("plot-action", false);
			}
		}
	}

	// action manager
	private ActionManager actionManager;
	// loading bar
	private JProgressBar loadingBar;
	// status bar
	private JXStatusBar statusBar;

	// tab area
	private JTabbedPane tabPane;

	public MainFrame() {
		super("OpenPlotTool 0.4 Dev");
		// set icon
		this.setIconImage(new ImageIcon("./icon/logo.png").getImage());

		// create action manager
		actionManager = ActionManager.getInstance();

		// create file action
		BoundAction fileAction = ActionFactory.createBoundAction("file-action",
				"File", null);
		ActionFactory.decorateAction(fileAction, null, null, null, null, null);
		actionManager.addAction(fileAction);

		// create new plot action
		BoundAction newAction = ActionFactory.createBoundAction("new-action",
				"New", null);
		ActionFactory.decorateAction(newAction, "Create New Plot",
				"Create New Plot", new ImageIcon("icon/new.png"), null, null);
		// add and register new plot action action
		actionManager.addAction(newAction);
		actionManager.registerCallback("new-action", FileHandler.getInstance(),
				"newPlot");

		// create open plot action
		BoundAction openAction = ActionFactory.createBoundAction("open-action",
				"Open", null);
		ActionFactory.decorateAction(openAction, "Open Plot File",
				"Open Plot File", new ImageIcon("icon/open.png"), null, null);
		// add and register new plot action action
		actionManager.addAction(openAction);
		actionManager.registerCallback("open-action",
				FileHandler.getInstance(), "openPlot");

		// create save plot action
		BoundAction saveAction = ActionFactory.createBoundAction("save-action",
				"Save", null);
		ActionFactory.decorateAction(saveAction, "Save Plot", "Save Plot",
				new ImageIcon("icon/save.png"), null, null);
		// add and register new plot action action
		actionManager.addAction(saveAction);
		actionManager.registerCallback("save-action",
				FileHandler.getInstance(), "savePlot");
		// disable action by default
		actionManager.setEnabled("save-action", false);

		// create save as plot action
		BoundAction saveAsAction = ActionFactory.createBoundAction(
				"save-as-action", "Save As...", null);
		ActionFactory.decorateAction(saveAsAction, "Save Plot As...",
				"Save Plot As...", new ImageIcon("icon/save-as.png"), null,
				null);
		// add and register new plot action action
		actionManager.addAction(saveAsAction);
		actionManager.registerCallback("save-as-action",
				FileHandler.getInstance(), "savePlotAs");
		// disable action by default
		actionManager.setEnabled("save-as-action", false);

		// create close plot action
		BoundAction closeAction = ActionFactory.createBoundAction(
				"close-action", "Close", null);
		ActionFactory.decorateAction(closeAction, "Close Plot", "Close Plot",
				new ImageIcon("icon/close.png"), null, null);
		// add and register close plot action
		actionManager.addAction(closeAction);
		actionManager.registerCallback("close-action",
				FileHandler.getInstance(), "closePlot");
		// disable action by default
		actionManager.setEnabled("close-action", false);

		// create close all plot action
		BoundAction closeAllAction = ActionFactory.createBoundAction(
				"close-all-action", "Close All", null);
		ActionFactory.decorateAction(closeAllAction, "Close All Plots",
				"Close All Plots", new ImageIcon("icon/close-all.png"), null,
				null);
		// add and register close all plot action
		actionManager.addAction(closeAllAction);
		actionManager.registerCallback("close-all-action",
				FileHandler.getInstance(), "closeAllPlots");
		// disable action by default
		actionManager.setEnabled("close-all-action", false);

		// create print plot action
		BoundAction printAction = ActionFactory.createBoundAction(
				"print-action", "Print", null);
		ActionFactory.decorateAction(printAction, "Print Plot", "Print Plot",
				new ImageIcon("icon/print.png"), null, null);
		// add and register print plot action
		actionManager.addAction(printAction);
		actionManager.registerCallback("print-action",
				FileHandler.getInstance(), "printPlot");
		// disable action by default
		actionManager.setEnabled("print-action", false);

		// create import action
		BoundAction importAction = ActionFactory.createBoundAction(
				"import-action", "Import", null);
		ActionFactory.decorateAction(importAction, "Import Data",
				"Import Data", new ImageIcon("icon/import.png"), null, null);
		// add and register export plot action
		actionManager.addAction(importAction);
		actionManager.registerCallback("import-action",
				FileHandler.getInstance(), "importData");
		// disable action by default
		actionManager.setEnabled("import-action", false);

		// create export plot action
		BoundAction exportAction = ActionFactory.createBoundAction(
				"export-action", "Export", null);
		ActionFactory.decorateAction(exportAction, "Export Plot",
				"Export Plot", new ImageIcon("icon/export.png"), null, null);
		// add and register export plot action
		actionManager.addAction(exportAction);
		actionManager.registerCallback("export-action",
				FileHandler.getInstance(), "exportPlot");
		// disable action by default
		actionManager.setEnabled("export-action", false);

		// create exit action
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
				"Undo Plot Edits", new ImageIcon("icon/undo.png"), null, null);
		// add and register undo action
		actionManager.addAction(undoAction);
		actionManager.registerCallback("undo-action",
				EditHandler.getInstance(), "undoEdit");
		// disable action by default
		actionManager.setEnabled("undo-action", false);

		// create redo action
		BoundAction redoAction = ActionFactory.createBoundAction("redo-action",
				"Redo", null);
		ActionFactory.decorateAction(redoAction, "Redo Plot Edits",
				"Redo Plot Edits", new ImageIcon("icon/redo.png"), null, null);
		// add and register redo action
		actionManager.addAction(redoAction);
		actionManager.registerCallback("redo-action",
				EditHandler.getInstance(), "redoEdit");
		// disable action by default
		actionManager.setEnabled("redo-action", false);

		// create preferences action
		BoundAction preferencesAction = ActionFactory.createBoundAction(
				"preferences-action", "Preferences", null);
		ActionFactory.decorateAction(preferencesAction, "Edit Preferences",
				"Edit Preferences", new ImageIcon("icon/preferences.png"),
				null, null);
		// add and register preferences action
		actionManager.addAction(preferencesAction);
		actionManager.registerCallback("preferences-action",
				EditHandler.getInstance(), "preferencesEdit");

		// create plot action
		BoundAction plotAction = ActionFactory.createBoundAction("plot-action",
				"Plot", null);
		ActionFactory.decorateAction(plotAction, null, null, null, null, null);
		actionManager.addAction(plotAction);
		// disable menu by default
		actionManager.setEnabled("plot-action", false);

		// create add data action
		BoundAction addDataAction = ActionFactory.createBoundAction(
				"add-data-action", "Add Data", null);
		ActionFactory.decorateAction(addDataAction, "Create new data item",
				"Create new data item", new ImageIcon("icon/add.png"), null,
				null);
		// add and register add data action
		actionManager.addAction(addDataAction);
		actionManager.registerCallback("add-data-action",
				PlotHandler.getInstance(), "addData");

		// create add data action
		BoundAction editDataAction = ActionFactory.createBoundAction(
				"edit-data-action", "Edit Data", null);
		ActionFactory.decorateAction(editDataAction, "Edit selected item",
				"Edit selected item", new ImageIcon("icon/edit.png"), null,
				null);
		// add and register add data action
		actionManager.addAction(editDataAction);
		actionManager.registerCallback("edit-data-action",
				PlotHandler.getInstance(), "editData");

		// create remove data action
		BoundAction removeDataAction = ActionFactory.createBoundAction(
				"remove-data-action", "Remove Data", null);
		ActionFactory.decorateAction(removeDataAction, "Remove selected data",
				"Remove selected data", new ImageIcon("icon/remove.png"), null,
				null);
		// add and register remove data action
		actionManager.addAction(removeDataAction);
		actionManager.registerCallback("remove-data-action",
				PlotHandler.getInstance(), "removeData");

		// create plot settings action
		BoundAction plotSettingsAction = ActionFactory.createBoundAction(
				"plot-settings-action", "Plot Settings", null);
		ActionFactory.decorateAction(plotSettingsAction, "Edit Plot Settings",
				"Edit Plot Settings", new ImageIcon("icon/plot-settings.png"),
				null, null);
		// add and register plot settings action
		actionManager.addAction(plotSettingsAction);
		actionManager.registerCallback("plot-settings-action",
				PlotHandler.getInstance(), "editPlotSettings");

		// create help action
		BoundAction helpAction = ActionFactory.createBoundAction("help-action",
				"Help", null);
		ActionFactory.decorateAction(helpAction, null, null, null, null, null);
		actionManager.addAction(helpAction);

		// create about action
		BoundAction aboutAction = ActionFactory.createBoundAction(
				"about-action", "About", null);
		ActionFactory.decorateAction(aboutAction, "View About",
				"View About Dialog", new ImageIcon("icon/about.png"), null,
				null);
		// add and register preferences action
		actionManager.addAction(aboutAction);
		actionManager.registerCallback("about-action",
				HelpHandler.getInstance(), "showAbout");

		// create action container factory
		ActionContainerFactory factory = new ActionContainerFactory(
				actionManager);

		// create menu bar
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		// create file menu
		String[] fileMenuItems = { "file-action", "new-action", "open-action",
				null, "save-action", "save-as-action", null, "close-action",
				"close-all-action", null, "print-action", null,
				"import-action", "export-action", null, "exit-action" };
		JMenu fileMenu = factory.createMenu(fileMenuItems);
		menuBar.add(fileMenu);
		// create edit menu
		String[] editMenuItems = { "edit-action", "undo-action", "redo-action",
				null, "preferences-action" };
		JMenu editMenu = factory.createMenu(editMenuItems);
		menuBar.add(editMenu);
		// create plot menu
		String[] plotMenuItems = { "plot-action", "add-data-action",
				"edit-data-action", "remove-data-action", null,
				"plot-settings-action" };
		JMenu plotMenu = factory.createMenu(plotMenuItems);
		menuBar.add(plotMenu);
		// create help menu
		String[] helpMenuItems = { "help-action", "about-action" };
		JMenu helpMenu = factory.createMenu(helpMenuItems);
		menuBar.add(helpMenu);

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
		tabPane.addChangeListener(new TabListener());
		mainPanel.add(tabPane, BorderLayout.CENTER);

		// pack main frame
		setSize(500, 400);

		// turn of the default window closing code
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Set the new frame location
		setLocation(100, 100);
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public JProgressBar getLoadingBar() {
		return loadingBar;
	}

	public JTabbedPane getTabPane() {
		return tabPane;
	}
}
