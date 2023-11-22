package dev.awlb.opt.plot;

import java.awt.Graphics;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class Plot extends JPanel implements Serializable {
	// serial version UID
	private static final long serialVersionUID = 43493445736684770L;
	// list of data types this plot supports
	private String[] dataTypes = {};
	// edited status
	private boolean edited = false;
	// plot data
	private ArrayList<PlotData> plotDataList = new ArrayList<PlotData>();
	// plot file
	private File plotFile = null;
	// plot type name
	String plotType = "default";

	public Plot() {

	}

	public void addData(PlotData plotDataItem) {
		// add data item to list
		if (plotDataItem != null) {
			plotDataList.add(plotDataItem);
			// update edited status
			edited = true;
		}
	}

	public abstract PlotData createData(String type) throws Exception;

	public abstract void drawAxis(Graphics g);

	public void drawData(Graphics g) {
		// draw data items on axis
		for (PlotData plotDataItem : plotDataList) {
			plotDataItem.draw(g);
		}
	}

	public abstract PlotData editData(PlotData plotData) throws Exception;

	public abstract void editPlotSettings();

	public String[] getDataTypes() {
		return dataTypes;
	}

	public ArrayList<PlotData> getPlotDataList() {
		return plotDataList;
	}

	public File getPlotFile() {
		return plotFile;
	}

	public String getPlotType() {
		return plotType;
	}

	public boolean isEdited() {
		return edited;
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
			// update edited status
			edited = true;
		}
	}

	public void replaceData(PlotData oldData, PlotData newData) {
		// replace old plot data with new one
		int oldIndex = plotDataList.indexOf(oldData);
		if (oldIndex > -1) {
			plotDataList.set(oldIndex, newData);
		}
	}

	public void setDataTypes(String[] dataTypes) {
		this.dataTypes = dataTypes;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public void setPlotFile(File plotFile) {
		this.plotFile = plotFile;
	}

	public void setPlotType(String plotType) {
		this.plotType = plotType;
	}
}
