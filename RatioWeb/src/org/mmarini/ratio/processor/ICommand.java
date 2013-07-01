package org.mmarini.ratio.processor;


public interface ICommand {
	/**
	 * @throws ProcessException
	 */
	public abstract void execute() throws ProcessException;
}
