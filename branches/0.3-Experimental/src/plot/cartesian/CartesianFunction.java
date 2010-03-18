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

package plot.cartesian;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import net.smplmathparser.EvaluationTree;
import net.smplmathparser.MathParser;
import net.smplmathparser.MathParserException;
import plot.Axis;
import plot.PlotFunction;

public class CartesianFunction extends PlotFunction {
	private EvaluationTree functionTree = null;
	private double jumpSize = 100;
	private MathParser parser = new MathParser();

	public CartesianFunction(String funcStr) throws MathParserException {
		this.setFunctionString(funcStr);
		functionTree = parser.parse(this.getFunctionString());
	}

	@Override
	public void draw(Graphics g, Axis axis) {
		try {
			// call different draw method depending on type
			if (this.getDrawMethod() == PlotFunction.LINE_DRAW) {
				lineDraw(g, axis);
			} else if (this.getDrawMethod() == PlotFunction.POINT_DRAW) {
				pointDraw(g, axis);
			}
		} catch (MathParserException e) {
			e.printStackTrace();
		}
	}

	public double getJumpSize() {
		return jumpSize;
	}

	private void lineDraw(Graphics g, Axis axis) throws MathParserException {
		Graphics2D gc = (Graphics2D) g;
		// get Cartesian axis
		CartesianAxis caxis = (CartesianAxis) axis;
		// get plot settings
		CartesianSettings settings = (CartesianSettings) caxis
				.getPlotSettings();

		// set draw colour and style
		gc.setColor(this.getDrawColor());
		gc.setStroke(new BasicStroke(this.getDrawSize()));

		double xValue = settings.getxMin();
		double spacingSize = settings.getxSplitSize() / jumpSize;
		while (xValue <= settings.getxMax()) {
			// set x value
			if (!functionTree.isConstant()) {
				functionTree.setVariable("x", xValue);
			}
			// calculate y value
			double yValue = functionTree.evaluate();
			if (yValue >= settings.getyMin() && yValue <= settings.getyMax()) {
				// calculate first x draw point
				double drawXPoint1 = caxis.getxIndent()
						+ caxis.getxLowerSectionPad()
						+ ((xValue - settings.getxMin() - caxis.getxBelow()) / settings
								.getxSplitSize()) * caxis.getxSectionSize();
				int iDrawXPoint1 = (int) Math.round(drawXPoint1);

				// calculate first y draw point
				double drawYPoint1 = caxis.getyIndent()
						+ caxis.getyUpperSectionPad()
						+ ((settings.getyMax() - caxis.getyOver() - yValue) / settings
								.getySplitSize()) * caxis.getySectionSize();
				int iDrawYPoint1 = (int) Math.round(drawYPoint1);

				// increase i by spacing size
				xValue = xValue + spacingSize;
				// set second x value
				if (!functionTree.isConstant()) {
					functionTree.setVariable("x", xValue);
				}
				// calc second y value
				yValue = functionTree.evaluate();
				if (yValue >= settings.getyMin()
						&& yValue <= settings.getyMax()) {
					// calculate second x draw point
					double drawXPoint2 = caxis.getxIndent()
							+ caxis.getxLowerSectionPad()
							+ ((xValue - settings.getxMin() - caxis.getxBelow()) / settings
									.getxSplitSize()) * caxis.getxSectionSize();
					int iDrawXPoint2 = (int) Math.round(drawXPoint2);

					// calculate second y draw point
					double drawYPoint2 = caxis.getyIndent()
							+ caxis.getyUpperSectionPad()
							+ ((settings.getyMax() - caxis.getyOver() - yValue) / settings
									.getySplitSize()) * caxis.getySectionSize();
					int iDrawYPoint2 = (int) Math.round(drawYPoint2);

					// draw line between the two calculate points
					gc.drawLine(iDrawXPoint1, iDrawYPoint1, iDrawXPoint2,
							iDrawYPoint2);
				}
			} else {
				// increase i value
				xValue = xValue + spacingSize;
			}
		}
	}

	private void pointDraw(Graphics g, Axis axis) throws MathParserException {
		CartesianAxis caxis = (CartesianAxis) axis;
		// get plot settings
		CartesianSettings settings = (CartesianSettings) caxis
				.getPlotSettings();

		// set draw colour
		g.setColor(this.getDrawColor());

		// initialise i value
		double xValue = settings.getxMin() + caxis.getxBelow();
		while (xValue <= settings.getxMax()) {
			// set x value
			if (!functionTree.isConstant()) {
				functionTree.setVariable("x", xValue);
			}
			// calc y value
			double yValue = functionTree.evaluate();
			// draw point
			if (yValue >= settings.getyMin() && yValue <= settings.getyMax()) {
				// calculate x draw point
				double drawXPoint = caxis.getxIndent()
						+ caxis.getxLowerSectionPad()
						+ ((xValue - settings.getxMin() - caxis.getxBelow()) / settings
								.getxSplitSize()) * caxis.getxSectionSize();
				int iDrawXPoint = (int) Math.round(drawXPoint);

				// calculate y draw point
				double drawYPoint = caxis.getyIndent()
						+ caxis.getyUpperSectionPad()
						+ ((settings.getyMax() - caxis.getyOver() - yValue) / settings
								.getySplitSize()) * caxis.getySectionSize();
				int iDrawYPoint = (int) Math.round(drawYPoint);

				// draw point
				g.fillRect(iDrawXPoint - 2, iDrawYPoint - 2, 5, 5);
			}
			// increase i value
			xValue = xValue + settings.getxSplitSize();
		}
	}

	public void setJumpSize(double jumpSize) {
		this.jumpSize = jumpSize;
	}

	@Override
	public String toString() {
		return "XYFunction: " + this.getFunctionString();
	}
}
