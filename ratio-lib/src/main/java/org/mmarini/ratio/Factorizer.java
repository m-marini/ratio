package org.mmarini.ratio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author US00852
 * @version $Id: Factorizer.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class Factorizer {
	private Eratostene primes;

	/**
	 * 
	 */
	public Factorizer() {
		primes = new Eratostene(97);
	}

	/**
	 * Creates the list of factors that composes an integer
	 * 
	 * @param number
	 *            the integer
	 * @return the list of PoweredNumber
	 */
	public List<PoweredNumber> createFactors(final int number) {
		final List<PoweredNumber> f;
		if (number == 0)
			f = Arrays.asList(PoweredNumber.ZERO);
		else if (number == 1)
			f = Arrays.asList(PoweredNumber.ONE);
		else
			f = createFactorsGreaterOne(number);
		return f;
	}

	/**
	 * @param number
	 */
	private List<PoweredNumber> createFactorsGreaterOne(final int number) {
		final List<PoweredNumber> f = new ArrayList<>();
		int n = number;
		for (final int p : primes.getPrimes()) {
			if (p >= n)
				break;
			// calculate the exponent
			int exp = 0;
			while ((n % p) == 0) {
				++exp;
				n /= p;
			}
			if (exp > 0)
				f.add(new PoweredNumber(p, exp));
		}
		if (n > 1)
			f.add(new PoweredNumber(n, 1));
		return f;
	}

	/**
	 * @param a1
	 * @param b1
	 * @return
	 */
	public int getMCD(final int a1, final int b1) {
		final int a = Math.abs(a1);
		final int b = Math.abs(b1);
		if (a <= 1 || b <= 1)
			return 1;
		final int max = (int) Math.sqrt(Math.max(a, b));
		if (primes.getMax() < max)
			primes = new Eratostene(max);
		final List<PoweredNumber> la = createFactors(a);
		final List<PoweredNumber> lb = createFactors(b);
		final int na = la.size();
		final int nb = lb.size();
		int i = 0;
		int j = 0;
		int mcd = 1;
		while (i < na && j < nb) {
			PoweredNumber fa = la.get(i);
			PoweredNumber fb = lb.get(j);
			final int ba = fa.getBase();
			final int bb = fb.getBase();
			if (ba == bb) {
				final int exp = Math.min(fa.getExponent(), fb.getExponent());
				for (int k = 0; k < exp; ++k)
					mcd *= ba;
				++i;
				++j;
			} else if (ba < bb) {
				++i;
			} else {
				++j;
			}
		}
		return mcd;
	}
}