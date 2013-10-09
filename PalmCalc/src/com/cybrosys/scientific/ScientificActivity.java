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

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cybrosys.scientific.History;
import com.cybrosys.scientific.Logic;
import com.cybrosys.scientific.Persist;
import com.cybrosys.scientific.CalculatorDisplay;
import com.cybrosys.palmcalc.R;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ScientificActivity extends SherlockFragment implements
		Logic.Listener {

	SharedPreferences sharedPref;
	static SharedPreferences.Editor shared_editor;
	Dialog dialog;
	Button btnRaise, btnRoot, btnPerc, btnPow;
	static EventListener mListener = new EventListener();
	TextView txtvRaise, txtvRoot, txtvPerc, txtvPow, txtvLn, txtvLog, txtvSin,
			txtvCos, txtvTan;
	static TextView txtvDeg, txtvShift, txtvFSE, txtvHyp;
	Button btn9, btn8, btn7, btn6, btn5, btn4, btn3, btn2, btn1, btn0, btnAc,
			btnDel;
	private static final boolean isLogEnabled = false;

	CalculatorEditText txtInput, txtOutput;
	CalculatorDisplay mDisplay;
	private Persist mPersist;
	private History mHistory;
	private Logic mLogic;
	ViewPager mPager;
	static InputMethodManager imm;
	DisplayMetrics metrics;
	private String myString = "";
	private String strFSEstate = "FloatPt";
	static int inDispheight;
	static int inDispwidth;
	static Context ctx;
	private String strAlt = "";
	private String strDeg = "[DEG]";
	private String strHyp = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflating layout from the layout folder
		return inflater.inflate(R.layout.activity_scientific, container, false);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		getActivity().getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
				WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		ctx = getActivity();
		init();
		mDisplay = (CalculatorDisplay) getView().findViewById(R.id.display);
		imm = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		mDisplay = (CalculatorDisplay) getView().findViewById(R.id.display);
		metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		inDispheight = (int) (metrics.heightPixels * .6f);
		inDispwidth = (int) (metrics.widthPixels * .8f);
		mPersist = new Persist(ctx);
		mPersist.load();
		mHistory = mPersist.history;
		mLogic = new Logic(ctx, mHistory, mDisplay);
		mLogic.setDeleteMode(mPersist.getDeleteMode());
		mLogic.setLineLength(mDisplay.getMaxDigits());
		HistoryAdapter historyAdapter = new HistoryAdapter(ctx, mHistory,
				mLogic);
		mHistory.setObserver(historyAdapter);

		mListener.setHandler(mLogic, mPager);
		mDisplay.setOnKeyListener(mListener);

		// tablelayout for the functions
		TableLayout tblltPad = (TableLayout) getView().findViewById(
				R.id.tableLayout1);
		int inPadCount = tblltPad.getChildCount();

		for (int inI = 0; inI < inPadCount; inI++) {
			View vwView = tblltPad.getChildAt(inI);
			if (vwView instanceof TableRow) {
				TableRow tblrRow = (TableRow) vwView;
				int inRowCount = tblrRow.getChildCount();
				for (int inR = 0; inR < inRowCount; inR++) {
					View v2 = tblrRow.getChildAt(inR);
					if (v2 instanceof Button) {
						Button b = (Button) v2;
						// setting listeners for the function buttons
						b.setOnClickListener(mListener);
						b.setOnLongClickListener(mListener);
					} else if (v2 instanceof TextView) {
						TextView txtv = (TextView) v2;
						txtv.setOnClickListener(null);
					}
				}
			}
		}
		// tablelayout for the numbers
		TableLayout tblltNum = (TableLayout) getView().findViewById(
				R.id.tableLayout2);
		int inNumcount = tblltNum.getChildCount();

		mDisplay.setText(myString, null);
		txtvFSE.setText(strFSEstate);
		txtvShift.setText(strAlt);
		txtvDeg.setText(strDeg);
		txtvHyp.setText(strHyp);

		for (int inI = 0; inI < inNumcount; inI++) {
			View vw1 = tblltNum.getChildAt(inI);
			if (vw1 instanceof TableRow) {
				TableRow tblrRow = (TableRow) vw1;
				int inRowCount = tblrRow.getChildCount();
				for (int inR = 0; inR < inRowCount; inR++) {
					View vw2 = tblrRow.getChildAt(inR);
					if (vw2 instanceof Button) {
						Button b = (Button) vw2;
						// setting listeners for the buttons
						b.setOnClickListener(mListener);
						b.setOnLongClickListener(mListener);
					} else if (vw2 instanceof TextView) {
						TextView txtv = (TextView) vw2;
						txtv.setOnClickListener(null);
					}
				}
			}
		}

	}

	// to restore states
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mDisplay = (CalculatorDisplay) getView().findViewById(R.id.display);

		if (savedInstanceState != null) {
			myString = savedInstanceState.getString("disp");
			strFSEstate = savedInstanceState.getString("fse");
			strAlt = savedInstanceState.getString("alt");
			strDeg = savedInstanceState.getString("deg");
			strHyp = savedInstanceState.getString("hyp");
		}
	}

	// to save states of views
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("disp", mDisplay.getText().toString());
		savedInstanceState.putString("fse", txtvFSE.getText().toString());
		savedInstanceState.putString("alt", txtvShift.getText().toString());
		savedInstanceState.putString("deg", txtvDeg.getText().toString());
		savedInstanceState.putString("hyp", txtvHyp.getText().toString());
		super.onSaveInstanceState(savedInstanceState);
	}

	static void log(String strMsg) {
		if (isLogEnabled) {
		}
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.sci_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.clear_mem:
			alert("Clear all memories?", 1);
			return true;
		case R.id.clear_hist:
			alert("Clear all History?", 0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// to show pop-up alert
	public void alert(String strMsg, final int mode) {

		new AlertDialog.Builder(ctx)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("PalmCalc")
				.setMessage(strMsg)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (mode == 1)
									clearMem();
								else
									clearHist();
							}
						}).setNegativeButton("No", null).show();

	}

	// To clear history
	private void clearHist() {
		History.clearHIst();
		Toast.makeText(ctx, "History Cleared", Toast.LENGTH_SHORT).show();
	}

	// To clear memory
	private void clearMem() {
		EventListener.clearMem();
		Toast.makeText(ctx, "Memory Cleared", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onDeleteModeChange() {
	}

	// Initializing views
	public void init() {
		btnRaise = (Button) getView().findViewById(R.id.buttonRaise);
		btnRoot = (Button) getView().findViewById(R.id.buttonRoot);
		btnPerc = (Button) getView().findViewById(R.id.buttonPerc);
		btnPow = (Button) getView().findViewById(R.id.buttonPow);
		btnRaise.setText(Html.fromHtml("<i>Y</i><sup><i>x</i></sup>"));
		btnRoot.setText(Html.fromHtml("&radic"));
		btnPow.setText(Html.fromHtml("</i>x</i><sup><small>2</small></sup>"));
		txtInput = (CalculatorEditText) getView().findViewById(R.id.txtInput);
		txtOutput = (CalculatorEditText) getView().findViewById(R.id.txtOutput);

		txtvDeg = (TextView) getView().findViewById(R.id.textViewDRG);
		txtvShift = (TextView) getView().findViewById(R.id.textViewShift);
		txtvFSE = (TextView) getView().findViewById(R.id.textViewFSE);
		txtvHyp = (TextView) getView().findViewById(R.id.textViewNumHyp);
		txtvRoot = (TextView) getView().findViewById(R.id.txtvRoot);
		txtvPerc = (TextView) getView().findViewById(R.id.txtvBy);
		txtvPow = (TextView) getView().findViewById(R.id.txtvPow);
		txtvLn = (TextView) getView().findViewById(R.id.txtvLnIn);
		txtvLog = (TextView) getView().findViewById(R.id.txtvLogIn);
		txtvSin = (TextView) getView().findViewById(R.id.txtvSinin);
		txtvCos = (TextView) getView().findViewById(R.id.txtvCosin);
		txtvTan = (TextView) getView().findViewById(R.id.txtvTanIn);
		txtvRoot.setText(Html.fromHtml("<small>3</small>&radic"));
		txtvPerc.setText(Html.fromHtml("<small>1</small>/x"));
		txtvPow.setText(Html.fromHtml("x<sup><small>3</small></sup>"));
		txtvLn.setText(Html.fromHtml("e<sup><small>x</small></sup>"));
		txtvLog.setText(Html.fromHtml("10<sup><small>x</small></sup>"));

	}

}
