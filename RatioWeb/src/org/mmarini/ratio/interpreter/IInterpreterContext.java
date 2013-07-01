package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.processor.ICommand;
import org.mmarini.ratio.processor.IRationalValue;
import org.mmarini.ratio.processor.ProcessException;

public interface IInterpreterContext {
	/**
	 * @return
	 */
	public abstract String peek();

	/**
	 * 
	 *
	 */
	public abstract void next();

	/**
	 * @throws ProcessException
	 */
	public abstract void addCommand(ICommand command);

	/**
	 * @param name
	 * @return
	 */
	public abstract IRationalValue getValue(String name);

	/**
	 * @param num
	 */
	public abstract void push(IRationalValue num);

	/**
	 * @return
	 */
	public abstract IRationalValue pop();

	/**
	 * @param name
	 */
	public abstract void setAssignName(String name);

	/**
	 * @param a
	 */
	public abstract void assign(IRationalValue a);
}