package org.mmarini.ratio.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.mmarini.ratio.interpreter.IVariableResolver;
import org.mmarini.ratio.processor.IRationalValue;

/**
 * @author $Author: marco $
 * @version $Id: VariableResolver.java,v 1.2 2007/01/09 22:11:32 marco Exp $
 */
public class VariableResolver implements IVariableResolver, Serializable {
	private static final long serialVersionUID = 1L;
	private Map variableMap = new HashMap();
	private String lastInsert;

	/**
	 * 
	 */
	public VariableResolver() {}

	/**
	 * @param variableMap
	 */
	public VariableResolver(Map variableMap) {
		this.variableMap = variableMap;
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IVariableResolver#put(java.lang.String,
	 *      org.mmarini.ratio.processor.IRationalValue)
	 */
	public void put(String name, IRationalValue value) {
		getVariableMap().put(name, value);
		setLastInsert(name);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IVariableResolver#get(java.lang.String)
	 */
	public IRationalValue get(String name) {
		return (IRationalValue) getVariableMap().get(name);
	}

	/**
	 * @return Returns the lastInsert.
	 */
	public String getLastInsert() {
		return lastInsert;
	}

	/**
	 * @return Returns the variableMap.
	 */
	public Map getVariableMap() {
		return variableMap;
	}

	/**
	 * @param lastInsert
	 *            The lastInsert to set.
	 */
	private void setLastInsert(String lastInsert) {
		this.lastInsert = lastInsert;
	}

	/**
	 * @param name
	 */
	public void remove(String name) {
		getVariableMap().remove(name);
		setLastInsert(null);
	}

	/**
	 * 
	 *
	 */
	public void clear() {
		getVariableMap().clear();
		setLastInsert(null);
	}
}
