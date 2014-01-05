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
	public RationalArray agumentCol(final RationalArray o) {
		final int n = values.length;
		final int m = values[0].length;
		final int no = o.values.length;
		final int mo = o.values[0].length;
		if (n != no)
			throw new IllegalArgumentException(
					"Augment of different dimensions arrays " + n + "x" + m
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
	 * @param os
	 * @return
	 */
	public RationalArray agumentCol(final RationalNumber o) {
		final int n = values.length;
		final int m = values[0].length;
		if (n != 1)
			throw new IllegalArgumentException(
					"Augment of different dimensions arrays " + n + "x" + m
							+ " != 1");

		final RationalNumber[][] v = new RationalNumber[n][m + 1];
		System.arraycopy(values[0], 0, v[0], 0, m);
		v[0][m] = o;
		return new RationalArray(v);
	}

	/**
	 * @param o
	 * @return
	 */
	public RationalArray agumentRow(final RationalArray o) {
		final int n = values.length;
		final int m = values[0].length;
		final RationalNumber[][] vo = o.values;
		final int no = vo.length;
		final int mo = vo[0].length;
		if (m != mo)
			throw new IllegalArgumentException(
					"Augment of different dimensions arrays " + n + "x" + m
							+ " != " + no + "x" + mo);

		final RationalNumber[][] v = new RationalNumber[n + no][m];
		System.arraycopy(values, 0, v, 0, n);
		System.arraycopy(vo, 0, v, n, no);
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
		final GaussAlgorithm g = new GaussAlgorithm(
				clone(agumentCol(identity(values.length)).values));
		return g.isTriangular() ? g.getDeterminer() : RationalNumber.ZERO;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RationalArray other = (RationalArray) obj;
		if (!Arrays.deepEquals(values, other.values))
			return false;
		return true;
	}

	/**
	 * @return
	 */
	public int getColumnCount() {
		return values[0].length;
	}

	/**
	 * @return
	 */
	public int getRowCount() {
		return values.length;
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
				clone(agumentCol(identity(n)).values));
		if (!g.isTriangular())
			throw new IllegalArgumentException("divide by 0");
		return new RationalArray(g.getArray()).sliceCol(n, n);
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalArray mul(final RationalArray a) {
		final int n = values.length;
		final int m = values[0].length;
		final RationalNumber[][] va = a.values;
		final int na = va.length;
		final int ma = va[0].length;
		if (m != na)
			throw new IllegalArgumentException(
					"Multiply of different dimensions arrays " + n + "x" + m
							+ " != " + na + "x" + ma);

		final RationalNumber[][] v = new RationalNumber[n][ma];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < ma; ++j) {
				RationalNumber t = RationalNumber.ZERO;
				for (int k = 0; k < m; ++k)
					t = t.add(values[i][k].mul(va[k][j]));
				v[i][j] = t;
			}
		return new RationalArray(v);
	}

	/**
	 * @param scale
	 * @return
	 */
	public RationalArray mul(final RationalNumber scale) {
		final int n = values.length;
		final int m = values[0].length;
		final RationalNumber[][] v = new RationalNumber[n][m];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j)
				v[i][j] = values[i][j].mul(scale);
		return new RationalArray(v);
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
	 * @param i
	 * @param size
	 * @return
	 */
	public RationalArray sliceRow(final int i, final int size) {
		final RationalNumber[][] v = new RationalNumber[size][];
		System.arraycopy(values, i, v, 0, size);
		return new RationalArray(v);
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalArray sub(final RationalArray a) {
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
		final StringBuilder b = new StringBuilder();
		b.append("[");
		boolean firstRow = true;
		for (final RationalNumber[] row : values) {
			if (firstRow)
				firstRow = false;
			else
				b.append("; ");
			boolean firstCol = true;
			for (final RationalNumber n : row) {
				if (firstCol)
					firstCol = false;
				else
					b.append(", ");
				b.append(n);
			}
		}
		b.append("]");
		return b.toString();
	}

	/**
	 * @return
	 */
	public RationalNumber trace() {
		final int n = Math.min(values.length, values[0].length);
		RationalNumber t = RationalNumber.ZERO;
		for (int i = 0; i < n; ++i)
			t = t.add(values[i][i]);
		return t;
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