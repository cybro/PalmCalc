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

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cybrosys.palmcalc.R;
import com.cybrosys.palmcalc.PalmCalcActivity;
import java.lang.reflect.Method;

// Java Packages

@SuppressWarnings("deprecation")
public class CalculatorEditText extends EditText {

	private static final int inCut = 0;
	private static final int inCopy = 1;
	private static final int inPaste = 2;
	private String[] strMenuItems;
	adapterM ma = null;
	private static final Class<TextView> mViewClass = TextView.class;
	private static Method mMethod;

	public static final boolean setCustomSelectionActionModeCallback(
			View mView, ActionMode.Callback mCallback) {
		if (mMethod == null) {
			try {
				mMethod = mViewClass.getMethod(
						"setCustomSelectionActionModeCallback",
						new Class[] { ActionMode.Callback.class });
			} catch (NoSuchMethodException e) {
				return false;
			}
		}
		try {
			mMethod.invoke(mView, new Object[] { mCallback });
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public CalculatorEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			init(context, attrs);
		}
	}

	public CalculatorEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (!isInEditMode()) {
			init(context, attrs);
		}
	}

	private void init(Context context, AttributeSet attrs) {
		if (!isInEditMode()) {

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
			cancelLongPress();
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean performLongClick() {
		show();
		return true;
	}

	static class adapterM extends ArrayAdapter<CharSequence> {

		public adapterM(Context context, int inTextViewResId,
				CharSequence[] strings) {
			super(context, inTextViewResId, strings);
		}

		public static adapterM createFromResource(Context context,
				int inTextArrayResId, int inTextViewResId) {

			Resources resources = context.getResources();
			CharSequence[] strings = resources.getTextArray(inTextArrayResId);

			return new adapterM(context, inTextViewResId, strings);
		}

		public boolean isEnabled(int inPosition) {
			return true;
		}
	}

	private void show() {

		final Dialog dialog = new Dialog(PalmCalcActivity.ctx,
				R.style.AlertDialogCustom);

		dialog.setContentView(R.layout.custom_clip);

		dialog.setTitle("Clipboard");
		ListView lstvOpt = (ListView) dialog.findViewById(R.id.lstvOpt);

		ma = adapterM.createFromResource(PalmCalcActivity.ctx,
				R.array.strContext, R.layout.sci_clip);
		CharSequence primaryClip = getClipText();
		if (getText().length() == 0) {
			if (primaryClip == null || !canPaste(primaryClip)) {
				ma = adapterM.createFromResource(PalmCalcActivity.ctx,
						R.array.strContextEmpty, R.layout.sci_clip);
			} else {
				ma = adapterM.createFromResource(PalmCalcActivity.ctx,
						R.array.strContextEm, R.layout.sci_clip);
			}
		} else if (getText().length() > 0) {
			if (primaryClip == null || !canPaste(primaryClip)) {
				ma = adapterM.createFromResource(PalmCalcActivity.ctx,
						R.array.strContextHa, R.layout.sci_clip);

			} else
				ma = adapterM.createFromResource(PalmCalcActivity.ctx,
						R.array.strContext, R.layout.sci_clip);
		}

		ma.notifyDataSetChanged();
		lstvOpt.setAdapter(ma);
		lstvOpt.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View vwClick,
					int inPos, long lngRowId) {

				System.out.println("" + inPos + "view" + vwClick + ""
						+ lngRowId);
				if (ma.getItem(inPos).toString().equalsIgnoreCase("CUT")) {
					cutContent();
				} else if (ma.getItem(inPos).toString()
						.equalsIgnoreCase("COPY")) {
					copyContent();
				} else if (ma.getItem(inPos).toString()
						.equalsIgnoreCase("PASTE"))
					pasteContent();
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private class MenuHandler implements MenuItem.OnMenuItemClickListener {
		public boolean onMenuItemClick(MenuItem item) {
			return onTextContextMenuItem(item.getTitle());
		}
	}

	public boolean onTextContextMenuItem(CharSequence title) {
		boolean handled = false;
		if (TextUtils.equals(title, strMenuItems[inCut])) {
			cutContent();
			handled = true;
		} else if (TextUtils.equals(title, strMenuItems[inCopy])) {
			copyContent();
			handled = true;
		} else if (TextUtils.equals(title, strMenuItems[inPaste])) {
			pasteContent();
			handled = true;
		}
		return handled;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu) {
		MenuHandler handler = new MenuHandler();
		if (strMenuItems == null) {

			Resources resources = getResources();
			strMenuItems = new String[3];
			strMenuItems[inCut] = resources.getString(android.R.string.cut);
			strMenuItems[inCopy] = resources.getString(android.R.string.copy);
			strMenuItems[inPaste] = resources.getString(android.R.string.paste);
		}
		for (int i = 0; i < strMenuItems.length; i++) {
			menu.add(Menu.NONE, i, i, strMenuItems[i])
					.setOnMenuItemClickListener(handler);
		}
		if (getText().length() == 0) {
			menu.getItem(inCut).setVisible(false);
			menu.getItem(inCopy).setVisible(false);
		}
		CharSequence primaryClip = getClipText();
		if (primaryClip == null || !canPaste(primaryClip)) {
			menu.getItem(inPaste).setVisible(false);
		}
	}

	private void setClipText(CharSequence clip) {
		ClipboardManager clipboard = (ClipboardManager) getContext()
				.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setText(clip);
	}

	private void copyContent() {
		final Editable text = getText();
		int inTextLength = text.length();
		setSelection(0, inTextLength);
		ClipboardManager clipboard = (ClipboardManager) getContext()
				.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setText(text);

		setSelection(inTextLength);
	}

	private void cutContent() {
		final Editable text = getText();
		int inTextLength = text.length();
		setSelection(0, inTextLength);
		setClipText(text);
		((Editable) getText()).delete(0, inTextLength);
		setSelection(0);
	}

	private CharSequence getClipText() {
		ClipboardManager clipboard = (ClipboardManager) getContext()
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return clipboard.getText();
	}

	private void pasteContent() {
		CharSequence clip = getClipText();
		if (clip != null) {
			if (canPaste(clip)) {
				((Editable) getText()).insert(getSelectionEnd(), clip);
			}
		}
	}

	private boolean canPaste(CharSequence paste) {
		boolean canPaste = true;
		try {
			Float.parseFloat(paste.toString());
		} catch (NumberFormatException e) {
			canPaste = false;
		}
		return canPaste;
	}

	class NoTextSelectionMode implements ActionMode.Callback {
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			return false;
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			copyContent();
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
	}
}
