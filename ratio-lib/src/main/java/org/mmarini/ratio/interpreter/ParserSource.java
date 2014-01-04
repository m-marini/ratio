/**
 * 
 */
package org.mmarini.ratio.interpreter;

/**
 * @author US00852
 * 
 */
public interface ParserSource {

	/**
	 * 
	 */
	public abstract void consumeToken();

	/**
	 * @param cause
	 * @param message
	 * @param args
	 * @return
	 */
	public abstract ParserException fail(Exception cause, String message,
			Object... args);

	/**
	 * @param message
	 * @param args
	 * @return
	 */
	public abstract ParserException fail(String message, Object... args);

	/**
	 * 
	 * @return
	 */
	public abstract String getToken();
}
