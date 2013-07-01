package org.mmarini.ratio.processor;

import org.mmarini.ratio.interpreter.IInterpreterContext;

/**
 * @author $Author: marco $
 * @version $Id: AbstractNameCommand.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public abstract class AbstractNameCommand extends AbstractCommand {
	private String name;

	/**
	 * @param context
	 * @param name
	 */
	public AbstractNameCommand(IInterpreterContext context, String name) {
		super(context);
		this.name = name;
	}

	/**
	 * @return Returns the name.
	 */
	protected String getName() {
		return name;
	}
}
