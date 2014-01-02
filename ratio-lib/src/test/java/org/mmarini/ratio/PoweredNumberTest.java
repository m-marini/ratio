/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author US00852
 * @version $Id: PoweredNumberTest.java,v 1.1 2005/02/10 22:29:26 marco Exp $
 */
public class PoweredNumberTest {

	@Test
	public final void testDoubleValue() {
		assertThat(new PoweredNumber(3, 3).doubleValue(), equalTo(27.));
	}

	@Test
	public final void testEqualsObject() {
		final PoweredNumber number27a = new PoweredNumber(3, 3);
		final PoweredNumber number27b = new PoweredNumber(3, 3);
		assertThat(number27a, not(equalTo(null)));
		assertThat(number27a, equalTo(number27a));
		assertThat(number27a, equalTo(number27b));
		assertThat(number27a, equalTo((Number) (new Integer(27))));
		assertThat(number27a.hashCode(), equalTo(number27b.hashCode()));
	}

	@Test
	public final void testIntValue() {
		assertThat(new PoweredNumber(3, 3).intValue(), equalTo(27));
	}

	@Test
	public final void testToString() {
		assertThat(new PoweredNumber(3, 3), hasToString("3^3"));
	}
}
