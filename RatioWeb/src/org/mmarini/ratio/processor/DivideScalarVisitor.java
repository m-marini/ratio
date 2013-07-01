package org.mmarini.ratio.processor;

import org.mmarini.ratio.RationalArray;
import org.mmarini.ratio.RationalNumber;

/**
 * @author $Author: marco $
 * @version $Id: DivideScalarVisitor.java,v 1.1.2.2 2006/01/21 18:10:44 marco
 *          Exp $
 */
public class DivideScalarVisitor extends AbstractScalarVisitor {
	/**
	 * @param value
	 */
	public DivideScalarVisitor(ScalarRationalValue value) {
		super(value);
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitNumber(org.mmarini.ratio.processor.ScalarRationalValue)
	 */
	public void visitNumber(ScalarRationalValue value) {
		RationalNumber res = getValue().getNumber().divide(value.getNumber());
		setResult(new ScalarRationalValue(res));
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitArray(org.mmarini.ratio.processor.ArrayRationalValue)
	 */
	public void visitArray(ArrayRationalValue value) {
		RationalNumber a = getValue().getNumber();
		try {
			RationalArray b = value.getArray().inverse();
			setResult(new ArrayRationalValue(b.multiply(a)));
		} catch (IllegalArgumentException e) {
			setException(new ProcessException(
					ProcessException.ILLEGAL_ARGUMENT, e, new Object[] {e
							.getMessage()}));
		}
	}
}
