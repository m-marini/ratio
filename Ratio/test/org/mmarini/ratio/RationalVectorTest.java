/*
 * Created on Jul 12, 2004
 */
package org.mmarini.ratio;

import org.mmarini.ratio.RationalNumber;
import org.mmarini.ratio.RationalVector;

import junit.framework.TestCase;

/**
 * @author us00852
 * @version $Id: RationalVectorTest.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RationalVectorTest extends TestCase {
	RationalVector refVector;
	RationalVector equVector;
	RationalVector refSum;
	RationalNumber refLength2;

	String refString = "(0, 1, 2)";

	public final void testRationalVector() {
		RationalVector vector = new RationalVector(2);
		assertNotNull(vector);
	}

	public final void testSetGetValue() {
		int size = 3;
		RationalVector vector = new RationalVector(size);
		assertNotNull(vector);
		for (int i = 0; i < size; ++i)
			vector.setValue(i, new RationalNumber(i));
		for (int i = 0; i < size; ++i) {
			String msg = "Value[" + i + "]";
			RationalNumber value = vector.getValue(i);
			assertNotNull(msg, value);
			assertEquals(msg, new RationalNumber(i), value);
		}
	}

	public final void testGetSum() {
		RationalVector value = refVector.add(refVector);
		assertEquals(refSum, value);
	}

	public final void testGetDifference() {
		RationalVector value = refVector.subtract(refVector);
		assertEquals(new RationalVector(3), value);
	}

	/*
	 * Class to test for RationalNumber getProduct(RationalVector)
	 */
	public final void testGetProductRationalVector() {
		RationalNumber value = refVector.multiply(refVector);
		assertEquals(refLength2, value);
	}

	/*
	 * Class to test for RationalVector getProduct(RationalNumber)
	 */
	public final void testGetProductRationalNumber() {
		RationalVector value = refVector.multiply(new RationalNumber(2));
		assertEquals(refSum, value);
	}

	public final void testGetLength2() {
		assertEquals(refLength2, refVector.getLength2());
	}

	public final void testGetRank() {
		assertEquals(3, refVector.getRank());
	}

	/*
	 * Class to test for boolean equals(Object)
	 */
	public final void testEqualsObject() {
		assertFalse(refVector.equals(null));
		assertTrue(refVector.equals(equVector));
		assertTrue(equVector.equals(refVector));
	}

	/*
	 * Class to test for String toString()
	 */
	public final void testToString() {
		assertEquals(refString, refVector.toString());
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		refVector = new RationalVector(3);
		refVector.setValue(0, new RationalNumber());
		refVector.setValue(1, new RationalNumber(1));
		refVector.setValue(2, new RationalNumber(2));

		refLength2 = new RationalNumber(5);

		refSum = new RationalVector(3);
		refSum.setValue(0, new RationalNumber());
		refSum.setValue(1, new RationalNumber(2));
		refSum.setValue(2, new RationalNumber(4));

		equVector = new RationalVector(3);
		equVector.setValue(0, new RationalNumber());
		equVector.setValue(1, new RationalNumber(1));
		equVector.setValue(2, new RationalNumber(2));
	}
}
