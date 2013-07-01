package org.mmarini.ratio;

import java.util.ArrayList;
import java.util.List;

/**
 * @author US00852
 * @version $Id: Factorizer.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class Factorizer {
	private static Factorizer instance = new Factorizer();

	/**
	 * @return Returns the instance.
	 */
	public static Factorizer getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private Factorizer() {}

	/**
	 * Creates the list of factors that composes an integer
	 * 
	 * @param number
	 *            the integer
	 * @return the list of PoweredNumber
	 */
	public List createFactors(int number) {
		List factors = new ArrayList();
		if (number == 0) {
			factors.add(new PoweredNumber(0));
		} else if (number == 1) {
			factors.add(new PoweredNumber(1));
		} else {
			this.createFactors(factors, number);
		}
		return factors;
	}

	/**
	 * @param factors
	 * @param number
	 */
	private void createFactors(List factors, int number) {
		int maxPrime = (int) Math.sqrt(number);
		int[] primes = Eratostene.getInstance().getPrimes(maxPrime);
		for (int i = 0; number > 1 && i < primes.length; ++i) {
			int prime = primes[i];
			if (prime >= number)
				break;
			// calculate the exponent
			int exp = 0;
			while ( (number % prime) == 0) {
				++exp;
				number /= prime;
			}
			if (exp > 0) {
				factors.add(new PoweredNumber(prime, exp));
			}
		}
		if (number > 1)
			factors.add(new PoweredNumber(number, 1));
	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	public int getMCD(int a, int b) {
		int mcd = 1;
		a = Math.abs(a);
		b = Math.abs(b);
		if (a <= 1 || b <= 1)
			return 1;
		List factorsA = this.createFactors(a);
		List factorsB = this.createFactors(b);
		int countA = factorsA.size();
		int countB = factorsB.size();
		int idxA = 0;
		int idxB = 0;
		while (idxA < countA && idxB < countB) {
			PoweredNumber factorA = (PoweredNumber) factorsA.get(idxA);
			PoweredNumber factorB = (PoweredNumber) factorsB.get(idxB);
			int baseA = factorA.getBase();
			int baseB = factorB.getBase();
			if (baseA == baseB) {
				int exp = Math.min(factorA.getPower(), factorB.getPower());
				for (int i = 0; i < exp; ++i)
					mcd *= baseA;
				++idxA;
				++idxB;
			} else if (baseA < baseB) {
				++idxA;
			} else {
				++idxB;
			}
		}
		return mcd;
	}
}