package org.mmarini.ratio;

/**
 * 
 * @author US00852
 * @version $Id: LaplaceAlgorithm.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class LaplaceAlgorithm implements ArrayOperationStrategy {
	/**
	 * 
	 * @see org.mmarini.ratio.ArrayOperationStrategy#getDeterminer(org.mmarini.ratio.RationalArray)
	 */
	public RationalNumber getDeterminer(RationalArray array) {
		int rank = array.getRowsCount();
		if (rank == 1)
			return array.getValue(0, 0);
		else if (rank == 2) {
			RationalNumber det = array.getValue(0, 0).multiply(
					array.getValue(1, 1));
			return det.subtract(array.getValue(0, 1).multiply(
					array.getValue(1, 0)));
		} else {
			RationalNumber det = new RationalNumber();
			for (int i = 0; i < rank; ++i) {
				RationalNumber term = array.complement(0, i).getDeterminer()
						.multiply(array.getValue(0, i));
				if ((i % 2) == 1)
					term = term.negate();
				det = det.add(term);
			}
			return det;
		}
	}

	/**
	 * 
	 * @see org.mmarini.ratio.ArrayOperationStrategy#inverse(org.mmarini.ratio.RationalArray)
	 */
	public RationalArray inverse(RationalArray array) {
		RationalNumber det = getDeterminer(array);
		if (det.isNull()) {
			throw new IllegalArgumentException(
					"inverse of a null determiner array");
		}
		int nr = array.getRowsCount();
		RationalArray inv = new RationalArray(nr, nr);
		for (int i = 0; i < nr; ++i) {
			for (int j = 0; j < nr; ++j) {
				RationalNumber value = array.complement(i, j).getDeterminer();
				value = value.divide(det);
				if (((i + j) % 2) == 1)
					value = value.negate();
				inv.setValue(j, i, value);
			}
		}
		return inv;
	}

}
