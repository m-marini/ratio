/**
 * 
 */
package org.mmarini.ratio.interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author US00852
 * 
 */
public class StringSource implements ParserSource {

	private final String text;
	private int startToken;
	private int endToken;
	private String token;
	private final Pattern spacePattern;
	private final Pattern digitPattern;
	private final Pattern idPattern;
	private final Pattern anyPattern;

	/**
	 * 
	 * @param text
	 */
	public StringSource(final String text) {
		this.text = text;
		startToken = -1;
		endToken = 0;
		spacePattern = Pattern.compile("\\s*");
		digitPattern = Pattern.compile("\\d+");
		idPattern = Pattern.compile("\\w+");
		anyPattern = Pattern.compile(".");
	}

	/**
	 * @see org.mmarini.ratio.interpreter.ParserSource#consumeToken()
	 */
	@Override
	public void consumeToken() {
		parse();
	}

	/**
	 * 
	 * @param e
	 * @param message
	 * @return
	 */
	private ParserException fail(final Exception e, final String txt) {
		final int li = text.lastIndexOf("\n", startToken);
		final int sl = li >= 0 ? li + 1 : 0;
		final int ii = text.indexOf("\n", sl);
		final int el = ii >= 0 ? ii : text.length();
		final String line = text.substring(sl, el);
		int r = 1;
		for (final char ch : text.substring(0, startToken).toCharArray())
			if (ch == '\n')
				++r;
		return new ParserException(e, txt, line, r, startToken - sl + 1);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.ParserSource#fail(java.lang.Exception,
	 *      java.lang.String)
	 */
	@Override
	public final ParserException fail(final Exception e, final String message,
			final Object... args) {
		return fail(e, String.format(message, args));
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	private ParserException fail(final String txt) {
		final int li = text.lastIndexOf("\n", startToken);
		final int sl = li >= 0 ? li + 1 : 0;
		final int ii = text.indexOf("\n", sl);
		final int el = ii >= 0 ? ii : text.length();
		final String line = text.substring(sl, el);
		int r = 1;
		for (final char ch : text.substring(0, startToken).toCharArray())
			if (ch == '\n')
				++r;
		return new ParserException(txt, line, r, startToken - sl + 1);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.ParserSource#fail(java.lang.String,
	 *      java.lang.String[])
	 */
	@Override
	public ParserException fail(final String message, final Object... args) {
		return fail(String.format(message, args));
	}

	/**
	 * @param regExp
	 */
	private String findToken(final Pattern p) {
		startToken = endToken;
		final int n = text.length();
		for (int l = startToken + 1; l <= n; ++l) {
			final Matcher m = p.matcher(text.substring(startToken, l));
			if (!m.matches())
				break;
			endToken = l;
		}
		token = startToken < endToken ? text.substring(startToken, endToken)
				: null;
		return token;
	}

	/**
	 * @see org.mmarini.ratio.interpreter.ParserSource#getToken()
	 */
	@Override
	public String getToken() {
		if (startToken < 0) {
			parse();
		}
		return token;
	}

	/**
	 * Parse for next token
	 */
	private void parse() {
		findToken(spacePattern);
		if (findToken(digitPattern) == null)
			if (findToken(idPattern) == null)
				findToken(anyPattern);
	}
}
