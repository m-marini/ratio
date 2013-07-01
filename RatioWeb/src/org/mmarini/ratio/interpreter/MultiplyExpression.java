package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: MultiplyExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public class MultiplyExpression extends SequenceExpression {
	private static MultiplyExpression instance = new MultiplyExpression();

	/**
	 * @return Returns the instance.
	 */
	public static MultiplyExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private MultiplyExpression() {
		AbstractExpression[] expression = new AbstractExpression[2];
		expression[0] = UnaryExpression.getInstance();
		expression[1] = MultiplySuffixExpression.getInstance();
		setExpression(expression);
	}
}
