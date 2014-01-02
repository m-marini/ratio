/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mmarini.ratio.RatioMatchers.ratio;

import org.junit.Before;
import org.junit.Test;

/**
 * @author US00852
 * @version $Id: RationalNumberTest.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RationalNumberTest {
	private RationalNumber r1_2;
	private RationalNumber r2_4;
	private RationalNumber r2_3;
	private RationalNumber r_2_3;
	private RationalNumber r2__3;
	private RationalNumber r2;
	private RationalNumber r0_4;

	@Before
	public void setUp() {
		r1_2 = RationalNumber.create(1, 2);
		r2_4 = RationalNumber.create(2, 4);
		r2_3 = RationalNumber.create(2, 3);
		r_2_3 = RationalNumber.create(-2, 3);
		r2__3 = RationalNumber.create(2, -3);
		r2 = RationalNumber.create(2);
		r0_4 = RationalNumber.create(0, 4);
	}

	@Test
	public final void testAbsolute() {
		assertThat(r1_2.abs(), ratio(1, 2));
		assertThat(r2_3.abs(), ratio(2, 3));
		assertThat(r_2_3.abs(), ratio(2, 3));
	}

	@Test
	public final void testAdd() {
		assertThat(r1_2.add(r2_4), ratio(1, 1));
		assertThat(r1_2.add(r2_3), ratio(7, 6));
	}

	@Test
	public final void testCompare() {
		assertThat(r1_2.compareTo(r2_4), equalTo(0));
		assertThat(r1_2.compareTo(r2_3), lessThan(0));
		assertThat(r2_3.compareTo(r1_2), greaterThan(0));
		assertThat(r_2_3.compareTo(r2_3), lessThan(0));
		assertThat(r2_3.compareTo(r_2_3), greaterThan(0));
	}

	@Test
	public final void testDivide() {
		assertThat(r1_2.div(r2_4), ratio(1, 1));
		assertThat(r1_2.div(r2_3), ratio(3, 4));
	}

	@Test
	public final void testDoubleValue() {
		assertThat(r1_2.doubleValue(), equalTo(0.5));
		assertThat(r2_4.doubleValue(), equalTo(0.5));
		assertThat(r2_3.doubleValue(), equalTo(2. / 3.));
	}

	@Test
	public final void testEqualsObject() {
		assertThat(r1_2, not(equalTo(null)));
		assertThat(r1_2, equalTo(r2_4));
		assertThat(r2_4, equalTo(r1_2));
		assertThat(r1_2.hashCode(), equalTo(r2_4.hashCode()));
	}

	@Test
	public final void testInverse() {
		assertThat(r1_2.inv(), ratio(2, 1));
		assertThat(r2_3.inv(), ratio(3, 2));
	}

	@Test
	public final void testMultiply() {
		assertThat(r1_2.mul(r2_4), ratio(1, 4));
		assertThat(r1_2.mul(r2_3), ratio(1, 3));
	}

	@Test
	public final void testNegate() {
		assertThat(r1_2.neg(), ratio(-1, 2));
		assertThat(r2_3.neg(), ratio(-2, 3));
	}

	@Test
	public final void testSubtract() {
		assertThat(r1_2.sub(r2_4), ratio(0, 1));
		assertThat(r1_2.sub(r2_3), ratio(-1, 6));
	}

	@Test
	public final void testToString() {
		assertThat(r1_2, hasToString("1/2"));
		assertThat(r2_4, hasToString("1/2"));
		assertThat(r2_3, hasToString("2/3"));
		assertThat(r_2_3, hasToString("-2/3"));
		assertThat(r2__3, hasToString("-2/3"));
		assertThat(r2, hasToString("2"));
		assertThat(r0_4, hasToString("0"));
	}
}
