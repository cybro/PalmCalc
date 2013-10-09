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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

class HistoryEntry {
	private static final int VERSION_1 = 1;
	private String strBase;
	private String strEdited;

	HistoryEntry(String strMy) {
		strBase = strMy;
		clearEdited();
	}

	HistoryEntry(int inVersion, DataInput in) throws IOException {
		if (inVersion >= VERSION_1) {
			strBase = in.readUTF();
			strEdited = in.readUTF();
			;
		} else {
			throw new IOException("invalid inVersion " + inVersion);
		}
	}

	void write(DataOutput out) throws IOException {
		out.writeUTF(strBase);
		out.writeUTF(strEdited);
	}

	@Override
	public String toString() {
		return strBase;
	}

	void clearEdited() {
		strEdited = strBase;
	}

	String getEdited() {
		return strEdited;
	}

	void setEdited(String edited) {
		strEdited = edited;
	}

	String getBase() {
		return strBase;
	}
}
