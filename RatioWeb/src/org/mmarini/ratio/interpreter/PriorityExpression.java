package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: PriorityExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public class PriorityExpression extends SequenceExpression {
	private static PriorityExpression instance = new PriorityExpression();

	/**
	 * @return Returns the instance.
	 */
	public static PriorityExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private PriorityExpression() {
		AbstractExpression[] expression = new AbstractExpression[3];
		expression[0] = new LiteralExpression("(");
		expression[1] = AddExpression.getInstance();
		expression[2] = new LiteralExpression(")");
		setExpression(expression);
	}
}
