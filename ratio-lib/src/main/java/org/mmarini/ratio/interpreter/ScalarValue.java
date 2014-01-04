/**
 * 
 */
package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.RationalNumber;

/**
 * @author US00852
 * 
 */
public class ScalarValue implements Value {
	private final RationalNumber value;

	/**
	 * 
	 * @param v
	 */
	public ScalarValue(final int v) {
		this(RationalNumber.create(v));
	}

	/**
	 * @param value
	 */
	public ScalarValue(final RationalNumber value) {
		this.value = value;
	}

	/**
	 * @throws ParserException
	 * @see org.mmarini.ratio.interpreter.Value#apply(org.mmarini.ratio.interpreter
	 *      .ValueVisitor)
	 */
	@Override
	public <T> T apply(final ValueVisitor<T> v) throws ParserException {
		return v.visit(this);
	}

	/**
	 * @return the value
	 */
	public RationalNumber getValue() {
		return value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
