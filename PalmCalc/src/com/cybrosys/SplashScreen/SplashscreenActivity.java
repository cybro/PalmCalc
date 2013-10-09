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

package com.cybrosys.SplashScreen;

import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//class to display the splash screen on app startup
public class SplashscreenActivity extends Activity {

	private static final int SPLASH_DISPLAY_TIME = 3000; /* 3 seconds */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(SplashscreenActivity.this,
						PalmCalcActivity.class);
				SplashscreenActivity.this.startActivity(mainIntent);
				SplashscreenActivity.this.finish();
				overridePendingTransition(R.anim.mainfadein,
						R.anim.splashfadeout);
			}
		}, SPLASH_DISPLAY_TIME);
	}
}