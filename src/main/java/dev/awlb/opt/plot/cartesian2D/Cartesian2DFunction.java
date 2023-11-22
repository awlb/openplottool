package dev.awlb.opt.plot.cartesian2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import dev.awlb.opt.plot.Plot;
import dev.awlb.opt.plot.PlotData;
import net.smplmathparser.EvaluationTree;
import net.smplmathparser.MathParser;
import net.smplmathparser.MathParserException;

public class Cartesian2DFunction extends PlotData {
	public static final int LINE_DRAW = 0;
	public static final int POINT_DRAW = 1;
	private static final long serialVersionUID = 3333435371909369250L;
	private int drawMethod = LINE_DRAW;
	private int drawSize = 1;
	private String functionString = "y=x";
	private EvaluationTree functionTree = null;
	private double jumpSize = 100;
	private MathParser parser = new MathParser();

	public Cartesian2DFunction(Plot parentPlot) throws MathParserException {
		super(parentPlot);
		this.setDataType("Function");
		functionTree = parser.parse(functionString);
	}

	@Override
	public void draw(Graphics g) {
		try {
			// call different draw method depending on type
			if (drawMethod == LINE_DRAW) {
				lineDraw(g, (Cartesian2D) this.getParentPlot());
			} else if (drawMethod == POINT_DRAW) {
				pointDraw(g, (Cartesian2D) this.getParentPlot());
			}
		} catch (MathParserException e) {
			e.printStackTrace();
		}
	}

	public int getDrawMethod() {
		return drawMethod;
	}

	public int getDrawSize() {
		return drawSize;
	}

	public String getFunctionString() {
		return functionString;
	}

	private void lineDraw(Graphics g, Cartesian2D plot)
			throws MathParserException {
		Graphics2D gc = (Graphics2D) g;
		// get plot settings
		Cartesian2DSettings settings = (Cartesian2DSettings) plot
				.getPlotSettings();

		// set draw colour and style
		gc.setColor(Color.RED);
		gc.setStroke(new BasicStroke(drawSize));

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
				double drawXPoint1 = plot.getxIndent()
						+ plot.getxLowerSectionPad()
						+ ((xValue - settings.getxMin() - plot.getxBelow()) / settings
								.getxSplitSize()) * plot.getxSectionSize();
				int iDrawXPoint1 = (int) Math.round(drawXPoint1);

				// calculate first y draw point
				double drawYPoint1 = plot.getyIndent()
						+ plot.getyUpperSectionPad()
						+ ((settings.getyMax() - plot.getyOver() - yValue) / settings
								.getySplitSize()) * plot.getySectionSize();
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
					double drawXPoint2 = plot.getxIndent()
							+ plot.getxLowerSectionPad()
							+ ((xValue - settings.getxMin() - plot.getxBelow()) / settings
									.getxSplitSize()) * plot.getxSectionSize();
					int iDrawXPoint2 = (int) Math.round(drawXPoint2);

					// calculate second y draw point
					double drawYPoint2 = plot.getyIndent()
							+ plot.getyUpperSectionPad()
							+ ((settings.getyMax() - plot.getyOver() - yValue) / settings
									.getySplitSize()) * plot.getySectionSize();
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

	private void pointDraw(Graphics g, Cartesian2D plot)
			throws MathParserException {
		// get plot settings
		Cartesian2DSettings settings = (Cartesian2DSettings) plot
				.getPlotSettings();

		// set draw colour TODO look at this
		g.setColor(Color.RED);

		// initialise i value
		double xValue = settings.getxMin() + plot.getxBelow();
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
				double drawXPoint = plot.getxIndent()
						+ plot.getxLowerSectionPad()
						+ ((xValue - settings.getxMin() - plot.getxBelow()) / settings
								.getxSplitSize()) * plot.getxSectionSize();
				int iDrawXPoint = (int) Math.round(drawXPoint);

				// calculate y draw point
				double drawYPoint = plot.getyIndent()
						+ plot.getyUpperSectionPad()
						+ ((settings.getyMax() - plot.getyOver() - yValue) / settings
								.getySplitSize()) * plot.getySectionSize();
				int iDrawYPoint = (int) Math.round(drawYPoint);

				// draw point
				g.fillRect(iDrawXPoint - 2, iDrawYPoint - 2, 5, 5);
			}
			// increase i value
			xValue = xValue + settings.getxSplitSize();
		}
	}

	public void setDrawMethod(int drawMethod) {
		this.drawMethod = drawMethod;
	}

	public void setDrawSize(int drawSize) {
		this.drawSize = drawSize;
	}

	public void setFunctionString(String functionString)
			throws MathParserException {
		this.functionString = functionString;
		functionTree = parser.parse(functionString);
	}

	public void setJumpSize(double jumpSize) {
		this.jumpSize = jumpSize;
	}

	@Override
	public String toString() {
		return "<html><font color='#ff0000'>" + functionString
				+ "</font></html>";
	}
}
