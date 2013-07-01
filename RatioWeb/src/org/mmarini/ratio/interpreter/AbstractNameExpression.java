package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: AbstractNameExpression.java,v 1.1.2.1 2006/01/18 18:08:52 marco
 *          Exp $
 */
public abstract class AbstractNameExpression extends AbstractExpression {
	/**
	 * 
	 */
	protected AbstractNameExpression() {}

	/**
	 * @param context
	 * @return
	 * @throws SyntaxException
	 */
	protected String parseName(IInterpreterContext context)
			throws SyntaxException {
		String token = context.peek();
		if (token == null)
			throw new SyntaxException(SyntaxException.END_EXP_VAR_NAME);
		if (!isName(token)) {
			throw new SyntaxException(SyntaxException.INVALID_VAR_NAME_CHAR,
					new Object[] {token});
		}
		context.next();
		return token;
	}

	/**
	 * @param token
	 * @return
	 */
	private boolean isName(String token) {
		return VariableNameValidator.getInstance().isName(token);
	}
}
