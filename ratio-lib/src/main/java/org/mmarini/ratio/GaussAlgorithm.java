package org.mmarini.ratio;

/**
 * 
 * @author US00852
 * @version $Id: GaussAlgorithm.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class GaussAlgorithm {
	private final RationalNumber[][] array;
	private RationalNumber determiner;

	/**
	 * @param array
	 */
	public GaussAlgorithm(RationalNumber[][] array) {
		this.array = array;
		determiner = RationalNumber.ONE;
		process();
	}

	/**
	 * @param i
	 * @param value
	 */
	private void divideRow(int i, RationalNumber value) {
		final RationalNumber[] r = array[i];
		final int n = r.length;
		for (int j = 0; j < n; ++j)
			r[j] = r[j].div(value);
		determiner = determiner.mul(value);
	}

	/**
	 * @return the gaussArray
	 */
	public RationalNumber[][] getArray() {
		return array;
	}

	/**
	 * @return the lambda
	 */
	public RationalNumber getDeterminer() {
		return determiner;
	}

	/**
	 * 
	 * @return
	 */
	public int getRank() {
		final int n = array.length;
		final int m = array[0].length;
		int r = 0;
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j)
				if (!RationalNumber.ZERO.equals(array[i][j])) {
					++r;
					break;
				}
		return r;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isTriangular() {
		final int m = array.length;
		final int n = array[0].length;
		for (int i = 0; i < m && i < n; ++i)
			if (RationalNumber.ZERO.equals(array[i][i]))
				return false;
		return true;
	}

	/**
	 * subtract value * row i from row j
	 * 
	 * @param value
	 * @param i
	 * @param j
	 */
	private void multiplySubtractRows(RationalNumber value, int i, int j) {
		final RationalNumber[] ri = array[i];
		final RationalNumber[] rj = array[j];
		final int n = rj.length;
		for (int k = 0; k < n; ++k)
			rj[k] = rj[k].sub(ri[k].mul(value));
	}

	/**
	 * 
	 */
	private void process() {
		final int m = array.length;
		final int n = array[0].length;
		int i = 0;
		int j = 0;
		while (i < m && j < n) {
			// Find pivot in column j, starting in row i:
			RationalNumber pv = array[i][j];
			RationalNumber pav = pv.abs();
			int pi = i;
			for (int k = i + 1; k < m; ++k) {
				final RationalNumber v = array[k][j];
				final RationalNumber av = v.abs();
				if (av.compareTo(pav) > 0) {
					pv = v;
					pav = av;
					pi = k;
				}
			}
			if (!pv.equals(RationalNumber.ZERO)) {
				switchRows(i, pi);
				// Now A[i, j] will contain the same value as max_val
				divideRow(i, pv);

				// Now A[i, j] will have the value 1
				for (int u = 0; u < m; ++u) {
					if (u != i) {
						multiplySubtractRows(array[u][j], i, u);
						/*
						 * Now A[u, j] will be 0, since A[u, j] - A[u, j] *
						 * array A[i, j] = A[u, j] - 1 * A[u, j] = 0
						 */
					}
				}
				++i;
			}
			++j;
		}
	}

	/**
	 * 
	 * @param i
	 * @param j
	 */
	private void switchRows(int i, int j) {
		if (i != j) {
			final RationalNumber[] t = array[i];
			array[i] = array[j];
			array[j] = t;
			determiner = determiner.neg();
		}
	}
}
