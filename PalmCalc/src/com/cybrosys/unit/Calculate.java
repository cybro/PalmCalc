/**
 * <Palmcalc is a multipurpose application consisting of calculators, converters
 * and world clock> Copyright (C) <2013> <Cybrosys Technologies pvt. ltd.>
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 **/

package com.cybrosys.unit;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

import java.util.Locale;

/**
 * Created by Administrator on 10/1/13.
 */
public class Calculate {
	double dblValue = 0;
	private static Symbols mSymbols = new Symbols();

	// for calculating value with given precision
	public double getValue(String input) {
		try {
			dblValue = mSymbols.eval(input);
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
		String strReslt = "";
		for (int inPrecision = 13; inPrecision > 6; inPrecision--) {
			strReslt = tryFormattingWithPrecision(dblValue, inPrecision);
			if (strReslt.length() <= 13) {
				break;
			}
		}
		return Double.parseDouble(strReslt);

	}

	private String tryFormattingWithPrecision(double dblValue, int inPrecision) {
		String strReslt = String.format(Locale.US, "%" + 13 + "." + inPrecision
				+ "g", dblValue);

		String strMantissa = strReslt;
		String strExponent = null;
		int inExp = strReslt.indexOf('e');
		if (inExp != -1) {
			strMantissa = strReslt.substring(0, inExp);
			strExponent = strReslt.substring(inExp + 1);
			if (strExponent.startsWith("+")) {
				strExponent = strExponent.substring(1);
			}
			strExponent = String.valueOf(Integer.parseInt(strExponent));
		} else {
			strMantissa = strReslt;
		}
		int inPeriod = strMantissa.indexOf('.');
		if (inPeriod == -1) {
			inPeriod = strMantissa.indexOf(',');
		}
		if (inPeriod != -1) {
			while (strMantissa.length() > 0 && strMantissa.endsWith("0")) {
				strMantissa = strMantissa
						.substring(0, strMantissa.length() - 1);
			}
			if (strMantissa.length() == inPeriod + 1) {
				strMantissa = strMantissa
						.substring(0, strMantissa.length() - 1);
			}
		}

		if (strExponent != null) {
			strReslt = strMantissa + 'e' + strExponent;
		} else {
			strReslt = strMantissa;
		}

		return strReslt;
	}
}
