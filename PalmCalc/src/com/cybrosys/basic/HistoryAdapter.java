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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cybrosys.palmcalc.R;

import org.javia.arity.SyntaxException;

import java.util.Vector;

class HistoryAdapter extends BaseAdapter {
	private Vector<HistoryEntry> mEntries;
	private LayoutInflater mInflater;
	private com.cybrosys.basic.Logic mEval;

	HistoryAdapter(Context context, History history,
			com.cybrosys.basic.Logic evaluator) {
		mEntries = history.mEntries;
		// mInflater = (LayoutInflater)
		// context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mEval = evaluator;
	}

	// @Override
	public int getCount() {
		return mEntries.size() - 1;
	}

	// @Override
	public Object getItem(int inPos) {
		return mEntries.elementAt(inPos);
	}

	// @Override
	public long getItemId(int inPos) {
		return inPos;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	// @Override
	public View getView(int inPos, View vwConvert, ViewGroup parent) {
		View vwView;
		if (vwConvert == null) {
			vwView = mInflater.inflate(R.layout.history_item, parent, false);
		} else {
			vwView = vwConvert;
		}

		TextView txtvExpr = (TextView) vwView.findViewById(R.id.historyExpr);
		TextView txtvResult = (TextView) vwView
				.findViewById(R.id.historyResult);

		HistoryEntry entry = mEntries.elementAt(inPos);
		String base = entry.getBase();
		txtvExpr.setText(entry.getBase());

		try {
			String strRes = mEval.evaluate(base);
			txtvResult.setText("= " + strRes);
		} catch (SyntaxException e) {
			txtvResult.setText("");
		}

		return vwView;
	}
}
