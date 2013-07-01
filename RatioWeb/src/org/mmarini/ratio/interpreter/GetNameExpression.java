package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.processor.GetNameCommand;

/**
 * @author $Author: marco $
 * @version $Id: GetNameExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public class GetNameExpression extends AbstractNameExpression {
	private static GetNameExpression instance = new GetNameExpression();

	/**
	 * @return Returns the instance.
	 */
	public static GetNameExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private GetNameExpression() {}

	/**
	 * @see org.mmarini.ratio.interpreter.AbstractExpression#interpret(org.mmarini.ratio.interpreter.IInterpreterContext)
	 */
	public void interpret(IInterpreterContext context) throws SyntaxException {
		String name = parseName(context);
		context.addCommand(new GetNameCommand(context, name));
	}
}
