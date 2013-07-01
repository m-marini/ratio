package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.processor.AddCommand;
import org.mmarini.ratio.processor.SubtractCommand;

/**
 * @author $Author: marco $
 * @version $Id: AddSuffixExpression.java,v 1.1.2.3 2006/01/18 23:22:48 marco
 *          Exp $
 */
public class AddSuffixExpression extends NonTerminalExpression {
	public static final String MINUS = "-";
	public static final String PLUS = "+";
	private static AddSuffixExpression instance = new AddSuffixExpression();

	/**
	 * @return Returns the instance.
	 */
	public static AddSuffixExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private AddSuffixExpression() {
		AbstractExpression[] expression = new AbstractExpression[1];
		expression[0] = MultiplyExpression.getInstance();
		setExpression(expression);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.AbstractExpression#interpret(org.mmarini.ratio.interpreter.IInterpreterContext)
	 */
	public void interpret(IInterpreterContext context) throws SyntaxException {
		AbstractExpression expression = getExpression()[0];
		for (;;) {
			String token = context.peek();
			if (PLUS.equals(token)) {
				context.next();
				expression.interpret(context);
				context.addCommand(new AddCommand(context));
			} else if (MINUS.equals(token)) {
				context.next();
				expression.interpret(context);
				context.addCommand(new SubtractCommand(context));
			} else {
				break;
			}
		}
	}
}
