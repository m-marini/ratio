/**
 * 
 */
package org.mmarini.ratio.interpreter;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertThat;
import static org.mmarini.ratio.RatioMatchers.ratio;

import org.junit.Test;
import org.mmarini.ratio.RationalNumber;

/**
 * @author US00852
 * 
 */
public class ScalarValueTest {

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.ScalarValue#ScalarValue(int)}.
	 */
	@Test
	public void testScalarValueInt() {
		final ScalarValue v = new ScalarValue(1);
		assertThat(v, hasProperty("value", ratio(1, 1)));
	}

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.ScalarValue#ScalarValue(org.mmarini.ratio.RationalNumber)}
	 * .
	 */
	@Test
	public void testScalarValueRationalNumber() {
		final ScalarValue v = new ScalarValue(RationalNumber.ONE);
		assertThat(v, hasProperty("value", ratio(1, 1)));
	}

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.ScalarValue#toString()}.
	 */
	@Test
	public void testToString() {
		final ScalarValue v = new ScalarValue(RationalNumber.create(2, 3));
		assertThat(v, hasToString("2/3"));
	}
}
