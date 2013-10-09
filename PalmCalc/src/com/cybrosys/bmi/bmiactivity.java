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

package com.cybrosys.bmi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;
import com.cybrosys.unit.ArrayWheelAdapter;
import com.cybrosys.unit.OnWheelChangedListener;
import com.cybrosys.unit.OnWheelScrollListener;
import com.cybrosys.unit.WheelView;

public class bmiactivity extends SherlockFragment {

	private boolean wheelScrolled = false;
	Bundle savedstate;
	int wheelselection = 0;
	String strhight;
	float flthight, fltweight, fltresult;
	String strweight;
	public static WheelView wheelweight, wheelhight;
	String wheelMenuweight[] = new String[461];
	String wheelMenuheight[] = new String[231];
	String wheelMenuweight1[] = new String[508];
	String wheelMenuheight1[] = new String[96];
	public static ToggleButton mode;
	TextView txtvResult, txtvCategory;
	public static boolean isMetric = false;
	private Context mContext;
	SharedPreferences sharedPrefs;

	// for vibrate
	public void vibrate() {
		Boolean isVibe = sharedPrefs.getBoolean("prefVibe", false);
		Vibrator vibe = (Vibrator) PalmCalcActivity.ctx
				.getSystemService(Context.VIBRATOR_SERVICE);
		if (isVibe) {
			vibe.vibrate(100);
		}
	}

	@Override
	public void onCreate(Bundle outState) {
		setHasOptionsMenu(true);
		super.onCreate(outState);
		savedstate = outState;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(PalmCalcActivity.ctx);
		return inflater.inflate(R.layout.bmicalc, container, false);
	}

	@Override
	public void onStart() {
		try {
			super.onStart();
			mContext = getActivity();
			txtvResult = (TextView) getView().findViewById(R.id.txtvresult);
			txtvCategory = (TextView) getView().findViewById(R.id.txtvcater);
			wheelweight = (WheelView) getView().findViewById(R.id.p1);
			wheelhight = (WheelView) getView().findViewById(R.id.p2);
			mode = (ToggleButton) getView().findViewById(R.id.ToggleButton01);
			// to check if metric or english mode
			if (mode.isChecked()) {
				isMetric = false;
				Englishmode();

			} else {
				isMetric = true;
				Metricmode();

			}

			mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {

						isMetric = false;
						Englishmode();

					} else {

						isMetric = true;
						Metricmode();

					}
				}

			});

		} catch (Exception e) {
		}
	}

	// To load values to the wheels if english mode selected
	public void Englishmode() {
		int j = 0;
		// weight from 44 to 551
		for (int i = 44; i <= 551; i++) {
			String strW = i + " lb";
			if (j <= 507) {
				wheelMenuweight1[j] = strW;
			}
			j++;
		}
		j = 0;
		for (int i = 1; i < 9; i++) {
			for (int k = 0; k < 12; k++) {
				String strH = i + " ft " + k + " in";
				if (j <= 95) {
					wheelMenuheight1[j] = strH;
				}
				j++;
			}

		}

		initWheel3(R.id.p1, wheelMenuweight1);
		initWheel3(R.id.p2, wheelMenuheight1);
		if (savedstate == null) {
			wheelweight.setCurrentItem(70);
			wheelhight.setCurrentItem(46);
		} else {
			wheelweight.setCurrentItem(savedstate.getInt("weight"));
			wheelhight.setCurrentItem(savedstate.getInt("height"));
		}

	}

	// To load values to the wheels if Metric mode selected
	public void Metricmode() {

		int j = 0;
		for (double i = 20; i <= 250; i = i + .5) {
			String strW = String.format("%1$,.2f kg", i);
			if (j <= 461) {
				wheelMenuweight[j] = strW;
			}
			j++;
		}
		j = 0;
		for (int i = 50; i <= 280; i++) {
			if (j <= 230) {
				wheelMenuheight[j] = i + " cm";
			}
			j++;

		}

		initWheel3(R.id.p1, wheelMenuweight);
		initWheel3(R.id.p2, wheelMenuheight);
		if (savedstate == null) {
			wheelweight.setCurrentItem(70);
			wheelhight.setCurrentItem(70);
		} else {
			wheelweight.setCurrentItem(savedstate.getInt("weight"));
			wheelhight.setCurrentItem(savedstate.getInt("height"));
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initWheel3(int id, String[] wheelMenuTipcntry2) {
		WheelView wheel = (WheelView) getView().findViewById(id);
		wheel.setViewAdapter(new ArrayWheelAdapter(mContext, wheelMenuTipcntry2));
		wheel.setVisibleItems(2);
		wheel.setCurrentItem(0);
		wheel.addChangingListener(changedListener);
		wheel.addScrollingListener(scrolledListener);
	}

	private WheelView getWheel(int id) {
		return (WheelView) getView().findViewById(id);
	}

	@SuppressWarnings("unused")
	private int getWheelValue(int id2) {
		return getWheel(id2).getCurrentItem();
	}

	@SuppressWarnings("unused")
	private int getWheelValuethree(int id3) {
		return getWheel(id3).getCurrentItem();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {

		super.onStop();
	}

	OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {

		@SuppressWarnings("unused")
		public void onScrollStarts(WheelView wheel) {
			wheelScrolled = true;
		}

		@SuppressWarnings("unused")
		public void onScrollEnds(WheelView wheel) {
			wheelScrolled = false;
			updateStatus();
		}

		private void updateStatus() {
		}

		@Override
		public void onScrollingStarted(WheelView wheel) {
			updateStatus();
		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			updateStatus();
		}

		@SuppressWarnings("unused")
		public void onScrollingStarted(WheelView wheel,
				String[] wheelMenuTipcntry, int index) {
		}
	};

	// Onwheel changed listener
	private final OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			try {
				String id = wheel.getTag().toString();

				if (!wheelScrolled) {
					vibrate();

					if (id.equalsIgnoreCase("1")) {
						if (isMetric)
							strweight = wheelMenuweight[wheelweight
									.getCurrentItem()];

						else

							strweight = wheelMenuweight1[wheelweight
									.getCurrentItem()];

						fltweight = formatWeight(strweight);

					} else if (id.equalsIgnoreCase("2")) {
						if (isMetric) {
							strhight = wheelMenuheight[wheelhight
									.getCurrentItem()];
							flthight = formatHeight(strhight) / 100;
						} else {
							strhight = wheelMenuheight1[wheelhight
									.getCurrentItem()];
							flthight = formatHeight(strhight) * 12;
						}
					}
					// BMI calculation
					if (isMetric) {
						fltresult = fltweight / (flthight * flthight);
						txtvResult.setText("" + fltresult);

					} else {
						fltresult = (fltweight / (flthight * flthight)) * 703;
						txtvResult.setText("" + fltresult);
					}
					checkcategory(fltresult);
				}

			} catch (Exception e) {
			}
		}

		// CAtegory checking based on value
		private void checkcategory(float fltresult) {
			if (fltresult < 18.5) {
				txtvCategory.setText("UNDER WEIGHTED");
				txtvCategory.setTextColor(0xAADBA901);
			} else if (fltresult >= 18.5 && fltresult < 24.9) {
				txtvCategory.setText("NORMAL WEIGHTED");
				txtvCategory.setTextColor(0xAA04B404);
			} else if (fltresult >= 25 && fltresult < 29.9) {
				txtvCategory.setText("OVER WEIGHTED");
				txtvCategory.setTextColor(0xAAFE642E);
			} else if (fltresult > 30) {
				txtvCategory.setText("OBESE");
				txtvCategory.setTextColor(0xAAFF0000);
			}

		}
	};

	// removing weight units for calculation
	protected float formatWeight(String strweight2) {
		if (strweight2.contains("lb")) {
			strweight2 = strweight2.replace(" lb", "");
		} else if (strweight2.contains("kg"))
			strweight2 = strweight2.replace(" kg", "");
		return Float.valueOf(strweight2);
	}

	float formatHeight(String strheight) {
		if (strheight.contains("cm")) {
			strheight = strheight.replace(" cm", "");
		} else if (strheight.contains("ft")) {
			strheight = strheight.replace(" ft ", ".");
			strheight = strheight.replace(" in", "");
		}
		return Float.valueOf(strheight);
	}

}
