package org.mmarini.ratio;

/**
 * @author US00852
 * @version $Id: RationalNumber.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class RationalNumber extends Number {
	private static final long serialVersionUID = 1L;
	public static final RationalNumber ZERO = new RationalNumber(0);
	public static final RationalNumber ONE = new RationalNumber(1);
	private int upper = 0;
	private int lower = 1;

	/**
	 * 
	 */
	public RationalNumber() {
	}

	/**
	 * @param number
	 */
	public RationalNumber(int number) {
		this(number, 1);
	}

	/**
	 * @param upper
	 * @param lower
	 */
	public RationalNumber(int upper, int lower) {
		this();
		setUpper(upper);
		setLower(lower);
		normalize();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Number))
			return false;
		if (!(obj instanceof RationalNumber))
			return this.doubleValue() == ((Number) obj).doubleValue();
		RationalNumber num = (RationalNumber) obj;
		return getUpper() * num.getLower() == getLower() * num.getUpper();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.intValue();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer value = new StringBuffer();
		value.append(this.getUpper());
		int lower = this.getLower();
		if (lower > 1) {
			value.append("/");
			value.append(lower);
		}
		return value.toString();
	}

	/**
	 * @return Returns the lower.
	 */
	public int getLower() {
		return lower;
	}

	/**
	 * @param lower
	 *            The lower to set.
	 */
	protected void setLower(int lower) {
		this.lower = lower;
	}

	/**
	 * @return Returns the upper.
	 */
	public int getUpper() {
		return upper;
	}

	/**
	 * @param upper
	 *            The upper to set.
	 */
	protected void setUpper(int upper) {
		this.upper = upper;
	}

	/**
	 * @see java.lang.Number#doubleValue()
	 */
	public double doubleValue() {
		return (double) this.getUpper() / this.getLower();
	}

	/**
	 * @see java.lang.Number#floatValue()
	 */
	public float floatValue() {
		return (float) this.doubleValue();
	}

	/**
	 * @see java.lang.Number#intValue()
	 */
	public int intValue() {
		return (int) this.doubleValue();
	}

	/**
	 * @see java.lang.Number#longValue()
	 */
	public long longValue() {
		return (long) this.doubleValue();
	}

	/**
	 * 
	 * 
	 */
	protected void normalize() {
		int upper = this.getUpper();
		int lower = this.getLower();
		if (upper == 0)
			lower = 1;
		if (lower < 0) {
			lower = -lower;
			upper = -upper;
		}
		int mcd = Factorizer.getInstance().getMCD(upper, lower);
		if (mcd > 1) {
			upper /= mcd;
			lower /= mcd;
		}
		this.setUpper(upper);
		this.setLower(lower);
	}

	/**
	 * @param number
	 * @return
	 */
	public RationalNumber add(RationalNumber number) {
		int lower = this.getLower() * number.getLower();
		int upper = this.getUpper() * number.getLower() + this.getLower()
				* number.getUpper();
		return new RationalNumber(upper, lower);
	}

	/**
	 * @param number
	 * @return
	 */
	public RationalNumber subtract(RationalNumber number) {
		int lower = this.getLower() * number.getLower();
		int upper = this.getUpper() * number.getLower() - this.getLower()
				* number.getUpper();
		return new RationalNumber(upper, lower);
	}

	/**
	 * @return
	 */
	public RationalNumber negate() {
		return new RationalNumber(-this.getUpper(), this.getLower());
	}

	/**
	 * @return
	 */
	public RationalNumber inverse() {
		return new RationalNumber(this.getLower(), this.getUpper());
	}

	/**
	 * @param number
	 * @return
	 */
	public RationalNumber multiply(RationalNumber number) {
		int upper = this.getUpper() * number.getUpper();
		int lower = this.getLower() * number.getLower();
		return new RationalNumber(upper, lower);
	}

	/**
	 * @param number
	 * @return
	 */
	public RationalNumber divide(RationalNumber number) {
		int upper = this.getUpper() * number.getLower();
		int lower = this.getLower() * number.getUpper();
		return new RationalNumber(upper, lower);
	}

	/**
	 * @return
	 */
	public boolean isNull() {
		return getUpper() == 0;
	}

	/**
	 * 
	 * @return
	 */
	public RationalNumber absolute() {
		if (getUpper() >= 0)
			return this;
		return negate();
	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public int compareTo(RationalNumber number) {
		return getUpper() * number.getLower() - getLower() * number.getUpper();
	}
}
