/**
 * 
 */
package org.mmarini.ratio.interpreter;

/**
 * @author US00852
 * 
 */
public interface ValueVisitor<T> {

	/**
	 * 
	 * @param value
	 * @return
	 * @throws ParserException
	 */
	public abstract T visit(ArrayValue value) throws ParserException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws ParserException
	 */
	public abstract T visit(ErrorValue value) throws ParserException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws ParserException
	 */
	public abstract T visit(ScalarValue value) throws ParserException;

}
