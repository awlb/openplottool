package dev.awlb.opt.plot.cartesian2D;

import dev.awlb.opt.Rounder;
import dev.awlb.opt.handlers.StrokeTypeHandler;
import dev.awlb.opt.plot.Plot;
import dev.awlb.opt.plot.PlotData;
import net.smplmathparser.MathParserException;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cartesian2D extends Plot {
	// serial UID
	private static final long serialVersionUID = 4025965656042258986L;
	private Cartesian2DSettings plotSettings;
	// axis values
	private int xIndent, yIndent;
	private int xLowerSectionPad;
	private double xOver, xBelow, yOver, yBelow;
	private int xSectionSize;
	private int ySectionSize;
	private int yUpperSectionPad;

	public Cartesian2D() {
		super();
		// set plot type
		this.setPlotType("Cartesian 2D");
		// create and set plot settings instance
		plotSettings = new Cartesian2DSettings();
		// set data types for Cartesian
		String[] dataTypes = { "Data Set", "Function", "Point" };
		this.setDataTypes(dataTypes);
	}

	@Override
	public PlotData createData(String type) throws Exception {
		if ("Data Set".equals(type)) {
			return createDataSet();
		} else if ("Point".equals(type)) {
			return createPoint();
		} else if ("Function".equals(type)) {
			try {
				return createFunction();
			} catch (MathParserException e) {
				throw new Exception("Invalid data type.");
			}
		} else {
			throw new Exception("Invalid data type.");
		}
	}

	private Cartesian2DDataSet createDataSet() throws Exception {
		Cartesian2DDataSet newDataSet = new Cartesian2DDataSet(this);
		DataSetDialog dataSetDialog = new DataSetDialog(newDataSet, false);
		dataSetDialog.setVisible(true);
		if (dataSetDialog.getPressed() == DataSetDialog.UPDATE_PRESSED) {
			return dataSetDialog.getDataSet();
		} else {
			return null;
		}
	}

	private Cartesian2DFunction createFunction() throws MathParserException {
		Cartesian2DFunction newFunction = new Cartesian2DFunction(this);
		FunctionDialog functionDialog = new FunctionDialog(newFunction, false);
		functionDialog.setVisible(true);
		if (functionDialog.getPressed() == FunctionDialog.UPDATE_PRESSED) {
			return functionDialog.getFunction();
		} else {
			return null;
		}
	}

	private Cartesian2DPoint createPoint() {
		Cartesian2DPoint newPoint = new Cartesian2DPoint(this);
		PointDialog pointDialog = new PointDialog(newPoint, false);
		pointDialog.setVisible(true);
		if (pointDialog.getPressed() == DataSetDialog.UPDATE_PRESSED) {
			return pointDialog.getPoint();
		} else {
			return null;
		}
	}

	@Override
	public void drawAxis(Graphics g) {
		Graphics2D gc = (Graphics2D) g;
		// set anti aliasing mode TODO implement this

		// Calculate indents
		xIndent = g.getFontMetrics().stringWidth(
				"-" + plotSettings.getNumXSplitSize());
		yIndent = g.getFontMetrics().getHeight() * 2;

		// calculate bounds
		int width = getWidth() - (2 * xIndent);
		int height = getHeight() - (2 * yIndent);

		// calculate x
		// calculate x axis range
		double xRange = plotSettings.getxMax() + (-plotSettings.getxMin());
		// calculate number of sections axis is split by
		double xSectionNumber = xRange / plotSettings.getxSplitSize();
		// calculate the size of these sections in pixels
		xSectionSize = (int) (width / xSectionNumber);
		// calculate the amount the x axis extends over the number of whole
		// sections
		xOver = Math.abs(plotSettings.getxMax() % plotSettings.getxSplitSize());
		// calculate the amount the x axis extends below the number of whole
		// sections
		xBelow = Math
				.abs(plotSettings.getxMin() % plotSettings.getxSplitSize());
		// calculate the number of whole sections on the x axis below 0
		int xLowerSectionNum = (int) -(plotSettings.getxMin() / plotSettings
				.getxSplitSize());
		// calculate the number of whole sections on the x axis above 0
		int xUpperSectionNum = (int) (plotSettings.getxMax() / plotSettings
				.getxSplitSize());
		// total the amounts of whole sections
		int xWholeSectionNum = xLowerSectionNum + xUpperSectionNum;
		// calculate space left for non whole sections
		int xPartSpace = width - (xWholeSectionNum * xSectionSize);
		xLowerSectionPad = 0;
		int xUpperSectionPad = 0;
		/*
		 * Check if there are actual any part sections that the size must be
		 * calculated for It is done in this way due to rounding errors of
		 * simply using %
		 */
		if (Rounder.roundCheck(xUpperSectionNum,
				Rounder.getNumberOfDecimals(plotSettings.getxSplitSize()))
				|| Rounder.roundCheck(xLowerSectionNum, Rounder
						.getNumberOfDecimals(plotSettings.getxSplitSize()))) {
			// calculate size of these part sections
			double xTotalOverRange = xOver + xBelow;
			xLowerSectionPad = (int) Math.abs(xPartSpace
					* (xBelow / xTotalOverRange));
			xUpperSectionPad = (int) Math.abs(xPartSpace
					* (xOver / xTotalOverRange));
		}

		// calc x padding
		int xPadding = width
				- (xLowerSectionPad + xUpperSectionPad + (xWholeSectionNum * xSectionSize));

		// calc y
		// calculate y axis range
		double yRange = plotSettings.getyMax() + (-plotSettings.getyMin());
		// calculate number of sections axis is split by
		double ySectionNumber = yRange / plotSettings.getySplitSize();
		// calculate the size of these sections in pixels
		ySectionSize = (int) (height / ySectionNumber);
		// calculate the amount the y axis extends over the number of whole
		// sections
		yOver = Math.abs(plotSettings.getyMax() % plotSettings.getySplitSize());
		// calculate the amount the y axis extends below the number of whole
		// sections
		yBelow = Math
				.abs(plotSettings.getyMin() % plotSettings.getySplitSize());
		// calculate the number of whole sections on the y axis below 0
		int yLowerSectionNum = (int) -(plotSettings.getyMin() / plotSettings
				.getySplitSize());
		// calculate the number of whole sections on the y axis above 0
		int yUpperSectionNum = (int) (plotSettings.getyMax() / plotSettings
				.getySplitSize());
		// total the amounts of whole sections
		int yWholeSectionNum = yLowerSectionNum + yUpperSectionNum;
		// calculate space left for non whole sections
		int yPartSpace = height - (yWholeSectionNum * ySectionSize);
		int yLowerSectionPad = 0;
		yUpperSectionPad = 0;
		/*
		 * Check if there are actual any part sections that the size must be
		 * calculated for It is done in this way due to rounding errors of
		 * simply using %
		 */
		if (Rounder.roundCheck(yUpperSectionNum,
				Rounder.getNumberOfDecimals(plotSettings.getySplitSize()))
				|| Rounder.roundCheck(yLowerSectionNum, Rounder
						.getNumberOfDecimals(plotSettings.getySplitSize()))) {
			// calculate size of these part sections
			double yTotalOverRange = yOver + yBelow;
			yLowerSectionPad = (int) Math.abs(yPartSpace
					* (yBelow / yTotalOverRange));
			yUpperSectionPad = (int) Math.abs(yPartSpace
					* (yOver / yTotalOverRange));
		}
		// calc y padding
		int yPadding = height
				- (yLowerSectionPad + yUpperSectionPad + (yWholeSectionNum * ySectionSize));

		// draw page background
		gc.setColor(plotSettings.getBgColor());
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
		// draw grid background
		gc.setColor(plotSettings.getGridBGColor());
		gc.fillRect(xIndent, yIndent, width - xPadding, height - yPadding);

		// set settings for sub grid
		gc.setColor(plotSettings.getSubGridColor());
		gc.setStroke(StrokeTypeHandler.getStrokeType(
				plotSettings.getSubGridType()).getStroke());

		// draw x sub grid lines
		for (int i = 0; i <= xWholeSectionNum; i++) {
			double num = (-xLowerSectionNum * plotSettings.getxSplitSize())
					+ (i * plotSettings.getxSplitSize());
			if (num != 0) {
				// check if this line should be drawn
				if (!Rounder.roundCheck(num / plotSettings.getNumXSplitSize(),
						1)) {
					// draw line if required
					int xPoint = xIndent + xLowerSectionPad
							+ (i * xSectionSize);
					gc.drawLine(xPoint, yIndent, xPoint, (height - yPadding)
							+ yIndent);
				}
			}
		}

		// draw y sub grid lines
		for (int i = 0; i <= yWholeSectionNum; i++) {
			double num = (yUpperSectionNum * plotSettings.getySplitSize())
					- (i * plotSettings.getySplitSize());
			if (num != 0) {
				// check if this line should be drawn
				if (!Rounder.roundCheck(num / plotSettings.getNumYSplitSize(),
						1)) {
					// draw line if required
					int yPoint = yIndent + yUpperSectionPad
							+ (i * ySectionSize);
					gc.drawLine(xIndent, yPoint, (width - xPadding) + xIndent,
							yPoint);
				}
			}
		}

		// draw x number grid lines and numbers
		for (int i = 0; i <= xWholeSectionNum; i++) {
			double num = (-xLowerSectionNum * plotSettings.getxSplitSize())
					+ (i * plotSettings.getxSplitSize());
			if (num != 0) {
				// check if this number should be shown
				if (Rounder
						.roundCheck(num / plotSettings.getNumXSplitSize(), 1)) {
					// round number to required decimal places for plotting
					num = Rounder.Round(num, Rounder
							.getNumberOfDecimals(plotSettings
									.getNumXSplitSize()));
					// calculate x draw position for this grid line and number
					int xPos = xIndent + xLowerSectionPad + (i * xSectionSize);
					// set settings for grid
					gc.setColor(plotSettings.getMainGridColor());
					gc.setStroke(StrokeTypeHandler.getStrokeType(
							plotSettings.getMainGridDrawType()).getStroke());
					// draw grid line
					gc.drawLine(xPos, yIndent, xPos, (height - yPadding)
							+ yIndent);
					// calculate y draw position for number
					int numStringWidth = gc.getFontMetrics().stringWidth(
							"" + num);
					int numStringHeight = gc.getFontMetrics().getHeight();
					int yPos;
					if (plotSettings.getxMin() > 0) {
						yPos = yIndent + height + numStringHeight;
					} else if (plotSettings.getxMax() < 0) {
						yPos = yIndent - 2;
					} else {
						yPos = yIndent + yUpperSectionPad
								+ (yUpperSectionNum * ySectionSize)
								+ numStringHeight;
					}

					// draw number
					gc.setColor(plotSettings.getTextColor());
					gc.drawString("" + num, xPos - (numStringWidth / 2), yPos);
				}
			}
		}

		// draw y number grid lines and numbers
		for (int i = 0; i <= yWholeSectionNum; i++) {
			double num = (yUpperSectionNum * plotSettings.getySplitSize())
					- (i * plotSettings.getySplitSize());
			if (num != 0) {
				// check if this number should be shown
				if (Rounder
						.roundCheck(num / plotSettings.getNumYSplitSize(), 1)) {
					// round number to required decimal places for plotting
					num = Rounder.Round(num, Rounder
							.getNumberOfDecimals(plotSettings
									.getNumYSplitSize()));
					// calculate y draw position for this grid line and number
					int yPoint = yIndent + yUpperSectionPad
							+ (i * ySectionSize);
					// set settings for grid
					gc.setColor(plotSettings.getMainGridColor());
					gc.setStroke(StrokeTypeHandler.getStrokeType(
							plotSettings.getMainGridDrawType()).getStroke());
					// draw grid line
					gc.drawLine(xIndent, yPoint, (width - xPadding) + xIndent,
							yPoint);
					// calculate x draw position for this number
					int numStringWidth = gc.getFontMetrics().stringWidth(
							"" + num);
					int numStringHeight = gc.getFontMetrics().getHeight();
					int xPos;
					if (plotSettings.getyMin() > 0) {
						xPos = xIndent - numStringWidth;
					} else if (plotSettings.getyMax() < 0) {
						xPos = xIndent + width;
					} else {
						xPos = (xIndent + xLowerSectionPad
								+ (xLowerSectionNum * xSectionSize) - (numStringWidth));
					}
					// draw number
					gc.setColor(plotSettings.getTextColor());
					gc.drawString("" + num, xPos, yPoint
							+ (numStringHeight / 2));
				}
			}
		}

		// draw axis
		gc.setColor(plotSettings.getAxisColor());
		gc.setStroke(StrokeTypeHandler.getStrokeType(
				plotSettings.getAxisDrawType()).getStroke());
		int xAxisPoint = xIndent + xLowerSectionPad
				+ (xLowerSectionNum * xSectionSize);
		gc.drawLine(xAxisPoint, yIndent, xAxisPoint, (height - yPadding)
				+ yIndent);
		int yAxisPoint = yIndent + yUpperSectionPad
				+ (yUpperSectionNum * ySectionSize);
		gc.drawLine(xIndent, yAxisPoint, (width - xPadding) + xIndent,
				yAxisPoint);

		// draw title TODO update this here
		gc.setColor(plotSettings.getTextColor());
		int xPos = xIndent + (width / 2)
				- (g.getFontMetrics().stringWidth("Title") / 2);
		int ypos = g.getFontMetrics().getHeight();
		String title = "Title";
		gc.drawString(title, xPos, ypos);
	}

	@Override
	public PlotData editData(PlotData plotData) throws Exception {
		if (plotData instanceof Cartesian2DDataSet) {
			return editDataSet((Cartesian2DDataSet) plotData);
		} else if (plotData instanceof Cartesian2DPoint) {
			return editPoint((Cartesian2DPoint) plotData);
		} else if (plotData instanceof Cartesian2DFunction) {
			try {
				return editFunction((Cartesian2DFunction) plotData);
			} catch (MathParserException e) {
				throw new Exception("Invalid data type.");
			}
		} else {
			throw new Exception("Invalid data type.");
		}
	}

	private Cartesian2DDataSet editDataSet(Cartesian2DDataSet plotData)
			throws Exception {
		DataSetDialog dataSetDialog = new DataSetDialog(plotData, true);
		dataSetDialog.setVisible(true);
		if (dataSetDialog.getPressed() == DataSetDialog.UPDATE_PRESSED) {
			return dataSetDialog.getDataSet();
		} else {
			return null;
		}
	}

	private Cartesian2DFunction editFunction(Cartesian2DFunction plotData)
			throws MathParserException {
		FunctionDialog functionDialog = new FunctionDialog(plotData, true);
		functionDialog.setVisible(true);
		if (functionDialog.getPressed() == FunctionDialog.UPDATE_PRESSED) {
			return functionDialog.getFunction();
		} else {
			return null;
		}
	}

	@Override
	public void editPlotSettings() {
		SettingsDialog settingsDialog = new SettingsDialog(this.getPlotSettings());
		settingsDialog.setVisible(true);
	}

	private Cartesian2DPoint editPoint(Cartesian2DPoint plotData) {
		PointDialog pointDialog = new PointDialog(plotData, true);
		pointDialog.setVisible(true);
		if (pointDialog.getPressed() == DataSetDialog.UPDATE_PRESSED) {
			return pointDialog.getPoint();
		} else {
			return null;
		}
	}

	public Cartesian2DSettings getPlotSettings() {
		return plotSettings;
	}

	public double getxBelow() {
		return xBelow;
	}

	public int getxIndent() {
		return xIndent;
	}

	public int getxLowerSectionPad() {
		return xLowerSectionPad;
	}

	public double getxOver() {
		return xOver;
	}

	public int getxSectionSize() {
		return xSectionSize;
	}

	public double getyBelow() {
		return yBelow;
	}

	public int getyIndent() {
		return yIndent;
	}

	public double getyOver() {
		return yOver;
	}

	public int getySectionSize() {
		return ySectionSize;
	}

	public int getyUpperSectionPad() {
		return yUpperSectionPad;
	}
}
