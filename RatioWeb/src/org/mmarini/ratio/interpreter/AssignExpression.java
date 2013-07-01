package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.processor.AssignCommand;

public class AssignExpression extends SequenceExpression {
	private static AssignExpression instance = new AssignExpression();

	/**
	 * @return Returns the instance.
	 */
	public static AssignExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private AssignExpression() {
		AbstractExpression[] expression = new AbstractExpression[4];
		expression[0] = SetNameExpression.getInstance();
		expression[1] = new LiteralExpression("=");
		expression[2] = AddExpression.getInstance();
		expression[3] = EndExpression.getInstance();
		setExpression(expression);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.SequenceExpression#interpret(org.mmarini.ratio.interpreter.IInterpreterContext)
	 */
	public void interpret(IInterpreterContext context) throws SyntaxException {
		super.interpret(context);
		context.addCommand(new AssignCommand(context));
	}

}
