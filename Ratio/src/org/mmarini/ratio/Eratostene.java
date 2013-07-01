package org.mmarini.ratio;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author US00852
 * @version $Id: Eratostene.java,v 1.2 2006/01/15 12:21:17 marco Exp $
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
	protected Eratostene() {}

	/**
	 * @return Returns the bitSet.
	 */
	protected BitSet getBitSet() {
		return bitSet;
	}

	/**
	 * @return Returns the primes.
	 */
	protected List getPrimesList() {
		return primesList;
	}

	/**
	 * @param to
	 */
	protected void initialize(int to) {
		for (Iterator iter = this.getPrimesList().iterator(); iter.hasNext();) {
			Integer prime = (Integer) iter.next();
			this.setMultiplies(prime.intValue(), to);
		}
	}

	/**
	 * @param to
	 */
	protected void generatePrimes(int to) {
		this.initialize(to);
		BitSet bitset = this.getBitSet();
		List primes = this.getPrimesList();
		for (int i = this.getMaxNumber() + 1; i <= to; ++i) {
			if (!bitset.get(i)) {
				primes.add(new Integer(i));
				this.setMultiplies(i, to);
			}
		}
		this.setMaxNumber(to);
	}

	/**
	 * @param prime
	 * @param to
	 * @param bitset
	 */
	protected void setMultiplies(int prime, int to) {
		BitSet bitset = this.getBitSet();
		int start = this.getMaxNumber() / prime + 1;
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
		if (max > this.getMaxNumber())
			this.generatePrimes(max);
		List primes = this.getPrimesList();
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
	protected int getMaxNumber() {
		return maxNumber;
	}

	/**
	 * @param maxNumber
	 *            The maxNumber to set.
	 */
	protected void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}
}