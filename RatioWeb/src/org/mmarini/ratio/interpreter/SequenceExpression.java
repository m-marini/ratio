package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: SequenceExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public abstract class SequenceExpression extends NonTerminalExpression {
	/**
	 * @see org.mmarini.ratio.interpreter.AbstractExpression#interpret(org.mmarini.ratio.interpreter.IInterpreterContext)
	 */
	public void interpret(IInterpreterContext context) throws SyntaxException {
		AbstractExpression[] exp = getExpression();
		for (int i = 0; i < exp.length; ++i) {
			exp[i].interpret(context);
		}
	}
}
