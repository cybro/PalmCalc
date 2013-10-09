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

package com.cybrosys.clock;

import java.util.TimeZone;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.cybrosys.palmcalc.PalmCalcActivity;
import com.cybrosys.palmcalc.R;

public class GlobalClockActivity extends SherlockFragment implements
		OnClickListener {
	Button btnRemove;
	Button btnSave;
	EditText editConutryName;
	Dialog dialog;
	ImageView imgView;
	Time timeSetter;
	String hour, min;
	int minute, hourOfDay;
	String timenow;
	Cursor cursor = null;
	ListView list = null;
	TimeZoneEntryAdapter adapter = null;
	SQLiteDatabase db = null;
	TimeZoneEntry current = null;
	boolean showedInstructions = false;
	int inListPosition;
	AddTimeZone MyTimezone;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.clockmain, container, false);
	}

	@Override
	public void onStart() {
		try {
			super.onStart();
			Button btnStart = (Button) getView().findViewById(R.id.btnStart);
			btnStart.setVisibility(View.INVISIBLE);
			db = (new SQLiteHelper(getActivity())).getWritableDatabase();
			list = (ListView) getView().findViewById(R.id.savedtzlist);
			list.setOnItemLongClickListener(onListLongClick);
			populateListOfcursor();
			new Thread(updateTZList).start();
			Button btnTimeZone = (Button) getView().findViewById(
					R.id.btnTimeZone);
			btnTimeZone.setOnClickListener(this);
		} catch (Exception e) {
		}
	}

	@Override
	public void onPause() {

		super.onPause();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	// Updating the saved list of TimeZone Entries from database.
	@SuppressWarnings("deprecation")
	private void populateListOfcursor() {
		try {
			if (cursor != null) {
				getActivity().stopManagingCursor(cursor);
				cursor.close();
			}

			cursor = TimeZoneEntry.getAll(this.db);
			getActivity().startManagingCursor(this.cursor);

			if (cursor.getCount() == 0 && !showedInstructions) {
				Button btnStart = (Button) getView()
						.findViewById(R.id.btnStart);
				btnStart.setVisibility(View.VISIBLE);
			} else {
				adapter = new TimeZoneEntryAdapter(cursor);
				list.setAdapter(adapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Runnable thread for updating the TimeZone Lists
	private Runnable updateTZList = new Runnable() {
		public void run() {
			try {
				for (int i = 0; i < 6; i++) {
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							populateListOfcursor();
						}
					});
					SystemClock.sleep(20000);
				}
			} catch (Exception e) {
			}
		}
	};

	// LongClick for the country list items. We can customize the country name
	// from here.
	// We can also remove the added country time from the list here.
	private AdapterView.OnItemLongClickListener onListLongClick = new AdapterView.OnItemLongClickListener() {
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			try {
				vibrate();
				inListPosition = position;
				EditCountryName();
			} catch (Exception e) {
			}
			return true;
		}
	};

	@Override
	public void onDestroy() {
		try {
			super.onDestroy();
		} catch (Exception e) {
		}
	}

	class TimeZoneEntryAdapter extends CursorAdapter {
		@SuppressWarnings("deprecation")
		TimeZoneEntryAdapter(Cursor c) {
			super(getActivity(), c);
		}

		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			TimeZoneEntryWrapper wrapper = (TimeZoneEntryWrapper) row.getTag();
			wrapper.populateFrom(c);
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View row = inflater.inflate(R.layout.clockmainlist, parent, false);
			TimeZoneEntryWrapper wrapper = new TimeZoneEntryWrapper(row);
			imgView = (ImageView) row.findViewById(R.id.imgvFlag);
			row.setTag(wrapper);
			wrapper.populateFrom(c);
			return row;
		}
	}

	class TimeZoneEntryWrapper {
		private TextView name = null;
		private TextView date = null;
		private TextView time = null;
		private View row = null;

		TimeZoneEntryWrapper(View row) {
			this.row = row;
		}

		void populateFrom(Cursor c) {
			try {
				String tzstr = c.getString(c.getColumnIndex("tzid"));
				int image = c.getInt(c.getColumnIndex("images"));
				String editcolumn = c.getString(c.getColumnIndex("contryname"));
				int inIndex = editcolumn.lastIndexOf('/');
				String str = editcolumn.substring(inIndex + 1,
						editcolumn.length());
				editcolumn = str;
				TimeZone tz = TimeZone.getTimeZone(tzstr);
				int gmtoffset = tz.getRawOffset() / (3600000);
				int hours = Math.abs(tz.getRawOffset()) / 3600000;
				int minutes = Math.abs(tz.getRawOffset() / 60000) % 60;
				String sign = tz.getRawOffset() >= 0 ? "+" : "-";
				Time now = new Time(tzstr);
				now.setToNow();
				String datenow = now.format("%A %B %e");
				getDate().setText(datenow);
				timenow = now.format("%H:%M");
				getTime().setText(timenow);
				String timeZonePretty = String.format("(GMT %s %02d:%02d)",
						sign, hours, minutes);
				@SuppressWarnings("unused")
				String sGmtOffset = new String("(GMT "
						+ (gmtoffset >= 0 ? "+" : "-") + hours + ":" + minutes
						+ ")");
				getName().setText(editcolumn + " " + timeZonePretty);
				imgView.setBackgroundResource(image);
			} catch (Exception e) {
			}
		}

		TextView getName() {
			if (name == null) {
				name = (TextView) row.findViewById(R.id.name);
			}
			return name;
		}

		TextView getTime() {
			if (time == null) {
				time = (TextView) row.findViewById(R.id.time);
			}
			return time;
		}

		TextView getDate() {
			if (date == null) {
				date = (TextView) row.findViewById(R.id.date);
			}
			return date;
		}
	}

	// Edit the countryName from here.
	private void EditCountryName() {
		try {
			dialog = new Dialog(getActivity());
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setCancelable(true);
			dialog.setContentView(R.layout.clock_editcountry);
			editConutryName = (EditText) dialog
					.findViewById(R.id.editConutryName);
			btnSave = (Button) dialog.findViewById(R.id.btnSave);
			btnSave.setOnClickListener(this);
			btnRemove = (Button) dialog.findViewById(R.id.btnRemove);
			btnRemove.setOnClickListener(this);
			dialog.show();
		} catch (Exception e) {
		}
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	// Here used all the button clicks included in global clock( Add TimeZone,
	// Saving the Customized names, -
	// and Removing the unwanted list entries etc. ).
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		try {
			vibrate();
			switch (v.getId()) {
			case R.id.btnTimeZone:
				startActivity(new Intent(getActivity(), AddTimeZone.class));
				break;
			case R.id.btnSave:
				String strCustomCountry = editConutryName.getText().toString();
				if (strCustomCountry.length() != 0) {
					cursor.moveToPosition(inListPosition);
					String args[] = { cursor.getString(cursor
							.getColumnIndex("tzid")) };
					ContentValues cv = new ContentValues();
					cv.put("contryname", strCustomCountry);
					db.update("timezones", cv, "tzid=?", args);
					cursor.requery();
				} else {
					Toast.makeText(getActivity(), "Enter Country's Name!!",
							Toast.LENGTH_SHORT).show();
				}
				dialog.dismiss();
				break;
			case R.id.btnRemove:
				cursor.moveToPosition(inListPosition);
				current = new TimeZoneEntry(cursor.getString(cursor
						.getColumnIndex("tzid")));
				current.delete(db);
				cursor.requery();
				if (cursor.getCount() == 0 && !showedInstructions) {
					Button btnStart = (Button) getView().findViewById(
							R.id.btnStart);
					btnStart.setVisibility(View.VISIBLE);
				}
				dialog.dismiss();
				break;
			default:
				break;
			}
		} catch (Exception e) {
		}
	}
}