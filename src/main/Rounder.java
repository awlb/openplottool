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

package main;

public class Rounder {
	// return an integer stating how many decimal points the number has
	public static int getNumberOfDecimals(double value) {
		// convert the number to a string
		String strValue = Double.toString(value);

		// get the length of the number as a string
		int stringLength = strValue.length();
		int numberOfDecimals = 0;
		char theChar = ' ';
		int counter;

		// check what number the decimal point character is in the string
		for (counter = 1; theChar != '.'; counter++) {
			theChar = strValue.charAt(counter);
		}

		// calculate the number of decimals the double has
		numberOfDecimals = stringLength - counter;
		return numberOfDecimals;
	}

	// round number to a given number of decimal places
	public static double Round(double value, int places) {
		double p = Math.pow(10, places);
		value = value * p;
		double tmp = Math.round(value);
		return tmp / p;
	}

	/*
	 * checks if a number rounded to a given number of decimal places is the*
	 * same as the the number rounded to the nearest whole number
	 */
	public static boolean roundCheck(double value, int places) {
		return Math.round(value) == Rounder.Round(value, places);
	}
}
