package org.mmarini.ratio.processor;

import org.mmarini.ratio.RationalArray;
import org.mmarini.ratio.RationalNumber;

/**
 * @author $Author: marco $
 * @version $Id: MultiplyArrayVisitor.java,v 1.1.2.2 2006/01/21 18:10:44 marco
 *          Exp $
 */
public class MultiplyArrayVisitor extends AbstractArrayVisitor {
	/**
	 * @param value
	 */
	public MultiplyArrayVisitor(ArrayRationalValue value) {
		super(value);
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitNumber(org.mmarini.ratio.processor.ScalarRationalValue)
	 */
	public void visitNumber(ScalarRationalValue value) {
		RationalArray a = getValue().getArray();
		RationalNumber b = value.getNumber();
		try {
			setResult(new ArrayRationalValue(a.multiply(b)));
		} catch (IllegalArgumentException e) {
			setException(new ProcessException(
					ProcessException.ILLEGAL_ARGUMENT, e, new Object[] {e
							.getMessage()}));
		}
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitArray(org.mmarini.ratio.processor.ArrayRationalValue)
	 */
	public void visitArray(ArrayRationalValue value) {
		RationalArray a = getValue().getArray();
		RationalArray b = value.getArray();
		try {
			setResult(new ArrayRationalValue(a.multiply(b)));
		} catch (IllegalArgumentException e) {
			setException(new ProcessException(
					ProcessException.ILLEGAL_ARGUMENT, e, new Object[] {e
							.getMessage()}));
		}
	}
}
