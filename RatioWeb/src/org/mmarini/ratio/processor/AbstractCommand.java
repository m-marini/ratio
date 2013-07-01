package org.mmarini.ratio.processor;

import org.mmarini.ratio.interpreter.IInterpreterContext;


public abstract class AbstractCommand implements ICommand {
	private IInterpreterContext context;

	/**
	 * @param context
	 */
	public AbstractCommand(IInterpreterContext context) {
		this.context = context;
	}

	/**
	 * @return Returns the context.
	 */
	protected IInterpreterContext getContext() {
		return context;
	}
}
