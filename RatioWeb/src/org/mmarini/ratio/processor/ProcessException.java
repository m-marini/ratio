package org.mmarini.ratio.processor;

/**
 * @author $Author: marco $
 * @version $Id: ProcessException.java,v 1.2 2007/01/09 22:11:22 marco Exp $
 */
public class ProcessException extends RationalException {
	private static final long serialVersionUID = 1L;
	public static final String ADD_ARRAY_SCALAR = "AddArrayScalar";
	public static final String ILLEGAL_ARGUMENT = "IllegalArgument";
	public static final String ADD_SCALAR_ARRAY = "AddScalarArray";
	public static final String UNDEFINED_VARIABLE = "UndefinedVariable";
	public static final String SUB_ARRAY_SCALAR = "SubArrayScalar";
	public static final String SUB_SCALAR_ARRAY = "SubScalarArray";

	/**
	 * 
	 */
	public ProcessException() {
		super();
	}

	/**
	 * @param message
	 * @param parameters
	 */
	public ProcessException(String message, Object[] parameters) {
		super(message, parameters);
	}

	/**
	 * @param message
	 * @param cause
	 * @param parameters
	 */
	public ProcessException(String message, Throwable cause, Object[] parameters) {
		super(message, cause, parameters);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ProcessException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param parameters
	 */
	public ProcessException(Throwable cause, Object[] parameters) {
		super(cause, parameters);
	}

	/**
	 * @param cause
	 */
	public ProcessException(Throwable cause) {
		super(cause);
	}
}
