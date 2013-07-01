package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: LiteralExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public class LiteralExpression extends AbstractExpression {
	private String literal;

	/**
	 * @param literal
	 */
	public LiteralExpression(String literal) {
		this.literal = literal;
	}

	/**
	 * @see org.mmarini.ratio.interpreter.AbstractExpression#interpret(org.mmarini.ratio.interpreter.IInterpreterContext)
	 */
	public void interpret(IInterpreterContext context) throws SyntaxException {
		String token = context.peek();
		String literal = getLiteral();
		if (token == null)
			throw new SyntaxException(SyntaxException.END_EXPR_LITERAL,
					new Object[] {literal});
		if (!literal.equals(token))
			throw new SyntaxException(SyntaxException.INVALID_CHAR_LITERAL,
					new Object[] {literal, token});
		context.next();
	}

	/**
	 * @return Returns the literal.
	 */
	private String getLiteral() {
		return literal;
	}
}
