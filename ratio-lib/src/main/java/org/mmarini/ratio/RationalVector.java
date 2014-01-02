/*
 * Created on Jul 12, 2004
 */
package org.mmarini.ratio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author US00852
 * @version $Id: RationalVector.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RationalVector {
	private final RationalNumber[] values;

	/**
	 * 
	 * @param list
	 */
	public RationalVector(final List<RationalNumber> list) {
		this(list.toArray(new RationalNumber[0]));
	}

	/**
	 * @param rank
	 */
	public RationalVector(final RationalNumber... values) {
		this.values = values;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalVector add(RationalVector a) {
		final int n = getDimensions();
		final int m = a.getDimensions();
		if (m != n)
			throw new IllegalArgumentException(
					"Add of different dimensions vector [" + n + "] != [" + m
							+ "]");
		final RationalNumber[] l = new RationalNumber[n];
		for (int i = 0; i < n; ++i)
			l[i] = values[i].add(a.values[i]);
		return new RationalVector(l);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RationalVector other = (RationalVector) obj;
		if (!Arrays.equals(values, other.values))
			return false;
		return true;
	}

	/**
	 * @return
	 */
	public int getDimensions() {
		return values.length;
	}

	/**
	 * @return
	 */
	public RationalNumber getLength2() {
		RationalNumber value = RationalNumber.ZERO;
		for (final RationalNumber v : values)
			value = value.add(v.mul(v));
		return value;
	}

	/**
	 * @return the values
	 */
	public RationalNumber[] getValues() {
		return values;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	/**
	 * @param scale
	 * @return
	 */
	public RationalVector multiply(RationalNumber scale) {
		final List<RationalNumber> l = new ArrayList<>();
		for (final RationalNumber v : values)
			l.add(v.mul(scale));
		return new RationalVector(l);
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalNumber multiply(RationalVector a) {
		final int n = getDimensions();
		final int m = a.getDimensions();
		if (m != n)
			throw new IllegalArgumentException(
					"Multiply of different dimensions vector [" + n + "] != ["
							+ m + "]");

		RationalNumber s = RationalNumber.ZERO;
		for (int i = 0; i < n; ++i)
			s = s.add(values[i].mul(a.values[i]));
		return s;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalVector subtract(RationalVector a) {
		final int n = getDimensions();
		final int m = a.getDimensions();
		if (m != n)
			throw new IllegalArgumentException(
					"Subtract of different dimensions vector [" + n + "] != ["
							+ m + "]");
		final RationalNumber[] l = new RationalNumber[n];
		for (int i = 0; i < n; ++i)
			l[i] = values[i].sub(a.values[i]);
		return new RationalVector(l);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Arrays.toString(values);
	}
}