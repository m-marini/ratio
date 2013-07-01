package org.mmarini.ratio.interpreter;

import org.mmarini.ratio.processor.RationalException;

/**
 * @author $Author: marco $
 * @version $Id: SyntaxException.java,v 1.2 2007/01/09 22:11:26 marco Exp $
 */
public class SyntaxException extends RationalException {
	private static final long serialVersionUID = 1L;
	public static final String END_EXP_VAR_NAME = "EndExpressionInVariableName";
	public static final String INVALID_VAR_NAME_CHAR = "InvalidVariableNameChar";
	public static final String END_EXP_CONST = "EndExpressionInConstant";
	public static final String INVALID_CONSTANT_CHAR = "InvalidConstantChar";
	public static final String INVALID_END_EXPRESSION = "InvalidEndExpression";
	public static final String END_EXP_UNARY = "EndExpressionInUnary";
	public static final String END_EXPR_LITERAL = "EndExpressionInLiteral";
	public static final String INVALID_CHAR_LITERAL = "InvalidLiteralChar";

	/**
	 * 
	 */
	public SyntaxException() {
		super();
	}

	/**
	 * @param message
	 * @param parameters
	 */
	public SyntaxException(String message, Object[] parameters) {
		super(message, parameters);
	}

	/**
	 * @param message
	 * @param cause
	 * @param parameters
	 */
	public SyntaxException(String message, Throwable cause, Object[] parameters) {
		super(message, cause, parameters);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SyntaxException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SyntaxException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param parameters
	 */
	public SyntaxException(Throwable cause, Object[] parameters) {
		super(cause, parameters);
	}

	/**
	 * @param cause
	 */
	public SyntaxException(Throwable cause) {
		super(cause);
	}
}
