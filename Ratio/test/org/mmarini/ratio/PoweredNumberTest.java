/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import org.mmarini.ratio.PoweredNumber;

import junit.framework.TestCase;

/**
 * @author US00852
 * @version $Id: PoweredNumberTest.java,v 1.1 2005/02/10 22:29:26 marco Exp $
 */
public class PoweredNumberTest extends TestCase {
	PoweredNumber number27 = new PoweredNumber(3, 3);

	public final void testDoubleValue() {
		assertEquals(27d, number27.doubleValue(), 1e-10);
	}

	public final void testIntValue() {
		assertEquals(27, number27.intValue());
	}

	/*
	 * Class to test for boolean equals(Object)
	 */
	public final void testEqualsObject() {
		PoweredNumber number27 = new PoweredNumber(3, 3);
		assertFalse(number27.equals(null));
		assertTrue(number27.equals(this.number27));
		assertTrue(this.number27.equals(number27));
		assertEquals(number27.hashCode(), this.number27.hashCode());
	}

	/*
	 * Class to test for String toString()
	 */
	public final void testToString() {
		assertEquals("3^3", number27.toString());
	}
}
