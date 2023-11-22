package dev.awlb.opt;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;

import dev.awlb.opt.gui.MainFrame;
import dev.awlb.opt.handlers.StrokeTypeHandler;
import org.xml.sax.SAXException;

public class OpenPlotTool {

	// openplot tool instance
	private static OpenPlotTool instance = null;

	public static OpenPlotTool getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		instance = new OpenPlotTool();
	}

	// main frame
	private MainFrame mainFrame = null;

	protected OpenPlotTool() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// create gui
				mainFrame = new MainFrame();
				// add action listener

				mainFrame.setVisible(true);
			}
		});
		// load strokes from XML
		try {
			StrokeTypeHandler.loadStrokeTypes();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}
}
