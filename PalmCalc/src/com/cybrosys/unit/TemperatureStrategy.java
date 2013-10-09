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

import com.cybrosys.palmcalc.R;

public class TemperatureStrategy implements Strategy {
	public double Convert(String from, String to, double input) {
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitc)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitf))))) {
			double ret = (input * 9 / 5) + 32;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitc)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitk))))) {
			double ret = input + 273.15;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitc)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitR))))) {
			double ret = (input * 9 / 5) + 32 + 459.67;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitf)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitc))))) {
			double ret = (input - 32) * 5 / 9;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitf)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitk))))) {
			double ret = (input - 32) * 5 / 9 + 273.15;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitf)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitR))))) {
			double ret = (input + 459.67);
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitk)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitc))))) {
			double ret = input - 273.15;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitk)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitf))))) {
			double ret = (input - 273.15) * 9 / 5 + 32;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitk)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitR))))) {
			double ret = input * 1.8;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitR)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitc))))) {
			double ret = (input - 32 - 459.67) / 1.8;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitR)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitf))))) {
			double ret = (input - 459.67);
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.temperatureunitR)) && to
				.equals((AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.temperatureunitk))))) {
			double ret = (input / 1.8);
			return ret;
		}
		if (from.equals(to)) {
			return input;
		}
		return 0.0;
	}
}