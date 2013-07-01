package org.mmarini.ratio.interpreter;


/**
 * @author $Author: marco $
 * @version $Id: AbstractExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public abstract class AbstractExpression {
	/**
	 * @param context
	 * @throws SyntaxException
	 */
	public abstract void interpret(IInterpreterContext context)
			throws SyntaxException;
}
