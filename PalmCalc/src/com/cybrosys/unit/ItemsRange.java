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

/**
 * Range for visible items.
 */
public class ItemsRange {
	// First item number
	private int first;

	// Items count
	private int count;

	/**
	 * Default constructor. Creates an empty range
	 */
	public ItemsRange() {
		this(0, 0);
	}

	/**
	 * Constructor
	 * 
	 * @param first
	 *            the number of first item
	 * @param count
	 *            the count of items
	 */
	public ItemsRange(int first, int count) {
		this.first = first;
		this.count = count;
	}

	/**
	 * Gets number of first item
	 * 
	 * @return the number of the first item
	 */
	public int getFirst() {
		return first;
	}

	/**
	 * Gets number of last item
	 * 
	 * @return the number of last item
	 */
	public int getLast() {
		return getFirst() + getCount() - 1;
	}

	/**
	 * Get items count
	 * 
	 * @return the count of items
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Tests whether item is contained by range
	 * 
	 * @param index
	 *            the item number
	 * @return true if item is contained
	 */
	public boolean contains(int index) {
		return index >= getFirst() && index <= getLast();
	}
}