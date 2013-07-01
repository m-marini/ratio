package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: NonTerminalExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public abstract class NonTerminalExpression extends AbstractExpression {
	private AbstractExpression[] expression;

	/**
	 * @return Returns the expression.
	 */
	protected AbstractExpression[] getExpression() {
		return expression;
	}

	/**
	 * @param expression
	 *            The expression to set.
	 */
	protected void setExpression(AbstractExpression[] expression) {
		this.expression = expression;
	}
}
