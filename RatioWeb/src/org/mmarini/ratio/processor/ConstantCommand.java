package org.mmarini.ratio.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.ratio.interpreter.IInterpreterContext;

/**
 * @author $Author: marco $
 * @version $Id: ConstantCommand.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class ConstantCommand extends AbstractCommand {
	private static Log log = LogFactory.getLog(ConstantCommand.class);
	private IRationalValue number;

	/**
	 * @param context
	 * @param number
	 */
	public ConstantCommand(IInterpreterContext context, IRationalValue number) {
		super(context);
		this.number = number;
	}

	/**
	 * @see org.mmarini.ratio.processor.ICommand#execute()
	 */
	public void execute() throws ProcessException {
		getContext().push(getNumber());
		log.debug("execute constant command " + getNumber());
	}

	/**
	 * @return Returns the number.
	 */
	private IRationalValue getNumber() {
		return number;
	}
}
