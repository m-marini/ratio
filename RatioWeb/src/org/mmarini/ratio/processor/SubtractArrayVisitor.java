package org.mmarini.ratio.processor;

import org.mmarini.ratio.RationalArray;

/**
 * @author $Author: marco $
 * @version $Id: SubtractArrayVisitor.java,v 1.1.2.2 2006/01/21 18:10:44 marco
 *          Exp $
 */
public class SubtractArrayVisitor extends AbstractArrayVisitor {
	/**
	 * @param value
	 */
	public SubtractArrayVisitor(ArrayRationalValue value) {
		super(value);
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitNumber(org.mmarini.ratio.processor.ScalarRationalValue)
	 */
	public void visitNumber(ScalarRationalValue value) {
		setException(new ProcessException(ProcessException.SUB_ARRAY_SCALAR));
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitArray(org.mmarini.ratio.processor.ArrayRationalValue)
	 */
	public void visitArray(ArrayRationalValue value) {
		RationalArray a = getValue().getArray();
		RationalArray b = value.getArray();
		try {
			setResult(new ArrayRationalValue(a.subtract(b)));
		} catch (IllegalArgumentException e) {
			setException(new ProcessException(
					ProcessException.ILLEGAL_ARGUMENT, e, new Object[] {e
							.getMessage()}));
		}
	}

}
