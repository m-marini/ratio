package org.mmarini.ratio.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.ratio.interpreter.IInterpreterContext;

/**
 * @author $Author: marco $
 * @version $Id: SetNameCommand.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class SetNameCommand extends AbstractNameCommand {
	private static Log log = LogFactory.getLog(SetNameCommand.class);

	/**
	 * @param context
	 * @param name
	 */
	public SetNameCommand(IInterpreterContext context, String name) {
		super(context, name);
	}

	/**
	 * @see org.mmarini.ratio.processor.ICommand#execute()
	 */
	public void execute() throws ProcessException {
		IInterpreterContext ctx = getContext();
		ctx.setAssignName(getName());
		log.debug("Execute setValue " + getName());
	}
}
