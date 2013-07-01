package org.mmarini.ratio.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.ratio.interpreter.IInterpreterContext;

/**
 * @author $Author: marco $
 * @version $Id: MultiplyCommand.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class MultiplyCommand extends AbstractCommand {
	private static Log log = LogFactory.getLog(MultiplyCommand.class);

	/**
	 * @param context
	 */
	public MultiplyCommand(IInterpreterContext context) {
		super(context);
	}

	/**
	 * @see org.mmarini.ratio.processor.ICommand#execute()
	 */
	public void execute() throws ProcessException {
		IInterpreterContext ctx = getContext();
		IRationalValue b = ctx.pop();
		IRationalValue a = ctx.pop();
		ctx.push(a.multiply(b));
		log.debug("execute multiply command ");
	}
}
