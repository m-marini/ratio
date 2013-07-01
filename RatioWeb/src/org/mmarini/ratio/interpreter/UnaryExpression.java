package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.processor.DetermineCommand;
import org.mmarini.ratio.processor.NegateCommand;
import org.mmarini.ratio.processor.TransposeCommand;

/**
 * @author $Author: marco $
 * @version $Id: UnaryExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public class UnaryExpression extends NonTerminalExpression {
	public static final String MINUS = "-";
	public static final String PLUS = "+";
	public static final String TRANSPOSE = "Tr";
	public static final String DETERMINER = "det";
	private static final int NAME_EXPRESSION_INDEX = 2;
	private static final int PRIORITY_EXPRESSION_INDEX = 1;
	private static final int CONSTANT_EXPRESSION_INDEX = 0;
	private static UnaryExpression instance = new UnaryExpression();

	/**
	 * @return Returns the instance.
	 */
	public static UnaryExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	public UnaryExpression() {}

	/**
	 * 
	 *
	 */
	private void createExpression() {
		AbstractExpression[] expression = new AbstractExpression[3];
		expression[CONSTANT_EXPRESSION_INDEX] = ConstantExpression
				.getInstance();
		expression[PRIORITY_EXPRESSION_INDEX] = PriorityExpression
				.getInstance();
		expression[NAME_EXPRESSION_INDEX] = GetNameExpression.getInstance();
		setExpression(expression);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.NonTerminalExpression#getExpression()
	 */
	protected AbstractExpression[] getExpression() {
		if (super.getExpression() == null) {
			createExpression();
		}
		return super.getExpression();
	}

	/**
	 * @see org.mmarini.ratio.interpreter.AbstractExpression#interpret(org.mmarini.ratio.interpreter.IInterpreterContext)
	 */
	public void interpret(IInterpreterContext context) throws SyntaxException {
		AbstractExpression[] exp = getExpression();
		String token = context.peek();
		if (token == null) {
			throw new SyntaxException(SyntaxException.END_EXP_UNARY);
		} else if (PLUS.equals(token)) {
			context.next();
			interpret(context);
		} else if (MINUS.equals(token)) {
			context.next();
			interpret(context);
			context.addCommand(new NegateCommand(context));
		} else if (TRANSPOSE.equals(token)) {
			context.next();
			exp[PRIORITY_EXPRESSION_INDEX].interpret(context);
			context.addCommand(new TransposeCommand(context));
		} else if (DETERMINER.equals(token)) {
			context.next();
			exp[PRIORITY_EXPRESSION_INDEX].interpret(context);
			context.addCommand(new DetermineCommand(context));
		} else if ("(".equals(token)) {
			exp[PRIORITY_EXPRESSION_INDEX].interpret(context);
		} else if (Character.isDigit(token.charAt(0))) {
			exp[CONSTANT_EXPRESSION_INDEX].interpret(context);
			return;
		} else {
			exp[NAME_EXPRESSION_INDEX].interpret(context);
		}
	}
}
