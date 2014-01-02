/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

/**
 * @author US00852
 * @version $Id: PoweredNumber.java,v 1.2 2006/01/15 12:21:16 marco Exp $
 */
public class PoweredNumber extends Number {
	private static final long serialVersionUID = -1619691714218374240L;
	public static final PoweredNumber ZERO = new PoweredNumber();
	public static final PoweredNumber ONE = new PoweredNumber(1);

	private final int base;
	private final int exponent;

	/**
	 * 
	 */
	public PoweredNumber() {
		this(0, 1);
	}

	/**
	 * @param base
	 */
	public PoweredNumber(final int base) {
		this(base, 1);
	}

	/**
	 * @param base
	 * @param power
	 */
	public PoweredNumber(final int base, final int power) {
		this.base = base;
		this.exponent = power;
	}

	/**
	 * @see java.lang.Number#doubleValue()
	 */
	@Override
	public double doubleValue() {
		return this.intValue();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Number))
			return false;
		return doubleValue() == ((Number) obj).doubleValue();
	}

	/**
	 * @see java.lang.Number#floatValue()
	 */
	@Override
	public float floatValue() {
		return this.intValue();
	}

	/**
	 * @return Returns the base.
	 */
	public int getBase() {
		return base;
	}

	/**
	 * @return Returns the power.
	 */
	public int getExponent() {
		return exponent;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.intValue();
	}

	/**
	 * @see java.lang.Number#intValue()
	 */
	@Override
	public int intValue() {
		int value;
		if (exponent == 0)
			value = 1;
		else {
			value = base;
			for (int i = 1; i < exponent; ++i)
				value *= base;
		}
		return value;
	}

	/**
	 * @see java.lang.Number#longValue()
	 */
	@Override
	public long longValue() {
		return intValue();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(base).append("^").append(exponent);
		return builder.toString();
	}
}
