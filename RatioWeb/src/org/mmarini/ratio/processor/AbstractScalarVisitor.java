package org.mmarini.ratio.processor;

/**
 * @author $Author: marco $
 * @version $Id: AbstractScalarVisitor.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public abstract class AbstractScalarVisitor extends AbstractValueVisitor {
	private ScalarRationalValue value;

	/**
	 * @param value
	 */
	public AbstractScalarVisitor(ScalarRationalValue value) {
		this.value = value;
	}

	/**
	 * @return Returns the value.
	 */
	protected ScalarRationalValue getValue() {
		return value;
	}
}
