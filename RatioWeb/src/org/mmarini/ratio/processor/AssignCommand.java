package org.mmarini.ratio.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.ratio.interpreter.IInterpreterContext;

/**
 * @author $Author: marco $
 * @version $Id: AssignCommand.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class AssignCommand extends AbstractCommand {
	private static Log log = LogFactory.getLog(AssignCommand.class);

	/**
	 * @param context
	 */
	public AssignCommand(IInterpreterContext context) {
		super(context);
	}

	/**
	 * @see org.mmarini.ratio.processor.ICommand#execute()
	 */
	public void execute() throws ProcessException {
		IInterpreterContext ctx = getContext();
		IRationalValue a = ctx.pop();
		ctx.assign(a);
		log.debug("execute assign command " + a);
	}
}
