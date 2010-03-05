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

package plot.piechart;

import handlers.PreferenceHandler;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import plot.Axis;
import plot.DrawTypes;
import plot.PlotSettings;

public class PieChartAxis extends Axis {
	private int pieSize, yPiePos, xPiePos;
	private int xIndent, yIndent;

	public PieChartAxis(PlotSettings plotSettings) {
		this.setPlotSettings(plotSettings);
	}

	@Override
	public void draw(Graphics g, int width, int height) {
		PieChartSettings settings = (PieChartSettings) this.getPlotSettings();
		Graphics2D gc = (Graphics2D) g;
		gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, PreferenceHandler
				.getSettings().getAntiAliasingMode());

		// Calculate indent
		xIndent = gc.getFontMetrics().getHeight();
		yIndent = (int) Math.round(gc.getFontMetrics().getHeight() * 1.5);
		// draw background
		gc.setColor(settings.getBackgroundColor());
		gc.fillRect(xIndent, yIndent, width - (2 * xIndent), height
				- (2 * yIndent));
		if (this.getParentPage().getSettings().getDrawBorder()) {
			// draw border if required
			gc.setStroke(DrawTypes.getDrawType(this.getParentPage()
					.getSettings().getBorderDrawType()));
			gc.setColor(this.getParentPage().getSettings().getBorderColor());
			gc.drawRect(xIndent, yIndent, width - (2 * xIndent), height
					- (2 * yIndent));
		}
		// draw pie outline
		gc.setColor(settings.getOutlineDrawColor());
		gc.setStroke(DrawTypes.getDrawType("Line"));
		pieSize = height - (4 * yIndent);
		yPiePos = (yIndent * 2);
		xPiePos = xIndent + ((width - (2 * xIndent)) / 2) - pieSize / 2;
		gc.drawOval(xPiePos - 1, yPiePos - 1, pieSize + 1, pieSize + 1);

		// draw title
		String title = this.getParentPage().getSettings().getTitle();
		gc.setColor(this.getParentPage().getSettings().getTitleColor());
		int xPos = xIndent + ((width - (2 * xIndent)) / 2)
				- (gc.getFontMetrics().stringWidth(title) / 2);
		int ypos = g.getFontMetrics().getHeight() + 1;
		gc.drawString(title, xPos, ypos);
	}

	public int getPieSize() {
		return pieSize;
	}

	public int getxIndent() {
		return xIndent;
	}

	public int getxPiePos() {
		return xPiePos;
	}

	public int getyIndent() {
		return yIndent;
	}

	public int getyPiePos() {
		return yPiePos;
	}
}
