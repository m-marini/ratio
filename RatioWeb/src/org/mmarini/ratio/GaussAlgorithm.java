package org.mmarini.ratio;

/**
 * 
 * @author US00852
 * @version $Id: GaussAlgorithm.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class GaussAlgorithm implements ArrayOperationStrategy {
	private RationalArray gaussArray;
	private RationalNumber determiner;

	/**
	 * 
	 * @see org.mmarini.ratio.ArrayOperationStrategy#getDeterminer(org.mmarini.ratio.RationalArray)
	 */
	public RationalNumber getDeterminer(RationalArray array) {
		int nr = array.getRowsCount();
		int nc = array.getColumnsCount();
		RationalArray gaussArray = new RationalArray(nr, nc);
		for (int i = 0; i < nr; ++i) {
			for (int j = 0; j < nc; ++j) {
				gaussArray.setValue(i, j, array.getValue(i, j));
			}
		}
		setGaussArray(gaussArray);
		process();
		if (!isTriangular())
			return RationalNumber.ZERO;
		return getDeterminer();
	}

	/**
	 * 
	 * @return
	 */
	private boolean isTriangular() {
		RationalArray array = getGaussArray();
		int m = array.getRowsCount();
		int n = array.getColumnsCount();
		for (int i = 0; i < m && i < n; ++i) {
			if (RationalNumber.ZERO.equals(array.getValue(i, i)))
				return false;
		}
		return true;
	}

	/**
	 * 
	 */
	private void process() {
		RationalArray array = getGaussArray();
		int m = array.getRowsCount();
		int n = array.getColumnsCount();
		int i = 0;
		int j = 0;
		while (i < m && j < n) {
			// Find pivot in column j, starting in row i:
			RationalNumber maxVal = array.getValue(i, j);
			RationalNumber absMaxVal = maxVal.absolute();
			int maxIdx = i;
			for (int k = i + 1; k < m; ++k) {
				RationalNumber val = array.getValue(k, j);
				RationalNumber absVal = val.absolute();
				if (absVal.compareTo(absMaxVal) > 0) {
					maxVal = val;
					absMaxVal = absVal;
					maxIdx = k;
				}
			}
			if (!maxVal.equals(RationalNumber.ZERO)) {
				switchRows(i, maxIdx);
				// Now A[i, j] will contain the same value as max_val
				divideRow(i, maxVal);
				// Now A[i, j] will have the value 1
				for (int u = 0; u < m; ++u) {
					if (u != i) {
						multiplySubtractRows(array.getValue(u, j), i, u);
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
	 * subtract value * row i from row u
	 * 
	 * @param value
	 * @param i
	 * @param u
	 */
	private void multiplySubtractRows(RationalNumber value, int i, int u) {
		RationalArray array = getGaussArray();
		int n = array.getColumnsCount();
		for (int j = 0; j < n; ++j) {
			array.setValue(u, j, array.getValue(u, j).subtract(
					value.multiply(array.getValue(i, j))));
		}
	}

	/**
	 * @param idx
	 * @param value
	 */
	private void divideRow(int idx, RationalNumber value) {
		RationalArray array = getGaussArray();
		int n = array.getColumnsCount();
		for (int j = 0; j < n; ++j) {
			array.setValue(idx, j, array.getValue(idx, j).divide(value));
		}
		setDeterminer(getDeterminer().multiply(value));
	}

	/**
	 * 
	 * @param idx0
	 * @param idx1
	 */
	private void switchRows(int idx0, int idx1) {
		if (idx0 == idx1)
			return;
		RationalArray array = getGaussArray();
		int n = array.getColumnsCount();
		for (int j = 0; j < n; ++j) {
			RationalNumber tmp = array.getValue(idx0, j);
			array.setValue(idx0, j, array.getValue(idx1, j));
			array.setValue(idx1, j, tmp);
		}
		setDeterminer(getDeterminer().negate());
	}

	/**
	 * 
	 * @see org.mmarini.ratio.ArrayOperationStrategy#inverse(org.mmarini.ratio.RationalArray)
	 */
	public RationalArray inverse(RationalArray array) {
		int nr = array.getRowsCount();
		int nc = array.getColumnsCount();
		int nc2 = nc + nc;
		RationalArray gaussArray = new RationalArray(nr, nc2);
		for (int i = 0; i < nr; ++i) {
			for (int j = 0; j < nc2; ++j) {
				if (j < nc)
					gaussArray.setValue(i, j, array.getValue(i, j));
				else if (i == j - nc)
					gaussArray.setValue(i, j, RationalNumber.ONE);
				else
					gaussArray.setValue(i, j, RationalNumber.ZERO);
			}
		}
		setGaussArray(gaussArray);
		process();
		if (!isTriangular())
			throw new IllegalArgumentException(
					"inverse of a null determiner array");

		RationalArray inverse = new RationalArray(nr, nc);
		for (int i = 0; i < nr; ++i) {
			for (int j = 0; j < nc; ++j) {
				inverse.setValue(i, j, gaussArray.getValue(i, j + nc));
			}
		}
		return inverse;
	}

	/**
	 * @return the gaussArray
	 */
	private RationalArray getGaussArray() {
		return gaussArray;
	}

	/**
	 * @param gaussArray
	 *            the gaussArray to set
	 */
	private void setGaussArray(RationalArray gaussArray) {
		this.gaussArray = gaussArray;
		setDeterminer(RationalNumber.ONE);
	}

	/**
	 * @return the lambda
	 */
	private RationalNumber getDeterminer() {
		return determiner;
	}

	/**
	 * @param lambda
	 *            the lambda to set
	 */
	private void setDeterminer(RationalNumber lambda) {
		this.determiner = lambda;
	}

}
