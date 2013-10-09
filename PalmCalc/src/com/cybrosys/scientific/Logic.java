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

import com.cybrosys.scientific.CalculatorDisplay.Scroll;
import com.cybrosys.palmcalc.R;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;
import android.content.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

class Logic {
	private CalculatorDisplay mDisplay;
	private static final String strInfinityUnicode = "\u221e";
	public static final String strMarker = "?";
	private static final String strInfin = "Infinity";
	private static final String strNan = "NaN";
	static final char chMinus = '-';
	private final String strErrorString;
	public final static int inDelModeBa = 0;
	public final static int inDelModClea = 1;
	private int mDeleteMode = inDelModeBa;
	private Symbols mSymbols = new Symbols();
	private History mHistory;
	private boolean isError = false;
	private int inLineLength = 0;
	private Context ctx;
	static int inDeg = 1;
	String strResult = "";
	String strPrefName = "mypref";
	HistoryAdapter hst;
	String strCurrentMode = "[DEG]";
	int inModeFlag = 0;

	public interface Listener {
		void onDeleteModeChange();
	}

	private Listener mListener;

	Logic(Context context, History history, CalculatorDisplay display) {
		strErrorString = context.getResources().getString(R.string.error);
		mHistory = history;
		mDisplay = display;
		mDisplay.setLogic(this);
		context = ctx;
		hst = new HistoryAdapter(context, mHistory, this);
	}

	public void setListener(Listener listener) {
		this.mListener = listener;
	}

	public void setDeleteMode(int mode) {
		if (mDeleteMode != mode) {
			mDeleteMode = mode;
			mListener.onDeleteModeChange();
		}
	}

	public int getDeleteMode() {
		return mDeleteMode;
	}

	void setLineLength(int inDigits) {
		inLineLength = inDigits;
	}

	boolean eatHorizontalMove(boolean isToLeft) {
		EditText etxtEditText = mDisplay.getEditText();
		ScientificActivity.imm.hideSoftInputFromWindow(
				etxtEditText.getWindowToken(), 0);
		int cursorPos = etxtEditText.getSelectionStart();
		return isToLeft ? cursorPos == 0 : cursorPos >= etxtEditText.length();
	}

	private String getText() {

		return mDisplay.getText().toString();
	}

	void insert(String strDelta) {
		mDisplay.insert(strDelta);
		setDeleteMode(inDelModeBa);
	}

	public void resumeWithHistory() {
		clearWithHistory(false);
	}

	private void clearWithHistory(boolean isScroll) {

		String strText = mHistory.getText();

		if (strMarker.equals(strText)) {
			if (!mHistory.moveToPrevious()) {
				strText = "";
			}
			strText = mHistory.getText();
			evaluateAndShowResult(strText, CalculatorDisplay.Scroll.NONE);
		} else {
			strResult = "";
			mDisplay.setText(strText, isScroll ? CalculatorDisplay.Scroll.UP
					: CalculatorDisplay.Scroll.NONE);
			isError = false;
		}
	}

	private void clear(boolean isScroll) {
		mHistory.enter("");
		mDisplay.setText("", isScroll ? CalculatorDisplay.Scroll.UP
				: CalculatorDisplay.Scroll.NONE);
		cleared();
	}

	void cleared() {
		strResult = "";
		isError = false;
		updateHistory();
		setDeleteMode(inDelModeBa);
	}

	boolean acceptInsert(String strDelta) {
		String strText = getText();
		return !isError
				&& (!strResult.equals(strText) || isOperator(strDelta) || mDisplay
						.getSelectionStart() != strText.length());
	}

	void onDelete() {
		if (getText().equals(strResult) || isError) {
			clear(false);
		} else {
			mDisplay.dispatchKeyEvent(new KeyEvent(0, KeyEvent.KEYCODE_DEL));
			strResult = "";
		}
	}

	void onClear() {
		clear(mDeleteMode == inDelModClea);
	}

	void onEnter() {
		if (mDeleteMode == inDelModClea) {
			clearWithHistory(false);
		} else {
			evaluateAndShowResult(getText(), CalculatorDisplay.Scroll.UP);
		}
	}

	void onShow() {
		insert(mHistory.getAns());
	}

	// returns history
	String[] HistorySize() {
		String strAns[] = new String[hst.getCount()];
		int inJ = hst.getCount() - 1;
		if (hst.getCount() > 0)
			for (int inI = 0; inI < hst.getCount(); inI++) {
				strAns[inI] = "" + hst.getItem(inJ);
				inJ--;
			}
		return strAns;
	}

	// handle DEG change
	public String CheckMode(String strText) {

		String strDisplay = strText;
		if (inDeg == 1) {
			if (strDisplay.contains("sin(")) {
				strDisplay = strDisplay.replace("sin(", "sind(");
			}
			if (strDisplay.contains("cos(")) {
				strDisplay = strDisplay.replace("cos(", "cosd(");
			}
			if (strDisplay.contains("tan(")) {
				strDisplay = strDisplay.replace("tan(", "tand(");
			}
			return strDisplay;
		} else
			return strDisplay;

	}

	// to check number of open brackets and closed brackets are same in the
	// input
	public static Boolean getBracketCount(String strText) {
		int inBracketOCount = 0;
		int inBracketCCount = 0;
		if (strText.contains("(")) {
			for (final char ch : strText.toCharArray()) {
				switch (ch) {
				case '(':
					++inBracketOCount;
					break;

				case ')':
					++inBracketCCount;
					break;
				}
			}
			if (inBracketOCount == inBracketCCount)
				return true;
			else
				return false;
		} else
			return true;

	}

	// function shows the result by reading input
	public void evaluateAndShowResult(String strText, Scroll scroll) {
		if (getBracketCount(strText)) {
			try {

				String strReslt = evaluate(CheckMode(strText));
				if (!strText.equals(strReslt) && !strText.equalsIgnoreCase("")) {
					mHistory.enter(strReslt);
					strResult = strReslt;
					mDisplay.setText(strResult, scroll);
				}
			} catch (SyntaxException inExp) {
				isError = true;
				strResult = strErrorString;
				mDisplay.setText(strResult, scroll);
			}
		} else {
			isError = true;
			strResult = strErrorString;
			mDisplay.setText(strResult, scroll);
		}
	}

	void onUp() {
		String strText = getText();
		if (!strText.equals(strResult)) {
			mHistory.update(strText);
		}
		if (mHistory.moveToPrevious()) {
			mDisplay.setText(mHistory.getText(), CalculatorDisplay.Scroll.DOWN);
		}
	}

	void onDown() {
		String strText = getText();
		if (!strText.equals(strResult)) {
			mHistory.update(strText);
		}
		if (mHistory.moveToNext()) {
			mDisplay.setText(mHistory.getText(), CalculatorDisplay.Scroll.UP);
		}
	}

	void updateHistory() {
		String strText = getText();
		if (!TextUtils.isEmpty(strText)
				&& !TextUtils.equals(strText, strErrorString)
				&& strText.equals(strResult)) {
			mHistory.update(strMarker);
		} else {
			mHistory.update(getText());
		}
	}

	// to evaluate the result of calculation
	String evaluate(String strInput) throws SyntaxException {
		if (strInput.trim().equals("")) {

			return "";
		}
		double dblValue = mSymbols.eval(strInput);
		String strReslt = "";
		if (ScientificActivity.txtvFSE.getText().toString()
				.equalsIgnoreCase("Floatpt")) {
			for (int inPrecision = inLineLength; inPrecision > 6; inPrecision--) {
				strReslt = tryFormattingWithPrecision(dblValue, inPrecision);
				if (strReslt.length() <= inLineLength) {
					break;
				}
			}
		} else if (ScientificActivity.txtvFSE.getText().toString()
				.contains("FIX")) {
			strReslt = EventListener.fix(dblValue,
					PreferenceClass.getMyIntPref(EventListener.ctx));

		} else if (ScientificActivity.txtvFSE.getText().toString()
				.contains("SCI")) {
			strReslt = EventListener.sci(dblValue,
					PreferenceClass.getMyIntPref(EventListener.ctx));
		}
		return strReslt.replace('-', chMinus).replace(strInfin,
				strInfinityUnicode);
	}

	// To format the orginal result with predefined precision
	private String tryFormattingWithPrecision(double dblValue, int inPrecision) {
		String strReslt = String.format(Locale.US, "%" + inLineLength + "."
				+ inPrecision + "g", dblValue);
		if (strReslt.equals(strNan)) {
			isError = true;
			return strErrorString;
		}
		String strMantissa = strReslt;
		String strExponent = null;
		int inExp = strReslt.indexOf('e');
		if (inExp != -1) {
			strMantissa = strReslt.substring(0, inExp);
			strExponent = strReslt.substring(inExp + 1);
			if (strExponent.startsWith("+")) {
				strExponent = strExponent.substring(1);
			}
			strExponent = String.valueOf(Integer.parseInt(strExponent));
		} else {
			strMantissa = strReslt;
		}
		int inPeriod = strMantissa.indexOf('.');
		if (inPeriod == -1) {
			inPeriod = strMantissa.indexOf(',');
		}
		if (inPeriod != -1) {
			while (strMantissa.length() > 0 && strMantissa.endsWith("0")) {
				strMantissa = strMantissa
						.substring(0, strMantissa.length() - 1);
			}
			if (strMantissa.length() == inPeriod + 1) {
				strMantissa = strMantissa
						.substring(0, strMantissa.length() - 1);
			}
		}

		if (strExponent != null) {
			strReslt = strMantissa + 'e' + strExponent;
		} else {
			strReslt = strMantissa;
		}

		return strReslt;
	}

	@SuppressWarnings("unused")
	private String formater(String strReslt) {
		String strMantissa = strReslt;
		int inLen = 0;
		int inPeriod = strMantissa.indexOf('.');
		if (inPeriod != -1) {
			String strSub = strMantissa.substring(inPeriod);
			inLen = strSub.length();

		}
		String strHash = ".";
		if (inLen != 0) {
			for (int i = 0; i < inLen - 1; i++) {
				strHash = strHash.concat("#");
			}
		}
		System.out.println(strHash);
		String strFormat = "#,##0" + strHash;

		NumberFormat formatter = new DecimalFormat(strFormat);

		return formatter.format(Double.parseDouble(strReslt));

	}

	static boolean isOperator(String strText) {
		return strText.length() == 1 && isOperator(strText.charAt(0));
	}

	static boolean isOperator(char chC) {
		return "+-\u00d7\u00f7/*".indexOf(chC) != -1;
	}

	// To handle DEG change event
	public void onDegChange() {
		if (strCurrentMode.equalsIgnoreCase(ScientificActivity.txtvDeg
				.getText().toString())) {
			ScientificActivity.txtvDeg.setText("[RAD]");
			inDeg = 0;
		} else {
			ScientificActivity.txtvDeg.setText("[DEG]");
			inDeg = 1;
		}
	}

	// To handle ALT press event
	public void onShiftPress() {
		ScientificActivity.txtvShift.setText("[ALT]");

	}

	// To return display text
	public String getDisplayText() {
		return mDisplay.getText().toString();
	}

	// To handle hyp press event
	public void onHypPress() {
		ScientificActivity.txtvHyp.setText("[HYP]");
	}

	// To handle FSE press event
	public void onFSEChange() {
		inModeFlag = 1;
	}
}
