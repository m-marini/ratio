/*
 * Created on Jul 12, 2004
 */
package org.mmarini.ratio;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.mmarini.ratio.RatioMatchers.containsRatio;
import static org.mmarini.ratio.RatioMatchers.ratio;

import org.junit.Test;

/**
 * @author US00852
 * @version $Id: RationalArrayTest.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class GaussAlgorithmTest {
	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		final RationalNumber[][] array = new RationalNumber[][] {
				{ RationalNumber.create(8), RationalNumber.ONE,
						RationalNumber.create(6), RationalNumber.ONE,
						RationalNumber.ZERO, RationalNumber.ZERO },
				{ RationalNumber.create(3), RationalNumber.create(5),
						RationalNumber.create(7), RationalNumber.ZERO,
						RationalNumber.ONE, RationalNumber.ZERO },
				{ RationalNumber.create(4), RationalNumber.create(9),
						RationalNumber.create(2), RationalNumber.ZERO,
						RationalNumber.ZERO, RationalNumber.ONE } };
		final GaussAlgorithm g = new GaussAlgorithm(array);
		assertThat(g, hasProperty("triangular", equalTo(true)));
		assertThat(g, hasProperty("determiner", ratio(-360, 1)));
		assertThat(
				g,
				hasProperty(
						"array",
						arrayContaining(
								containsRatio(ratio(1, 1), ratio(0, 1),
										ratio(0, 1), ratio(53, 360),
										ratio(-13, 90), ratio(23, 360)),
								containsRatio(ratio(0, 1), ratio(1, 1),
										ratio(0, 1), ratio(-11, 180),
										ratio(1, 45), ratio(19, 180)),
								containsRatio(ratio(0, 1), ratio(0, 1),
										ratio(1, 1), ratio(-7, 360),
										ratio(17, 90), ratio(-37, 360)))));
	}

	@Test
	public void testRank() {
		final RationalNumber[][] a = new RationalNumber[][] {
				{ RationalNumber.create(8), RationalNumber.ONE,
						RationalNumber.create(6) },
				{ RationalNumber.create(3), RationalNumber.create(5),
						RationalNumber.create(7) },
				{ RationalNumber.create(4), RationalNumber.create(9),
						RationalNumber.create(2) } };
		assertThat(new GaussAlgorithm(a), hasProperty("rank", equalTo(3)));
	}

	@Test
	public void testRank1() {
		final RationalNumber[][] a = new RationalNumber[][] {
				{ RationalNumber.ONE, RationalNumber.create(2),
						RationalNumber.create(3) },
				{ RationalNumber.create(4), RationalNumber.create(5),
						RationalNumber.create(6) },
				{ RationalNumber.create(7), RationalNumber.create(8),
						RationalNumber.create(9) } };
		assertThat(new GaussAlgorithm(a), hasProperty("rank", equalTo(2)));
	}
}
