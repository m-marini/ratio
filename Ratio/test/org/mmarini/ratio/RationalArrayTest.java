/*
 * Created on Jul 12, 2004
 */
package org.mmarini.ratio;

import org.mmarini.ratio.RationalArray;
import org.mmarini.ratio.RationalNumber;
import org.mmarini.ratio.RationalVector;

import junit.framework.TestCase;

/**
 * @author US00852
 * @version $Id: RationalArrayTest.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class RationalArrayTest extends TestCase {
	RationalArray array1 = RationalArray.getIdentity(3);
	RationalArray refArray2;
	RationalArray transArray;
	RationalArray addArray;
	RationalArray mulArray;
	RationalArray refArray3;
	RationalArray complement200;
	RationalArray complement201;
	RationalArray complement210;
	RationalArray complement300;
	RationalArray complement301;
	RationalArray complement310;

	RationalVector refVector;
	RationalVector prodVector;

	String stringValue = "[(1, 2), (3, 4)]";

	RationalNumber[][] refNumbers;
	RationalNumber number0 = new RationalNumber();
	RationalNumber number1 = new RationalNumber(1);
	RationalNumber determiner2 = new RationalNumber(-2);
	RationalNumber determiner3 = new RationalNumber(-24);

	public final void testGetIdentity() {
		assertNotNull(array1);
		for (int i = 0; i < array1.getRank(); ++i) {
			for (int j = 0; j < array1.getRank(); ++j) {
				String msg = "Value[" + i + "][" + j + "]";
				RationalNumber value = array1.getValue(i, j);
				assertNotNull(msg, value);
				if (i == j)
					assertEquals(msg, number1, value);
				else
					assertEquals(msg, number0, value);
			}
		}
	}

	public final void testRationalArray() {
		RationalArray array0 = new RationalArray(3);
		for (int i = 0; i < array0.getRank(); ++i) {
			for (int j = 0; j < array0.getRank(); ++j) {
				String msg = "Value[" + i + "][" + j + "]";
				RationalNumber value = array0.getValue(i, j);
				assertNotNull(msg, value);
				assertEquals(msg, number0, value);
			}
		}
	}

	public final void testSetGetValue() {
		RationalArray array = new RationalArray(refNumbers.length);
		for (int i = 0; i < array.getRank(); ++i) {
			for (int j = 0; j < array.getRank(); ++j) {
				RationalNumber refValue = refNumbers[i][j];
				array.setValue(i, j, refValue);
			}
		}
		for (int i = 0; i < array.getRank(); ++i) {
			for (int j = 0; j < array.getRank(); ++j) {
				String msg = "Value[" + i + "][" + j + "]";
				RationalNumber refValue = refNumbers[i][j];
				RationalNumber value = array.getValue(i, j);
				assertNotNull(msg, value);
				assertEquals(msg, refValue, value);
			}
		}
	}

	public final void testToString() {
		String value = refArray2.toString();
		assertEquals(stringValue, value);
	}

	public final void testGetDeterminer() {
		assertEquals(determiner2, refArray2.getDeterminer());
		assertEquals(determiner3, refArray3.getDeterminer());
	}

	public final void testGetTranspose() {
		RationalArray trans = refArray2.transpose();
		assertEquals(transArray, trans);
	}

	public final void testGetSum() {
		RationalArray trans = refArray2.add(refArray2);
		assertEquals(addArray, trans);
	}

	public final void testGetDifference() {
		RationalArray trans = refArray2.subtract(refArray2);
		assertEquals(new RationalArray(2), trans);
	}

	public final void testGetComplement() {
		RationalArray value = refArray2.complement(0, 0);
		assertEquals(complement200, value);
		value = refArray2.complement(0, 1);
		assertEquals(complement201, value);
		value = refArray2.complement(1, 0);
		assertEquals(complement210, value);
		value = refArray3.complement(0, 0);
		assertEquals(complement300, value);
		value = refArray3.complement(0, 1);
		assertEquals(complement301, value);
		value = refArray3.complement(1, 0);
		assertEquals(complement310, value);
	}

	public final void testGetProductRationalArray() {
		RationalArray value = refArray2.multiply(refArray2);
		assertEquals(mulArray, value);
	}

	public final void testGetProductRationalVector() {
		RationalVector value = refArray3.multiply(refVector);
		assertEquals(prodVector, value);
	}

	public final void testGetProductRationalNumber() {
		RationalArray value = refArray2.multiply(new RationalNumber(2));
		assertEquals(addArray, value);
	}

	public final void testGetInverse() {
		RationalArray value = refArray2.inverse();
		value = refArray2.multiply(value);
		assertEquals(RationalArray.getIdentity(2), value);

		value = refArray3.inverse();
		value = refArray3.multiply(value);
		assertEquals(RationalArray.getIdentity(3), value);
	}

	public final void testEquals() {
		RationalArray arrayA = new RationalArray(3);
		RationalArray arrayB = new RationalArray(3);
		RationalArray arrayC = new RationalArray(4);
		assertFalse(arrayA.equals(null));
		assertTrue(arrayA.equals(arrayB));
		assertTrue(arrayB.equals(arrayA));
		assertFalse(arrayA.equals(arrayC));
		assertFalse(arrayC.equals(arrayA));
	}

	public final void testGetSize() {
		assertEquals(2, refArray2.getRank());
		assertEquals(3, refArray3.getRank());
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		int size = 2;
		refNumbers = new RationalNumber[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				RationalNumber refValue = new RationalNumber(i * 10 + j);
				refNumbers[i][j] = refValue;
			}
		}

		refArray2 = new RationalArray(size);
		refArray2.setValue(0, 0, new RationalNumber(1));
		refArray2.setValue(0, 1, new RationalNumber(2));
		refArray2.setValue(1, 0, new RationalNumber(3));
		refArray2.setValue(1, 1, new RationalNumber(4));

		transArray = new RationalArray(size);
		transArray.setValue(0, 0, new RationalNumber(1));
		transArray.setValue(0, 1, new RationalNumber(3));
		transArray.setValue(1, 0, new RationalNumber(2));
		transArray.setValue(1, 1, new RationalNumber(4));

		addArray = new RationalArray(size);
		addArray.setValue(0, 0, new RationalNumber(2));
		addArray.setValue(0, 1, new RationalNumber(4));
		addArray.setValue(1, 0, new RationalNumber(6));
		addArray.setValue(1, 1, new RationalNumber(8));

		mulArray = new RationalArray(size);
		mulArray.setValue(0, 0, new RationalNumber(7));
		mulArray.setValue(0, 1, new RationalNumber(10));
		mulArray.setValue(1, 0, new RationalNumber(15));
		mulArray.setValue(1, 1, new RationalNumber(22));

		refArray3 = new RationalArray(3);
		refArray3.setValue(0, 0, new RationalNumber(1));
		refArray3.setValue(0, 1, new RationalNumber(2));
		refArray3.setValue(0, 2, new RationalNumber(3));
		refArray3.setValue(1, 0, new RationalNumber(0));
		refArray3.setValue(1, 1, new RationalNumber(5));
		refArray3.setValue(1, 2, new RationalNumber(6));
		refArray3.setValue(2, 0, new RationalNumber(7));
		refArray3.setValue(2, 1, new RationalNumber(8));
		refArray3.setValue(2, 2, new RationalNumber(9));

		complement200 = new RationalArray(1);
		complement200.setValue(0, 0, new RationalNumber(4));

		complement201 = new RationalArray(1);
		complement201.setValue(0, 0, new RationalNumber(3));

		complement210 = new RationalArray(1);
		complement210.setValue(0, 0, new RationalNumber(2));

		complement300 = new RationalArray(2);
		complement300.setValue(0, 0, new RationalNumber(5));
		complement300.setValue(0, 1, new RationalNumber(6));
		complement300.setValue(1, 0, new RationalNumber(8));
		complement300.setValue(1, 1, new RationalNumber(9));

		complement301 = new RationalArray(2);
		complement301.setValue(0, 0, new RationalNumber(0));
		complement301.setValue(0, 1, new RationalNumber(6));
		complement301.setValue(1, 0, new RationalNumber(7));
		complement301.setValue(1, 1, new RationalNumber(9));

		complement310 = new RationalArray(2);
		complement310.setValue(0, 0, new RationalNumber(2));
		complement310.setValue(0, 1, new RationalNumber(3));
		complement310.setValue(1, 0, new RationalNumber(8));
		complement310.setValue(1, 1, new RationalNumber(9));

		refVector = new RationalVector(3);
		refVector.setValue(0, new RationalNumber());
		refVector.setValue(1, new RationalNumber(1));
		refVector.setValue(2, new RationalNumber(2));

		prodVector = new RationalVector(3);
		prodVector.setValue(0, new RationalNumber(8));
		prodVector.setValue(1, new RationalNumber(17));
		prodVector.setValue(2, new RationalNumber(26));
	}
}
