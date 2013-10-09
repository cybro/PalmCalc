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
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

	public static final String MYDATABASENAME = "timezones.db";
	public static final String MYDATABASETABLE = "timezone_table1";
	public static final int MYDATABASEVERSION = 1;
	public static final String KEY_id = "_id";
	public static final String KEY_countryname = "countrkey";

	private static final String SCRIPT_CREATE_DATABASE = "create table if not exists "
			+ MYDATABASETABLE
			+ " ("
			+ KEY_id
			+ " integer primary key autoincrement, "
			+ KEY_countryname
			+ " text not null);";
	private datahelper sqliteopenhelper;
	public static String userselectedlang;
	private SQLiteDatabase sqlitedatabase;
	private Context context;

	public Database(Context c) {
		System.out.println("DBcontext!!!1111");
		context = c;
	}

	public Database openToread() throws android.database.SQLException {
		System.out.println("DBINSERTTTTTTTTT!!!OPENREAD22222");
		sqliteopenhelper = new datahelper(context, MYDATABASENAME, null,
				MYDATABASEVERSION);
		System.out.println("DBINSERTTTTTTTTT!!!OPENREAD22222");
		sqlitedatabase = sqliteopenhelper.getReadableDatabase();
		System.out.println("DBINSERTTTTTTTTT!!!OPENREAD22222");
		return this;
	}

	public Database openTowrite() throws android.database.SQLException {
		System.out.println("DBINSERTTTTTTTTT!!!OPENWRITE22222");
		sqliteopenhelper = new datahelper(context, MYDATABASENAME, null,
				MYDATABASEVERSION);
		sqlitedatabase = sqliteopenhelper.getWritableDatabase();
		return this;
	}

	public void close() {
		sqliteopenhelper.close();
	}

	public Cursor retriveall() {
		System.out.println("RETRIEVE ALL OK!!!!!!!!!");
		String[] columns = { KEY_id, KEY_countryname };
		Cursor cursor = sqlitedatabase.query(MYDATABASETABLE, columns, null,
				null, null, null, null);
		return cursor;
	}

	public long insert(String countrname) {
		System.out.println("DBINSERTTTTTTTTT!!!INSERT !!!!!!!!!1111");
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(KEY_countryname, countrname);
		return sqlitedatabase.insert(MYDATABASETABLE, null, contentvalues);
	}

	/*
	 * public int deleteall(){
	 * 
	 * return sqlitedatabase.delete(MYDATABASETABLE,null,null); }
	 */
	/*
	 * public int deleterow(){ String selectedrow=listlang.selectedlanguage;
	 * 
	 * return sqlitedatabase.delete(MYDATABASETABLE,selectedrow, null); }
	 */
	private static class datahelper extends SQLiteOpenHelper {

		public datahelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(SCRIPT_CREATE_DATABASE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			// db.update(table, values, whereClause, whereArgs)
		}

	}

}
