/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import org.mmarini.ratio.RationalNumber;

import junit.framework.TestCase;

/**
 * @author US00852
 * @version $Id: RationalNumberTest.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RationalNumberTest extends TestCase {
	RationalNumber number1 = new RationalNumber(1, 2);
	RationalNumber number2 = new RationalNumber(2, 4);
	RationalNumber number3 = new RationalNumber(2, 3);
	RationalNumber number4 = new RationalNumber(-2, 3);
	RationalNumber number5 = new RationalNumber(2, -3);
	RationalNumber number6 = new RationalNumber(2, 1);
	RationalNumber number7 = new RationalNumber(0, 4);

	public final void testDoubleValue() {
		assertEquals(0.5d, number1.doubleValue(), 1e-10);
		assertEquals(0.5d, number2.doubleValue(), 1e-10);
		assertEquals(2d / 3, number3.doubleValue(), 1e-10);
	}

	/*
	 * Class to test for boolean equals(Object)
	 */
	public final void testEqualsObject() {
		assertFalse(number1.equals(null));
		assertTrue(number1.equals(number2));
		assertTrue(number2.equals(number1));
		assertEquals(number1.hashCode(), number2.hashCode());
	}

	/*
	 * Class to test for String toString()
	 */
	public final void testToString() {
		assertEquals("1/2", number1.toString());
		assertEquals("1/2", number2.toString());
		assertEquals("2/3", number3.toString());
		assertEquals("-2/3", number4.toString());
		assertEquals("-2/3", number5.toString());
		assertEquals("2", number6.toString());
		assertEquals("0", number7.toString());
	}

	public final void testAdd() {
		assertEquals(new RationalNumber(1), number1.add(number2));
		assertEquals(new RationalNumber(7, 6), number1.add(number3));
	}

	public final void testSubtract() {
		assertEquals(new RationalNumber(0), number1.subtract(number2));
		assertEquals(new RationalNumber(-1, 6), number1.subtract(number3));
	}

	public final void testNegate() {
		assertEquals(new RationalNumber(-1, 2), number1.negate());
		assertEquals(new RationalNumber(-2, 3), number3.negate());
	}

	public final void testInverse() {
		assertEquals(new RationalNumber(2), number1.inverse());
		assertEquals(new RationalNumber(3, 2), number3.inverse());
	}

	public final void testMultiply() {
		assertEquals(new RationalNumber(1, 4), number1.multiply(number2));
		assertEquals(new RationalNumber(1, 3), number1.multiply(number3));
	}

	public final void testDivide() {
		assertEquals(new RationalNumber(1), number1.divide(number2));
		assertEquals(new RationalNumber(3, 4), number1.divide(number3));
	}
}
