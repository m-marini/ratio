package org.mmarini.ratio.processor;

import org.mmarini.ratio.RationalArray;
import org.mmarini.ratio.RationalNumber;

/**
 * @author $Author: marco $
 * @version $Id: DivideArrayVisitor.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class DivideArrayVisitor extends AbstractArrayVisitor {
	/**
	 * @param value
	 */
	public DivideArrayVisitor(ArrayRationalValue value) {
		super(value);
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitNumber(org.mmarini.ratio.processor.ScalarRationalValue)
	 */
	public void visitNumber(ScalarRationalValue value) {
		RationalArray a = getValue().getArray();
		try {
			RationalNumber b = value.getNumber().inverse();
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
		try {
			RationalArray b = value.getArray().inverse();
			setResult(new ArrayRationalValue(a.multiply(b)));
		} catch (IllegalArgumentException e) {
			setException(new ProcessException(
					ProcessException.ILLEGAL_ARGUMENT, e, new Object[] {e
							.getMessage()}));
		}
	}
}
