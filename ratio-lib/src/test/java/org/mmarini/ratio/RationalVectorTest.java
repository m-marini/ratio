/*
 * Created on Jul 12, 2004
 */
package org.mmarini.ratio;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.describedAs;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * @author us00852
 * @version $Id: RationalVectorTest.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
@RunWith(Theories.class)
public class RationalVectorTest {

	@DataPoints
	public static final RationalNumber[] VALUES = { RationalNumber.ZERO,
			RationalNumber.ONE, RationalNumber.create(2),
			RationalNumber.create(3), RationalNumber.create(4) };

	@Theory
	public final void testAdd(RationalNumber a0, RationalNumber a1,
			RationalNumber b0, RationalNumber b1) {
		final RationalVector v = new RationalVector(a0, a1)
				.add(new RationalVector(b0, b1));
		assertThat(v, hasProperty("dimensions", equalTo(2)));
		assertThat(v,
				hasProperty("values", arrayContaining(a0.add(b0), a1.add(b1))));
	}

	@Theory
	public final void testDimensions(RationalNumber a0, RationalNumber a1,
			RationalNumber a2) {
		final RationalVector a = new RationalVector(a0, a1, a2);
		assertThat(a, hasProperty("dimensions", equalTo(3)));
	}

	@Theory
	public final void testEquals(RationalNumber a0, RationalNumber a1) {
		final RationalVector a = new RationalVector(a0, a1);
		final RationalVector b = new RationalVector(a0, a1);
		assertThat(a, not(equalTo(null)));
		assertThat(a, equalTo(b));
		assertThat(b, equalTo(a));
		assertThat(a.hashCode(), equalTo(b.hashCode()));
	}

	@Theory
	public void testGetValue(RationalNumber v0, RationalNumber v1,
			RationalNumber v2) {
		final RationalVector v = new RationalVector(v0, v1, v2);
		assertThat(v, hasProperty("dimensions", equalTo(3)));
		assertThat(v, hasProperty("values", arrayContaining(v0, v1, v2)));
	}

	@Theory
	public final void testLength2(RationalNumber a0, RationalNumber a1,
			RationalNumber a2) {
		final RationalVector a = new RationalVector(a0, a1, a2);
		final RationalNumber v = a.getLength2();
		final RationalNumber e = a0.mul(a0).add(a1.mul(a1)).add(a2.mul(a2));
		assertThat(v, describedAs("%0 %0", equalTo(e), a));
	}

	@Theory
	public final void testMultiply(RationalNumber a0, RationalNumber a1,
			RationalNumber b0, RationalNumber b1) {
		final RationalVector a = new RationalVector(a0, a1);
		final RationalVector b = new RationalVector(b0, b1);
		final RationalNumber v = a.multiply(b);
		final RationalNumber e = a0.mul(b0).add(a1.mul(b1));
		assertThat(v, describedAs("%0 * %1", equalTo(e), a, b));
	}

	@Theory
	public final void testScale(RationalNumber a0, RationalNumber a1,
			RationalNumber s) {
		final RationalVector v = new RationalVector(a0, a1).multiply(s);
		assertThat(v,
				hasProperty("values", arrayContaining(a0.mul(s), a1.mul(s))));
	}

	@Theory
	public final void testSub(RationalNumber a0, RationalNumber a1,
			RationalNumber b0, RationalNumber b1) {
		final RationalVector v = new RationalVector(a0, a1)
				.subtract(new RationalVector(b0, b1));
		assertThat(v, hasProperty("dimensions", equalTo(2)));
		assertThat(v,
				hasProperty("values", arrayContaining(a0.sub(b0), a1.sub(b1))));
	}

	@Theory
	public final void testToString(RationalNumber a0, RationalNumber a1) {
		final RationalVector a = new RationalVector(a0, a1);
		assertThat(a, hasToString("[" + a0 + ", " + a1 + "]"));
	}
}
