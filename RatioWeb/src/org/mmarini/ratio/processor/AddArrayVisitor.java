package org.mmarini.ratio.processor;

import org.mmarini.ratio.RationalArray;

/**
 * @author $Author: marco $
 * @version $Id: AddArrayVisitor.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class AddArrayVisitor extends AbstractArrayVisitor {
	/**
	 * @param value
	 */
	public AddArrayVisitor(ArrayRationalValue value) {
		super(value);
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitNumber(org.mmarini.ratio.processor.ScalarRationalValue)
	 */
	public void visitNumber(ScalarRationalValue value) {
		setException(new ProcessException(ProcessException.ADD_ARRAY_SCALAR));
	}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitArray(org.mmarini.ratio.processor.ArrayRationalValue)
	 */
	public void visitArray(ArrayRationalValue value) {
		RationalArray a = getValue().getArray();
		RationalArray b = value.getArray();
		try {
			setResult(new ArrayRationalValue(a.add(b)));
		} catch (IllegalArgumentException e) {
			setException(new ProcessException(
					ProcessException.ILLEGAL_ARGUMENT, e, new Object[] {e
							.getMessage()}));
		}
	}

}
