/**
 * 
 */
package org.mmarini.ratio.interpreter;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertThat;
import static org.mmarini.ratio.RatioMatchers.containsRatio;
import static org.mmarini.ratio.RatioMatchers.hasRows;

import org.junit.Test;
import org.mmarini.ratio.RationalArray;

/**
 * @author US00852
 * 
 */
public class ArrayValueTest {

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.ScalarValue#toString()}.
	 */
	@Test
	public void testToString() {
		final ArrayValue v = new ArrayValue(RationalArray.identity(2));
		assertThat(v, hasToString("[1, 0; 0, 1]"));
	}

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.ScalarValue#ScalarValue(org.mmarini.ratio.RationalNumber)}
	 * .
	 */
	@Test
	public void testVectorValue() {
		final ArrayValue v = new ArrayValue(RationalArray.identity(2));
		assertThat(
				v,
				hasProperty("value",
						hasRows(containsRatio(1, 0), containsRatio(0, 1))));
	}
}
