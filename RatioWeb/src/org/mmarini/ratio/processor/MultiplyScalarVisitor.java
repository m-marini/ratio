package org.mmarini.ratio.processor;

import org.mmarini.ratio.RationalNumber;

/**
 * @author $Author: marco $
 * @version $Id: MultiplyScalarVisitor.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class MultiplyScalarVisitor extends AbstractScalarVisitor {
	/**
	 * @param value
	 */
	public MultiplyScalarVisitor(ScalarRationalValue value) {
		super(value);
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitNumber(org.mmarini.ratio.processor.ScalarRationalValue)
	 */
	public void visitNumber(ScalarRationalValue value) {
		RationalNumber res = getValue().getNumber().multiply(value.getNumber());
		setResult(new ScalarRationalValue(res));
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitArray(org.mmarini.ratio.processor.ArrayRationalValue)
	 */
	public void visitArray(ArrayRationalValue value) {
		setResult(new ArrayRationalValue(value.getArray().multiply(
				getValue().getNumber())));
	}
}
