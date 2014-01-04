/**
 * 
 */
package org.mmarini.ratio.swing;

/**
 * @author US00852
 * 
 */
public class ExpressionDef {
	private final String id;
	private final String expression;
	private final String value;

	/**
	 * @param id
	 * @param expression
	 * @param value
	 */
	public ExpressionDef(String id, String expression, String value) {
		super();
		this.id = id;
		this.expression = expression;
		this.value = value;
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExpressionDef [id=").append(id).append(", expression=")
				.append(expression).append(", value=").append(value)
				.append("]");
		return builder.toString();
	}
}
