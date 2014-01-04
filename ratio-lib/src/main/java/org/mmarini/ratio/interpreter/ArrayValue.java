/**
 * 
 */
package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.RationalArray;

/**
 * @author US00852
 * 
 */
public class ArrayValue implements Value {

	private final RationalArray value;

	/**
	 * 
	 */
	public ArrayValue(final RationalArray value) {
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
	public RationalArray getValue() {
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
