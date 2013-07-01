package org.mmarini.ratio;

import org.mmarini.ratio.Eratostene;

import junit.framework.TestCase;

/**
 * @author US00852
 * @version $Id: EratosteneTest.java,v 1.2 2006/01/31 22:31:30 marco Exp $
 */
public class EratosteneTest extends TestCase {

	public final void testGetInstance() {
		assertNotNull(Eratostene.getInstance());
	}
	
	public final void testGetPrimes() {
		int[] primes = Eratostene.getInstance().getPrimes(10);
		assertEquals(4, primes.length);
		assertEquals(2, primes[0]);
		assertEquals(3, primes[1]);
		assertEquals(5, primes[2]);
		assertEquals(7, primes[3]);

		primes = Eratostene.getInstance().getPrimes(20);
		assertEquals(8, primes.length);
		assertEquals(2, primes[0]);
		assertEquals(3, primes[1]);
		assertEquals(5, primes[2]);
		assertEquals(7, primes[3]);
		assertEquals(11, primes[4]);
		assertEquals(13, primes[5]);
		assertEquals(17, primes[6]);
		assertEquals(19, primes[7]);

		primes = Eratostene.getInstance().getPrimes(10);
		assertEquals(4, primes.length);
		assertEquals(2, primes[0]);
		assertEquals(3, primes[1]);
		assertEquals(5, primes[2]);
		assertEquals(7, primes[3]);

	}

}
