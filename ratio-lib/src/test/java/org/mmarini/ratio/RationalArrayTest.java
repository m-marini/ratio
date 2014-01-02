/*
 * Created on Jul 12, 2004
 */
package org.mmarini.ratio;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.mmarini.ratio.RatioMatchers.containsRatio;
import static org.mmarini.ratio.RatioMatchers.hasRows;
import static org.mmarini.ratio.RatioMatchers.ratio;

import org.junit.Before;
import org.junit.Test;

/**
 * @author US00852
 * @version $Id: RationalArrayTest.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RationalArrayTest {

	private RationalArray magic;
	private RationalArray no_magic;

	@Before
	public void setup() {
		magic = new RationalArray(new RationalNumber[][] {
				{ RationalNumber.create(8), RationalNumber.ONE,
						RationalNumber.create(6) },
				{ RationalNumber.create(3), RationalNumber.create(5),
						RationalNumber.create(7) },
				{ RationalNumber.create(4), RationalNumber.create(9),
						RationalNumber.create(2) } });
		no_magic = new RationalArray(new RationalNumber[][] {
				{ RationalNumber.ONE, RationalNumber.create(2),
						RationalNumber.create(3) },
				{ RationalNumber.create(4), RationalNumber.create(5),
						RationalNumber.create(6) },
				{ RationalNumber.create(7), RationalNumber.create(8),
						RationalNumber.create(9) } });
	}

	@Test
	public void testAdd() {
		assertThat(
				magic.add(RationalArray.identity(3)),
				hasRows(containsRatio(9, 1, 6), containsRatio(3, 6, 7),
						containsRatio(4, 9, 3)));
	}

	@Test
	public void testAgumentArray() {
		assertThat(
				magic.agument(RationalArray.identity(3)),
				hasRows(containsRatio(8, 1, 6, 1, 0, 0),
						containsRatio(3, 5, 7, 0, 1, 0),
						containsRatio(4, 9, 2, 0, 0, 1)));
	}

	@Test
	public void testAgumentVector() {
		assertThat(
				magic.agument(new RationalVector(RationalNumber.ONE,
						RationalNumber.create(2), RationalNumber.create(3))),
				hasRows(containsRatio(8, 1, 6, 1), containsRatio(3, 5, 7, 2),
						containsRatio(4, 9, 2, 3)));
	}

	@Test
	public void testDet() {
		assertThat(magic.det(), ratio(-360, 1));
	}

	@Test
	public void testDet0() {
		assertThat(no_magic.det(), ratio(0, 1));
	}

	@Test
	public void testFill2x3() {
		assertThat(RationalArray.fill(2, 3, RationalNumber.ONE),
				hasRows(containsRatio(1, 1, 1), containsRatio(1, 1, 1)));
	}

	@Test
	public void testFill3x2() {
		assertThat(
				RationalArray.fill(3, 2, RationalNumber.ONE),
				hasRows(containsRatio(1, 1), containsRatio(1, 1),
						containsRatio(1, 1)));
	}

	@Test
	public void testIdentity1() {
		assertThat(RationalArray.identity(1), hasRows(containsRatio(1)));
	}

	@Test
	public void testIdentity3() {
		assertThat(
				RationalArray.identity(3),
				hasRows(containsRatio(1, 0, 0), containsRatio(0, 1, 0),
						containsRatio(0, 0, 1)));
	}

	@Test
	public void testInv() {
		assertThat(
				magic.inv(),
				hasRows(containsRatio(ratio(53, 360), ratio(-13, 90),
						ratio(23, 360)),
						containsRatio(ratio(-11, 180), ratio(1, 45),
								ratio(19, 180)),
						containsRatio(ratio(-7, 360), ratio(17, 90),
								ratio(-37, 360))));
	}

	@Test
	public void testMagic() {
		assertThat(
				magic,
				hasRows(containsRatio(8, 1, 6), containsRatio(3, 5, 7),
						containsRatio(4, 9, 2)));
	}

	@Test
	public void testMultiplyArray() {
		assertThat(
				magic.mul(magic),
				hasRows(containsRatio(64 + 3 + 24, 8 + 5 + 54, 48 + 7 + 12),
						containsRatio(24 + 15 + 28, 3 + 25 + 63, 18 + 35 + 14),
						containsRatio(32 + 27 + 8, 4 + 45 + 18, 24 + 63 + 4)));
	}

	@Test
	public void testMultiplyVector() {
		assertThat(
				magic.mul(new RationalVector(RationalNumber.ONE, RationalNumber
						.create(2), RationalNumber.create(3))),
				hasProperty("values",
						containsRatio(8 + 2 + 18, 3 + 10 + 21, 4 + 18 + 6)));
	}

	@Test
	public void testNeg() {
		assertThat(
				magic.neg(),
				hasRows(containsRatio(-8, -1, -6), containsRatio(-3, -5, -7),
						containsRatio(-4, -9, -2)));
	}

	@Test
	public void testRank() {
		assertThat(magic.rank(), equalTo(3));
		assertThat(no_magic.rank(), equalTo(2));
	}

	@Test
	public void testReduce() {
		assertThat(
				magic.agument(RationalArray.identity(3)).reduce(),
				hasRows(containsRatio(ratio(1, 1), ratio(0, 1), ratio(0, 1),
						ratio(53, 360), ratio(-13, 90), ratio(23, 360)),
						containsRatio(ratio(0, 1), ratio(1, 1), ratio(0, 1),
								ratio(-11, 180), ratio(1, 45), ratio(19, 180)),
						containsRatio(ratio(0, 1), ratio(0, 1), ratio(1, 1),
								ratio(-7, 360), ratio(17, 90), ratio(-37, 360))));
	}

	@Test
	public void testScale() {
		assertThat(
				magic.mul(RationalNumber.create(2)),
				hasRows(containsRatio(16, 2, 12), containsRatio(6, 10, 14),
						containsRatio(8, 18, 4)));
	}

	@Test
	public void testSlice() {
		assertThat(
				magic.sliceCol(1, 2),
				hasRows(containsRatio(1, 6), containsRatio(5, 7),
						containsRatio(9, 2)));
	}

	@Test
	public void testSub() {
		assertThat(
				magic.sub(RationalArray.identity(3)),
				hasRows(containsRatio(7, 1, 6), containsRatio(3, 4, 7),
						containsRatio(4, 9, 1)));
	}

	@Test
	public void testTranspose() {
		assertThat(
				magic.transpose(),
				hasRows(containsRatio(8, 3, 4), containsRatio(1, 5, 9),
						containsRatio(6, 7, 2)));
	}

	@Test
	public void testZero2x3() {
		assertThat(RationalArray.zeros(2, 3),
				hasRows(containsRatio(0, 0, 0), containsRatio(0, 0, 0)));
	}

	@Test
	public void testZero3x2() {
		assertThat(
				RationalArray.zeros(3, 2),
				hasRows(containsRatio(0, 0), containsRatio(0, 0),
						containsRatio(0, 0)));
	}

	@Test
	public void testZero3x3() {
		assertThat(
				RationalArray.zeros(3, 3),
				hasRows(containsRatio(0, 0, 0), containsRatio(0, 0, 0),
						containsRatio(0, 0, 0)));
	}

	@Test
	public void testZeros1x1() {
		assertThat(RationalArray.zeros(1, 1), hasRows(containsRatio(0)));
	}

}
