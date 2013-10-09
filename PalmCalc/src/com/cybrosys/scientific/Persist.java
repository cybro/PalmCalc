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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import android.content.Context;

class Persist {
	private static final int inLastVersion = 2;
	private static final String strFilename = "calculator.txt";
	private Context mContext;
	History history = new History();
	private int inDelMode;

	Persist(Context context) {
		this.mContext = context;
	}

	public void setDeleteMode(int mode) {
		inDelMode = mode;
	}

	public int getDeleteMode() {
		return inDelMode;
	}

	public void load() {
		try {
			InputStream is = new BufferedInputStream(
					mContext.openFileInput(strFilename), 8192);
			DataInputStream in = new DataInputStream(is);
			int inVersion = in.readInt();
			if (inVersion > 1) {
				inDelMode = in.readInt();
			} else if (inVersion > inLastVersion) {
				throw new IOException("data inVersion " + inVersion
						+ "; expected " + inLastVersion);
			}
			history = new History(inVersion, in);
			in.close();
		} catch (FileNotFoundException e) {
			ScientificActivity.log("" + e);
		} catch (IOException e) {
			ScientificActivity.log("" + e);
		}
	}

	public void save(String strResult) {
		try {
			OutputStream os = new BufferedOutputStream(mContext.openFileOutput(
					strFilename, 0), 8192);
			DataOutputStream out = new DataOutputStream(os);
			out.writeInt(inLastVersion);
			out.writeInt(inDelMode);
			history.write(out);
			out.close();
		} catch (IOException e) {
			ScientificActivity.log("" + e);
		}
	}
}
