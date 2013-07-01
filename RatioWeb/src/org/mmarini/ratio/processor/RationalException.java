package org.mmarini.ratio.processor;

/**
 * @author $Author: marco $
 * @version $Id: RationalException.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class RationalException extends Exception {
	private static final long serialVersionUID = 1L;
	private Object[] parameters = new Object[0];

	/**
	 * 
	 *
	 */
	public RationalException() {
		super();
	}

	/**
	 * @param message
	 */
	public RationalException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RationalException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public RationalException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 */
	public RationalException(String message, Object[] parameters) {
		super(message);
		this.parameters = parameters;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RationalException(String message, Throwable cause, Object[] parameters) {
		super(message, cause);
		this.parameters = parameters;
	}

	/**
	 * @param cause
	 */
	public RationalException(Throwable cause, Object[] parameters) {
		super(cause);
		this.parameters = parameters;
	}

	/**
	 * @return Returns the paramters.
	 */
	public Object[] getParameters() {
		return parameters;
	}
}
