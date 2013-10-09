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

public class AreaStrategy implements Strategy {
	HashMap<String, Double> facts = new HashMap<String, Double>();
	Calculate cal = new Calculate();

	// To convert values with conversion factor
	public double Convert(String from, String to, double input) {
		facts.put("mm#2", 1E6);
		facts.put("cm#2", 1E4);
		facts.put("km#2", 1E-6);
		facts.put("mil#2", 3.8610061413536e-07);
		facts.put("yd#2", 1.1959900463011);
		facts.put("ha", 0.0001);
		facts.put("ft#2", 10.76391041671);
		facts.put("acre", 0.00024710538146717);
		if (from.equals(to)) {
			return input;
		} else if (from.equals("m#2")) {
			return cal.getValue(input + "*" + facts.get(to));
		} else if (to.equals("m#2")) {
			return cal.getValue(input + "/" + facts.get(from));
		} else {
			double v1 = (input / facts.get(from));
			double v2 = facts.get(to);

			return cal.getValue(v1 + "*" + v2);
		}
	}
}
