package org.mmarini.ratio;

import java.util.Arrays;

/**
 * @author US00852
 * @version $Id: RationalArray.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RationalArray {

	/**
	 * 
	 * @param n
	 * @param m
	 * @param v
	 * @return
	 */
	public static RationalArray fill(final int n, final int m,
			final RationalNumber v) {
		final RationalNumber[][] array = new RationalNumber[n][m];
		for (final RationalNumber[] r : array)
			Arrays.fill(r, v);
		return new RationalArray(array);
	}

	/**
	 * @param n
	 * @return
	 */
	public static RationalArray identity(final int n) {
		final RationalNumber[][] array = new RationalNumber[n][n];
		for (int i = 0; i < n; ++i) {
			Arrays.fill(array[i], RationalNumber.ZERO);
			array[i][i] = RationalNumber.ONE;
		}
		return new RationalArray(array);
	}

	/**
	 * @param n
	 * @return
	 */
	public static RationalArray zeros(final int n, final int m) {
		return fill(n, m, RationalNumber.ZERO);
	}

	private final RationalNumber[][] values;

	/**
	 * @param values
	 */
	public RationalArray(final RationalNumber[][] values) {
		this.values = values;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalArray add(final RationalArray a) {
		final int n = values.length;
		final int m = values[0].length;
		final int na = a.values.length;
		final int ma = a.values[0].length;
		if (n != na || m != ma)
			throw new IllegalArgumentException(
					"Add of different dimensions arrays " + n + "x" + m
							+ " != " + na + "x" + ma);

		final RationalNumber[][] v = new RationalNumber[n][m];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j)
				v[i][j] = values[i][j].add(a.values[i][j]);
		return new RationalArray(v);
	}

	/**
	 * 
	 * @param os
	 * @return
	 */
	public RationalArray agument(final RationalArray o) {
		final int n = values.length;
		final int m = values[0].length;
		final int no = o.values.length;
		final int mo = o.values[0].length;
		if (n != no)
			throw new IllegalArgumentException(
					"Agument of different dimensions arrays " + n + "x" + m
							+ " != " + no + "x" + mo);

		final RationalNumber[][] v = new RationalNumber[n][m + mo];
		for (int i = 0; i < n; ++i) {
			System.arraycopy(values[i], 0, v[i], 0, m);
			System.arraycopy(o.values[i], 0, v[i], m, mo);
		}
		return new RationalArray(v);
	}

	/**
	 * 
	 * @param o
	 * @return
	 */
	public RationalArray agument(RationalVector o) {
		final int n = values.length;
		final int m = values[0].length;
		final int no = o.getDimensions();
		if (n != no)
			throw new IllegalArgumentException(
					"Agument of different dimensions " + n + "x" + m + " != "
							+ no);

		final RationalNumber[][] v = new RationalNumber[n][m + 1];
		RationalNumber[] ov = o.getValues();
		for (int i = 0; i < n; ++i) {
			System.arraycopy(values[i], 0, v[i], 0, m);
			v[i][m] = ov[i];
		}
		return new RationalArray(v);
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	private RationalNumber[][] clone(final RationalNumber[][] v) {
		final int n = v.length;
		final RationalNumber[][] r = new RationalNumber[n][];
		for (int i = 0; i < n; ++i)
			r[i] = Arrays.copyOf(v[i], v[i].length);
		return r;
	}

	/**
	 * 
	 * @return
	 */
	public RationalNumber det() {
		final int n = values.length;
		final int m = values[0].length;
		if (n != m)
			throw new IllegalArgumentException("Inverse of not square array "
					+ n + "x" + m);
		final GaussAlgorithm g = new GaussAlgorithm(
				clone(agument(identity(n)).values));
		return g.isTriangular() ? g.getDeterminer() : RationalNumber.ZERO;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RationalArray other = (RationalArray) obj;
		if (!Arrays.deepEquals(values, other.values))
			return false;
		return true;
	}

	/**
	 * @return the values
	 */
	public RationalNumber[][] getValues() {
		return values;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public RationalArray inv() {
		final int n = values.length;
		final int m = values[0].length;
		if (n != m)
			throw new IllegalArgumentException("Inverse of not square array "
					+ n + "x" + m);
		final GaussAlgorithm g = new GaussAlgorithm(
				clone(agument(identity(n)).values));
		return new RationalArray(g.getArray()).sliceCol(n, n);
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalArray mul(RationalArray a) {
		final int n = values.length;
		final int m = values[0].length;
		final int na = a.values.length;
		final int ma = a.values[0].length;
		if (m != na)
			throw new IllegalArgumentException(
					"Multyply of different dimensions arrays " + n + "x" + m
							+ " != " + na + "x" + ma);

		final RationalNumber[][] v = new RationalNumber[n][ma];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < ma; ++j) {
				RationalNumber t = RationalNumber.ZERO;
				for (int k = 0; k < m; ++k)
					t = t.add(values[i][k].mul(values[k][j]));
				v[i][j] = t;
			}
		return new RationalArray(v);
	}

	/**
	 * @param scale
	 * @return
	 */
	public RationalArray mul(RationalNumber scale) {
		final int n = values.length;
		final int m = values[0].length;
		final RationalNumber[][] v = new RationalNumber[n][m];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j)
				v[i][j] = values[i][j].mul(scale);
		return new RationalArray(v);
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalVector mul(RationalVector a) {
		final int n = values.length;
		final int m = values[0].length;
		final int na = a.getDimensions();
		if (m != na)
			throw new IllegalArgumentException(
					"Multyply of different dimensions array and vector " + n
							+ "x" + m + " != " + n);

		final RationalNumber[] va = a.getValues();
		final RationalNumber[] v = new RationalNumber[n];
		for (int i = 0; i < n; ++i) {
			RationalNumber t = RationalNumber.ZERO;
			for (int j = 0; j < m; ++j) {
				t = t.add(values[i][j].mul(va[j]));
			}
			v[i] = t;
		}
		return new RationalVector(v);
	}

	/**
	 * 
	 * @return
	 */
	public RationalArray neg() {
		final int n = values.length;
		final int m = values[0].length;
		final RationalNumber[][] v = new RationalNumber[n][m];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j)
				v[i][j] = values[i][j].neg();
		return new RationalArray(v);
	}

	/**
	 * 
	 * @return
	 */
	public int rank() {
		return new GaussAlgorithm(clone(values)).getRank();
	}

	/**
	 * 
	 * @return
	 */
	public RationalArray reduce() {
		return new RationalArray(new GaussAlgorithm(clone(values)).getArray());
	}

	/**
	 * 
	 * @param col
	 * @param size
	 * @return
	 */
	public RationalArray sliceCol(final int col, final int size) {
		final int n = values.length;
		final RationalNumber[][] v = new RationalNumber[n][size];
		for (int i = 0; i < n; ++i)
			System.arraycopy(values[i], col, v[i], 0, size);
		return new RationalArray(v);
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalArray sub(RationalArray a) {
		final int n = values.length;
		final int m = values[0].length;
		final int na = a.values.length;
		final int ma = a.values[0].length;
		if (n != na || m != ma)
			throw new IllegalArgumentException(
					"Subtract of different dimensions arrays " + n + "x" + m
							+ " != " + na + "x" + ma);

		final RationalNumber[][] v = new RationalNumber[n][m];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j)
				v[i][j] = values[i][j].sub(a.values[i][j]);
		return new RationalArray(v);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RationalArray [values=")
				.append(Arrays.toString(values)).append("]");
		return builder.toString();
	}

	/**
	 * @return
	 */
	public RationalArray transpose() {
		final int n = values.length;
		final int m = values[0].length;
		final RationalNumber[][] v = new RationalNumber[m][n];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j)
				v[j][i] = values[i][j];
		return new RationalArray(v);
	}
}