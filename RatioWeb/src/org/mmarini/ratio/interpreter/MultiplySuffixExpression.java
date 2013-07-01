package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.processor.DivideCommand;
import org.mmarini.ratio.processor.MultiplyCommand;

/**
 * @author $Author: marco $
 * @version $Id: MultiplySuffixExpression.java,v 1.1.2.2 2006/01/18 23:22:48
 *          marco Exp $
 */
public class MultiplySuffixExpression extends NonTerminalExpression {
	public static final String SLASH = "/";
	public static final String STAR = "*";
	private static MultiplySuffixExpression instance = new MultiplySuffixExpression();

	/**
	 * @return Returns the instance.
	 */
	public static MultiplySuffixExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private MultiplySuffixExpression() {
		AbstractExpression[] expression = new AbstractExpression[1];
		expression[0] = UnaryExpression.getInstance();
		setExpression(expression);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.AbstractExpression#interpret(org.mmarini.ratio.interpreter.IInterpreterContext)
	 */
	public void interpret(IInterpreterContext context) throws SyntaxException {
		AbstractExpression expression = getExpression()[0];
		for (;;) {
			String token = context.peek();
			if (STAR.equals(token)) {
				context.next();
				expression.interpret(context);
				context.addCommand(new MultiplyCommand(context));
			} else if (SLASH.equals(token)) {
				context.next();
				expression.interpret(context);
				context.addCommand(new DivideCommand(context));
			} else {
				break;
			}
		}
	}
}
