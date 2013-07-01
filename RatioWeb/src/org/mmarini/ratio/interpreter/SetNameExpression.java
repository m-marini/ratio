package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.processor.SetNameCommand;

/**
 * @author $Author: marco $
 * @version $Id: SetNameExpression.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public class SetNameExpression extends AbstractNameExpression {
	private static SetNameExpression instance = new SetNameExpression();

	/**
	 * @return Returns the instance.
	 */
	public static SetNameExpression getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private SetNameExpression() {}

	/**
	 * @see org.mmarini.ratio.interpreter.AbstractExpression#interpret(org.mmarini.ratio.interpreter.IInterpreterContext)
	 */
	public void interpret(IInterpreterContext context) throws SyntaxException {
		String name = parseName(context);
		context.addCommand(new SetNameCommand(context, name));
	}
}
