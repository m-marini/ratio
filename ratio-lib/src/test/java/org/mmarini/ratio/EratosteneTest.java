/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author US00852
 * @version $Id: EratosteneTest.java,v 1.1 2005/02/10 22:29:26 marco Exp $
 */
public class EratosteneTest {

	@Test
	public void testGetPrimesTo10() {
		final Eratostene e = new Eratostene(10);
		assertThat(e, hasProperty("max", equalTo(10)));
		assertThat(e, hasProperty("primes", contains(2, 3, 5, 7)));
	}

	@Test
	public void testGetPrimesTo20() {
		final Eratostene e = new Eratostene(20);
		assertThat(e, hasProperty("max", equalTo(20)));
		assertThat(e,
				hasProperty("primes", contains(2, 3, 5, 7, 11, 13, 17, 19)));
	}

	@Test
	public void testGetPrimesTo30() {
		final Eratostene e = new Eratostene(30);
		assertThat(e, hasProperty("max", equalTo(30)));
		assertThat(
				e,
				hasProperty("primes",
						contains(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)));
	}
}
