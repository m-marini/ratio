/**
 * 
 */
package org.mmarini.ratio.interpreter;

/**
 * @author us00852
 * 
 */
public interface Functor2<R, T0, T1> {

	/**
	 * 
	 * @param p0
	 * @param p1
	 * @return
	 * @throws ParserException
	 */
	public abstract R apply(T0 p0, T1 p1) throws ParserException;

}
