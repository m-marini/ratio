/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

/**
 * @author US00852
 * @version $Id: PoweredNumber.java,v 1.2 2006/01/15 12:21:16 marco Exp $
 */
public class PoweredNumber extends Number {
	private int base = 0;
	private int power = 1;

	/**
	 * 
	 */
	public PoweredNumber() {}

	/**
	 * @param base
	 */
	public PoweredNumber(int base) {
		this(base, 1);
	}

	/**
	 * @param base
	 * @param power
	 */
	public PoweredNumber(int base, int power) {
		this.setBase(base);
		this.setPower(power);
	}

	/**
	 * @see java.lang.Number#doubleValue()
	 */
	public double doubleValue() {
		return this.intValue();
	}

	/**
	 * @see java.lang.Number#floatValue()
	 */
	public float floatValue() {
		return this.intValue();
	}

	/**
	 * @see java.lang.Number#intValue()
	 */
	public int intValue() {
		int base = this.getBase();
		int value = this.getBase();
		int power = this.getPower();
		for (int i = 1; i < power; ++i)
			value *= base;
		return value;
	}

	/**
	 * @see java.lang.Number#longValue()
	 */
	public long longValue() {
		return this.intValue();
	}

	/**
	 * @param base
	 *            The base to set.
	 */
	protected void setBase(int base) {
		this.base = base;
	}

	/**
	 * @param power
	 *            The power to set.
	 */
	protected void setPower(int power) {
		this.power = power;
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
	public int getPower() {
		return power;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (! (obj instanceof Number))
			return false;
		return this.doubleValue() == ((Number) obj).doubleValue();
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
		value.append(this.getBase());
		value.append("^");
		value.append(this.getPower());
		return value.toString();
	}
}
