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
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Spanned;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ViewSwitcher;

import com.cybrosys.basic.BasicActivity;

/**
 * Provides vertical scrolling for the input/result EditText.
 */
class CalculatorDisplay extends ViewSwitcher {
	// maximum digits
	private static final String strMAX_DIGITS = "maxDigits";
	private static final int inMax_Digits = 13;
	private static final char[] chAccepted_chars = "0123456789.+-*/\u2212\u00d7\u00f7()!%^"
			.toCharArray();

	private static final int inAnimDuration = 500;

	enum Scroll {
		UP, DOWN, NONE
	}

	TranslateAnimation inAnimUp;
	TranslateAnimation outAnimUp;
	TranslateAnimation inAnimDown;
	TranslateAnimation outAnimDown;

	@SuppressWarnings("unused")
	private Logic mLogic;
	private int inMaxDigits = inMax_Digits;

	public CalculatorDisplay(Context context, AttributeSet attrs) {
		super(context, attrs);
		inMaxDigits = attrs.getAttributeIntValue(null, strMAX_DIGITS,
				inMax_Digits);

	}

	public int getMaxDigits() {
		return inMaxDigits;
	}

	protected void setLogic(Logic logic) {
		mLogic = logic;
		NumberKeyListener calculatorKeyListener = new NumberKeyListener() {

			public int getInputType() {
				return EditorInfo.TYPE_CLASS_TEXT;
			}

			@Override
			protected char[] getAcceptedChars() {
				return chAccepted_chars;
			}

			@Override
			public CharSequence filter(CharSequence source, int inStart,
					int inEnd, Spanned dest, int inDstart, int inDend) {
				return null;
			}
		};

		Editable.Factory factory = new CalculatorEditable.Factory(logic);
		for (int i = 0; i < 2; ++i) {
			EditText etxtText = (EditText) getChildAt(i);
			etxtText.setBackgroundDrawable(null);
			etxtText.setEditableFactory(factory);
			etxtText.setKeyListener(calculatorKeyListener);
			etxtText.setSingleLine();
		}
	}

	@Override
	public void setOnKeyListener(OnKeyListener l) {
		getChildAt(0).setOnKeyListener(l);
		getChildAt(1).setOnKeyListener(l);
	}

	@Override
	protected void onSizeChanged(int inW, int inH, int inOldW, int inOldH) {
		inAnimUp = new TranslateAnimation(0, 0, inH, 0);
		inAnimUp.setDuration(inAnimDuration);
		outAnimUp = new TranslateAnimation(0, 0, 0, -inH);
		outAnimUp.setDuration(inAnimDuration);

		inAnimDown = new TranslateAnimation(0, 0, -inH, 0);
		inAnimDown.setDuration(inAnimDuration);
		outAnimDown = new TranslateAnimation(0, 0, 0, inH);
		outAnimDown.setDuration(inAnimDuration);
	}

	void insert(String strInput) {
		EditText etxtEditor = (EditText) getCurrentView();
		etxtEditor.setTextColor(Color.parseColor("#000000"));
		int cursor = etxtEditor.getSelectionStart();
		etxtEditor.getText().insert(cursor, strInput);
	}

	EditText getEditText() {
		return (EditText) getCurrentView();
	}

	Editable getText() {
		EditText etxtText = (EditText) getCurrentView();
		return etxtText.getText();
	}

	void setText(CharSequence text, Scroll dir) {
		if (getText().length() == 0) {
			dir = Scroll.NONE;
		}
		if (dir == Scroll.UP) {
			setInAnimation(inAnimUp);
			setOutAnimation(outAnimUp);
		} else if (dir == Scroll.DOWN) {
			setInAnimation(inAnimDown);
			setOutAnimation(outAnimDown);
		} else {
			setInAnimation(null);
			setOutAnimation(null);
		}
		EditText etxtEditText = (EditText) getNextView();
		etxtEditText.setTextColor(Color.parseColor("#000000"));
		etxtEditText.setText(text);
		BasicActivity.imm.hideSoftInputFromWindow(
				etxtEditText.getWindowToken(), 0);
		etxtEditText.setSelection(text.length());
		showNext();
	}

	int getSelectionStart() {
		EditText etxtEditText = (EditText) getCurrentView();
		return etxtEditText.getSelectionStart();
	}

	@Override
	protected void onFocusChanged(boolean gain, int inDirection, Rect prev) {
		if (!gain) {
			requestFocus();
		}
	}
}
