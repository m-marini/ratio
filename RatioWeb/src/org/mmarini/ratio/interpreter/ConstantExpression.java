package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.RationalNumber;
import org.mmarini.ratio.processor.ConstantCommand;
import org.mmarini.ratio.processor.IRationalValue;
import org.mmarini.ratio.processor.ScalarRationalValue;

/**
 * @author $Author: marco $
 * @version $Id: ConstantExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public class ConstantExpression extends AbstractExpression {
	private static ConstantExpression instance = new ConstantExpression();

	/**
	 * @return Returns the instance.
	 */
	public static ConstantExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private ConstantExpression() {}

	/**
	 * @see org.mmarini.ratio.interpreter.AbstractExpression#interpret(org.mmarini.ratio.interpreter.IInterpreterContext)
	 */
	public void interpret(IInterpreterContext context) throws SyntaxException {
		String token = context.peek();
		if (token == null)
			throw new SyntaxException(SyntaxException.END_EXP_CONST);
		if (!Character.isDigit(token.charAt(0)))
			throw new SyntaxException(SyntaxException.INVALID_CONSTANT_CHAR,
					new Object[] {token});
		context.next();
		/*
		 * Parse del numero
		 */
		int index = 0;
		int len = token.length();
		int upper = 0;
		while (index < len) {
			char ch = token.charAt(index);
			if (!Character.isDigit(ch))
				break;
			upper = upper * 10 + Character.digit(ch, 10);
			++index;
		}
		++index;
		int lower = 1;
		/**
		 * PArte frazionaria
		 */
		while (index < len) {
			char ch = token.charAt(index);
			if (!Character.isDigit(ch))
				break;
			upper = upper * 10 + Character.digit(ch, 10);
			lower *= 10;
			++index;
		}
		IRationalValue number = new ScalarRationalValue(new RationalNumber(
				upper, lower));
		context.addCommand(new ConstantCommand(context, number));
	}
}
