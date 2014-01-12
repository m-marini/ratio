/**
 * 
 */
package org.mmarini.ratio.web;

import org.mmarini.ratio.RationalNumber;
import org.mmarini.ratio.interpreter.ArrayValue;
import org.mmarini.ratio.interpreter.ErrorValue;
import org.mmarini.ratio.interpreter.ParserException;
import org.mmarini.ratio.interpreter.ScalarValue;
import org.mmarini.ratio.interpreter.Value;
import org.mmarini.ratio.interpreter.ValueVisitor;

/**
 * @author us00852
 * 
 */
public class ExprResult {

	/**
	 * @param values
	 * @return
	 */
	private static String composeArrayValue(final RationalNumber[][] values) {
		final int n = values.length;
		final int m = values[0].length;
		final String[][] v = new String[n][m];
		int l = 0;
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j) {
				final String s = String.valueOf(values[i][j]);
				final int k = s.length();
				if (k > l)
					l = k;
				v[i][j] = s;
			}

		final StringBuilder b = new StringBuilder();
		boolean firstRow = true;
		for (final String[] r : v) {
			if (firstRow)
				firstRow = false;
			else
				b.append('\n');
			boolean firstCell = true;
			for (final String c : r) {
				if (firstCell)
					firstCell = false;
				else
					b.append(", ");
				b.append(fill(c, l));
			}
		}
		return b.toString();
	}

	/**
	 * 
	 * @param id
	 * @param text
	 * @param value
	 * @return
	 */
	public static ExprResult create(final String id, final String text,
			final Value value) {
		try {
			return value.apply(new ValueVisitor<ExprResult>() {

				@Override
				public ExprResult visit(final ArrayValue value)
						throws ParserException {
					return new ExprResult(id, text, value.toString(),
							composeArrayValue(value.getValue().getValues()), "");
				}

				@Override
				public ExprResult visit(final ErrorValue value)
						throws ParserException {
					return new ExprResult(id, text, value.toString(), value
							.toString(), value.getException().getDescription());
				}

				@Override
				public ExprResult visit(final ScalarValue value)
						throws ParserException {
					return new ExprResult(id, text, value.toString(), value
							.toString(), "");
				}
			});
		} catch (final ParserException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param c
	 * @param l
	 * @return
	 */
	private static String fill(final String c, final int l) {
		final StringBuilder b = new StringBuilder(c);
		while (b.length() < l)
			b.append(' ');
		return b.toString();
	}

	public final String id;
	public final String text;

	public final String error;

	public final String value;

	public final String fmtValue;

	/**
	 * @param id
	 * @param text
	 * @param value
	 * @param fmtValue
	 * @param error
	 */
	public ExprResult(final String id, final String text, final String value,
			final String fmtValue, final String error) {
		this.id = id;
		this.text = text;
		this.error = error;
		this.value = value;
		this.fmtValue = fmtValue;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @return the fmtValue
	 */
	public String getFmtValue() {
		return fmtValue;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
