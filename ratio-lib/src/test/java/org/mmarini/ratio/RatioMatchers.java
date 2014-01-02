/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

import org.hamcrest.Matcher;

/**
 * @author US00852
 * @version $Id: RationalNumberTest.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RatioMatchers {

	public static Matcher<RationalNumber[]> containsRatio(int... rows) {
		@SuppressWarnings("unchecked")
		Matcher<? super RationalNumber>[] m = new Matcher[rows.length];
		for (int i = 0; i < rows.length; ++i)
			m[i] = ratio(rows[i], 1);
		return containsRatio(m);
	}

	@SafeVarargs
	public static Matcher<RationalNumber[]> containsRatio(
			Matcher<? super RationalNumber>... rows) {
		return arrayContaining(rows);
	}

	public static Matcher<RationalNumber[]> containsRatio(
			RationalNumber... rows) {
		@SuppressWarnings("unchecked")
		Matcher<? super RationalNumber>[] m = new Matcher[rows.length];
		for (int i = 0; i < rows.length; ++i)
			m[i] = equalTo(rows[i]);
		return containsRatio(m);
	}

	@SafeVarargs
	public static Matcher<RationalArray> hasRows(
			Matcher<RationalNumber[]>... cols) {
		return hasProperty("values", arrayContaining(cols));
	}

	public static Matcher<? super RationalNumber> ratio(int upper, int lower) {
		return ratio(equalTo(upper), equalTo(lower));
	}

	public static Matcher<? super RationalNumber> ratio(
			final Matcher<Integer> upper, final Matcher<Integer> lower) {
		return allOf(hasProperty("upper", upper), hasProperty("lower", lower));
	}
}
