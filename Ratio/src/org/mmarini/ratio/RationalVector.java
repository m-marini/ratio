/*
 * Created on Jul 12, 2004
 */
package org.mmarini.ratio;

/**
 * @author US00852
 * @version $Id: RationalVector.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RationalVector {
	private RationalNumber[] vector;

	/**
	 * @param rank
	 */
	public RationalVector(int rank) {
		this.setVector(new RationalNumber[rank]);
		RationalNumber number0 = new RationalNumber();
		for (int i = 0; i < rank; ++i) {
			this.setValue(i, number0);
		}
	}

	/**
	 * @return Returns the array.
	 */
	public RationalNumber getValue(int i) {
		return this.getVector()[i];
	}

	/**
	 * @return Returns the array.
	 */
	public void setValue(int i, RationalNumber value) {
		this.getVector()[i] = value;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalVector add(RationalVector a) {
		int rank = this.getRank();
		if (a.getRank() != rank)
			throw new IllegalArgumentException("Add of different rank vector");
		RationalVector vector = new RationalVector(rank);
		for (int i = 0; i < rank; ++i)
			vector.setValue(i, this.getValue(i).add(a.getValue(i)));
		return vector;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalVector subtract(RationalVector a) {
		int rank = this.getRank();
		if (a.getRank() != rank)
			throw new IllegalArgumentException(
					"Subtract of different rank vector");
		RationalVector vector = new RationalVector(rank);
		for (int i = 0; i < rank; ++i)
			for (int j = 0; j < rank; ++j)
				vector.setValue(i, this.getValue(i).subtract(a.getValue(i)));
		return vector;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalNumber multiply(RationalVector a) {
		int rank = this.getRank();
		if (a.getRank() != rank)
			throw new IllegalArgumentException(
					"Multiply of different rank arrays");
		RationalNumber value = new RationalNumber();
		for (int i = 0; i < rank; ++i) {
			RationalNumber term = this.getValue(i).multiply(a.getValue(i));
			value = value.add(term);
		}
		return value;
	}

	/**
	 * @param scale
	 * @return
	 */
	public RationalVector multiply(RationalNumber scale) {
		int rank = this.getRank();
		RationalVector scaled = new RationalVector(rank);
		for (int i = 0; i < rank; ++i) {
			scaled.setValue(i, this.getValue(i).multiply(scale));
		}
		return scaled;
	}

	/**
	 * @return
	 */
	public RationalNumber getLength2() {
		int rank = this.getRank();
		RationalNumber value = new RationalNumber();
		for (int i = 0; i < rank; ++i) {
			value = value.add(this.getValue(i).multiply(this.getValue(i)));
		}
		return value;
	}

	/**
	 * @return
	 */
	public int getRank() {
		return this.getVector().length;
	}

	/**
	 * @param array
	 *            The array to set.
	 */
	protected void setVector(RationalNumber[] array) {
		this.vector = array;
	}

	/**
	 * @return Returns the array.
	 */
	protected RationalNumber[] getVector() {
		return vector;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (! (obj instanceof RationalVector))
			return false;
		RationalVector array = (RationalVector) obj;
		int size = this.getRank();
		if (size != array.getRank())
			return false;
		for (int i = 0; i < size; ++i)
			if (!this.getValue(i).equals(array.getValue(i)))
				return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer value = new StringBuffer();
		value.append("(");
		int size = this.getRank();
		for (int i = 0; i < size; ++i) {
			if (i > 0)
				value.append(", ");
			value.append(this.getValue(i));
		}
		value.append(")");
		return value.toString();
	}
}