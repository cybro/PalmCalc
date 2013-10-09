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

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * Wheel items adapter interface
 */
public interface WheelViewAdapter {
	/**
	 * Gets items count
	 * 
	 * @return the count of wheel items
	 */
	public int getItemsCount();

	/**
	 * Get a View that displays the data at the specified position in the data
	 * set
	 * 
	 * @param index
	 *            the item index
	 * @param convertView
	 *            the old view to reuse if possible
	 * @param parent
	 *            the parent that this view will eventually be attached to
	 * @return the wheel item View
	 */
	public View getItem(int index, View convertView, ViewGroup parent);

	/**
	 * Get a View that displays an empty wheel item placed before the first or
	 * after the last wheel item.
	 * 
	 * @param convertView
	 *            the old view to reuse if possible
	 * @param parent
	 *            the parent that this view will eventually be attached to
	 * @return the empty item View
	 */
	public View getEmptyItem(View convertView, ViewGroup parent);

	/**
	 * Register an observer that is called when changes happen to the data used
	 * by this adapter.
	 * 
	 * @param observer
	 *            the observer to be registered
	 */
	public void registerDataSetObserver(DataSetObserver observer);

	/**
	 * Unregister an observer that has previously been registered
	 * 
	 * @param observer
	 *            the observer to be unregistered
	 */
	void unregisterDataSetObserver(DataSetObserver observer);
}
