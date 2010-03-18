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

package main;

import java.awt.RenderingHints;

public class Preferences {
	private Object antiAliasingMode = RenderingHints.VALUE_ANTIALIAS_ON;
	private String antiAliasing = "Full";
	private String lookAndFeel = "System";
	private boolean compressFiles = true;

	public Object getAntiAliasingMode() {
		return antiAliasingMode;
	}

	public String getAntiAliasing() {
		return antiAliasing;
	}

	public String getLookAndFeel() {
		return lookAndFeel;
	}

	public void setAntiAliasing(String mode) {
		// update anti aliasing mode
		if (mode.equals("Full")) {
			antiAliasing = "Full";
			antiAliasingMode = RenderingHints.VALUE_ANTIALIAS_ON;
		} else {
			antiAliasing = "None";
			antiAliasingMode = RenderingHints.VALUE_ANTIALIAS_OFF;
		}
	}

	public void setLookAndFeel(String lookAndFeel) {
		this.lookAndFeel = lookAndFeel;
	}

	public boolean useCompressedFiles() {
		return compressFiles;
	}

	public void setCompressFiles(boolean compressFiles) {
		this.compressFiles = compressFiles;
	}
}
