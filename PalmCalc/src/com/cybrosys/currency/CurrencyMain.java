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

package com.cybrosys.currency;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import com.actionbarsherlock.app.SherlockFragment;
import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class CurrencyMain extends SherlockFragment {
	SharedPreferences Settings;
	SharedPreferences sharedPrefs = PreferenceManager
			.getDefaultSharedPreferences(PalmCalcActivity.ctx);
	LinearLayout linlaHeaderProgress;
	int InCheck = 1;
	String[] strCrnCode = new String[6];
	int[] fltFlags = new int[6];
	boolean isFlag, isFlag2, isFlag3 = false, isLong = false;
	private Boolean readyToClear = false;
	// public static final String strPREFERNAME = "preference1";
	public static final String strPREFERNAME = "curpref";
	double isAnswer = 0.0;
	static int inLongClickpos;
	ListView lstvMainlistView;
	ImageView imgvHead;
	TextView txtvHead;
	EditText etxtText;
	ImageButton imgbtnRefresh;
	String[] Currency;
	String strDecimals;
	double[] dblCurrencyvalue = new double[6];
	ArrayList<HashMap<String, String>> aList;
	HashMap<String, String> hm;
	ListAdapter1 adapter;
	long lastUpdateTime;
	SlidingDrawer slide;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.currency_final, container, false);
	}

	public void vibrate() {
		Boolean isVibe = sharedPrefs.getBoolean("prefVibe", false);
		Vibrator vibe = (Vibrator) PalmCalcActivity.ctx
				.getSystemService(Context.VIBRATOR_SERVICE);
		if (isVibe) {
			vibe.vibrate(100);
		}
	}

	public void onStart() {
		super.onStart();
		getActivity().getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		linlaHeaderProgress = (LinearLayout) getView().findViewById(
				R.id.linlaHeaderProgress);
		imgvHead = (ImageView) getView().findViewById(R.id.imageHead);
		txtvHead = (TextView) getView().findViewById(R.id.textHead);
		lstvMainlistView = (ListView) getView().findViewById(R.id.listview);
		etxtText = (EditText) getView().findViewById(R.id.editText1);
		slide = (SlidingDrawer) getView().findViewById(R.id.SlidingDrawer);
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
		isFlag2 = true;
		Settings = getActivity().getSharedPreferences(strPREFERNAME, 0);
		for (int inI = 0; inI <= 5; inI++) {
			if (Settings.getString("val" + inI, "").equals("")
					&& Settings.getString("CrnCode" + inI, "").equals("")
					&& Settings.getString("flag" + inI, "").equals("")) {
				isFlag2 = false;
			}

		}
		if (isFlag2 == true) {
			Sharepreferences();
		} else {
			CustomListView();
			if (isConnection() == true) {
			} else {
				isFlag2 = true;
				isFlag = true;
				Toast.makeText(getActivity(), "No Internet Conection",
						Toast.LENGTH_LONG).show();
			}
		}
		SharedPreferences prefs = PalmCalcActivity.ctx.getSharedPreferences(
				"UpdateTime", 0);
		lastUpdateTime = prefs.getLong("lastUpdateTime", 0);
		if ((lastUpdateTime) <= System.currentTimeMillis()) {
			lastUpdateTime = System.currentTimeMillis();
			SharedPreferences.Editor editors = prefs.edit();
			editors.putLong("lastUpdateTime", lastUpdateTime);
			editors.commit();
			if (isConnection() == true) {
				new BackProsess().execute(getActivity());
			}
		}
		etxtText.addTextChangedListener(textwach);
		aList = new ArrayList<HashMap<String, String>>();
		for (int InI = 0; InI < 5; InI++) {
			hm = new HashMap<String, String>();
			hm.put("flag", Integer.toString(fltFlags[InI]));
			if (InI != 5) {
				hm.put("cur", strCrnCode[InI].substring(0, 3));
			} else {
				hm.put("cur", strCrnCode[InI]);
			}
			aList.add(hm);
		}
		txtvHead.setText(strCrnCode[5]);
		imgvHead.setImageResource(fltFlags[5]);
		adapter = new ListAdapter1(getActivity(), aList);
		lstvMainlistView.setAdapter(adapter);
		lstvMainlistView.setOnItemClickListener(ListsingleClick);
		lstvMainlistView.setOnItemLongClickListener(ListLongClick);
		Decimalpoint();
		View vwMain = (View) getActivity().findViewById(R.id.idCurrencyMain);
		vwMain.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				slide.close();
				return true;
			}
		});
		etxtText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!slide.isOpened()) {
					slide.open();
				}
			}
		});
		if (!Settings.getString("Input", "").equals("")) {
			calculation(Double.parseDouble(Settings.getString("Input", "")));
			etxtText.setText("");
			etxtText.setText(Settings.getString("Input", ""));
		} else {
			etxtText.setText(Settings.getString("Input", "1"));
		}
	}

	private void Decimalpoint() {

		Boolean isCheck = sharedPrefs.getBoolean("prefDigit", false);
		int inDecimal = Integer.parseInt(sharedPrefs.getString("prefDec", "2"));
		switch (inDecimal) {
		case 2:
			if (isCheck) {
				strDecimals = "#,##0.##";
			} else {
				strDecimals = "#.##";
			}
			break;
		case 3:
			if (isCheck) {
				strDecimals = "#,##0.###";
			} else {
				strDecimals = "#.###";
			}
			break;
		case 4:
			if (isCheck) {
				strDecimals = "#,##0.####";
			} else {
				strDecimals = "#.####";
			}
			break;
		case 5:
			if (isCheck) {
				strDecimals = "#,##0.#####";
			} else {
				strDecimals = "#.#####";
			}
			break;
		case 6:
			if (isCheck) {
				strDecimals = "#,##0.######";
			} else {
				strDecimals = "#.######";
			}
			break;
		case 7:
			if (isCheck) {
				strDecimals = "#,##0.#######";
			} else {
				strDecimals = "#.#######";
			}
			break;
		default:
			if (isCheck) {
				strDecimals = "#,##0.##";
			} else {
				strDecimals = "#.##";
			}
			break;
		}
	}

	// For updating the Currency values ( if Net connection available ).
	private void onupdate() {
		try {

			if (isFlag == true) {
				if (isConnection() == true) {
					isFlag = false;
					new BackProsess().execute(getActivity());
				} else {
					Toast.makeText(getActivity(), "No Internet Conection",
							Toast.LENGTH_LONG).show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Pop-up button menu click details.
	// This menu is used as a keypad for currency converter. It appears when we
	// click on editText.
	private OnClickListener buttonpad = new OnClickListener() {
		@Override
		public void onClick(View v) {
			vibrate();
			switch (v.getId()) {
			case R.id.btnupdate:
				onupdate();
				break;
			case R.id.btnclr:
				etxtText.setText("");

				break;
			case R.id.btnpoint:
				decimalpoints();
				break;
			case R.id.btnundo:
				undo();
				break;
			case R.id.btndone:
				slide.close();
				break;
			default:
				String strresult = v.getTag().toString();
				etxtText.append(strresult);
				break;
			}
		}

		// Check the decimal points on keypad.
		private void decimalpoints() {
			String txt = etxtText.getText().toString();
			if (!txt.contains(".")) {
				etxtText.append(".");
			}
		}

		// Delete operation on keypad.
		private void undo() {
			if (!readyToClear) {
				String txt = etxtText.getText().toString();
				if (txt.length() > 0) {
					txt = txt.substring(0, txt.length() - 1);
					etxtText.setText(txt);
				}
			}
		}
	};

	// TextWatcher
	// used to change the list of currency values, according to the input value
	TextWatcher textwach = new TextWatcher() {
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

				String strEtextvalue = etxtText.getText().toString();
				if (strEtextvalue.equals("")) {
					strEtextvalue = "0";
				} else if (strEtextvalue.equals(".")) {
					strEtextvalue = "0.";
				}
				isAnswer = Double.parseDouble(strEtextvalue);
				calculation(isAnswer);
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	// Single click with list items
	// used to get the currency value of clicked item as main.
	OnItemClickListener ListsingleClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View container,
				int positon, long itemid) {
			try {

				vibrate();
				if (!slide.isOpened()) {
					if (isFlag == true) {
						long positions = adapter.getItemId(positon);
						int inRowpos = (int) positions;
						double dblCurrencyrate = dblCurrencyvalue[inRowpos];
						String strCurrency = strCrnCode[inRowpos];
						int img = fltFlags[inRowpos];
						dblCurrencyvalue[inRowpos] = dblCurrencyvalue[5];
						strCrnCode[inRowpos] = strCrnCode[5];
						fltFlags[inRowpos] = fltFlags[5];
						dblCurrencyvalue[5] = dblCurrencyrate;
						strCrnCode[5] = strCurrency;
						fltFlags[5] = img;
						calculation(isAnswer);
						txtvHead.setText(strCrnCode[5]);
						imgvHead.setImageResource(fltFlags[5]);
						adapter.notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), " Wait For Update ",
								Toast.LENGTH_LONG).show();
					}
				} else {
					slide.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	// Long click with list items
	// used to change the country lists on the main screen.
	OnItemLongClickListener ListLongClick = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View container,
				int positon, long itemid) {
			try {
				vibrate();
				inLongClickpos = (int) adapter.getItemId(positon);
				if (isFlag == true) {
					Intent itntlist = new Intent(getActivity(),
							Currency_Transprentlist.class);
					itntlist.putExtra("currencyitem",
							strCrnCode[inLongClickpos]);
					itntlist.putExtra("currencyflag", fltFlags[inLongClickpos]);
					itntlist.putExtra("currencyRates",
							dblCurrencyvalue[inLongClickpos]);
					isLong = true;
					getActivity().startActivity(itntlist);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	};

	// Internet connection check
	public final boolean isConnection() {
		@SuppressWarnings("static-access")
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(getActivity().CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}
		NetworkInfo mobileNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}
		return false;
	}

	private void Sharepreferences() {
		try {
			SharedPreferences Settings = getActivity().getSharedPreferences(
					strPREFERNAME, 0);
			for (int inI = 0; inI <= 5; inI++) {
				if (isFlag2 == true) {
					fltFlags[inI] = Integer.parseInt(Settings.getString("flag"
							+ inI, ""));
					strCrnCode[inI] = Settings.getString("CrnCode" + inI, "");
				}
				dblCurrencyvalue[inI] = Double.parseDouble(Settings.getString(
						"val" + inI, ""));
			}
			isFlag = true;
			if (isFlag3 == true) {
				calculation(isAnswer);
				isFlag3 = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		try {
			if (isLong == true) {
				if (!Currency_Transprentlist.Currencyname.equals("")) {
					strCrnCode[inLongClickpos] = Currency_Transprentlist.Currencyname;
					fltFlags[inLongClickpos] = Currency_Transprentlist.CurrencyFlagpars;
					dblCurrencyvalue[inLongClickpos] = Currency_Transprentlist.CurrencyRate;
					if (isConnection() == true) {
						new BackProsess().execute(getActivity());
					}
					calculation(isAnswer);
				}
			}
			Currency_Transprentlist.Currencyname = "";
			Currency_Transprentlist.CurrencyFlagpars = 0;
			Currency_Transprentlist.CurrencyRate = 0;
			isLong = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CustomListView() {
		strCrnCode = new String[] { "EUR-Euro", "GBP-British Pound",
				"CAD-Canadian Dollar", "AUD-Australian Dollar",
				"CHF-Swiss Franc", "USD-US Dollar" };
		fltFlags = new int[] { R.drawable.eur, R.drawable.gbp, R.drawable.cad,
				R.drawable.aud, R.drawable.chf, R.drawable.usd };
		dblCurrencyvalue = new double[] { 0.767224183, 0.660894852, 1.02689964,
				0.977326036, 0.941299615, 1 };
	}

	// Used to check the BigDecimal values
	// ( BigDecimal Numbers : Immutable, arbitrary-precision signed decimal
	// numbers. )
	private void calculation(double input) {
		try {
			BigDecimal obj_0 = new BigDecimal(input * dblCurrencyvalue[0]
					/ dblCurrencyvalue[5]);

			BigDecimal obj_1 = new BigDecimal(input * dblCurrencyvalue[1]
					/ dblCurrencyvalue[5]);

			BigDecimal obj_2 = new BigDecimal(input * dblCurrencyvalue[2]
					/ dblCurrencyvalue[5]);

			BigDecimal obj_3 = new BigDecimal(input * dblCurrencyvalue[3]
					/ dblCurrencyvalue[5]);

			BigDecimal obj_4 = new BigDecimal(input * dblCurrencyvalue[4]
					/ dblCurrencyvalue[5]);

			NumberFormat formatter = new DecimalFormat(strDecimals);
			String strConversionResult0 = formatter.format(obj_0);
			String strConversionResult1 = formatter.format(obj_1);
			String strConversionResult2 = formatter.format(obj_2);
			String strConversionResult3 = formatter.format(obj_3);
			String strConversionResult4 = formatter.format(obj_4);
			Currency = new String[] { strConversionResult0,
					strConversionResult1, strConversionResult2,
					strConversionResult3, strConversionResult4 };
			hm.clear();
			aList.clear();
			for (int i = 0; i < 5; i++) {
				hm = new HashMap<String, String>();
				hm.put("flag", Integer.toString(fltFlags[i]));
				hm.put("txt", Currency[i]);
				if (i != 5) {
					hm.put("cur", strCrnCode[i].substring(0, 3));
				} else {
					hm.put("cur", strCrnCode[i]);
				}
				aList.add(hm);
			}
			adapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressLint("CommitPrefEdits")
	public void onStop() {
		super.onStop();
		try {

			Settings = getActivity().getSharedPreferences(strPREFERNAME, 0);
			SharedPreferences.Editor editor = Settings.edit();
			for (int i = 0; i <= 5; i++) {
				editor.putString("flag" + i, "" + fltFlags[i]);
				editor.putString("val" + i, "" + dblCurrencyvalue[i]);
				editor.putString("CrnCode" + i, strCrnCode[i]);
			}
			editor.putString("Input", etxtText.getText().toString());
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Getting the currency rate from Google Calculator
	public String Currencyrate() {
		String strC = null;
		String url = "http://www.google.com/ig/calculator?hl=en&q=USD=?"
				+ strCrnCode[0].subSequence(0, 3);
		Currency_JSONParsor jparser = new Currency_JSONParsor();
		JSONObject json = jparser.getJSONformUrl(url);
		try {
			strC = (String) json.get("rhs");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return strC;
	}

	// Getting the currency rate from Google Calculator
	public String Currencyrate1() {
		String strC = null;
		String strUrl = "http://www.google.com/ig/calculator?hl=en&q=USD=?"
				+ strCrnCode[1].subSequence(0, 3);
		Currency_JSONParsor jparser = new Currency_JSONParsor();
		JSONObject json = jparser.getJSONformUrl(strUrl);
		if (json.has("rhs")) {
			try {
				strC = (String) json.get("rhs");

			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
		return strC;

	}

	// Getting the currency rate from Google Calculator
	public String Currencyrate2() {
		String strC = null;
		JSONObject json = null;
		Currency_JSONParsor jparser;
		String strUrl = "http://www.google.com/ig/calculator?hl=en&q=USD=?"
				+ strCrnCode[2].subSequence(0, 3);
		jparser = new Currency_JSONParsor();
		json = jparser.getJSONformUrl(strUrl);
		if (json.has("rhs")) {

			try {
				strC = (String) json.get("rhs");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return strC;
	}

	// Getting the currency rate from Google Calculator
	public String Currencyrate3() {
		String strC = null;
		String strUrl = "http://www.google.com/ig/calculator?hl=en&q=USD=?"
				+ strCrnCode[3].subSequence(0, 3);
		Currency_JSONParsor jparser = new Currency_JSONParsor();
		JSONObject json = jparser.getJSONformUrl(strUrl);
		if (json.has("rhs")) {
			try {
				strC = (String) json.get("rhs");
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
		return strC;
	}

	// Getting the currency rate from Google Calculator
	public String Currencyrate4() {
		String strC = null;
		String strUrl = "http://www.google.com/ig/calculator?hl=en&q=USD=?"
				+ strCrnCode[4].subSequence(0, 3);
		Currency_JSONParsor jparser = new Currency_JSONParsor();
		JSONObject json = jparser.getJSONformUrl(strUrl);
		if (json.has("rhs")) {
			try {
				strC = (String) json.get("rhs");

			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
		return strC;
	}

	// Getting the currency rate from Google Calculator
	public String Currencyrate5() {
		String strC = null;
		String strUrl = "http://www.google.com/ig/calculator?hl=en&q=USD=?"
				+ strCrnCode[5].subSequence(0, 3);
		Currency_JSONParsor jparser = new Currency_JSONParsor();
		JSONObject json = jparser.getJSONformUrl(strUrl);
		if (json.has("rhs")) {
			try {
				strC = (String) json.get("rhs");

			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
		return strC;
	}

	String strTempcr = null;

	// This BackProcess class is used for updating the Currency values.
	// ( If Internet connection available ). Its done on background,
	// without disturbing the forground process.
	class BackProsess extends AsyncTask<Context, Integer, String> {

		@Override
		protected void onPreExecute() {

			linlaHeaderProgress.setVisibility(View.VISIBLE);

			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Context... params) {

			isFlag = false;
			try {

				String value1 = Currencyrate();
				String value2 = Currencyrate1();
				String value3 = Currencyrate2();
				String value4 = Currencyrate3();
				String value5 = Currencyrate4();
				String value6 = Currencyrate5();

				return value1 + "-" + value2 + "-" + value3 + "-" + value4
						+ "-" + value5 + "-" + value6;
			} catch (Exception e) {
				e.printStackTrace();
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getActivity(), "Connection Error",
								Toast.LENGTH_SHORT).show();
					}
				});

				return "";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				if (!result.equals("")) {
					String[] strS = result.split("-");
					boolean isNull = true;
					for (int i = 0; i < strS.length; i++) {
						if (strS[i].equals("")) {
							isNull = false;
						}
					}
					if (isNull == true) {

						for (int i = 0; i <= 5; i++) {
							dblCurrencyvalue[i] = Double.parseDouble(strS[i]
									.substring(0, strS[i].indexOf(" "))
									.replaceAll("[^\\d.]", ""));
						}
						isFlag2 = false;
						isFlag3 = true;
					}
				}
				isFlag = true;
				linlaHeaderProgress.setVisibility(View.GONE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
