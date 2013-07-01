package org.mmarini.ratio.processor;

import java.io.Serializable;

import org.mmarini.ratio.RationalNumber;

/**
 * @author $Author: marco $
 * @version $Id: ScalarRationalValue.java,v 1.1.2.1 2006/01/20 20:52:47 marco
 *          Exp $
 */
public class ScalarRationalValue implements IRationalValue, Serializable {
	private static final long serialVersionUID = 1L;
	private RationalNumber number;

	/**
	 * @param number
	 */
	public ScalarRationalValue(RationalNumber number) {
		this.number = number;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getNumber().toString();
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#negate()
	 */
	public IRationalValue negate() {
		return new ScalarRationalValue(getNumber().negate());
	}

	/**
	 * @throws ProcessException
	 * @see org.mmarini.ratio.processor.IRationalValue#add(org.mmarini.ratio.processor.IRationalValue)
	 */
	public IRationalValue add(IRationalValue value) throws ProcessException {
		AbstractScalarVisitor visitor = new AddScalarVisitor(this);
		value.accept(visitor);
		if (visitor.getException() != null)
			throw visitor.getException();
		return visitor.getResult();
	}

	/**
	 * @throws ProcessException
	 * @see org.mmarini.ratio.processor.IRationalValue#subtract(org.mmarini.ratio.processor.IRationalValue)
	 */
	public IRationalValue subtract(IRationalValue value)
			throws ProcessException {
		AbstractScalarVisitor visitor = new SubtractScalarVisitor(this);
		value.accept(visitor);
		if (visitor.getException() != null)
			throw visitor.getException();
		return visitor.getResult();
	}

	/**
	 * @throws ProcessException
	 * @see org.mmarini.ratio.processor.IRationalValue#multiply(org.mmarini.ratio.processor.IRationalValue)
	 */
	public IRationalValue multiply(IRationalValue value)
			throws ProcessException {
		AbstractScalarVisitor visitor = new MultiplyScalarVisitor(this);
		value.accept(visitor);
		if (visitor.getException() != null)
			throw visitor.getException();
		return visitor.getResult();
	}

	/**
	 * @throws ProcessException
	 * @see org.mmarini.ratio.processor.IRationalValue#divide(org.mmarini.ratio.processor.IRationalValue)
	 */
	public IRationalValue divide(IRationalValue value) throws ProcessException {
		AbstractScalarVisitor visitor = new DivideScalarVisitor(this);
		value.accept(visitor);
		if (visitor.getException() != null)
			throw visitor.getException();
		return visitor.getResult();
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#transpose()
	 */
	public IRationalValue transpose() {
		return this;
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#determine()
	 */
	public IRationalValue determine() throws ProcessException {
		return this;
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalValue#accept(org.mmarini.ratio.processor.IRationalVisitor)
	 */
	public void accept(IRationalVisitor visitor) {
		visitor.visitNumber(this);
	}

	/**
	 * @return Returns the number.
	 */
	public RationalNumber getNumber() {
		return number;
	}
}
