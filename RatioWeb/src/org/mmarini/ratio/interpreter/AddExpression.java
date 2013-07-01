package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: AddExpression.java,v 1.2 2007/01/09 22:11:27 marco Exp $
 */
public class AddExpression extends SequenceExpression {
	private static AddExpression instance = new AddExpression();

	/**
	 * @return Returns the instance.
	 */
	public static AddExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private AddExpression() {
		AbstractExpression[] expression = new AbstractExpression[2];
		expression[0] = MultiplyExpression.getInstance();
		expression[1] = AddSuffixExpression.getInstance();
		setExpression(expression);
	}
}
