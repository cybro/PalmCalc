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

package com.cybrosys.scientific;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceClass {
	private static String strStringPref = "mystringpref";
	private static String strModepref = "strModepref";
	private static String strIntPref = "shareduserid";

	public static SharedPreferences getPrefs(Context context) {
		return context.getSharedPreferences("scientific", Context.MODE_PRIVATE);
	}

	public static String getModePref(Context context) {
		return getPrefs(context).getString(strModepref, "Floatpt");
	}

	public static void setModePref(Context context, String strValue) {
		getPrefs(context).edit().putString(strModepref, strValue).commit();
	}

	public static String getMyStringPref(Context context) {
		return getPrefs(context).getString(strStringPref, "0");
	}

	public static int getMyIntPref(Context context) {
		return getPrefs(context).getInt(strIntPref, 1);
	}

	public static void setMyStringPref(Context context, String strValue) {
		getPrefs(context).edit().putString(strStringPref, strValue).commit();
	}

	public static void setMyIntPref(Context context, int inValue) {
		getPrefs(context).edit().putInt(strIntPref, inValue).commit();
	}
}