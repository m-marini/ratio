package org.mmarini.ratio.processor;

/**
 * @author $Author: marco $
 * @version $Id: IRationalVisitor.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public interface IRationalVisitor {
	/**
	 * @param value
	 */
	public abstract void visitNumber(ScalarRationalValue value);

	/**
	 * @param value
	 */
	public abstract void visitArray(ArrayRationalValue value);
}
