package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: AbstractInterpreterAdapter.java,v 1.2.2.1 2006/02/08 22:11:24
 *          marco Exp $
 */
public abstract class AbstractInterpreterAdapter implements IInterpreterContext {
	private IParser parser;
	private String token;

	/**
	 * @param parser
	 */
	public AbstractInterpreterAdapter(IParser parser) {
		this.parser = parser;
	}

	/**
	 * 
	 *
	 */
	private void skipBlanks() {
		IParser parser = getParser();
		int ch = parser.peek();
		while (isBlanks((char) ch)) {
			parser.next();
			ch = parser.peek();
		}
	}

	/**
	 * @param c
	 * @return
	 */
	private boolean isBlanks(int c) {
		return Character.isWhitespace(c);
	}

	/**
	 * @return Returns the parser.
	 */
	protected IParser getParser() {
		return parser;
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IInterpreterContext#peek()
	 */
	public String peek() {
		String token = getToken();
		if (token != null)
			return token;
		skipBlanks();
		IParser parser = getParser();
		int ch = parser.peek();
		if (ch < 0)
			return null;
		if (isDigit(ch)) {
			parseInteger();
		} else if (isStartName((char) ch)) {
			parseName();
		} else {
			setToken(String.valueOf((char) ch));
			parser.next();
		}
		return getToken();
	}

	/**
	 * 
	 *
	 */
	private void parseName() {
		IParser parser = getParser();
		int ch = parser.peek();
		StringBuffer name = new StringBuffer();
		do {
			name.append((char) ch);
			parser.next();
			ch = parser.peek();
		} while (isName((char) ch));
		setToken(name.toString());
	}

	/**
	 * @param ch
	 * @return
	 */
	private boolean isStartName(int ch) {
		if (ch < 0)
			return false;
		return VariableNameValidator.getInstance().isNameStart((char) ch);
	}

	/**
	 * @param ch
	 * @return
	 */
	private boolean isName(int ch) {
		if (ch < 0)
			return false;
		return VariableNameValidator.getInstance().isNameContent((char) ch);
	}

	/**
	 * @param ch
	 * @return
	 */
	private boolean isDigit(int ch) {
		return Character.isDigit(ch);
	}

	private void parseInteger() {
		skipBlanks();
		IParser parser = getParser();
		int ch = parser.peek();
		StringBuffer value = new StringBuffer();
		do {
			value.append((char) ch);
			parser.next();
			ch = parser.peek();
		} while (isDigit(ch));
		if (ch == '.') {
			do {
				value.append((char) ch);
				parser.next();
				ch = parser.peek();
			} while (isDigit(ch));
		}
		setToken(value.toString());
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IInterpreterContext#next()
	 */
	public void next() {
		setToken(null);
	}

	/**
	 * @return Returns the token.
	 */
	private String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            The token to set.
	 */
	private void setToken(String token) {
		this.token = token;
	}
}