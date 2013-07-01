package org.mmarini.ratio.processor;

import java.io.Serializable;

import org.mmarini.ratio.RationalArray;

/**
 * @author $Author: marco $
 * @version $Id: ArrayRationalValue.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class ArrayRationalValue implements Serializable, IRationalValue {
	private static final long serialVersionUID = 1L;
	private RationalArray array;

	/**
	 * @param array
	 */
	public ArrayRationalValue(RationalArray array) {
		this.array = array;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getArray().toString();
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#negate()
	 */
	public IRationalValue negate() {
		return new ArrayRationalValue(getArray().negate());
	}

	/**
	 * @throws ProcessException
	 * @see org.mmarini.ratio.processor.IRationalValue#add(org.mmarini.ratio.processor.IRationalValue)
	 */
	public IRationalValue add(IRationalValue value) throws ProcessException {
		AbstractValueVisitor visitor = new AddArrayVisitor(this);
		value.accept(visitor);
		if (visitor.getException() != null)
			throw visitor.getException();
		return visitor.getResult();
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#subtract(org.mmarini.ratio.processor.IRationalValue)
	 */
	public IRationalValue subtract(IRationalValue value)
			throws ProcessException {
		AbstractValueVisitor visitor = new SubtractArrayVisitor(this);
		value.accept(visitor);
		if (visitor.getException() != null)
			throw visitor.getException();
		return visitor.getResult();
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#multiply(org.mmarini.ratio.processor.IRationalValue)
	 */
	public IRationalValue multiply(IRationalValue value)
			throws ProcessException {
		AbstractValueVisitor visitor = new MultiplyArrayVisitor(this);
		value.accept(visitor);
		if (visitor.getException() != null)
			throw visitor.getException();
		return visitor.getResult();
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#divide(org.mmarini.ratio.processor.IRationalValue)
	 */
	public IRationalValue divide(IRationalValue value) throws ProcessException {
		AbstractValueVisitor visitor = new DivideArrayVisitor(this);
		value.accept(visitor);
		if (visitor.getException() != null)
			throw visitor.getException();
		return visitor.getResult();
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#transpose()
	 */
	public IRationalValue transpose() {
		return new ArrayRationalValue(getArray().transpose());
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#determine()
	 */
	public IRationalValue determine() throws ProcessException {
		try {
			return new ScalarRationalValue(getArray().getDeterminer());
		} catch (IllegalArgumentException e) {
			throw new ProcessException(ProcessException.ILLEGAL_ARGUMENT, e,
					new Object[] {e.getMessage()});
		}
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#accept(org.mmarini.ratio.processor.IRationalVisitor)
	 */
	public void accept(IRationalVisitor visitor) {
		visitor.visitArray(this);
	}

	/**
	 * @return Returns the vector.
	 */
	public RationalArray getArray() {
		return array;
	}
}
