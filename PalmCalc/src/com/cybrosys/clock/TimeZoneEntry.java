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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TimeZoneEntry {
	private String id = "";

	TimeZoneEntry() {
	}

	TimeZoneEntry(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return getId();
	}

	boolean alreadySaved(SQLiteDatabase db) {
		String args[] = { id };
		ContentValues cv = new ContentValues();
		cv.put("tzid", id);
		Cursor c = db.rawQuery("SELECT * FROM timezones WHERE tzid=?", args);
		// return db.rawQuery("SELECT * FROM timezones WHERE tzid=?",
		// args).getCount() != 0;
		// while (c.moveToNext())
		// {
		// String ajay=c.getString();
		// if(ajay.equals(object)){}
		// }
		if (c.getCount() == 0) {
			return false;
		}
		return true;
	}

	void save(SQLiteDatabase db) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("tzid", id);
			db.insert("timezones", "tzid", cv);
		} catch (Exception e) {
		}
	}

	void delete(SQLiteDatabase db) {
		try {
			String args[] = { id };
			ContentValues cv = new ContentValues();
			cv.put("tzid", id);
			db.delete("timezones", "tzid=?", args);
		} catch (Exception e) {
		}
	}

	void Edite(SQLiteDatabase db) {
		try {
			String args[] = { id };
			ContentValues cv = new ContentValues();
			cv.put("contryname", id);
			db.update("timezones", cv, "countryname=?", args);
		} catch (Exception e) {
		}
	}

	static Cursor getAll(SQLiteDatabase db) {
		return db.rawQuery("SELECT * FROM timezones", null);
	}
}