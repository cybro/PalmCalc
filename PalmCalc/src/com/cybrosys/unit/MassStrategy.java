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

import java.util.HashMap;

public class MassStrategy implements Strategy {
	HashMap<String, Double> facts = new HashMap<String, Double>();
	Calculate cal = new Calculate();

	// To convert values with conversion factor
	public double Convert(String from, String to, double input) {

		facts.put("mg", 1000000.0);
		facts.put("g", 1000.0);
		facts.put("t", 0.001);
		facts.put("lb", 2.2046226218488);
		facts.put("oz", 35.27396194958);

		if (from.equals(to)) {
			return input;
		} else if (from.equals("kg")) {
			return cal.getValue(input + "*" + facts.get(to));
		} else if (to.equals("kg")) {
			return cal.getValue(input + "/" + facts.get(from));
		} else {
			double v1 = (input / facts.get(from));
			double v2 = facts.get(to);

			return cal.getValue(v1 + "*" + v2);
		}
	}
}
