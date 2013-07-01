package org.mmarini.ratio;

/**
 * @author US00852
 * @version $Id: RationalArray.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RationalArray {

	private RationalNumber[][] array;

	/**
	 * @param rank
	 * @return
	 */
	public static RationalArray getIdentity(int rank) {
		RationalArray identity = new RationalArray(rank);
		RationalNumber number1 = new RationalNumber(1);
		for (int i = 0; i < rank; ++i) {
			identity.setValue(i, i, number1);
		}
		return identity;
	}

	/**
	 * @param rank
	 */
	public RationalArray(int rank) {
		this.setArray(new RationalNumber[rank][rank]);
		RationalNumber number0 = new RationalNumber();
		for (int i = 0; i < rank; ++i) {
			for (int j = 0; j < rank; ++j) {
				this.setValue(i, j, number0);
			}
		}
	}

	/**
	 * @return Returns the array.
	 */
	public RationalNumber getValue(int i, int j) {
		return this.getArray()[i][j];
	}

	/**
	 * @return Returns the array.
	 */
	public void setValue(int i, int j, RationalNumber value) {
		this.getArray()[i][j] = value;
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
		int rank = this.getRank();
		if (rank == 1)
			throw new IllegalArgumentException("Complement of one rank array");
		RationalArray complement = new RationalArray(rank - 1);
		if (rank == 2) {
			complement.setValue(0, 0, this.getValue(1 - i, 1 - j));
			return complement;
		}
		for (int ii = 0; ii < rank - 1; ++ii) {
			int iidx = ii;
			if (iidx >= i)
				++iidx;
			for (int jj = 0; jj < rank - 1; ++jj) {
				int jidx = jj;
				if (jidx >= j)
					++jidx;
				complement.setValue(ii, jj, this.getValue(iidx, jidx));
			}
		}
		return complement;
	}

	/**
	 * @return
	 */
	public RationalNumber getDeterminer() {
		int rank = this.getRank();
		if (rank == 1)
			return this.getValue(0, 0);
		else if (rank == 2) {
			RationalNumber det = this.getValue(0, 0).multiply(
					this.getValue(1, 1));
			return det.subtract(this.getValue(0, 1).multiply(
					this.getValue(1, 0)));
		} else {
			RationalNumber det = new RationalNumber();
			for (int i = 0; i < rank; ++i) {
				RationalNumber term = this.complement(0, i).getDeterminer()
						.multiply(this.getValue(0, i));
				if ( (i % 2) == 1)
					term = term.negate();
				det = det.add(term);
			}
			return det;
		}
	}

	/**
	 * @return
	 */
	public RationalArray transpose() {
		int rank = this.getRank();
		RationalArray array = new RationalArray(rank);
		for (int i = 0; i < rank; ++i)
			for (int j = 0; j < rank; ++j)
				array.setValue(j, i, this.getValue(i, j));
		return array;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalArray add(RationalArray a) {
		int rank = this.getRank();
		if (a.getRank() != rank)
			throw new IllegalArgumentException("Add of different rank arrays");
		RationalArray array = new RationalArray(rank);
		for (int i = 0; i < rank; ++i)
			for (int j = 0; j < rank; ++j)
				array.setValue(i, j, this.getValue(i, j).add(a.getValue(i, j)));
		return array;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalArray subtract(RationalArray a) {
		int rank = this.getRank();
		if (a.getRank() != rank)
			throw new IllegalArgumentException(
					"Subtract of different rank arrays");
		RationalArray array = new RationalArray(rank);
		for (int i = 0; i < rank; ++i)
			for (int j = 0; j < rank; ++j)
				array.setValue(i, j, this.getValue(i, j).subtract(
						a.getValue(i, j)));
		return array;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalVector multiply(RationalVector a) {
		int rank = this.getRank();
		if (a.getRank() != rank)
			throw new IllegalArgumentException(
					"Multiply of different rank array and vector");
		RationalVector value = new RationalVector(rank);
		for (int i = 0; i < rank; ++i) {
			RationalNumber temp = new RationalNumber();
			for (int j = 0; j < rank; ++j) {
				RationalNumber term = this.getValue(i, j).multiply(
						a.getValue(j));
				temp = temp.add(term);
			}
			value.setValue(i, temp);
		}
		return value;
	}

	/**
	 * @param a
	 * @return
	 */
	public RationalArray multiply(RationalArray a) {
		int rank = this.getRank();
		if (a.getRank() != rank)
			throw new IllegalArgumentException(
					"Multiply of different rank arrays");
		RationalArray array = new RationalArray(rank);
		for (int i = 0; i < rank; ++i)
			for (int j = 0; j < rank; ++j) {
				RationalNumber value = new RationalNumber();
				for (int k = 0; k < rank; ++k) {
					RationalNumber term = this.getValue(i, k).multiply(
							a.getValue(k, j));
					value = value.add(term);
				}
				array.setValue(i, j, value);
			}
		return array;
	}

	/**
	 * @param scale
	 * @return
	 */
	public RationalArray multiply(RationalNumber scale) {
		int rank = this.getRank();
		RationalArray scaled = new RationalArray(rank);
		for (int i = 0; i < rank; ++i) {
			for (int j = 0; j < rank; ++j) {
				scaled.setValue(i, j, this.getValue(i, j).multiply(scale));
			}
		}
		return scaled;
	}

	/**
	 * @return
	 */
	public RationalArray inverse() {
		RationalNumber det = this.getDeterminer();
		if (det.equals(new RationalNumber())) {
			throw new IllegalArgumentException(
					"Inverse of null determiner array");
		}
		int rank = this.getRank();
		RationalArray inv = new RationalArray(rank);
		for (int i = 0; i < rank; ++i) {
			for (int j = 0; j < rank; ++j) {
				RationalNumber value = this.complement(i, j).getDeterminer();
				value = value.divide(det);
				if ( ( (i + j) % 2) == 1)
					value = value.negate();
				inv.setValue(j, i, value);
			}
		}
		return inv;
	}

	/**
	 * @return
	 */
	public int getRank() {
		return this.getArray().length;
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
	protected RationalNumber[][] getArray() {
		return array;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (! (obj instanceof RationalArray))
			return false;
		RationalArray array = (RationalArray) obj;
		int rank = this.getRank();
		if (rank != array.getRank())
			return false;
		for (int i = 0; i < rank; ++i)
			for (int j = 0; j < rank; ++j)
				if (!this.getValue(i, j).equals(array.getValue(i, j)))
					return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer value = new StringBuffer();
		value.append("[");
		int rank = this.getRank();
		for (int i = 0; i < rank; ++i) {
			if (i > 0)
				value.append(", ");
			value.append("(");
			for (int j = 0; j < rank; ++j) {
				if (j > 0)
					value.append(", ");
				value.append(this.getValue(i, j));
			}
			value.append(")");
		}
		value.append("]");
		return value.toString();
	}
}