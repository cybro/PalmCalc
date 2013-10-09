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

public class WeightStrategy implements Strategy {

	// To convert values with conversion factor
	public double Convert(String from, String to, double input) {
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitkg)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitgm)))) {
			double ret = input * 1000;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitgm)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitkg)))) {
			double ret = input * 0.001;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitkg)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitlb)))) {
			double ret = input * 2.204622621849;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitlb)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitkg)))) {
			double ret = input * 0.45359237;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitkg)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitounce)))) {
			double ret = input * 0.45359237;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitounce)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitkg)))) {
			double ret = input * 0.028349523125;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitkg)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitmg)))) {
			double ret = input * 1000000;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitmg)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitkg)))) {
			double ret = input * 0.000001;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitgm)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitlb)))) {
			double ret = input * 0.002204622621849;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitlb)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitgm)))) {
			double ret = input * 453.59237;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitgm)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitmg)))) {
			double ret = input * 1000;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitmg)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitgm)))) {
			double ret = input * 0.001;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitgm)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitounce)))) {
			double ret = input * 0.03527396194958;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitounce)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitgm)))) {
			double ret = input * 28.349523125;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitlb)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitmg)))) {
			double ret = input * 453592.37;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitmg)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitlb)))) {
			double ret = input * 0.000002204622621849;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitounce)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitmg)))) {
			double ret = input * 28349.523125;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitmg)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitounce)))) {
			double ret = input * 0.00003527396194958;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitlb)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitounce)))) {
			double ret = input * 16;
			return ret;
		}
		if ((from.equals(AndroidQAActivity.getInstance().getActivity()
				.getResources().getString(R.string.weightunitounce)) && to
				.equals(AndroidQAActivity.getInstance().getActivity()
						.getResources().getString(R.string.weightunitlb)))) {
			double ret = input * 0.0625;
			return ret;
		}
		if (from.equals(to)) {
			return input;
		}
		return 0.0;
	}
}