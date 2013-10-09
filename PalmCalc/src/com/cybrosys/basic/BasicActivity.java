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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cybrosys.palmcalc.R;

public class BasicActivity extends SherlockFragment implements Logic.Listener {

	SharedPreferences sharedPref;
	static SharedPreferences.Editor shared_editor;
	static EventListener mListener = new EventListener();
	Button btn9, btn8, btn7, btn6, btn5, btn4, btn3, btn2, btn1, btn0, btnAc,
			btnDel, btnminus, btnmul;
	private static final boolean isLogEnabled = false;
	CalculatorEditText txtInput, txtOutput;
	CalculatorDisplay mDisplay;
	private Persist mPersist;
	private History mHistory;
	private Logic mLogic;
	ViewPager mPager;
	static InputMethodManager imm;

	private String myString = "";
	static Context ctx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.basic_final, container, false);

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

		btnmul = (Button) getView().findViewById(R.id.btnmul);
		btnmul.setText(Html.fromHtml("x"));
		btnminus = (Button) getView().findViewById(R.id.btnminus);
		btnminus.setText(Html.fromHtml("-"));

		// tablelayout of numbers
		TableLayout tblltPad = (TableLayout) getView().findViewById(
				R.id.tabcalc);
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
						if (b.getId() == R.id.buttonDel) {
							b.setOnLongClickListener(new View.OnLongClickListener() {
								@Override
								public boolean onLongClick(View v) {
									mLogic.onClear();
									return true;
								}
							});
						}
						// setting on click listener for the buttons
						b.setOnClickListener(mListener);
					}
				}
			}
		}
		mDisplay.setText(myString, null);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mDisplay = (CalculatorDisplay) getView().findViewById(R.id.display);

		if (savedInstanceState != null) {
			myString = savedInstanceState.getString("disp");

		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("disp", mDisplay.getText().toString());

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

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void updateDeleteMode() {
		if (mLogic.getDeleteMode() == Logic.DELETE_MODE_BACKSPACE) {

		}
	}

	@Override
	public void onDeleteModeChange() {
		updateDeleteMode();
	}

	public void init() {

		txtInput = (CalculatorEditText) getView().findViewById(R.id.txtInput);
		txtOutput = (CalculatorEditText) getView().findViewById(R.id.txtOutput);

	}

}
