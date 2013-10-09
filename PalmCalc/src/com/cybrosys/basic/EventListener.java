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

package com.cybrosys.basic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;

class EventListener implements View.OnKeyListener, OnClickListener {

	int inP = 0;

	Button btnHistory[] = null;
	TextView txtvHistory[] = null;
	TableRow tblrRowL[] = null;
	Button btns[] = new Button[9];
	Button btnsM[] = new Button[10];
	View vwLayout;
	TableLayout tblltTable;
	Logic mHandler;
	ViewPager mPager;
	// private Symbols mSymbols = new Symbols();
	int inShift = 0, inHyp = 0;
	public static Context ctx = PalmCalcActivity.ctx;
	static SharedPreferences spMemory;
	SharedPreferences shPref;
	static SharedPreferences.Editor editor;

	void setHandler(Logic handler, ViewPager pager) {
		mHandler = handler;
		mPager = pager;
	}

	// vibrate
	public void vibrate() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(PalmCalcActivity.ctx);
		Boolean isVibe = sharedPrefs.getBoolean("prefVibe", false);
		Vibrator vibe = (Vibrator) PalmCalcActivity.ctx
				.getSystemService(Context.VIBRATOR_SERVICE);
		if (isVibe) {
			vibe.vibrate(100);

		}

	}

	// click handler
	@SuppressLint("NewApi")
	@Override
	public void onClick(View vwView) {
		vibrate();
		ctx = PalmCalcActivity.ctx;
		int id = vwView.getId();

		switch (id) {

		case R.id.buttonDel:
			mHandler.onDelete();

		case R.id.ButtonEqual:
			mHandler.onEnter();
			break;
		default:
			if (vwView instanceof Button) {
				String strText = ((Button) vwView).getTag().toString();
				System.out.println(strText);
				Log.d("PALMER", strText);

				mHandler.insert(strText);

			}
		}
	}

	@Override
	public boolean onKey(View vwView, int keyCode, KeyEvent keyEvent) {
		int action = keyEvent.getAction();

		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
				|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			boolean eat = mHandler
					.eatHorizontalMove(keyCode == KeyEvent.KEYCODE_DPAD_LEFT);
			return eat;
		}
		if (action == KeyEvent.ACTION_MULTIPLE
				&& keyCode == KeyEvent.KEYCODE_UNKNOWN) {
			return true;
		}
		if (keyEvent.getUnicodeChar() == '=') {
			if (action == KeyEvent.ACTION_UP) {
				mHandler.onEnter();
			}
			return true;
		}
		if (keyCode != KeyEvent.KEYCODE_DPAD_CENTER
				&& keyCode != KeyEvent.KEYCODE_DPAD_UP
				&& keyCode != KeyEvent.KEYCODE_DPAD_DOWN
				&& keyCode != KeyEvent.KEYCODE_ENTER) {
			return false;
		}
		if (action == KeyEvent.ACTION_UP) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_ENTER:
			case KeyEvent.KEYCODE_DPAD_CENTER:
				mHandler.onEnter();
				break;

			case KeyEvent.KEYCODE_DPAD_UP:
				mHandler.onUp();
				break;

			case KeyEvent.KEYCODE_DPAD_DOWN:
				mHandler.onDown();
				break;
			}
		}
		return true;
	}

}
