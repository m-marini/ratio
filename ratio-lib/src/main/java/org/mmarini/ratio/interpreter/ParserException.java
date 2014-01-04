/**
 * 
 */
package org.mmarini.ratio.interpreter;

/**
 * @author US00852
 * 
 */
public class ParserException extends Exception {
	private static final long serialVersionUID = -6943748218594237349L;
	private final String line;
	private final int row;
	private final int col;
	private final String errorLocator;
	private final String description;

	/**
	 * @param e
	 * @param txt
	 * @param line2
	 * @param r
	 * @param i
	 */
	public ParserException(final Exception e, final String message,
			final String line, final int row, final int col) {
		super(message, e);
		this.line = line;
		this.row = row;
		this.col = col;
		final StringBuilder b = new StringBuilder();
		for (int i = 1; i < col; ++i)
			b.append(" ");
		errorLocator = b.append("^ ").append(getMessage()).toString();
		description = String.format("%s\n%s", line, errorLocator);
	}

	/**
	 * @param message
	 * @param line
	 * @param startToken
	 * @param endToken
	 */
	public ParserException(final String message, final String line,
			final int row, final int col) {
		super(message);
		this.line = line;
		this.row = row;
		this.col = col;
		final StringBuilder b = new StringBuilder();
		for (int i = 1; i < col; ++i)
			b.append(" ");
		errorLocator = b.append("^ ").append(getMessage()).toString();
		description = String.format("%s\n%s", line, errorLocator);
	}

	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the errorLocator
	 */
	public String getErrorLocator() {
		return errorLocator;
	}

	/**
	 * @return the text
	 */
	public String getLine() {
		return line;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
}
