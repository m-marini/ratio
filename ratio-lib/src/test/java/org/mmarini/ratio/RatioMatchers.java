/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasToString;

import org.hamcrest.Matcher;
import org.mmarini.ratio.interpreter.Interpreter;

/**
 * @author US00852
 * @version $Id: RationalNumberTest.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RatioMatchers {

	public static Matcher<RationalNumber[]> containsRatio(final int... rows) {
		@SuppressWarnings("unchecked")
		final Matcher<? super RationalNumber>[] m = new Matcher[rows.length];
		for (int i = 0; i < rows.length; ++i)
			m[i] = ratio(rows[i], 1);
		return containsRatio(m);
	}

	@SafeVarargs
	public static Matcher<RationalNumber[]> containsRatio(
			final Matcher<? super RationalNumber>... rows) {
		return arrayContaining(rows);
	}

	public static Matcher<RationalNumber[]> containsRatio(
			final RationalNumber... rows) {
		@SuppressWarnings("unchecked")
		final Matcher<? super RationalNumber>[] m = new Matcher[rows.length];
		for (int i = 0; i < rows.length; ++i)
			m[i] = equalTo(rows[i]);
		return containsRatio(m);
	}

	public static Matcher<Interpreter> hasInterpreterValue(final String id,
			final String value) {
		return hasProperty("values", hasEntry(equalTo(id), hasToString(value)));
	}

	@SafeVarargs
	public static Matcher<RationalArray> hasRows(
			final Matcher<RationalNumber[]>... cols) {
		return hasProperty("values", arrayContaining(cols));
	}

	public static Matcher<? super RationalNumber> ratio(final int upper,
			final int lower) {
		return ratio(equalTo(upper), equalTo(lower));
	}

	public static Matcher<? super RationalNumber> ratio(
			final Matcher<Integer> upper, final Matcher<Integer> lower) {
		return allOf(hasProperty("upper", upper), hasProperty("lower", lower));
	}
}
