package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.processor.IRationalValue;

/**
 * @author $Author: marco $
 * @version $Id: IVariableResolver.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public interface IVariableResolver {

	/**
	 * @param assignName
	 * @param a
	 */
	public abstract void put(String assignName, IRationalValue a);

	/**
	 * @param name
	 * @return
	 */
	public abstract IRationalValue get(String name);
}
