/**
 * 
 */
package org.mmarini.ratio.interpreter;

/**
 * @author us00852
 * 
 */
public interface Functor3<R, T0, T1, T2> {

	/**
	 * 
	 * @param p0
	 * @param p1
	 * @param p2
	 * @return
	 * @throws ParserException
	 */
	public abstract R apply(T0 p0, T1 p1, T2 p2) throws ParserException;

}
