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

import android.text.Editable;
import android.text.SpannableStringBuilder;


class CalculatorEditable extends SpannableStringBuilder {
	private static final char[] ORIGINALS = { '-', '*', '/' };
	private static final char[] REPLACEMENTS = { '-', '\u00d7', '\u00f7' };
	private boolean isInsideReplace = false;
	private Logic mLogic;

	private CalculatorEditable(CharSequence source, Logic logic) {
		super(source);
		mLogic = logic;
	}

	@Override
	public SpannableStringBuilder replace(int inStart, int inEnd,
			CharSequence tb, int inTbstart, int inTbend) {
		if (isInsideReplace) {
			return super.replace(inStart, inEnd, tb, inTbstart, inTbend);
		} else {
			isInsideReplace = true;
			try {
				String strDelta = tb.subSequence(inTbstart, inTbend).toString();
				return internalReplace(inStart, inEnd, strDelta);
			} finally {
				isInsideReplace = false;
			}
		}
	}

	private SpannableStringBuilder internalReplace(int inStart, int inEnd,
			String strDelta) {
		if (!mLogic.acceptInsert(strDelta)) {
			mLogic.cleared();
			inStart = 0;
			inEnd = length();
		}

		for (int i = ORIGINALS.length - 1; i >= 0; --i) {
			strDelta = strDelta.replace(ORIGINALS[i], REPLACEMENTS[i]);
		}
		int inLength = strDelta.length();
		if (inLength == 1) {
			char chText = strDelta.charAt(0);
			if (chText == '.') {
				int inP = inStart - 1;
				while (inP >= 0 && Character.isDigit(charAt(inP))) {
					--inP;
				}
				if (inP >= 0 && charAt(inP) == '.') {
					return super.replace(inStart, inEnd, "");
				}
			}

			char chPrevChar = inStart > 0 ? charAt(inStart - 1) : '\0';
			if (chText == Logic.chMinus && chPrevChar == Logic.chMinus) {
				return super.replace(inStart, inEnd, "");
			}
			if (Logic.isOperator(chText)) {
				while (Logic.isOperator(chPrevChar)
						&& (chText != Logic.chMinus || chPrevChar == '+')) {
					--inStart;
					chPrevChar = inStart > 0 ? charAt(inStart - 1) : '\0';
				}
			}
			if (inStart == 0 && Logic.isOperator(chText)
					&& chText != Logic.chMinus) {
				return super.replace(inStart, inEnd, "");
			}
		}
		return super.replace(inStart, inEnd, strDelta);
	}

	public static class Factory extends Editable.Factory {
		private Logic mLogic;

		public Factory(Logic logic) {
			mLogic = logic;
		}

		public Editable newEditable(CharSequence source) {
			return new CalculatorEditable(source, mLogic);
		}
	}
}
