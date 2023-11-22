package dev.awlb.opt.handlers;

import dev.awlb.opt.OpenPlotTool;
import dev.awlb.opt.gui.AddDataDialog;
import dev.awlb.opt.plot.Plot;
import dev.awlb.opt.plot.PlotData;
import dev.awlb.opt.view.PlotView;
import dev.awlb.opt.view.ViewManager;

import javax.swing.JOptionPane;

public class PlotHandler {

	private static PlotHandler instance = null;

	public static PlotHandler getInstance() {
		if (instance == null) {
			instance = new PlotHandler();
		}
		return instance;
	}

	protected PlotHandler() {

	}

	public void addData() {
		// get selected page
		PlotView selectedView = ViewManager.getSelectedView();
		if (selectedView != null) {
			Plot selectedPlot = ViewManager.getSelectedPlot();
			// display add data dialog
			AddDataDialog addDataDialog = new AddDataDialog(selectedPlot);
			addDataDialog.setVisible(true);
			if (addDataDialog.getPressed() == AddDataDialog.OK_PRESSED) {
				// create new data of selected type
				String dataType = addDataDialog.getSelected();
				try {
					PlotData newData = selectedPlot.createData(dataType);
					if (newData != null) {
						selectedView.addData(newData);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(OpenPlotTool.getInstance()
							.getMainFrame(), "Failed to create data of type: "
							+ dataType, "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
			addDataDialog.dispose();
		}
	}

	public void editData() {
		// get selected data
		PlotView selectedView = ViewManager.getSelectedView();
		if (selectedView != null) {
			PlotData selectedData = selectedView.getSelectedData();
			if (selectedData != null) {
				try {
					PlotData newPlotData = selectedView.getPlot().editData(
							selectedData);
					if (newPlotData != null) {
						// replace data in view
						selectedView.replaceData(selectedData, newPlotData);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(
							OpenPlotTool.getInstance().getMainFrame(),
							"Failed to edit plot data of type: "
									+ selectedData.getDataType(), "Error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
	}

	public void editPlotSettings() {
		// get selected page
		PlotView selectedView = ViewManager.getSelectedView();
		if (selectedView != null) {
			Plot selectedPlot = ViewManager.getSelectedPlot();
			selectedPlot.editPlotSettings();
		}
	}

	public void removeData() {
		PlotView selectedView = ViewManager.getSelectedView();
		if (selectedView != null) {
			// get selected data
			PlotData selectedData = selectedView.getSelectedData();
			if (selectedData != null) {
				// remove data from view
				selectedView.removeData(selectedData);
			}
		}
	}
}
