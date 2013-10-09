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

import com.actionbarsherlock.app.SherlockFragment;
import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AndroidQAActivity extends SherlockFragment {
	// setting fontsize of wheel
	public static String strWhealFontSize = "20";
	int wheelselection = 0;

	// string arrays for storing values
	String strWheelMenu1[] = new String[] { "Temperature", "Length", "Weight",
			"Speed", "Volume", "Area", "Mass", "Time" };
	String strWheelMenu2[] = new String[] { "C", "F", "K", "R" };
	String strWheelMenu3[] = new String[] { "cm", "m", "mm", "km", "ft", "mi",
			"in", "yd", "nm" };
	String strWheelMenu4[] = new String[] { "Kg", "gm", "lb", "ounce", "mg" };
	String strWheelMenu5[] = new String[] { "m/s", "mph", "kmph", "ft/s", "kn" };
	String strWheelMenu6[] = new String[] { "ml", "l", "mm#3", "cm#3", "m#3",
			"ft#3", "gal#uk", "qt#uk", "pt#uk", "fl oz#uk", "gal#us", "qt#us",
			"pt#us", "fl oz#us", "cup", "tbsp", "tsp" };
	String strWheelMenu7[] = new String[] { "mm#2", "m#2", "cm#2", "km#2",
			"ft#2", "yd#2", "mil#2", "ha", "acre" };
	String strWheelMenu8[] = new String[] { "mg", "g", "t", "kg", "lb", "oz" };
	String strWheelMenu9[] = new String[] { "ns", "µs", "ms", "s", "min", "h",
			"day", "week", "month", "year" };
	Context context;
	// Wheel scrolled flag
	private boolean wheelScrolled = false;
	private Boolean readyToClear = false;
	private TextView txtvMain;
	private TextView txtvSubone;
	private TextView txtvSubtwo;
	private TextView txtvInput;
	private TextView txtvResult;
	Context ctx = PalmCalcActivity.ctx;
	@SuppressWarnings("unused")
	private int operator = 1;
	@SuppressWarnings("unused")
	private boolean hasChanged = false;
	private Strategy currentStrategy;
	@SuppressWarnings("unused")
	private Strategy lastStrategy;
	private String unitfrom = "";
	private String unitto = "";
	private static AndroidQAActivity instance;
	String PREFERNAME = "UNITCONVERTERSHARED";
	WheelView wheelmain;
	WheelView wheelone;
	WheelView wheeltwo;
	String strMainwheel = "";
	String strWheelone = "";
	String strWheeltwo = "";
	Button btnclickone, btnclicktwo, btnclickthree, btnclickplusminus,
			btnclickfour, btnclickfive, btnclicksix, btnclickclr,
			btnclickseven, btnclickeight, btnclicknine, btnclickundo,
			btnclickzero, btnclickpoint, btnclickok;
	ImageButton imgbtnExit, imgbtnabout;
	private String strInput = "";
	private String strInput2 = "";
	String strUnitfrom = "C";
	String strUnitto = "C";
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		strWhealFontSize = getString(R.string.whealfontsize);
		sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(PalmCalcActivity.ctx);
		return inflater.inflate(R.layout.unitmain, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		IntwheelSelector(R.id.p1, strWheelMenu1);
		IntwheelSelector(R.id.p2, strWheelMenu2);
		IntwheelSelector(R.id.p3, strWheelMenu2);
		TableLayout tablePad = (TableLayout) getView().findViewById(
				R.id.tablone);
		int inPadCount = tablePad.getChildCount();
		for (int i = 0; i < inPadCount; i++) {
			View v = tablePad.getChildAt(i);
			if (v instanceof TableRow) {
				TableRow row = (TableRow) v;
				int rowCount = row.getChildCount();
				for (int r = 0; r < rowCount; r++) {
					View v2 = row.getChildAt(r);
					if (v2 instanceof Button) {
						Button b = (Button) v2;
						b.setOnClickListener(buttonpad);
					} else if (v2 instanceof TextView) {
						TextView txtv = (TextView) v2;
						txtv.setOnClickListener(null);
					}
				}
			}
		}

		txtvMain = (TextView) getView().findViewById(R.id.r1);
		txtvSubone = (TextView) getView().findViewById(R.id.r2);
		txtvSubtwo = (TextView) getView().findViewById(R.id.r3);
		txtvInput = (TextView) getView().findViewById(R.id.editText1);
		txtvResult = (TextView) getView().findViewById(R.id.textView6);
		instance = this;
		SharedPreferences Settings = getActivity().getSharedPreferences(
				PREFERNAME, 0);

		strMainwheel = Settings.getString("valuemain", "Temperature");
		strWheelone = Settings.getString("valueone", "C");
		strWheeltwo = Settings.getString("valuetwo", "C");
		strInput2 = Settings.getString("result", "");
		strInput = Settings.getString("input", "");
		wheelmain = (WheelView) getView().findViewById(R.id.p1);
		wheelone = (WheelView) getView().findViewById(R.id.p2);
		wheeltwo = (WheelView) getView().findViewById(R.id.p3);
		txtvResult.setText(strInput2);
		txtvInput.setText(strInput);

		txtvSubone.setText(strWheelone);
		txtvSubtwo.setText(strWheeltwo);

		currentStrategy = setStrategy();
		lastStrategy = currentStrategy;
		findstartvalue();
		txtvInput.addTextChangedListener(textwatch);
	}

	// restoring states of views on orientation change
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			strInput = savedInstanceState.getString("input");
			strInput2 = savedInstanceState.getString("result");
			strWheelone = savedInstanceState.getString("unitfrom");
			strWheeltwo = savedInstanceState.getString("unitto");
			strMainwheel = savedInstanceState.getString("wheel1");

		}
	}

	// saving states of views on orientation change

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("input", txtvInput.getText().toString());
		outState.putString("result", txtvResult.getText().toString());
		outState.putString("unitfrom", txtvSubone.getText().toString());
		outState.putString("unitto", txtvSubtwo.getText().toString());
		outState.putString("wheel1", txtvMain.getText().toString());
	}

	// Text watcher class
	TextWatcher textwatch = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			try {
				unitfrom = txtvSubone.getText().toString();
				unitto = txtvSubtwo.getText().toString();
				if (!txtvInput.getText().toString().equalsIgnoreCase("")) {
					double in = Double.parseDouble(txtvInput.getText()
							.toString());
					double result = currentStrategy.Convert(unitfrom, unitto,
							in);
					txtvResult.setText("" + result);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	};

	// Wheel scrolled listener
	OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
		@SuppressWarnings("unused")
		public void onScrollStarts(WheelView wheel) {
			wheelScrolled = true;
		}

		@SuppressWarnings("unused")
		public void onScrollEnds(WheelView wheel) {
			wheelScrolled = false;
		}

		@Override
		public void onScrollingStarted(WheelView wheel) {
		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
		}
	};

	// Wheel changed listener
	private final OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			String id = wheel.getTag().toString();
			if (!wheelScrolled) {
				vibrate();
				if (id.equalsIgnoreCase("1")) {
					int currentvalue = newValue;
					switch (currentvalue) {
					// Loading data two other two wheels
					case 0:
						IntwheelSelector(R.id.p2, strWheelMenu2);
						IntwheelSelector(R.id.p3, strWheelMenu2);
						txtvMain.setText(strWheelMenu1[getWheel(R.id.p1)
								.getCurrentItem()]);
						wheelselection = 1;
						setStrategy(new TemperatureStrategy());
						break;
					case 1:
						IntwheelSelector(R.id.p2, strWheelMenu3);
						IntwheelSelector(R.id.p3, strWheelMenu3);
						txtvMain.setText(strWheelMenu1[getWheel(R.id.p1)
								.getCurrentItem()]);
						wheelselection = 2;
						setStrategy(new LengthStrategy());
						break;
					case 2:
						IntwheelSelector(R.id.p2, strWheelMenu4);
						IntwheelSelector(R.id.p3, strWheelMenu4);
						txtvMain.setText(strWheelMenu1[getWheel(R.id.p1)
								.getCurrentItem()]);
						wheelselection = 3;
						setStrategy(new WeightStrategy());
						break;
					case 3:
						IntwheelSelector(R.id.p2, strWheelMenu5);
						IntwheelSelector(R.id.p3, strWheelMenu5);
						txtvMain.setText(strWheelMenu1[getWheel(R.id.p1)
								.getCurrentItem()]);
						wheelselection = 4;
						setStrategy(new SpeedStrategy());
						break;
					case 4:
						IntwheelSelector(R.id.p2, strWheelMenu6);
						IntwheelSelector(R.id.p3, strWheelMenu6);
						txtvMain.setText(strWheelMenu1[getWheel(R.id.p1)
								.getCurrentItem()]);
						wheelselection = 5;
						setStrategy(new VolumeStrategy());
						break;
					case 5:
						IntwheelSelector(R.id.p2, strWheelMenu7);
						IntwheelSelector(R.id.p3, strWheelMenu7);
						txtvMain.setText(strWheelMenu1[getWheel(R.id.p1)
								.getCurrentItem()]);
						wheelselection = 6;
						setStrategy(new AreaStrategy());
						break;
					case 6:
						IntwheelSelector(R.id.p2, strWheelMenu8);
						IntwheelSelector(R.id.p3, strWheelMenu8);
						txtvMain.setText(strWheelMenu1[getWheel(R.id.p1)
								.getCurrentItem()]);
						wheelselection = 7;
						setStrategy(new MassStrategy());
						break;
					case 7:
						IntwheelSelector(R.id.p2, strWheelMenu9);
						IntwheelSelector(R.id.p3, strWheelMenu9);
						txtvMain.setText(strWheelMenu1[getWheel(R.id.p1)
								.getCurrentItem()]);
						wheelselection = 8;
						setStrategy(new TimeStrategy());
						break;
					default:
						IntwheelSelector(R.id.p2, strWheelMenu2);
						IntwheelSelector(R.id.p3, strWheelMenu2);
						txtvMain.setText(strWheelMenu1[getWheel(R.id.p1)
								.getCurrentItem()]);
						wheelselection = 1;
						setStrategy(new TemperatureStrategy());
						break;
					}
				} else {

					switch (wheelselection) {
					// for getting current items from wheel two and three
					case 1:
						txtvSubone.setText(strWheelMenu2[getWheel(R.id.p2)
								.getCurrentItem()]);
						txtvSubtwo.setText(strWheelMenu2[getWheel(R.id.p3)
								.getCurrentItem()]);
						break;
					case 2:
						txtvSubone.setText(strWheelMenu3[getWheel(R.id.p2)
								.getCurrentItem()]);
						txtvSubtwo.setText(strWheelMenu3[getWheel(R.id.p3)
								.getCurrentItem()]);
						break;
					case 3:
						txtvSubone.setText(strWheelMenu4[getWheel(R.id.p2)
								.getCurrentItem()]);
						txtvSubtwo.setText(strWheelMenu4[getWheel(R.id.p3)
								.getCurrentItem()]);
						break;
					case 4:
						txtvSubone.setText(strWheelMenu5[getWheel(R.id.p2)
								.getCurrentItem()]);
						txtvSubtwo.setText(strWheelMenu5[getWheel(R.id.p3)
								.getCurrentItem()]);
						break;
					case 5:
						txtvSubone.setText(strWheelMenu6[getWheel(R.id.p2)
								.getCurrentItem()]);
						txtvSubtwo.setText(strWheelMenu6[getWheel(R.id.p3)
								.getCurrentItem()]);
						break;
					case 6:
						txtvSubone.setText(strWheelMenu7[getWheel(R.id.p2)
								.getCurrentItem()]);
						txtvSubtwo.setText(strWheelMenu7[getWheel(R.id.p3)
								.getCurrentItem()]);
						break;
					case 7:
						txtvSubone.setText(strWheelMenu8[getWheel(R.id.p2)
								.getCurrentItem()]);
						txtvSubtwo.setText(strWheelMenu8[getWheel(R.id.p3)
								.getCurrentItem()]);
						break;
					case 8:
						txtvSubone.setText(strWheelMenu9[getWheel(R.id.p2)
								.getCurrentItem()]);
						txtvSubtwo.setText(strWheelMenu9[getWheel(R.id.p3)
								.getCurrentItem()]);
						break;
					default:
						txtvSubone.setText(strWheelMenu2[getWheel(R.id.p2)
								.getCurrentItem()]);
						txtvSubtwo.setText(strWheelMenu2[getWheel(R.id.p3)
								.getCurrentItem()]);
						break;
					}

					try {
						unitfrom = txtvSubone.getText().toString();
						unitto = txtvSubtwo.getText().toString();
						if (!txtvInput.getText().toString()
								.equalsIgnoreCase("")) {
							double in = Double.parseDouble(txtvInput.getText()
									.toString());
							double result = currentStrategy.Convert(unitfrom,
									unitto, in);

							txtvResult.setText("" + result);
						}
					} catch (NumberFormatException e) {

						e.printStackTrace();
					}
				}

			}
		}
	};

	public static AndroidQAActivity getInstance() {
		return instance;
	}

	private void setStrategy(Strategy s) {
		lastStrategy = currentStrategy;
		currentStrategy = s;
		// make the last strategy eligible for garbage collection
		lastStrategy = null;
	}

	// Initializing wheels
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void IntwheelSelector(int id, String[] change) {
		WheelView wheel = (WheelView) getView().findViewById(id);
		wheel.setViewAdapter(new ArrayWheelAdapter(ctx, change));
		wheel.setVisibleItems(2);
		wheel.setCurrentItem(0);
		wheel.addChangingListener(changedListener);
		wheel.addScrollingListener(scrolledListener);
	}

	private WheelView getWheel(int id) {

		return (WheelView) getView().findViewById(id);
	}

	@SuppressWarnings("unused")
	private int getWheelValue(int id) {

		return getWheel(id).getCurrentItem();
	}

	// setting the strategy
	public Strategy setStrategy() {
		Strategy strStrategy = null;
		if (strMainwheel.equalsIgnoreCase("Temperature")) {
			strStrategy = new TemperatureStrategy();

		} else if (strMainwheel.equalsIgnoreCase("Length")) {
			strStrategy = new LengthStrategy();

		} else if (strMainwheel.equalsIgnoreCase("Weight")) {
			strStrategy = new WeightStrategy();

		} else if (strMainwheel.equalsIgnoreCase("Speed")) {
			strStrategy = new SpeedStrategy();

		} else if (strMainwheel.equalsIgnoreCase("Volume")) {
			strStrategy = new VolumeStrategy();

		} else if (strMainwheel.equalsIgnoreCase("Area")) {
			strStrategy = new AreaStrategy();

		} else if (strMainwheel.equalsIgnoreCase("Mass")) {
			strStrategy = new MassStrategy();

		} else if (strMainwheel.equalsIgnoreCase("Time")) {
			strStrategy = new TimeStrategy();

		} else {
			strStrategy = new TemperatureStrategy();
		}

		return strStrategy;

	}

	// saving states of the views to preference on stop
	@Override
	public void onStop() {

		SharedPreferences Settings = getActivity().getSharedPreferences(
				PREFERNAME, 0);
		SharedPreferences.Editor editor = Settings.edit();
		// String strName =etxtName.getText().toString();

		String strMainvalue = txtvMain.getText().toString();
		String strSubone = txtvSubone.getText().toString();
		String strSubtwo = txtvSubtwo.getText().toString();
		String strinput = txtvInput.getText().toString();
		String strresult = txtvResult.getText().toString();
		editor.putString("input", strinput);
		editor.putString("result", strresult);
		editor.putString("valuemain", strMainvalue);
		editor.putString("valueone", strSubone);
		editor.putString("valuetwo", strSubtwo);
		editor.commit();
		super.onStop();
	}

	public void findstartvalue()

	{
		int inWheel = 0;
		String strSel[] = new String[] {};

		for (int i = 0; i < strWheelMenu1.length; i++) {
			if (strWheelMenu1[i].equalsIgnoreCase(strMainwheel)) {
				inWheel = i;
				wheelmain.setCurrentItem(i);
			}
		}
		switch (inWheel) {
		case 0:
			strSel = strWheelMenu2;
			break;
		case 1:
			strSel = strWheelMenu3;
			break;
		case 2:
			strSel = strWheelMenu4;
			break;
		case 3:
			strSel = strWheelMenu5;
			break;
		case 4:
			strSel = strWheelMenu6;
			break;
		case 5:
			strSel = strWheelMenu7;
			break;
		case 6:
			strSel = strWheelMenu8;
			break;
		case 7:
			strSel = strWheelMenu9;
			break;
		case 8:
			break;

		}
		for (int i = 0; i < strSel.length; i++) {
			if (strSel[i].equalsIgnoreCase(strWheelone)) {
				wheelone.setCurrentItem(i);

			}
			if (strSel[i].equalsIgnoreCase(strWheeltwo)) {
				wheeltwo.setCurrentItem(i);

			}

		}
	}

	// clicj\k listener for on screen keyboard
	private OnClickListener buttonpad = new OnClickListener() {

		@Override
		public void onClick(View v) {

			vibrate();

			switch (v.getId()) {
			case R.id.btnplusminus:
				plusminus();
				break;
			case R.id.btnclr:
				txtvInput.setText("");
				txtvResult.setText("");
				break;
			case R.id.btnpoint:
				decimalpoints();
				break;
			case R.id.btnundo:
				undo();
				break;
			default:
				String strresult = v.getTag().toString();
				txtvInput.append(strresult);
				break;
			}

		}

		private void plusminus() {

			if (!readyToClear) {
				String txt = txtvInput.getText().toString();
				if (!txt.equals("")) {
					if (txt.charAt(0) == '-')
						txt = txt.substring(1, txt.length());
					else
						txt = "-" + txt;
					txtvInput.setText(txt);
				}
			}
		}

		private void decimalpoints() {

			String txt = txtvInput.getText().toString();
			if (!txt.contains(".")) {
				txtvInput.append(".");
			}
		}

		// to undo the typed
		private void undo() {
			if (!readyToClear) {
				String txt = txtvInput.getText().toString();
				if (txt.length() > 0) {
					txt = txt.substring(0, txt.length() - 1);
					if (txt.equals(""))
						txt = "";
					txtvResult.setText("");
					txtvInput.setText(txt);
				}
			}
		}
	};

}
