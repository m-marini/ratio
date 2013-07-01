package org.mmarini.ratio;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author US00852
 * @version $Id: Eratostene.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class Eratostene {
	private static Eratostene instance = new Eratostene();

	private List primesList = new ArrayList();
	private BitSet bitSet = new BitSet();
	private int maxNumber = 1;

	/**
	 * @return Returns the instance.
	 */
	public static Eratostene getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private Eratostene() {}

	/**
	 * @return Returns the bitSet.
	 */
	private BitSet getBitSet() {
		return bitSet;
	}

	/**
	 * @return Returns the primes.
	 */
	private List getPrimesList() {
		return primesList;
	}

	/**
	 * @param to
	 */
	private void initialize(int to) {
		for (Iterator iter = getPrimesList().iterator(); iter.hasNext();) {
			Integer prime = (Integer) iter.next();
			setMultiplies(prime.intValue(), to);
		}
	}

	/**
	 * @param to
	 */
	private void generatePrimes(int to) {
		initialize(to);
		BitSet bitset = getBitSet();
		List primes = getPrimesList();
		for (int i = getMaxNumber() + 1; i <= to; ++i) {
			if (!bitset.get(i)) {
				primes.add(new Integer(i));
				setMultiplies(i, to);
			}
		}
		setMaxNumber(to);
	}

	/**
	 * @param prime
	 * @param to
	 * @param bitset
	 */
	private void setMultiplies(int prime, int to) {
		BitSet bitset = getBitSet();
		int start = getMaxNumber() / prime + 1;
		start *= prime;
		for (int j = start; j <= to; j += prime) {
			bitset.set(j);
		}
	}

	/**
	 * Returns the prime integers list
	 * 
	 * @param max
	 *            the max integer value
	 * @return the list of prime integers
	 */
	public int[] getPrimes(int max) {
		synchronized (this) {
			if (max > getMaxNumber())
				generatePrimes(max);
		}
		List primes = getPrimesList();
		int counter = 0;
		for (Iterator iter = primes.iterator(); iter.hasNext();) {
			Integer prime = (Integer) iter.next();
			if (prime.intValue() > max)
				break;
			++counter;
		}
		int[] array = new int[counter];
		for (int i = 0; i < array.length; ++i)
			array[i] = ((Integer) primes.get(i)).intValue();
		return array;
	}

	/**
	 * @return Returns the maxNumber.
	 */
	private int getMaxNumber() {
		return maxNumber;
	}

	/**
	 * @param maxNumber
	 *            The maxNumber to set.
	 */
	private void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}
}