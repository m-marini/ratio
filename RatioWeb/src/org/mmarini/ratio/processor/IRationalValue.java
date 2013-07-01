package org.mmarini.ratio.processor;

/**
 * @author $Author: marco $
 * @version $Id: IRationalValue.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public interface IRationalValue {
	/**
	 * @return
	 * @throws ProcessException 
	 */
	public IRationalValue transpose() throws ProcessException;

	/**
	 * @return
	 * @throws ProcessException 
	 */
	public IRationalValue determine() throws ProcessException;

	/**
	 * @return
	 */
	public IRationalValue negate();

	/**
	 * @param value
	 * @return
	 * @throws ProcessException
	 */
	public IRationalValue add(IRationalValue value) throws ProcessException;

	/**
	 * @param value
	 * @return
	 * @throws ProcessException
	 */
	public IRationalValue subtract(IRationalValue value)
			throws ProcessException;

	/**
	 * @param value
	 * @return
	 * @throws ProcessException
	 */
	public IRationalValue multiply(IRationalValue value)
			throws ProcessException;

	/**
	 * @param value
	 * @return
	 * @throws ProcessException
	 */
	public IRationalValue divide(IRationalValue value) throws ProcessException;

	/**
	 * @param visitor
	 */
	public void accept(IRationalVisitor visitor);
}
