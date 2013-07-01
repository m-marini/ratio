package org.mmarini.ratio.processor;

/**
 * @author $Author: marco $
 * @version $Id: AbstractValueVisitor.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public abstract class AbstractValueVisitor implements IRationalVisitor {
	private IRationalValue result;
	private ProcessException exception;

	/**
	 * @return Returns the exception.
	 */
	public ProcessException getException() {
		return exception;
	}

	/**
	 * @param exception
	 */
	protected void setException(ProcessException exception) {
		this.exception = exception;
	}

	/**
	 * @return Returns the result.
	 */
	public IRationalValue getResult() {
		return result;
	}

	/**
	 * @param result
	 *            The result to set.
	 */
	protected void setResult(IRationalValue result) {
		this.result = result;
	}
}
