package org.mmarini.ratio;

/**
 * @author US00852
 * @version $Id: RationalNumber.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RationalNumber extends Number implements
		Comparable<RationalNumber> {
	private static final long serialVersionUID = 8377863324274024551L;
	public static final RationalNumber ZERO = new RationalNumber(0, 1);
	public static final RationalNumber ONE = new RationalNumber(1, 1);

	/**
	 * Compute the greater common divisor between two number.
	 * <p>
	 * It implements the euclidean algorithm.
	 * </p>
	 * 
	 * @param a1
	 * @param b1
	 * @return
	 */
	public static int computeGCD(final int a1, final int b1) {
		int a = Math.abs(a1);
		int b = Math.abs(b1);
		while (b != 0) {
			final int t = b;
			b = a % b;
			a = t;
		}
		return a;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public static RationalNumber create(final int n) {
		return new RationalNumber(n, 1);
	}

	/**
	 * 
	 *
	 */
	public static RationalNumber create(final int upper, final int lower) {
		final int l = upper == 0 ? 1 : lower >= 0 ? lower : -lower;
		final int u = lower >= 0 ? upper : -upper;
		final int gcd = computeGCD(u, l);
		return gcd > 1 ? new RationalNumber(u / gcd, l / gcd)
				: new RationalNumber(u, l);
	}

	private final int upper;

	private final int lower;

	/**
	 * @param upper
	 * @param lower
	 */
	private RationalNumber(final int upper, final int lower) {
		this.upper = upper;
		this.lower = lower;
	}

	/**
	 * 
	 * @return
	 */
	public RationalNumber abs() {
		return new RationalNumber(Math.abs(upper), lower);
	}

	/**
	 * @param number
	 * @return
	 */
	public RationalNumber add(final RationalNumber number) {
		return create(upper * number.lower + lower * number.upper, lower
				* number.lower);
	}

	/**
	 * @param value
	 * @return
	 */
	public RationalArray augmentCol(final RationalNumber value) {
		return new RationalArray(new RationalNumber[][] { { this, value } });
	}

	/**
	 * @param value
	 * @return
	 */
	public RationalArray augmentRow(final RationalNumber value) {
		return new RationalArray(new RationalNumber[][] { { this }, { value } });
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final RationalNumber o) {
		return upper * o.lower - o.upper * lower;
	}

	/**
	 * @param number
	 * @return
	 */
	public RationalNumber div(final RationalNumber number) {
		if (number.upper == 0)
			throw new IllegalArgumentException("divide by 0");
		return create(upper * number.lower, lower * number.upper);
	}

	/**
	 * @see java.lang.Number#doubleValue()
	 */
	@Override
	public double doubleValue() {
		return (double) upper / lower;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RationalNumber other = (RationalNumber) obj;
		if (lower != other.lower)
			return false;
		if (upper != other.upper)
			return false;
		return true;
	}

	/**
	 * @see java.lang.Number#floatValue()
	 */
	@Override
	public float floatValue() {
		return (float) doubleValue();
	}

	/**
	 * @return Returns the lower.
	 */
	public int getLower() {
		return lower;
	}

	/**
	 * @return Returns the upper.
	 */
	public int getUpper() {
		return upper;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lower;
		result = prime * result + upper;
		return result;
	}

	/**
	 * @see java.lang.Number#intValue()
	 */
	@Override
	public int intValue() {
		return (int) this.doubleValue();
	}

	/**
	 * @return
	 */
	public RationalNumber inv() {
		if (upper == 0)
			throw new IllegalArgumentException("divide by 0");
		return upper > 0 ? new RationalNumber(lower, upper)
				: new RationalNumber(-lower, -upper);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isInteger() {
		return lower == 1;
	}

	/**
	 * @see java.lang.Number#longValue()
	 */
	@Override
	public long longValue() {
		return (long) this.doubleValue();
	}

	/**
	 * @param number
	 * @return
	 */
	public RationalNumber mul(final RationalNumber number) {
		return create(upper * number.upper, lower * number.lower);
	}

	/**
	 * @return
	 */
	public RationalNumber neg() {
		return new RationalNumber(-upper, lower);
	}

	/**
	 * @param number
	 * @return
	 */
	public RationalNumber sub(final RationalNumber number) {
		return create(upper * number.lower - lower * number.upper, lower
				* number.lower);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(upper);
		if (lower > 1)
			builder.append("/").append(lower);
		return builder.toString();
	}
}
