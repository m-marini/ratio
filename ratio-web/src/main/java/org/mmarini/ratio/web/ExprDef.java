/**
 * 
 */
package org.mmarini.ratio.web;

/**
 * @author us00852
 * 
 */
public class ExprDef {
	private String id;
	private String text;

	/**
	 * 
	 */
	public ExprDef() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ExprDef [id=").append(id).append(", text=")
				.append(text).append("]");
		return builder.toString();
	}
}
