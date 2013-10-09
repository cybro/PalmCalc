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

package com.cybrosys.tip;

import java.text.NumberFormat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;
import com.cybrosys.unit.ArrayWheelAdapter;
import com.cybrosys.unit.OnWheelChangedListener;
import com.cybrosys.unit.OnWheelScrollListener;
import com.cybrosys.unit.WheelView;

public class TipActivity extends SherlockFragment

{
	public static String strWhealFontSize = "20";
	double tipAmount;
	double totalToPay;
	double perPersonPays;
	private boolean wheelScrolled = false;
	String strDecimals;
	int wheelselection = 0;
	String wheelMenuTipcntry[] = new String[] { "ARGENTINA", "AUSTRALIA",
			"AUSTRIA", "BOLIVIA", "BOSUIA", "CANADA", "CHILE", "COLOMBIA",
			"CROATIA", "CZECH REPUBLIC", "EGYPT", "FRANCE", "GERMANY", "INDIA",
			"IRELAND", "ISRAEL", "MEXICO", "NETHERLANDS", "NEW ZEALAND",
			"PAKISTAN", "POLAND", "ROMANIA", "SLOVENIA", "SWITZERLAND",
			"TURKEY", "UK", "US", "OTHERS" };
	String wheelMenuPercentage1[] = new String[] { "10" };
	String wheelMenuPercentage2[] = new String[] { "10", "11", "12", "13",
			"14", "15" };
	String wheelMenuPercentage3[] = new String[] { "5", "6", "7", "8", "9",
			"10" };
	String wheelMenuPercentage4[] = new String[] { "10", "11", "12", "13",
			"14", "15", "16", "17", "18", "19", "20" };
	String wheelMenuPercentage5[] = new String[] { "5" };
	String wheelMenuPercentage6[] = new String[] { "15" };
	String wheelMenuPercentage7[] = new String[] { "2", "3", "4", "5" };
	String wheelMenuPercentage8[] = new String[] { "10", "11", "12" };
	String wheelMenuPercentage9[] = new String[] { "3", "4", "5" };
	String wheelMenuPercentage10[] = new String[] { "15", "16", "17", "18",
			"19", "20" };
	String wheelMenuPercentage11[] = new String[] { "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
			"18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
			"29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
			"40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
			"51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61",
			"62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72",
			"73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83",
			"84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94",
			"95", "96", "97", "98", "99" };
	String wheelMenusplt[] = new String[] { "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
			"19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
			"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
			"41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51",
			"52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62",
			"63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73",
			"74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84",
			"85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95",
			"96", "97", "98", "99" };
	private Boolean readyToClear = false;
	Double d;
	private Context mContext;
	final static int DEFAULT_NUM_PEOPLE = 1;
	final static NumberFormat formatter = NumberFormat.getCurrencyInstance();
	@SuppressWarnings("unused")
	private int selected = 0;
	@SuppressWarnings("unused")
	private int buffKey = 0;
	String samnt;
	double amnt;
	String sPeople;
	int people;
	String sprcntg;
	int prcntg;
	protected TextView txtvTipAmount;
	protected TextView txtvTotalToPay;
	protected TextView txtvTipperperson;
	private EditText etxtcntry;
	private EditText etxtprcntg;
	private EditText etxtsplt;
	protected TextView txtvAmnt;
	WheelView wheelcntry;
	WheelView wheelprcntg;
	WheelView wheelsplt;
	public String PREFS_NAME = "LoginPrefs";
	String PREFERNAME = "tipcalc";
	SharedPreferences settings;
	String strcntry;
	String strprcntg;
	View vwMain;
	String strsplt;
	String strInput = "";
	String strInput2 = "";
	String strInput3 = "";
	String strrInput2 = "";
	String strrInput3 = "";
	String strrInput4 = "";
	SharedPreferences sharedPrefs = PreferenceManager
			.getDefaultSharedPreferences(PalmCalcActivity.ctx);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	public void vibrate() {
		Boolean isVibe = sharedPrefs.getBoolean("prefVibe", false);
		Vibrator vibe = (Vibrator) PalmCalcActivity.ctx
				.getSystemService(Context.VIBRATOR_SERVICE);
		if (isVibe) {
			vibe.vibrate(100);
		}
	}

	public void vibrateWheel() {
		Boolean isVibe = sharedPrefs.getBoolean("prefVibe", false);
		Vibrator vibe = (Vibrator) PalmCalcActivity.ctx
				.getSystemService(Context.VIBRATOR_SERVICE);
		if (isVibe) {
			vibe.vibrate(30);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.tip_activity, container, false);
	}

	@Override
	public void onStart() {
		try {
			super.onStart();
			txtvAmnt = (TextView) getView().findViewById(R.id.txtvAmnt);
			mContext = getActivity();
			etxtcntry = (EditText) getView().findViewById(R.id.etxtcntry);
			etxtprcntg = (EditText) getView().findViewById(R.id.etxtprcntg);
			etxtsplt = (EditText) getView().findViewById(R.id.etxtsplt);
			SharedPreferences Settings = getActivity().getSharedPreferences(
					PREFERNAME, 0);
			txtvAmnt.addTextChangedListener(amntwatcher);
			etxtsplt.addTextChangedListener(peoplewatcher);
			etxtprcntg.addTextChangedListener(prcntgwatcher);
			strcntry = Settings.getString("valuemain", "");
			strprcntg = Settings.getString("valueone", "");
			strsplt = Settings.getString("valuetwo", "");
			wheelcntry = (WheelView) getView().findViewById(R.id.tipcntry);
			wheelprcntg = (WheelView) getView()
					.findViewById(R.id.tippercentage);
			wheelsplt = (WheelView) getView().findViewById(R.id.tipsplt);
			txtvTipAmount = (TextView) getView()
					.findViewById(R.id.txtTipAmount);
			txtvTotalToPay = (TextView) getView().findViewById(
					R.id.txtTotalToPay);
			txtvTipperperson = (TextView) getView().findViewById(
					R.id.txtTipPerPerson);
			initWheel3(R.id.tipcntry, wheelMenuTipcntry);
			initWheel3(R.id.tippercentage, wheelMenuPercentage1);
			initWheel3(R.id.tipsplt, wheelMenusplt);
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
			txtvAmnt.setText(strInput);
			etxtprcntg.setText(strInput2);
			etxtsplt.setText(strInput3);
			txtvTipAmount.setText(strrInput2);
			txtvTotalToPay.setText(strrInput3);
			txtvTipperperson.setText(strrInput4);
			findstartvalue();
		} catch (Exception e) {
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		try {
			super.onActivityCreated(savedInstanceState);
			if (savedInstanceState != null) {
				strInput = savedInstanceState.getString("input");
				strInput2 = savedInstanceState.getString("input2");
				strInput3 = savedInstanceState.getString("input3");
				strrInput2 = savedInstanceState.getString("result1");
				strrInput3 = savedInstanceState.getString("result2");
				strrInput4 = savedInstanceState.getString("result3");
				txtvAmnt.setText(strInput);
				etxtprcntg.setText(strInput2);
				etxtsplt.setText(strInput3);
				txtvTipAmount.setText(strrInput2);
				txtvTotalToPay.setText(strrInput3);
				txtvTipperperson.setText(strrInput4);
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		try {
			super.onSaveInstanceState(outState);
			outState.putString("input", txtvAmnt.getText().toString());
			outState.putString("input2", etxtprcntg.getText().toString());
			outState.putString("input3", etxtsplt.getText().toString());
			outState.putString("result1", txtvTipAmount.getText().toString());
			outState.putString("result2", txtvTotalToPay.getText().toString());
			outState.putString("result3", txtvTipperperson.getText().toString());
		} catch (Exception e) {
		}
	}

	TextWatcher amntwatcher = new TextWatcher() {
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		public void afterTextChanged(Editable arg0) {
			try {

				if (txtvAmnt.getText().length() != 0) {
					txtvTipAmount.setText("");
					txtvTotalToPay.setText("");
					txtvTipperperson.setText("");
					amount();
				}
			} catch (Exception e) {
			}
		}
	};
	TextWatcher peoplewatcher = new TextWatcher() {
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		public void afterTextChanged(Editable arg0) {
			try {
				if (txtvAmnt.getText().length() != 0) {
					txtvTipAmount.setText("");
					txtvTotalToPay.setText("");
					txtvTipperperson.setText("");
					people();
				}
			} catch (Exception e) {
			}
		}
	};
	TextWatcher prcntgwatcher = new TextWatcher() {
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		public void afterTextChanged(Editable arg0) {
			try {
				if (txtvAmnt.getText().length() != 0) {
					txtvTipAmount.setText("");
					txtvTotalToPay.setText("");
					txtvTipperperson.setText("");
					percentage();
				}
			} catch (Exception e) {
			}
		}
	};

	public void amount() {
		try {
			samnt = "";
			sPeople = "";
			sprcntg = "";
			samnt = txtvAmnt.getText().toString();
			sPeople = etxtsplt.getText().toString();
			sprcntg = etxtprcntg.getText().toString();
			if ((samnt != "" || !(samnt.equals("")))
					&& (!sPeople.equals("") || (sPeople != "") || sprcntg != "" || !(sprcntg
							.equals(""))
							&& samnt.length() >= 1
							&& sPeople.length() >= 1 && sprcntg.length() >= 1)) {
				amnt = Double.parseDouble(samnt);
				people = Integer.parseInt(sPeople);
				prcntg = Integer.parseInt(sprcntg);
				tipAmount = ((amnt * prcntg) / 100);
				totalToPay = amnt + tipAmount;
				perPersonPays = totalToPay / people;
				/*
				 * Boolean isCheck=sharedPrefs.getBoolean("prefDigit", false);
				 * int
				 * inDecimal=Integer.parseInt(sharedPrefs.getString("prefDec",
				 * "2")); switch(inDecimal) { case 2: if(isCheck==true) {
				 * strDecimals="#,##0.##"; } else { strDecimals="#.##"; } break;
				 * case 3: if(isCheck==true) { strDecimals="#,##0.###"; } else {
				 * strDecimals="#.###"; } break; case 4: if(isCheck==true) {
				 * strDecimals="#,##0.####"; } else { strDecimals="#.####"; }
				 * break; case 5: if(isCheck==true) { strDecimals="#,##0.#####";
				 * } else { strDecimals="#.#####"; } break; case 6:
				 * if(isCheck==true) { strDecimals="#,##0.######"; } else {
				 * strDecimals="#.######"; } break; case 7: if(isCheck==true) {
				 * strDecimals="#,##0.#######"; } else {
				 * strDecimals="#.#######"; } break; default: if(isCheck==true)
				 * { strDecimals="#,##0.##"; } else { strDecimals="#.##"; }
				 * break; }
				 */
				/*
				 * NumberFormat numberFormatformatter = new
				 * DecimalFormat(strDecimals); String strFormattedTipAmount =
				 * numberFormatformatter.format(tipAmount); String
				 * strformattedTotalToPay =
				 * numberFormatformatter.format(totalToPay); String
				 * strformattedTipperperson=
				 * numberFormatformatter.format(perPersonPays);
				 */
				/*
				 * txtvTipAmount.setText(formatter.format(tipAmount));
				 * txtvTotalToPay.setText(formatter.format(totalToPay));
				 * txtvTipperperson.setText(formatter.format(perPersonPays));
				 */
				try {
					txtvTipAmount.setText("" + tipAmount);
					txtvTotalToPay.setText("" + totalToPay);
					txtvTipperperson.setText("" + perPersonPays);
				} catch (Exception e) {
				}
			} else if (amnt == 0 || samnt == null) {
				tipAmount = 0;
				totalToPay = 0;
				perPersonPays = 0;
			} else {
				Toast.makeText(getActivity(), "enter value for all field ",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
		}
	}

	public void people() {
		try {
			samnt = "";
			sPeople = "";
			sprcntg = "";
			sPeople = etxtsplt.getText().toString();
			people = Integer.parseInt(sPeople);
			if ((samnt != null || !(samnt.equals("")))
					&& (!sPeople.equals("") || (sPeople != null)
							|| sprcntg != null || !(sprcntg.equals(""))
							&& samnt.length() >= 1 && sPeople.length() >= 1
							&& sprcntg.length() >= 1)) {
				tipAmount = ((amnt * prcntg) / 100);
				totalToPay = amnt + tipAmount;
				perPersonPays = totalToPay / people;
				/*
				 * txtvTipAmount.setText(formatter.format(tipAmount));
				 * txtvTotalToPay.setText(formatter.format(totalToPay));
				 * txtvTipperperson.setText(formatter.format(perPersonPays));
				 */

				/*
				 * Boolean isCheck=sharedPrefs.getBoolean("prefDigit", false);
				 * int
				 * inDecimal=Integer.parseInt(sharedPrefs.getString("prefDec",
				 * "2")); switch(inDecimal) { case 2: if(isCheck==true) {
				 * strDecimals="#,##0.##"; } else { strDecimals="#.##"; } break;
				 * case 3: if(isCheck==true) { strDecimals="#,##0.###"; } else {
				 * strDecimals="#.###"; } break; case 4: if(isCheck==true) {
				 * strDecimals="#,##0.####"; } else { strDecimals="#.####"; }
				 * break; case 5: if(isCheck==true) { strDecimals="#,##0.#####";
				 * } else { strDecimals="#.#####"; } break; case 6:
				 * if(isCheck==true) { strDecimals="#,##0.######"; } else {
				 * strDecimals="#.######"; } break; case 7: if(isCheck==true) {
				 * strDecimals="#,##0.#######"; } else {
				 * strDecimals="#.#######"; } break; default: if(isCheck==true)
				 * { strDecimals="#,##0.##"; } else { strDecimals="#.##"; }
				 * break; }
				 */
				/*
				 * NumberFormat numberFormatformatter = new
				 * DecimalFormat(strDecimals); String strFormattedTipAmount =
				 * numberFormatformatter.format(tipAmount); String
				 * strformattedTotalToPay =
				 * numberFormatformatter.format(totalToPay); String
				 * strformattedTipperperson=
				 * numberFormatformatter.format(perPersonPays);
				 */
				try {
					txtvTipAmount.setText("" + tipAmount);
					txtvTotalToPay.setText("" + totalToPay);
					txtvTipperperson.setText("" + perPersonPays);
				} catch (Exception e) {
				}
			} else if (amnt == 0 || samnt == null) {
				tipAmount = 0;
				totalToPay = 0;
				perPersonPays = 0;
			} else {
				Toast.makeText(getActivity(), "enter value for all field ",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
		}
	}

	public void percentage() {
		try {
			samnt = "";
			sPeople = "";
			sprcntg = "";
			samnt = txtvAmnt.getText().toString();
			sPeople = etxtsplt.getText().toString();
			sprcntg = etxtprcntg.getText().toString();
			if ((samnt != "" || !(samnt.equals("")))
					&& (!sPeople.equals("") || (sPeople != "") || sprcntg != "" || !(sprcntg
							.equals(""))
							&& samnt.length() >= 1
							&& sPeople.length() >= 1 && sprcntg.length() >= 1)) {
				amnt = Double.parseDouble(samnt);
				people = Integer.parseInt(sPeople);
				prcntg = Integer.parseInt(sprcntg);
				tipAmount = ((amnt * prcntg) / 100);
				totalToPay = amnt + tipAmount;
				perPersonPays = totalToPay / people;
				/*
				 * txtvTipAmount.setText(formatter.format(tipAmount));
				 * txtvTotalToPay.setText(formatter.format(totalToPay));
				 * txtvTipperperson.setText(formatter.format(perPersonPays));
				 */
				/*
				 * Boolean isCheck=sharedPrefs.getBoolean("prefDigit", false);
				 * int
				 * inDecimal=Integer.parseInt(sharedPrefs.getString("prefDec",
				 * "2")); switch(inDecimal) { case 2: if(isCheck==true) {
				 * strDecimals="#,##0.##"; } else { strDecimals="#.##"; } break;
				 * case 3: if(isCheck==true) { strDecimals="#,##0.###"; } else {
				 * strDecimals="#.###"; } break; case 4: if(isCheck==true) {
				 * strDecimals="#,##0.####"; } else { strDecimals="#.####"; }
				 * break; case 5: if(isCheck==true) { strDecimals="#,##0.#####";
				 * } else { strDecimals="#.#####"; } break; case 6:
				 * if(isCheck==true) { strDecimals="#,##0.######"; } else {
				 * strDecimals="#.######"; } break; case 7: if(isCheck==true) {
				 * strDecimals="#,##0.#######"; } else {
				 * strDecimals="#.#######"; } break; default: if(isCheck==true)
				 * { strDecimals="#,##0.##"; } else { strDecimals="#.##"; }
				 * break; }
				 */
				/*
				 * NumberFormat numberFormatformatter = new
				 * DecimalFormat(strDecimals); String strFormattedTipAmount =
				 * numberFormatformatter.format(tipAmount); String
				 * strformattedTotalToPay =
				 * numberFormatformatter.format(totalToPay); String
				 * strformattedTipperperson=
				 * numberFormatformatter.format(perPersonPays);
				 */
				try {
					txtvTipAmount.setText("" + tipAmount);
					txtvTotalToPay.setText("" + totalToPay);
					txtvTipperperson.setText("" + perPersonPays);
				} catch (Exception e) {
				}
			} else if (amnt == 0 || samnt == null) {
				tipAmount = 0;
				totalToPay = 0;
				perPersonPays = 0;
			} else {
				Toast.makeText(getActivity(), "enter value for all field ",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
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
		SharedPreferences Settings = getActivity().getSharedPreferences(
				PREFERNAME, 0);
		SharedPreferences.Editor editor = Settings.edit();
		String substrcntry = etxtcntry.getText().toString();
		String substrprcntg = etxtprcntg.getText().toString();
		String substrsplt = etxtsplt.getText().toString();
		editor.putString("valuemain", substrcntry);
		editor.putString("valueone", substrprcntg);
		editor.putString("valuetwo", substrsplt);
		editor.commit();
		super.onStop();
	}

	public void findstartvalue() {
		etxtcntry.setText(wheelMenuTipcntry[getWheel(R.id.tipcntry)
				.getCurrentItem()]);
		etxtprcntg.setText(wheelMenuPercentage1[getWheel(R.id.tippercentage)
				.getCurrentItem()]);
		etxtsplt.setText(wheelMenusplt[getWheel(R.id.tipsplt).getCurrentItem()]);
		try {
			int inWheel = 0;
			String strSel[] = null;
			for (int i = 1; i < wheelMenuTipcntry.length; i++) {
				if (wheelMenuTipcntry[i].equalsIgnoreCase(strcntry)) {
					inWheel = i;
					wheelcntry.setCurrentItem(i);
				}
			}
			switch (inWheel) {
			case 0:
				strSel = wheelMenuPercentage1;
				break;
			case 1:
				strSel = wheelMenuPercentage2;
				break;
			case 2:
				strSel = wheelMenuPercentage3;
				break;
			case 3:
				strSel = wheelMenuPercentage4;
				break;
			case 4:
				strSel = wheelMenuPercentage5;
				break;
			case 5:
				strSel = wheelMenuPercentage6;
				break;
			case 6:
				strSel = wheelMenuPercentage7;
				break;
			case 7:
				strSel = wheelMenuPercentage8;
				break;
			case 8:
				strSel = wheelMenuPercentage9;
				break;
			case 9:
				strSel = wheelMenuPercentage10;
				break;
			case 10:
				strSel = wheelMenuPercentage11;
				break;
			case 11:
				strSel = wheelMenusplt;
				break;
			}
			for (int i = 1; i < strSel.length; i++) {
				if (strSel[i].equalsIgnoreCase(strprcntg)) {
					wheelprcntg.setCurrentItem(i);
				}
				if (strSel[i].equalsIgnoreCase(strsplt)) {
					wheelsplt.setCurrentItem(i);
				}
			}
		} catch (Exception e) {
		}
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
	private final OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			try {
				String id = wheel.getTag().toString();
				if (!wheelScrolled) {
					if (id.equalsIgnoreCase("1")) {
						int currentvalue = newValue;
						newValue = 0;
						switch (currentvalue) {
						case 0:
							initWheel3(R.id.tippercentage, wheelMenuPercentage1);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 0;
							break;
						case 1:
							initWheel3(R.id.tippercentage, wheelMenuPercentage2);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage2[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 1;
							break;
						case 2:
							initWheel3(R.id.tippercentage, wheelMenuPercentage3);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage3[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 2;
							break;
						case 3:
							initWheel3(R.id.tippercentage, wheelMenuPercentage5);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage5[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 3;
							break;
						case 4:
							initWheel3(R.id.tippercentage, wheelMenuPercentage4);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage4[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 4;
							break;
						case 5:
							initWheel3(R.id.tippercentage, wheelMenuPercentage1);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 5;
							break;
						case 6:
							initWheel3(R.id.tippercentage, wheelMenuPercentage1);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 6;
							break;
						case 7:
							initWheel3(R.id.tippercentage, wheelMenuPercentage1);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 7;
							break;
						case 8:
							initWheel3(R.id.tippercentage, wheelMenuPercentage9);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage9[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 8;
							break;
						case 9:
							initWheel3(R.id.tippercentage, wheelMenuPercentage1);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 9;
							break;
						case 10:
							initWheel3(R.id.tippercentage, wheelMenuPercentage3);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage3[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 10;
							break;
						case 11:
							initWheel3(R.id.tippercentage, wheelMenuPercentage1);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 11;
							break;
						case 12:
							initWheel3(R.id.tippercentage, wheelMenuPercentage3);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage3[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 12;
							break;
						case 13:
							initWheel3(R.id.tippercentage, wheelMenuPercentage2);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage2[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 13;
							break;
						case 14:
							initWheel3(R.id.tippercentage, wheelMenuPercentage1);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 14;
							break;
						case 15:
							initWheel3(R.id.tippercentage, wheelMenuPercentage8);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage8[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 15;
							break;
						case 16:
							initWheel3(R.id.tippercentage, wheelMenuPercentage2);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage2[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 16;
							break;
						case 17:
							initWheel3(R.id.tippercentage, wheelMenuPercentage7);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage7[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 17;
							break;
						case 18:
							initWheel3(R.id.tippercentage, wheelMenuPercentage2);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage2[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 18;
							break;

						case 19:
							initWheel3(R.id.tippercentage, wheelMenuPercentage3);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage3[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 20;
							break;
						case 20:
							initWheel3(R.id.tippercentage, wheelMenuPercentage1);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 21;
							break;
						case 21:
							initWheel3(R.id.tippercentage, wheelMenuPercentage1);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 22;
							break;
						case 22:
							initWheel3(R.id.tippercentage, wheelMenuPercentage4);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage4[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 23;
							break;
						case 23:
							initWheel3(R.id.tippercentage, wheelMenuPercentage6);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage6[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 24;
							break;
						case 24:
							initWheel3(R.id.tippercentage, wheelMenuPercentage3);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage3[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 25;
							break;
						case 25:
							initWheel3(R.id.tippercentage, wheelMenuPercentage1);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 26;
							break;
						case 26:
							initWheel3(R.id.tippercentage,
									wheelMenuPercentage10);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage10[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 27;
							break;
						case 27:
							initWheel3(R.id.tippercentage,
									wheelMenuPercentage11);
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage11[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							wheelselection = 19;
							break;
						}
					} else {
						switch (wheelselection) {
						case 0:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 1:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage2[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 2:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage3[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 3:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage5[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 4:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage4[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 5:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 6:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 7:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 8:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage9[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 9:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 10:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage3[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 11:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 12:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage3[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 13:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage2[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 14:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 15:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage8[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 16:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage2[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 17:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage7[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 18:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage2[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 19:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage3[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 20:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 21:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 22:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage4[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 23:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage6[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 24:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage3[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 25:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage1[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 26:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage10[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;
						case 27:
							etxtcntry.setText(wheelMenuTipcntry[getWheel(
									R.id.tipcntry).getCurrentItem()]);
							etxtprcntg.setText(wheelMenuPercentage11[getWheel(
									R.id.tippercentage).getCurrentItem()]);
							etxtsplt.setText(wheelMenusplt[getWheel(
									R.id.tipsplt).getCurrentItem()]);
							break;

						}
					}
				}
			} catch (Exception e) {
			}
		}
	};
	private OnClickListener buttonpad = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				vibrate();
				switch (v.getId()) {
				case R.id.btnplusminus:
					plusminus();
					break;
				case R.id.btnclr:
					txtvAmnt.setText("");
					txtvTotalToPay.setText("");
					txtvTipAmount.setText("");
					txtvTipperperson.setText("");

					break;
				case R.id.btnpoint:
					decimalpoints();
					break;
				case R.id.btnundo:
					undo();

					break;
				default:
					String strresult = v.getTag().toString();
					txtvAmnt.append(strresult);
					break;
				}
			} catch (Exception e) {
			}
		}

		private void plusminus() {
			try {
				if (!readyToClear) {
					String txt = txtvAmnt.getText().toString();
					if (!txt.equals("")) {
						if (txt.charAt(0) == 00)
							txt = txt.substring(1, txt.length());
						else
							txt = txt + "00";
						txtvAmnt.setText(txt);
					} else {
						txtvAmnt.setText("00");
					}
				}

				// txtvAmnt.setText("00");
			} catch (Exception e) {
			}
		}

		private void decimalpoints() {
			String txt = txtvAmnt.getText().toString();
			if (!txt.contains(".")) {
				txtvAmnt.append(".");
			}
		}

		private void undo() {
			if (!readyToClear) {
				String txt = txtvAmnt.getText().toString();
				if (txt.length() > 0) {
					txt = txt.substring(0, txt.length() - 1);
					if (txt.equals(""))
						txt = "";
					txtvAmnt.setText(txt);
				}
				if (txtvAmnt.getText().length() == 0) {
					txtvTotalToPay.setText("");
					txtvTipAmount.setText("");
					txtvTipperperson.setText("");
				}
			}
		}
	};
}