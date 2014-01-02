/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matcher;
import org.junit.Test;

/**
 * @author US00852
 * @version $Id: FactorizerTest.java,v 1.1 2005/02/10 22:29:26 marco Exp $
 */
public class FactorizerTest {

	private static Matcher<? super PoweredNumber> pow(final int base,
			final int exp) {
		return pow(equalTo(base), equalTo(exp));
	}

	private static Matcher<? super PoweredNumber> pow(
			final Matcher<Integer> base, final Matcher<Integer> exp) {
		return allOf(hasProperty("base", base), hasProperty("exponent", exp));
	}

	@Test
	public final void testCreateFactors0() {
		assertThat(new Factorizer().createFactors(0), contains(pow(0, 1)));
	}

	@Test
	public final void testCreateFactors1() {
		assertThat(new Factorizer().createFactors(1), contains(pow(1, 1)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testCreateFactors2200() {
		assertThat(new Factorizer().createFactors(2200),
				contains(pow(2, 3), pow(5, 2), pow(11, 1)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public final void testCreateFactors900() {
		assertThat(new Factorizer().createFactors(900),
				contains(pow(2, 2), pow(3, 2), pow(5, 2)));
	}

	@Test
	public final void testGetMCD_10_4() {
		assertThat(new Factorizer().getMCD(10, 4), equalTo(2));
	}

	@Test
	public final void testGetMCD_10_9() {
		assertThat(new Factorizer().getMCD(10, 9), equalTo(1));
	}

	@Test
	public final void testGetMCD_20_40() {
		assertThat(new Factorizer().getMCD(20, 40), equalTo(20));
	}

	@Test
	public final void testGetMCD_60_100() {
		assertThat(new Factorizer().getMCD(60, 100), equalTo(20));
	}
}
