package gui;

import handlers.ExitHandler;
import handlers.FileHandler;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.action.ActionContainerFactory;
import org.jdesktop.swingx.action.ActionFactory;
import org.jdesktop.swingx.action.ActionManager;
import org.jdesktop.swingx.action.BoundAction;

@SuppressWarnings("serial")
public class MainFrame extends JXFrame {
	// program tool bar
	private JToolBar toolBar;
	// menu bar
	private JMenuBar menuBar;

	public MainFrame() {
		super("OpenPlotTool 0.4");
		// create action manager
		ActionManager actionManager = ActionManager.getInstance();
		// create file action handler
		FileHandler fileActionHandler = new FileHandler();
		ExitHandler exitHandler = new ExitHandler();
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
		actionManager.registerCallback("new-action", fileActionHandler,
				"newPlot");

		// create open plot action
		BoundAction openAction = ActionFactory.createBoundAction("open-action",
				"Open", null);
		ActionFactory.decorateAction(openAction, "Open Plot File",
				"Open Plot File", new ImageIcon("icon/document-open.png"),
				null, null);
		// add and register new plot action action
		actionManager.addAction(openAction);
		actionManager.registerCallback("open-action", fileActionHandler,
				"openPlot");

		// create save plot action
		BoundAction saveAction = ActionFactory.createBoundAction("save-action",
				"Save", null);
		ActionFactory.decorateAction(saveAction, "Save Plot", "Save Plot",
				new ImageIcon("icon/document-save.png"), null, null);
		// add and register new plot action action
		actionManager.addAction(saveAction);
		actionManager.registerCallback("save-action", fileActionHandler,
				"savePlot");

		// create save as plot action
		BoundAction saveAsAction = ActionFactory.createBoundAction(
				"save-as-action", "Save As...", null);
		ActionFactory.decorateAction(saveAsAction, "Save Plot As...",
				"Save Plot As...", new ImageIcon("icon/document-save-as.png"),
				null, null);
		// add and register new plot action action
		actionManager.addAction(saveAsAction);
		actionManager.registerCallback("save-as-action", fileActionHandler,
				"savePlotAs");

		// create close plot action
		BoundAction closeAction = ActionFactory.createBoundAction(
				"close-action", "Close", null);
		ActionFactory.decorateAction(closeAction, "Close Plot", "Close Plot",
				null, null, null);
		// add and register close plot action
		actionManager.addAction(closeAction);
		actionManager.registerCallback("close-action", fileActionHandler,
				"closePlot");

		// create close all plot action
		BoundAction closeAllAction = ActionFactory.createBoundAction(
				"close-all-action", "Close All", null);
		ActionFactory.decorateAction(closeAllAction, "Close All Plots",
				"Close All Plots", null, null, null);
		// add and register close all plot action
		actionManager.addAction(closeAllAction);
		actionManager.registerCallback("close-all-action", fileActionHandler,
				"closeAllPlots");

		// create print plot action
		BoundAction printAction = ActionFactory.createBoundAction(
				"print-action", "Print", null);
		ActionFactory.decorateAction(printAction, "Print Plot", "Print Plot",
				new ImageIcon("icon/document-print.png"), null, null);
		// add and register print plot action
		actionManager.addAction(printAction);
		actionManager.registerCallback("print-action", fileActionHandler,
				"printPlot");

		// create export plot action
		BoundAction exportAction = ActionFactory.createBoundAction(
				"export-action", "Export", null);
		ActionFactory.decorateAction(exportAction, "Export Plot",
				"Export Plot", new ImageIcon("icon/document-export.png"), null,
				null);
		// add and register export plot action
		actionManager.addAction(exportAction);
		actionManager.registerCallback("export-action", fileActionHandler,
				"exportPlot");

		// create export plot action
		BoundAction exitAction = ActionFactory.createBoundAction("exit-action",
				"Exit", null);
		ActionFactory.decorateAction(exitAction, "Exit Program",
				"Exit Program", new ImageIcon("icon/exit.png"), null, null);
		// add and register exit action
		actionManager.addAction(exitAction);
		actionManager.registerCallback("exit-action", exitHandler,
				"exitProgram");

		// create action container factory
		ActionContainerFactory factory = new ActionContainerFactory(
				actionManager);

		// create menu bar
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		// create file menu
		String[] fileMenuItems = { "file-action", "new-action", "open-action",
				null, "save-action", "save-as-action", null, "close-action",
				"close-all-action", null, "print-action", null,
				"export-action", null, "exit-action" };
		JMenu fileMenu = factory.createMenu(fileMenuItems);
		menuBar.add(fileMenu);

		// create tool bar
		String[] toolBarItems = { "new-action", "open-action", "save-action",
				null, "print-action" };
		toolBar = factory.createToolBar(toolBarItems);
		toolBar.setFloatable(false);
		this.setToolBar(toolBar);
		// add tool bar actions

		// pack main frame
		pack();

		// turn of the default window closing code
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		// Calculate the frame location
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;

		// Set the new frame location
		setLocation(x, y);
	}
}
