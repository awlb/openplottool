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

package utils;

import java.io.File;

public class FileUtils {
	public static String getExtension(File file) {
		// find and return file extension
		String fileName = file.getName();
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0) {
			String extension = fileName.substring(lastDot + 1);
			return extension;
		} else {
			return "";
		}
	}
}
