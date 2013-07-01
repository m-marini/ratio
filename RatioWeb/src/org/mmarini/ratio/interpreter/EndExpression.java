package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: EndExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public class EndExpression extends AbstractExpression {
	private static EndExpression instance = new EndExpression();

	/**
	 * @return Returns the instance.
	 */
	public static EndExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private EndExpression() {}

	/**
	 * @see org.mmarini.ratio.interpreter.AbstractExpression#interpret(org.mmarini.ratio.interpreter.IInterpreterContext)
	 */
	public void interpret(IInterpreterContext context) throws SyntaxException {
		String token = context.peek();
		if (token != null)
			throw new SyntaxException(SyntaxException.INVALID_END_EXPRESSION,
					new Object[] {token});
	}
}
