package org.mmarini.ratio;

/**
 * @author US00852
 * @version $Id: RationalArray.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class RationalArray {
	private RationalNumber[][] array;

	/**
	 * @param rank
	 * @return
	 */
	public static RationalArray getIdentity(int rank) {
		RationalArray identity = new RationalArray(rank);
		for (int i = 0; i < rank; ++i) {
			identity.setValue(i, i, RationalNumber.ONE);
		}
		return identity;
	}

	/**
	 * @param rank
	 */
	public RationalArray(int rank) {
		this(rank, rank);
	}

	/**
	 * @param rows
	 * @param cols
	 */
	public RationalArray(int rows, int cols) {
		setArray(new RationalNumber[rows][cols]);
		RationalNumber number0 = new RationalNumber();
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				setValue(i, j, number0);
			}
		}
	}

	/**
	 * @param array
	 * @return
	 */
	public boolean isSameDimensions(RationalArray array) {
		if (getRowsCount() != array.getRowsCount())
			return false;
		if (getColumnsCount() != array.getColumnsCount())
			return false;
		return true;
	}

	/**
	 * @return
	 */
	public boolean isSquare() {
		return getRowsCount() == getColumnsCount();
	}

	/**
	 * @param array
	 * @return
	 */
	public RationalNumber multiplyScalar(RationalArray array) {
		int nt = getColumnsCount();
		if (nt != array.getRowsCount())
			throw new IllegalArgumentException(
					"Scalar product of different size arrays");
		int nr = getRowsCount();
		if (nr != 1)
			throw new IllegalArgumentException(
					"Scalar product a not one row array");
		int nc = array.getColumnsCount();
		if (nc != 1)
			throw new IllegalArgumentException(
					"Scalar product a not one column array");
		RationalNumber value = new RationalNumber();
		for (int k = 0; k < nt; ++k) {
			RationalNumber term = getValue(0, k).multiply(array.getValue(k, 0));
			value = value.add(term);
		}
		return value;
	}

	/**
	 * Ritorna la matrice complementare
	 * 
	 * @param i
	 *            indice della riga
	 * @param j
	 *            indice della colonna
	 * @return la matrice complementare
	 */
	public RationalArray complement(int i, int j) {
		int nr = getRowsCount();
		if (nr == 1)
			throw new IllegalArgumentException("Complement of one row array");
		int nc = getColumnsCount();
		if (nc == 1)
			throw new IllegalArgumentException(
					"Complement of one columns array");
		RationalArray complement = new RationalArray(nr - 1, nc - 1);
		for (int ii = 0; ii < nr - 1; ++ii) {
			int iidx = ii;
			if (iidx >= i)
				++iidx;
			for (int jj = 0; jj < nc - 1; ++jj) {
				int jidx = jj;
				if (jidx >= j)
					++jidx;
				complement.setValue(ii, jj, getValue(iidx, jidx));
			}
		}
		return complement;
	}

	/**
	 * @return
	 */
	public RationalNumber getDeterminer() {
		if (!isSquare())
			throw new IllegalArgumentException("not square array");
		ArrayOperationStrategy operationStrategy = createAlgorithm();
		return operationStrategy.getDeterminer(this);
	}

	/**
	 * @return
	 */
	private ArrayOperationStrategy createAlgorithm() {
		return new GaussAlgorithm();
	}

	/**
	 * @return
	 */
	public RationalArray transpose() {
		int nr = getRowsCount();
		int nc = getColumnsCount();
		RationalArray result = new RationalArray(nc, nr);
		for (int i = 0; i < nr; ++i)
			for (int j = 0; j < nc; ++j)
				result.setValue(j, i, getValue(i, j));
		return result;
	}

	/**
	 * @param array
	 * @return
	 */
	public RationalArray add(RationalArray array) {
		if (!isSameDimensions(array))
			throw new IllegalArgumentException("different size arrays addition");
		int nr = getRowsCount();
		int nc = getColumnsCount();
		RationalArray result = new RationalArray(nr, nc);
		for (int i = 0; i < nr; ++i)
			for (int j = 0; j < nc; ++j)
				result.setValue(i, j, getValue(i, j).add(array.getValue(i, j)));
		return result;
	}

	/**
	 * @param array
	 * @return
	 */
	public RationalArray subtract(RationalArray array) {
		if (!isSameDimensions(array))
			throw new IllegalArgumentException(
					"different size arrays subtraction");
		int nr = getRowsCount();
		int nc = getColumnsCount();
		RationalArray result = new RationalArray(nr, nc);
		for (int i = 0; i < nr; ++i)
			for (int j = 0; j < nc; ++j)
				result.setValue(i, j, getValue(i, j).subtract(
						array.getValue(i, j)));
		return result;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalArray multiply(RationalArray a) {
		int nt = getColumnsCount();
		if (nt != a.getRowsCount())
			throw new IllegalArgumentException(
					"different size arrays multiplication");
		int nr = getRowsCount();
		int nc = a.getColumnsCount();
		RationalArray array = new RationalArray(nr, nc);
		for (int i = 0; i < nr; ++i) {
			for (int j = 0; j < nc; ++j) {
				RationalNumber value = new RationalNumber();
				for (int k = 0; k < nt; ++k) {
					RationalNumber term = getValue(i, k).multiply(
							a.getValue(k, j));
					value = value.add(term);
				}
				array.setValue(i, j, value);
			}
		}
		return array;
	}

	/**
	 * @param scale
	 * @return
	 */
	public RationalArray multiply(RationalNumber scale) {
		int nr = getRowsCount();
		int nc = getColumnsCount();
		RationalArray scaled = new RationalArray(nr, nc);
		for (int i = 0; i < nr; ++i) {
			for (int j = 0; j < nc; ++j) {
				scaled.setValue(i, j, getValue(i, j).multiply(scale));
			}
		}
		return scaled;
	}

	/**
	 * @return
	 */
	public RationalArray inverse() {
		ArrayOperationStrategy operationStrategy = createAlgorithm();
		return operationStrategy.inverse(this);
	}

	/**
	 * @return
	 */
	public int getRowsCount() {
		return getArray().length;
	}

	/**
	 * @return
	 */
	public int getColumnsCount() {
		return getArray()[0].length;
	}

	/**
	 * @param array
	 *            The array to set.
	 */
	protected void setArray(RationalNumber[][] array) {
		this.array = array;
	}

	/**
	 * @return Returns the array.
	 */
	public RationalNumber[][] getArray() {
		return array;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RationalArray))
			return false;
		RationalArray array = (RationalArray) obj;
		int nr = getRowsCount();
		if (nr != array.getRowsCount())
			return false;
		int nc = getColumnsCount();
		if (nc != array.getColumnsCount())
			return false;
		for (int i = 0; i < nr; ++i)
			for (int j = 0; j < nc; ++j)
				if (!getValue(i, j).equals(array.getValue(i, j)))
					return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer value = new StringBuffer();
		value.append("[");
		int nr = getRowsCount();
		int nc = getColumnsCount();
		for (int i = 0; i < nr; ++i) {
			if (i > 0)
				value.append(", ");
			value.append("(");
			for (int j = 0; j < nc; ++j) {
				if (j > 0)
					value.append(", ");
				value.append(getValue(i, j));
			}
			value.append(")");
		}
		value.append("]");
		return value.toString();
	}

	/**
	 * @return
	 */
	public RationalArray negate() {
		int nr = getRowsCount();
		int nc = getColumnsCount();
		RationalArray neg = new RationalArray(nr, nc);
		for (int i = 0; i < nr; ++i) {
			for (int j = 0; j < nc; ++j) {
				RationalNumber value = getValue(i, j).negate();
				neg.setValue(i, j, value);
			}
		}
		return neg;
	}

	/**
	 * @return Returns the array.
	 */
	public RationalNumber getValue(int i, int j) {
		return getArray()[i][j];
	}

	/**
	 * @return Returns the array.
	 */
	public void setValue(int i, int j, RationalNumber value) {
		getArray()[i][j] = value;
	}
}