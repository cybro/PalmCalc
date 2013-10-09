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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

// Class used for getting the whole country lists in this world.
public class Currency_Transprentlist extends Activity {
	ArrayList<HashMap<String, String>> aList1;
	ListAdapter2 adapter1;
	ListView listview1;
	String[] Allcountries = new String[84];
	int[] Allflags = new int[84];
	double[] dblRate = new double[84];
	boolean ispreferFlag;
	public static final String PREFERNAME2 = "curTranList";
	// public static final String PREFERNAME2="preferenceAllcurrency";
	public static String Currencyname;
	public static int CurrencyFlagpars;
	public static double CurrencyRate;

	// method used for screen orientation and window-fullscreen settings
	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(PalmCalcActivity.ctx);
		int inOrien = Integer.parseInt(sharedPrefs.getString("prefOrientation",
				"0"));
		switch (inOrien) {
		case 0:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			break;
		case 1:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		case 2:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;

		}
		Boolean isScreen = sharedPrefs.getBoolean("prefScreen", false);
		if (isScreen)
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		else
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		Boolean isNoti = sharedPrefs.getBoolean("prefNoti", false);
		if (isNoti) {
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		} else {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

	}

	public void onCreate(Bundle savedInstanceState) {
		showUserSettings();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currency_transperent);
		ispreferFlag = false;

		SharedPreferences Settings = getSharedPreferences(PREFERNAME2, 0);
		if (Settings.getString("CRname1", "").equals("")
				&& Settings.getString("CRval1", "").equals("")
				&& Settings.getString("ALLflag1", "").equals("")) {
			ispreferFlag = true;
			Log.i("ShareTag", "prefer");
		}
		if (ispreferFlag == true) {
			AllCurrencyList();

		} else {
			Sharepreferences2();

		}

		aList1 = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < 84; i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("cur2", Allcountries[i]);
			hm.put("flag2", Integer.toString(Allflags[i]));
			aList1.add(hm);
		}
		Collections.sort(aList1, new Comparator<HashMap<String, String>>() {
			@Override
			public int compare(HashMap<String, String> m1,
					HashMap<String, String> m2) {
				return m1.get("cur2").compareTo(m2.get("cur2"));
			}
		});
		listview1 = (ListView) findViewById(R.id.secondlist);
		adapter1 = new ListAdapter2(this, aList1);
		listview1.setAdapter(adapter1);
		listview1.setOnItemClickListener(list);
	}

	// Method used for vibration alert.
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

	private void Sharepreferences2() {
		SharedPreferences Settings = getSharedPreferences(PREFERNAME2, 0);
		for (int i = 0; i < 84; i++) {

			Allflags[i] = Integer.parseInt(Settings
					.getString("ALLflag" + i, ""));
			Allcountries[i] = Settings.getString("CRname" + i, "");
			dblRate[i] = Double
					.parseDouble(Settings.getString("CRval" + i, ""));

		}

	}

	// List click for selecting the country list to get it on
	// Currency_converter_Main screen.
	OnItemClickListener list = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View container,
				int positon, long arg3) {
			vibrate();
			LinearLayout linearLayoutParent = (LinearLayout) container;
			LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent
					.getChildAt(1);
			TextView txtvCode = (TextView) linearLayoutChild.getChildAt(0);
			String posdata = txtvCode.getText().toString();
			String strtemp = posdata;
			String currencyName = getIntent().getExtras().getString(
					"currencyitem");
			int CurrencyFlag = getIntent().getExtras().getInt("currencyflag");
			double currencyRate = getIntent().getExtras().getDouble(
					"currencyRates");
			String strCurrencyitmName = null;
			int inCurrencyitmFlag = 0;
			double dblCurrencyitmRate = 0;
			Log.i("TAG crName", strtemp);
			for (int i = 0; i < 84; i++) {
				if (Allcountries[i].equals(strtemp)) {
					Log.i("TAG okkk", "okkkkk");
					strCurrencyitmName = Allcountries[i];
					inCurrencyitmFlag = Allflags[i];
					dblCurrencyitmRate = dblRate[i];
					Allcountries[i] = currencyName;
					Allflags[i] = CurrencyFlag;
					dblRate[i] = currencyRate;

				}
			}
			Currencyname = strCurrencyitmName;
			CurrencyFlagpars = inCurrencyitmFlag;
			CurrencyRate = dblCurrencyitmRate;
			finish();
		}
	};

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences Settings = getSharedPreferences(PREFERNAME2, 0);
		SharedPreferences.Editor editor = Settings.edit();
		editor.clear();
		for (int i = 0; i < 84; i++) {
			// String strName =etxtName.getText().toString();
			editor.putString("ALLflag" + i, "" + Allflags[i]);
			editor.putString("CRval" + i, "" + dblRate[i]);
			editor.putString("CRname" + i, Allcountries[i]);
		}
		editor.commit();

	}

	// List of Countries with country codes and Flags
	public void AllCurrencyList() {
		Allcountries = new String[] { "DZD-Algerian dinar",
				"ARS-Argentine Peso", "BHD-Bahraini Dinar",
				"BOB-Bolivian Boliviano", "BWP-Botswana Pula",
				"BRL-Brazilian Real", "BGN-Bulgarian Lev",
				"BND-Bruneian Dollar", "KYD-Cayman Islands Dollar",
				"CLP-Chilean Peso", "CNY-Chinese Yuan Renminbi",
				"COP-Colombian Peso", "CRC-Costa Rican Colones",
				"HRK-Croatian Kuna", "CZK-Czech Koruna", "DKK-Danish Krone",
				"DOP -Dominican Peso", "EGP-Egyptian Pound",
				"EEK-Estonian Kroon", "FJD-Fijian Dollar",
				"HNL-Honduran Lempira", "HKD-Hong Kong Dollar",
				"HUF-Hungarian Forint", "INR-Indian Rupee",
				"ILS-Israeli Shekel", "IDR-Indonesian Rupiah",
				"JMD-Jamaican Dollars", "JPY-Japanese Yen",
				"JOD-Jordanian Dinar", "KZT-Kazakhstani Tenge",
				"KES-Kenyan Shilling", "KWD-Kuwaiti Dinar", "LVL-Latvian Lats",
				"LBP-Lebanese Pound", "LTL-Lithuanian Litai",
				"MKD-Macedonian Denar", "MYR-Malaysian Ringgit",
				"MXN-Mexican Peso", "MUR-Mauritian Rupee", "MDL- Moldovan Leu",
				"MAD-Moroccan Dirham", "NAD-Namibian Dollar",
				"NPR-Nepalese Rupee", "ANG-Neth Antilles Guilder",
				"TWD-Taiwan New Dollar", "NZD-New Zealand Dollar",
				"NIO-Nicaraguan Cordoba", "NGN-Nigerian Naira",
				"NOK-Norwegian Krone", "OMR-Omani Rial",
				"PYG-Paraguayan Guarani", "PKR-Pakistani Rupee",
				"PGK-Papua New Guinean Kina", "PEN-Peruvian Nuevo Sol",
				"PHP-Philippine Peso", "PLN-Polish Zloty", "QAR-Qatari Riyal",
				"RON-Romanian New Leu", "RUB-Russian Ruble",
				"SVC-Salvadoran Colon", "SAR-Saudi Arabian Riyal",
				"RSD-Serbian Dinar", "SCR-Seychelles Rupee",
				"SLL-Sierra Leonean Leone", "SGD-Singapore Dollar",
				"SKK-Slovak Koruny", "ZAR-South African Rand",
				"KRW-South Korean Won", "LKR-Sri Lankan Rupee",
				"SEK-Swedish Krona", "TZS-Tanzanian Shilling", "THB-Thai Baht",
				"TTD-Trinidadian Dollar", "TND-Tunisian Dinar",
				"TRY-Turkish Lira", "UGX-Ugandan Shilling",
				"UAH-Ukrainian Hryvna", "AED-Emirati Dirham",
				"UYU-Uruguayan Peso", "UZS-Uzbekistani Som",
				"VEB-Venezuelan Bolivar", "VND-Vietnamese Dong",
				"YER-Yemeni Rial", "ZMK-Zambian Kwacha" };
		Allflags = new int[] { com.cybrosys.palmcalc.R.drawable.dzd,
				com.cybrosys.palmcalc.R.drawable.ars,
				com.cybrosys.palmcalc.R.drawable.bhd,
				com.cybrosys.palmcalc.R.drawable.bop,
				com.cybrosys.palmcalc.R.drawable.bwp,
				com.cybrosys.palmcalc.R.drawable.brl,
				com.cybrosys.palmcalc.R.drawable.bgn,
				com.cybrosys.palmcalc.R.drawable.bnd,
				com.cybrosys.palmcalc.R.drawable.kyd,
				com.cybrosys.palmcalc.R.drawable.clp,
				com.cybrosys.palmcalc.R.drawable.cny,
				com.cybrosys.palmcalc.R.drawable.cop,
				com.cybrosys.palmcalc.R.drawable.crc,
				com.cybrosys.palmcalc.R.drawable.hrk,
				com.cybrosys.palmcalc.R.drawable.czk,
				com.cybrosys.palmcalc.R.drawable.dkk,
				com.cybrosys.palmcalc.R.drawable.dop,
				com.cybrosys.palmcalc.R.drawable.egp,
				com.cybrosys.palmcalc.R.drawable.eek,
				com.cybrosys.palmcalc.R.drawable.fjd,
				com.cybrosys.palmcalc.R.drawable.hnl,
				com.cybrosys.palmcalc.R.drawable.hkd,
				com.cybrosys.palmcalc.R.drawable.huf,
				com.cybrosys.palmcalc.R.drawable.inr,
				com.cybrosys.palmcalc.R.drawable.ils,
				com.cybrosys.palmcalc.R.drawable.idr,
				com.cybrosys.palmcalc.R.drawable.jmd,
				com.cybrosys.palmcalc.R.drawable.jpy,
				com.cybrosys.palmcalc.R.drawable.jod,
				com.cybrosys.palmcalc.R.drawable.kzt,
				com.cybrosys.palmcalc.R.drawable.kes,
				com.cybrosys.palmcalc.R.drawable.kwd,
				com.cybrosys.palmcalc.R.drawable.lvl,
				com.cybrosys.palmcalc.R.drawable.lbp,
				com.cybrosys.palmcalc.R.drawable.ltl,
				com.cybrosys.palmcalc.R.drawable.mkd,
				com.cybrosys.palmcalc.R.drawable.myr,
				com.cybrosys.palmcalc.R.drawable.mxn,
				com.cybrosys.palmcalc.R.drawable.mur,
				com.cybrosys.palmcalc.R.drawable.mdl,
				com.cybrosys.palmcalc.R.drawable.mad,
				com.cybrosys.palmcalc.R.drawable.nad,
				com.cybrosys.palmcalc.R.drawable.npr,
				com.cybrosys.palmcalc.R.drawable.ang,
				com.cybrosys.palmcalc.R.drawable.twd,
				com.cybrosys.palmcalc.R.drawable.nzd,
				com.cybrosys.palmcalc.R.drawable.nio,
				com.cybrosys.palmcalc.R.drawable.ngn,
				com.cybrosys.palmcalc.R.drawable.nok,
				com.cybrosys.palmcalc.R.drawable.omr,
				com.cybrosys.palmcalc.R.drawable.pyg,
				com.cybrosys.palmcalc.R.drawable.pkr,
				com.cybrosys.palmcalc.R.drawable.pgk,
				com.cybrosys.palmcalc.R.drawable.pen,
				com.cybrosys.palmcalc.R.drawable.php,
				com.cybrosys.palmcalc.R.drawable.pln,
				com.cybrosys.palmcalc.R.drawable.qar,
				com.cybrosys.palmcalc.R.drawable.ron,
				com.cybrosys.palmcalc.R.drawable.rub,
				com.cybrosys.palmcalc.R.drawable.svc,
				com.cybrosys.palmcalc.R.drawable.sar,
				com.cybrosys.palmcalc.R.drawable.rsd,
				com.cybrosys.palmcalc.R.drawable.scr,
				com.cybrosys.palmcalc.R.drawable.sll,
				com.cybrosys.palmcalc.R.drawable.sgd,
				com.cybrosys.palmcalc.R.drawable.skk,
				com.cybrosys.palmcalc.R.drawable.zar,
				com.cybrosys.palmcalc.R.drawable.krw,
				com.cybrosys.palmcalc.R.drawable.lkr,
				com.cybrosys.palmcalc.R.drawable.sek,
				com.cybrosys.palmcalc.R.drawable.tzs,
				com.cybrosys.palmcalc.R.drawable.thb,
				com.cybrosys.palmcalc.R.drawable.ttd,
				com.cybrosys.palmcalc.R.drawable.tnd,
				com.cybrosys.palmcalc.R.drawable.tr,
				com.cybrosys.palmcalc.R.drawable.ugx,
				com.cybrosys.palmcalc.R.drawable.uah,
				com.cybrosys.palmcalc.R.drawable.aed,
				com.cybrosys.palmcalc.R.drawable.uyu,
				com.cybrosys.palmcalc.R.drawable.uzs,
				com.cybrosys.palmcalc.R.drawable.veb,
				com.cybrosys.palmcalc.R.drawable.vnd,
				com.cybrosys.palmcalc.R.drawable.yer,
				com.cybrosys.palmcalc.R.drawable.zmk };
		dblRate = new double[] { 79.1452315, 5.05369551, 0.376990036,
				7.01001731, 8.15660685, 1.9732, 1.50049892, 1.24550063,
				0.820000131, 474.383302, 6.2250996, 1814.88203, 499.5005,
				5.83018989, 19.7098707, 5.71961313, 41.049218, 6.74208816,
				11.841186, 1.79307872, 19.8298598, 7.75548507, 228.414801,
				54.9209139, 3.73489701, 9708.73786, 95.2743902, 93.2140194,
				0.70800019, 150.806816, 85.999312, 0.283810002, 0.537700031,
				1503.7594, 2.64930138, 46.7858145, 3.1024972, 12.7523369,
				30.5502093, 12.2904479, 8.52536723, 9.07737555, 87.7808989,
				1.74999869, 29.6647879, 1.20583625, 24.3101981, 157.381177,
				5.71311045, 0.38499994, 4016.06426, 98.2028872, 2.15517241,
				2.5973014, 40.7050108, 3.17430086, 3.64070601, 3.34600353,
				30.7455803, 8.74997813, 3.75030471, 85.5724799, 12.0499349,
				4310.34483, 1.24420047, 23.1133711, 9.07754035, 1086.95652,
				127.469726, 6.41231164, 1620.74554, 29.7849526, 6.38998051,
				1.58539911, 1.79839942, 2652.51989, 8.15002567, 3.67309458,
				19.129969, 1984.12698, 2145.92275, 20833.3333, 215.146299,
				5208.33333 };
	}
}
