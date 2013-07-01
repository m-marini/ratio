package org.mmarini.ratio.processor;

/**
 * @author $Author: marco $
 * @version $Id: CreateExpressionArrayVisitor.java,v 1.2 2006/01/31 22:31:29
 *          marco Exp $
 */
public class CheckForArrayVisitor implements IRationalVisitor {
	private ArrayRationalValue array;

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitNumber(org.mmarini.ratio.processor.ScalarRationalValue)
	 */
	public void visitNumber(ScalarRationalValue value) {}

	/**
	 * @see org.mmarini.ratio.processor.IRationalVisitor#visitArray(org.mmarini.ratio.processor.ArrayRationalValue)
	 */
	public void visitArray(ArrayRationalValue value) {
		setArray(value);
	}

	/**
	 * @return Returns the array.
	 */
	public ArrayRationalValue getArray() {
		return array;
	}

	/**
	 * @param array
	 *            The array to set.
	 */
	private void setArray(ArrayRationalValue array) {
		this.array = array;
	}
}
