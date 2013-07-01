package org.mmarini.ratio.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.ratio.interpreter.IInterpreterContext;

/**
 * @author $Author: marco $
 * @version $Id: TransposeCommand.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class TransposeCommand extends AbstractCommand {
	private static Log log = LogFactory.getLog(TransposeCommand.class);

	/**
	 * @param context
	 */
	public TransposeCommand(IInterpreterContext context) {
		super(context);
	}

	/**
	 * @see org.mmarini.ratio.processor.ICommand#execute()
	 */
	public void execute() throws ProcessException {
		IInterpreterContext ctx = getContext();
		IRationalValue a = ctx.pop();
		ctx.push(a.transpose());
		log.debug("execute transpose scommand");
	}
}
