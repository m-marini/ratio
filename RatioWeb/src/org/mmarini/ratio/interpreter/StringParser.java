package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: StringContextInterpreter.java,v 1.1.2.1 2006/01/18 16:52:19
 *          marco Exp $
 */
public class StringParser implements IParser {
	private String expression;
	private int index = 0;

	/**
	 * @param expression
	 */
	public StringParser(String expression) {
		this.expression = expression;
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IParser#peek()
	 */
	public int peek() {
		String exp = getExpression();
		int idx = getIndex();
		if (idx >= exp.length())
			return -1;
		return exp.charAt(idx);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IParser#next()
	 */
	public void next() {
		String exp = getExpression();
		int idx = getIndex();
		if (idx < exp.length())
			setIndex(idx + 1);
	}

	/**
	 * @return Returns the expression.
	 */
	private String getExpression() {
		return expression;
	}

	/**
	 * @return Returns the index.
	 */
	private int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            The index to set.
	 */
	private void setIndex(int index) {
		this.index = index;
	}
}
