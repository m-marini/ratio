package org.mmarini.ratio.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.ratio.interpreter.IInterpreterContext;

/**
 * @author $Author: marco $
 * @version $Id: GetNameCommand.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class GetNameCommand extends AbstractNameCommand {
	private static Log log = LogFactory.getLog(GetNameCommand.class);

	/**
	 * @param context
	 * @param name
	 */
	public GetNameCommand(IInterpreterContext context, String name) {
		super(context, name);
	}

	/**
	 * @see org.mmarini.ratio.processor.ICommand#execute()
	 */
	public void execute() throws ProcessException {
		IInterpreterContext ctx = getContext();
		String name = getName();
		IRationalValue value = ctx.getValue(name);
		if (value == null)
			throw new ProcessException(ProcessException.UNDEFINED_VARIABLE,
					new Object[] {name});
		ctx.push(value);
		log.debug("Execute get " + name);
	}
}
