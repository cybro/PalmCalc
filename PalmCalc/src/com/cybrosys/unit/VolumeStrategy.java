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

public class VolumeStrategy implements Strategy {

	HashMap<String, Double> facts = new HashMap<String, Double>();
	Calculate cal = new Calculate();

	// To convert values with conversion factor
	public double Convert(String from, String to, double input) {

		facts.put("ml", 0.000001);
		facts.put("l", 0.001);
		facts.put("mm#3", 0.000000001);
		facts.put("cm#3", 0.000001);
		facts.put("ft#3", 0.02831685);
		facts.put("fl oz#uk", 2.84131E-5);
		facts.put("fl oz#us", 2.95735E-5);
		facts.put("pt#uk", 5.68261E-4);
		facts.put("qt#uk", 1.13652E-3);
		facts.put("gal#uk", 4.54609E-3);

		facts.put("pt#us", 4.73176E-4);
		facts.put("qt#us", 9.46353E-4);
		facts.put("gal#us", 3.785411784E-3);

		facts.put("cup", 0.000236588);
		facts.put("tbsp", 1.4787e-5);
		facts.put("tsp", 4.9289e-6);

		if (from.equals(to)) {
			return input;
		} else if (from.equals("m#3")) {
			return cal.getValue(input + "/" + facts.get(to));
		} else if (to.equals("m#3")) {
			return cal.getValue(facts.get(from) + "*" + input);
		} else {

			return cal.getValue(input * facts.get(from) + "/" + facts.get(to));

		}
	}
}
