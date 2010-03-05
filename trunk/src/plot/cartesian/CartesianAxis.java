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

import handlers.PreferenceHandler;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import main.Rounder;
import plot.Axis;
import plot.DrawTypes;
import plot.PlotSettings;

public class CartesianAxis extends Axis {
	private int xIndent, yIndent;
	private int xLowerSectionPad;
	private double xOver, xBelow, yOver, yBelow;
	private int xSectionSize;
	private int ySectionSize;
	private int yUpperSectionPad;

	public CartesianAxis(PlotSettings plotSettings) {
		this.setPlotSettings(plotSettings);
	}

	@Override
	public void draw(Graphics g, int panelWidth, int panelHeight) {
		Graphics2D gc = (Graphics2D) g;
		// get plot settings
		CartesianSettings settings = (CartesianSettings) this.getPlotSettings();
		// set antialiasing mode
		gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, PreferenceHandler
				.getSettings().getAntiAliasingMode());

		// Calculate indents
		xIndent = g.getFontMetrics().stringWidth(
				"-" + settings.getNumXSplitSize());
		yIndent = g.getFontMetrics().getHeight() * 2;

		// calculate bounds
		int width = panelWidth - (2 * xIndent);
		int height = panelHeight - (2 * yIndent);

		// calculate x
		// calculate x axis range
		double xRange = settings.getxMax() + (-settings.getxMin());
		// calculate number of sections axis is split by
		double xSectionNumber = xRange / settings.getxSplitSize();
		// calculate the size of these sections in pixels
		xSectionSize = (int) (width / xSectionNumber);
		// calculate the amount the x axis extends over the number of whole
		// sections
		xOver = Math.abs(settings.getxMax() % settings.getxSplitSize());
		// calculate the amount the x axis extends below the number of whole
		// sections
		xBelow = Math.abs(settings.getxMin() % settings.getxSplitSize());
		// calculate the number of whole sections on the x axis below 0
		int xLowerSectionNum = (int) -(settings.getxMin() / settings
				.getxSplitSize());
		// calculate the number of whole sections on the x axis above 0
		int xUpperSectionNum = (int) (settings.getxMax() / settings
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
		if (Rounder.roundCheck(xUpperSectionNum, Rounder
				.getNumberOfDecimals(settings.getxSplitSize()))
				|| Rounder.roundCheck(xLowerSectionNum, Rounder
						.getNumberOfDecimals(settings.getxSplitSize()))) {
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
		double yRange = settings.getyMax() + (-settings.getyMin());
		// calculate number of sections axis is split by
		double ySectionNumber = yRange / settings.getySplitSize();
		// calculate the size of these sections in pixels
		ySectionSize = (int) (height / ySectionNumber);
		// calculate the amount the y axis extends over the number of whole
		// sections
		yOver = Math.abs(settings.getyMax() % settings.getySplitSize());
		// calculate the amount the y axis extends below the number of whole
		// sections
		yBelow = Math.abs(settings.getyMin() % settings.getySplitSize());
		// calculate the number of whole sections on the y axis below 0
		int yLowerSectionNum = (int) -(settings.getyMin() / settings
				.getySplitSize());
		// calculate the number of whole sections on the y axis above 0
		int yUpperSectionNum = (int) (settings.getyMax() / settings
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
		if (Rounder.roundCheck(yUpperSectionNum, Rounder
				.getNumberOfDecimals(settings.getySplitSize()))
				|| Rounder.roundCheck(yLowerSectionNum, Rounder
						.getNumberOfDecimals(settings.getySplitSize()))) {
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

		// draw background
		gc.setColor(settings.getBackgroundColor());
		gc.fillRect(xIndent, yIndent, width - xPadding, height - yPadding);

		// set settings for sub grid
		gc.setColor(settings.getSubGridColor());
		gc.setStroke(DrawTypes.getDrawType(settings.getSubGridType()));

		// draw x sub grid lines
		for (int i = 0; i <= xWholeSectionNum; i++) {
			double num = (-xLowerSectionNum * settings.getxSplitSize())
					+ (i * settings.getxSplitSize());
			if (num != 0) {
				// check if this line should be drawn
				if (!Rounder.roundCheck(num / settings.getNumXSplitSize(), 1)) {
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
			double num = (yUpperSectionNum * settings.getySplitSize())
					- (i * settings.getySplitSize());
			if (num != 0) {
				// check if this line should be drawn
				if (!Rounder.roundCheck(num / settings.getNumYSplitSize(), 1)) {
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
			double num = (-xLowerSectionNum * settings.getxSplitSize())
					+ (i * settings.getxSplitSize());
			if (num != 0) {
				// check if this number should be shown
				if (Rounder.roundCheck(num / settings.getNumXSplitSize(), 1)) {
					// round number to required decimal places for plotting
					num = Rounder.Round(num, Rounder
							.getNumberOfDecimals(settings.getNumXSplitSize()));
					// calculate x draw position for this grid line and number
					int xPos = xIndent + xLowerSectionPad + (i * xSectionSize);
					// set settings for grid
					gc.setColor(settings.getMainGridColor());
					gc.setStroke(DrawTypes.getDrawType(settings
							.getMainGridDrawType()));
					// draw grid line
					gc.drawLine(xPos, yIndent, xPos, (height - yPadding)
							+ yIndent);
					// calculate y draw position for number
					int numStringWidth = gc.getFontMetrics().stringWidth(
							"" + num);
					int numStringHeight = gc.getFontMetrics().getHeight();
					int yPos;
					if (settings.getxMin() > 0) {
						yPos = yIndent + height + numStringHeight;
					} else if (settings.getxMax() < 0) {
						yPos = yIndent - 2;
					} else {
						yPos = yIndent + yUpperSectionPad
								+ (yUpperSectionNum * ySectionSize)
								+ numStringHeight;
					}

					// draw number
					gc.setColor(settings.getTextColor());
					gc.drawString("" + num, xPos - (numStringWidth / 2), yPos);
				}
			}
		}

		// draw y number grid lines and numbers
		for (int i = 0; i <= yWholeSectionNum; i++) {
			double num = (yUpperSectionNum * settings.getySplitSize())
					- (i * settings.getySplitSize());
			if (num != 0) {
				// check if this number should be shown
				if (Rounder.roundCheck(num / settings.getNumYSplitSize(), 1)) {
					// round number to required decimal places for plotting
					num = Rounder.Round(num, Rounder
							.getNumberOfDecimals(settings.getNumYSplitSize()));
					// calculate y draw position for this grid line and number
					int yPoint = yIndent + yUpperSectionPad
							+ (i * ySectionSize);
					// set settings for grid
					gc.setColor(settings.getMainGridColor());
					gc.setStroke(DrawTypes.getDrawType(settings
							.getMainGridDrawType()));
					// draw grid line
					gc.drawLine(xIndent, yPoint, (width - xPadding) + xIndent,
							yPoint);
					// calculate x draw position for this number
					int numStringWidth = gc.getFontMetrics().stringWidth(
							"" + num);
					int numStringHeight = gc.getFontMetrics().getHeight();
					int xPos;
					if (settings.getyMin() > 0) {
						xPos = xIndent - numStringWidth;
					} else if (settings.getyMax() < 0) {
						xPos = xIndent + width;
					} else {
						xPos = (xIndent + xLowerSectionPad
								+ (xLowerSectionNum * xSectionSize) - (numStringWidth));
					}
					// draw number
					gc.setColor(settings.getTextColor());
					gc.drawString("" + num, xPos, yPoint
							+ (numStringHeight / 2));
				}
			}
		}

		// draw axis
		gc.setColor(settings.getAxisColor());
		gc.setStroke(DrawTypes.getDrawType(settings.getAxisDrawType()));
		int xAxisPoint = xIndent + xLowerSectionPad
				+ (xLowerSectionNum * xSectionSize);
		gc.drawLine(xAxisPoint, yIndent, xAxisPoint, (height - yPadding)
				+ yIndent);
		int yAxisPoint = yIndent + yUpperSectionPad
				+ (yUpperSectionNum * ySectionSize);
		gc.drawLine(xIndent, yAxisPoint, (width - xPadding) + xIndent,
				yAxisPoint);

		// draw border if required
		if (this.getParentPage().getSettings().getDrawBorder()) {
			gc.setColor(this.getParentPage().getSettings().getBorderColor());
			gc.setStroke(DrawTypes.getDrawType(this.getParentPage()
					.getSettings().getBorderDrawType()));
			gc.drawRect(xIndent, yIndent, width - xPadding, height - yPadding);
		}

		// draw title
		gc.setColor(this.getParentPage().getSettings().getTitleColor());
		int xPos = xIndent
				+ (width / 2)
				- (g.getFontMetrics().stringWidth(""
						+ this.getParentPage().getSettings().getTitle())) / 2;
		int ypos = g.getFontMetrics().getHeight();
		String title = this.getParentPage().getSettings().getTitle();
		gc.drawString(title, xPos, ypos);

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
