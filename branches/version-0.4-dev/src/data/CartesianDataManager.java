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

package data;

import main.OpenPlotTool;
import net.smplmathparser.MathParserException;
import plot.PlotDataSet;
import plot.PlotFunction;
import plot.PlotPage;
import plot.PlotPoint;
import plot.cartesian.CartesianDataSet;
import plot.cartesian.CartesianFunction;
import plot.cartesian.CartesianPoint;
import exceptions.NullDataValueException;
import gui.cartesian.CartesianDataDialog;
import gui.cartesian.CartesianFunctionDialog;
import gui.cartesian.CartesianPointDialog;

public class CartesianDataManager {
	public static void addDataSet(PlotPage page) throws NumberFormatException,
			NullDataValueException {
		/*
		 * // create and display a NewCartesianDataDialog CartesianDataDialog
		 * dialog = new CartesianDataDialog(null); dialog.setVisible(true); if
		 * (dialog.getPressed() == CartesianDataDialog.ADD_PRESSED) { // get new
		 * cartesian data set CartesianDataSet dataSet = dialog.getDataSet(); //
		 * add data set to page list and gui list page.addData(dataSet);
		 * OpenPlotTool.getMainFrame().getDataSetListModel().addElement(
		 * dataSet); // repaint page page.repaint(); } dialog.dispose();
		 */
	}

	public static void addFunction(PlotPage page)
			throws NullDataValueException, MathParserException {
		/*
		 * // create and display a NewCartesianFunctionDialog
		 * CartesianFunctionDialog dialog = new CartesianFunctionDialog(null);
		 * dialog.setVisible(true); if (dialog.getPressed() ==
		 * CartesianFunctionDialog.ADD_PRESSED) { // get new cartesian function
		 * CartesianFunction function = dialog.getFunction(); // add function to
		 * page list and gui list page.addFunction(function);
		 * OpenPlotTool.getMainFrame().getFunctionListModel().addElement(
		 * function); // repaint page page.repaint(); } dialog.dispose();
		 */
	}

	public static void addPoint(PlotPage page) throws NumberFormatException {
		/*
		 * // create and display a NewCartesianPointDialog CartesianPointDialog
		 * dialog = new CartesianPointDialog(null); dialog.setVisible(true); if
		 * (dialog.getPressed() == CartesianPointDialog.ADD_PRESSED) { // get
		 * new cartesian point CartesianPoint point = dialog.getPoint(); // add
		 * point to page list and gui list page.addPoint(point);
		 * OpenPlotTool.getMainFrame().getPointListModel().addElement(point); //
		 * repaint page page.repaint(); } dialog.dispose();
		 */
	}

	public static void editDataSet(PlotPage page, int selectedIndex)
			throws NumberFormatException, NullDataValueException {
		/*
		 * // get selected data set CartesianDataSet currentDataSet =
		 * (CartesianDataSet) OpenPlotTool
		 * .getMainFrame().getDataSetList().getSelectedValue(); if
		 * (currentDataSet != null) { // create and display a
		 * EditCartesianDataDialog CartesianDataDialog dialog = new
		 * CartesianDataDialog(currentDataSet); dialog.setVisible(true); if
		 * (dialog.getPressed() == CartesianFunctionDialog.ADD_PRESSED) { // get
		 * new cartesian data set CartesianDataSet dataSet =
		 * dialog.getDataSet(); // update data set in lists
		 * page.getPlotData().set(selectedIndex, dataSet);
		 * OpenPlotTool.getMainFrame().getDataSetListModel()
		 * .removeElement(currentDataSet);
		 * OpenPlotTool.getMainFrame().getDataSetListModel().add( selectedIndex,
		 * dataSet); // repaint page page.repaint();
		 * 
		 * } dialog.dispose(); }
		 */
	}

	public static void editFunction(PlotPage page, int selectedIndex)
			throws MathParserException, NullDataValueException {
		/*
		 * // get selected function CartesianFunction currentFunction =
		 * (CartesianFunction) OpenPlotTool
		 * .getMainFrame().getFunctionList().getSelectedValue(); if
		 * (currentFunction != null) { // create and display a
		 * EditCartesianFunctionDialog CartesianFunctionDialog dialog = new
		 * CartesianFunctionDialog( currentFunction); dialog.setVisible(true);
		 * if (dialog.getPressed() == CartesianFunctionDialog.ADD_PRESSED) { //
		 * get new cartesian function CartesianFunction newFunction =
		 * dialog.getFunction(); // update function in lists int curIndex =
		 * page.getPlotFunctions().indexOf(currentFunction);
		 * page.getPlotFunctions().set(curIndex, newFunction);
		 * OpenPlotTool.getMainFrame().getFunctionListModel()
		 * .removeElement(currentFunction);
		 * OpenPlotTool.getMainFrame().getFunctionListModel().add(
		 * selectedIndex, newFunction); // repaint page page.repaint(); }
		 * dialog.dispose(); }
		 */
	}

	public static void editPoint(PlotPage page, int selectedIndex)
			throws NumberFormatException {
		/*
		 * // get selected point CartesianPoint currentPoint = (CartesianPoint)
		 * OpenPlotTool .getMainFrame().getPointList().getSelectedValue(); if
		 * (currentPoint != null) { // create and display a
		 * EditCartesianPointDialog CartesianPointDialog dialog = new
		 * CartesianPointDialog(currentPoint); dialog.setVisible(true); if
		 * (dialog.getPressed() == CartesianFunctionDialog.ADD_PRESSED) { // get
		 * new cartesian point CartesianPoint newPoint = dialog.getPoint(); //
		 * update point in lists int curIndex =
		 * page.getPlotPoints().indexOf(currentPoint);
		 * page.getPlotPoints().set(curIndex, newPoint);
		 * OpenPlotTool.getMainFrame().getPointListModel().removeElement(
		 * currentPoint); OpenPlotTool.getMainFrame().getPointListModel().add(
		 * selectedIndex, newPoint); // repaint page page.repaint();
		 * 
		 * } dialog.dispose(); }
		 */
	}

	public static void removeDataSet(PlotPage page) {
		/*
		 * // get selected data set PlotDataSet dataSet = (PlotDataSet)
		 * OpenPlotTool.getMainFrame() .getDataSetList().getSelectedValue(); if
		 * (dataSet != null) { // remove data set from lists
		 * OpenPlotTool.getMainFrame().getDataSetListModel().removeElement(
		 * dataSet); page.removeData(dataSet); // repaint page page.repaint(); }
		 */
	}

	public static void removeFunction(PlotPage page) {
		/*
		 * // get selected function PlotFunction function = (PlotFunction)
		 * OpenPlotTool.getMainFrame() .getFunctionList().getSelectedValue(); if
		 * (function != null) { // remove function from lists
		 * OpenPlotTool.getMainFrame().getFunctionListModel().removeElement(
		 * function); page.removeFunction(function); // repaint page
		 * page.repaint(); }
		 */
	}

	public static void removePoint(PlotPage page) {
		/*
		 * // get selected point PlotPoint point = (PlotPoint)
		 * OpenPlotTool.getMainFrame() .getPointList().getSelectedValue(); if
		 * (point != null) { // remove function from lists
		 * OpenPlotTool.getMainFrame().getPointListModel()
		 * .removeElement(point); page.removePoint(point); // repaint page
		 * page.repaint(); }
		 */
	}
}
