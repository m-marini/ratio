/**
 * 
 */
package org.mmarini.ratio.interpreter;

/**
 * @author US00852
 * 
 */
public class ErrorValue implements Value {
	private final ParserException exception;

	/**
	 * @param exception
	 */
	public ErrorValue(final ParserException exception) {
		this.exception = exception;
	}

	/**
	 * @throws ParserException
	 * @see org.mmarini.ratio.interpreter.Value#apply(org.mmarini.ratio.interpreter
	 *      .ValueVisitor)
	 */
	@Override
	public <T> T apply(final ValueVisitor<T> v) throws ParserException {
		return v.visit(this);
	}

	/**
	 * @return the exception
	 */
	public ParserException getException() {
		return exception;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return exception != null ? "Error: " + exception.getMessage() : "null";
	}
}
