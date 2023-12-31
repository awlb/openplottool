/*	Copyright (C) 2009-2011  Alex Barfoot
 
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

package dev.awlb.opt.gui;

import dev.awlb.opt.utils.FileUtils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PlotFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (FileUtils.getExtension(f).equals("opf")) {
			return true;
		} else if (f.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getDescription() {
		return "*.opf";
	}
}
