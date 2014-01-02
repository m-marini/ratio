package org.mmarini.ratio;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * @author US00852
 * @version $Id: Eratostene.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class Eratostene {
	private final List<Integer> primes;
	private final int max;

	/**
	 * 
	 */
	public Eratostene(final int max) {
		final BitSet bitSet = new BitSet();
		primes = new ArrayList<>();
		this.max = max;
		for (int i = 2; i <= max; ++i)
			if (!bitSet.get(i)) {
				for (int j = i; j <= max; j += i)
					bitSet.set(j);
				primes.add(i);
			}
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param to
	 */
	public List<Integer> getPrimes() {
		return primes;
	}
}