package org.mmarini.ratio.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.ratio.interpreter.IInterpreterContext;

/**
 * @author $Author: marco $
 * @version $Id: DetermineCommand.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class DetermineCommand extends AbstractCommand {
	private static Log log = LogFactory.getLog(DetermineCommand.class);

	/**
	 * @param context
	 */
	public DetermineCommand(IInterpreterContext context) {
		super(context);
	}

	/**
	 * @see org.mmarini.ratio.processor.ICommand#execute()
	 */
	public void execute() throws ProcessException {
		IInterpreterContext ctx = getContext();
		IRationalValue a = ctx.pop();
		ctx.push(a.determine());
		log.debug("execute determine command");
	}
}
