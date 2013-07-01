package org.mmarini.ratio.processor;

import org.mmarini.ratio.RationalNumber;

/**
 * @author $Author: marco $
 * @version $Id: SubtractScalarVisitor.java,v 1.1.2.2 2006/01/21 18:10:44 marco
 *          Exp $
 */
public class SubtractScalarVisitor extends AbstractScalarVisitor {
	/**
	 * @param value
	 */
	public SubtractScalarVisitor(ScalarRationalValue value) {
		super(value);
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitNumber(org.mmarini.ratio.processor.ScalarRationalValue)
	 */
	public void visitNumber(ScalarRationalValue value) {
		RationalNumber res = getValue().getNumber().subtract(value.getNumber());
		setResult(new ScalarRationalValue(res));
	}

	public void visitArray(ArrayRationalValue value) {
		setException(new ProcessException(ProcessException.SUB_SCALAR_ARRAY));
	}
}
