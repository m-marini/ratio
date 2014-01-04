/**
 * 
 */
package org.mmarini.ratio.interpreter;

/**
 * @author US00852
 * 
 */
public interface Value {
	public static final Value UNDEFINED = new Value() {

		@Override
		public <T> T apply(final ValueVisitor<T> v) {
			return null;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "UNDEFINED";
		}
	};

	/**
	 * 
	 * @param v
	 * @return
	 * @throws ParserException
	 */
	public <T> T apply(ValueVisitor<T> v) throws ParserException;
}
