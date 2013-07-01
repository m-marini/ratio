package org.mmarini.ratio.web;

import org.mmarini.ratio.interpreter.IVariableResolver;
import org.mmarini.ratio.processor.IRationalValue;

public class VariableResolverAdapter implements IVariableResolver {
	private IVariableResolver adaptee;

	/**
	 * @param adaptee
	 */
	public VariableResolverAdapter(IVariableResolver adaptee) {
		this.adaptee = adaptee;
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IVariableResolver#get(java.lang.String)
	 */
	public IRationalValue get(String name) {
		return getAdaptee().get(name);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IVariableResolver#put(java.lang.String,
	 *      org.mmarini.ratio.processor.IRationalValue)
	 */
	public void put(String assignName, IRationalValue a) {
		getAdaptee().put(assignName, a);
	}

	/**
	 * @return Returns the adaptee.
	 */
	private IVariableResolver getAdaptee() {
		return adaptee;
	}
}
