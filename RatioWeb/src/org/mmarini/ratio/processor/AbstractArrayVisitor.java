package org.mmarini.ratio.processor;

/**
 * @author $Author: marco $
 * @version $Id: AbstractArrayVisitor.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public abstract class AbstractArrayVisitor extends AbstractValueVisitor {
	private ArrayRationalValue value;

	/**
	 * @param value
	 */
	public AbstractArrayVisitor(ArrayRationalValue value) {
		this.value = value;
	}

	/**
	 * @return Returns the value.
	 */
	protected ArrayRationalValue getValue() {
		return value;
	}
}
