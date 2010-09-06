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
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class Plot extends JPanel implements Serializable {
	// serial version UID
	private static final long serialVersionUID = 43493445736684770L;
	// plot data
	private ArrayList<PlotData> plotDataList = new ArrayList<PlotData>();
	// plot file
	private File plotFile = null;
	// plot settings
	private PlotSettings plotSettings = null;
	// list of data types this plot supports
	private String[] dataTypes = {};

	public Plot() {
		setPlotSettings(new PlotSettings());
	}

	public void addData(PlotData plotDataItem) {
		// add data item to list
		if (plotDataItem != null) {
			plotDataList.add(plotDataItem);
		}
	}

	public abstract void drawAxis(Graphics g);

	public void drawData(Graphics g) {
		// draw data items on axis
		for (PlotData plotDataItem : plotDataList) {
			plotDataItem.draw(g);
		}
	}

	public ArrayList<PlotData> getPlotDataList() {
		return plotDataList;
	}

	public File getPlotFile() {
		return plotFile;
	}

	public PlotSettings getPlotSettings() {
		return plotSettings;
	}

	@Override
	public void paintComponent(Graphics g) {
		// draw plot
		drawAxis(g);
		drawData(g);
	}

	public void removeData(PlotData plotDataItem) {
		// remove data item from list
		if (plotDataItem != null) {
			plotDataList.remove(plotDataItem);
		}
	}

	public void setPlotFile(File plotFile) {
		this.plotFile = plotFile;
	}

	public void setPlotSettings(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void setDataTypes(String[] dataTypes) {
		this.dataTypes = dataTypes;
	}

	public String[] getDataTypes() {
		return dataTypes;
	}
}
