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

package plot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlotPage extends JPanel implements Printable {
	// axis
	private Axis axis = null;
	// page file
	private File pageFile = null;
	// plot data
	private ArrayList<PlotDataSet> plotData = new ArrayList<PlotDataSet>();
	// plot functions
	private ArrayList<PlotFunction> plotFunctions = new ArrayList<PlotFunction>();
	// plot points
	private ArrayList<PlotPoint> plotPoints = new ArrayList<PlotPoint>();
	// page settings
	private PageSettings settings = null;
	// page type
	private String type = null;

	public PlotPage(Axis inAxis) {
		settings = new PageSettings();
		axis = inAxis;
		axis.setParentPage(this);
	}

	public void addData(PlotDataSet inData) {
		plotData.add(inData);
	}

	public void addFunction(PlotFunction inFunction) {
		plotFunctions.add(inFunction);
	}

	public void addPoint(PlotPoint inPoint) {
		plotPoints.add(inPoint);
	}

	public Axis getAxis() {
		return axis;
	}

	public File getPageFile() {
		return pageFile;
	}

	public ArrayList<PlotDataSet> getPlotData() {
		return plotData;
	}

	public ArrayList<PlotFunction> getPlotFunctions() {
		return plotFunctions;
	}

	public ArrayList<PlotPoint> getPlotPoints() {
		return plotPoints;
	}

	public PageSettings getSettings() {
		return settings;
	}

	public String getType() {
		return type;
	}

	@Override
	public void paintComponent(Graphics g) {
		plotRender(g);
	}

	// method to render plot
	public void plotRender(Graphics g) {
		// only draw when draw area has a size
		if (this.getWidth() > 0 & this.getHeight() > 0) {
			// draw background
			g.setColor(settings.getBackgroundColor());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			axis.draw(g, this.getWidth(), this.getHeight());
			// draw data sets
			for (PlotDataSet data : plotData) {
				data.draw(g, axis);
			}
			// draw points
			for (PlotPoint point : plotPoints) {
				point.draw(g, axis);
			}
			// draw functions
			for (PlotFunction function : plotFunctions) {
				function.draw(g, axis);
			}
		}
	}

	public void removeData(PlotDataSet inData) {
		plotData.remove(inData);
	}

	public void removeFunction(PlotFunction inFunction) {
		plotFunctions.remove(inFunction);
	}

	public void removePoint(PlotPoint inPoint) {
		plotPoints.remove(inPoint);
	}

	public void setPageFile(File pageFile) {
		this.pageFile = pageFile;
	}

	public void setSettings(PageSettings settings) {
		this.settings = settings;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		if (pageIndex >= 1) {
			return Printable.NO_SUCH_PAGE;
		}
		Graphics2D g2 = (Graphics2D) graphics;
		// translate image to correct place on page
		g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		paint(g2);
		return Printable.PAGE_EXISTS;
	}
}
