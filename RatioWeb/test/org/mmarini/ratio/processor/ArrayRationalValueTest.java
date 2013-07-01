package org.mmarini.ratio.processor;

import org.mmarini.ratio.RationalArray;
import org.mmarini.ratio.RationalNumber;

import junit.framework.TestCase;

public class ArrayRationalValueTest extends TestCase {
	RationalArray array = new RationalArray(2, 3);
	RationalArray expected;
	ArrayRationalValue arrayValue;

	public ArrayRationalValueTest(String name) {
		super(name);
		array.setValue(0, 0, new RationalNumber(1));
		array.setValue(0, 1, new RationalNumber(2));
		array.setValue(0, 2, new RationalNumber(3));
		array.setValue(1, 0, new RationalNumber(4));
		array.setValue(1, 1, new RationalNumber(5));
		array.setValue(1, 2, new RationalNumber(6));
	}

	protected void setUp() throws Exception {
		super.setUp();
		arrayValue = new ArrayRationalValue(array);
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.toString()'
	 */
	public void testToString() {
		assertEquals("[(1, 2, 3), (4, 5, 6)]", arrayValue.toString());
	}

	/*
	 * Test method for 'org.mmarini.ratio.processor.ArrayRationalValue.negate()'
	 */
	public void testNegate() {
		expected = new RationalArray(2, 3);
		expected.setValue(0, 0, new RationalNumber(-1));
		expected.setValue(0, 1, new RationalNumber(-2));
		expected.setValue(0, 2, new RationalNumber(-3));
		expected.setValue(1, 0, new RationalNumber(-4));
		expected.setValue(1, 1, new RationalNumber(-5));
		expected.setValue(1, 2, new RationalNumber(-6));

		IRationalValue res = arrayValue.negate();
		assertTrue(res instanceof ArrayRationalValue);
		assertEquals(expected, ((ArrayRationalValue) res).getArray());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.add(IRationalValue)'
	 */
	public void testAddScalar() {
		try {
			arrayValue.add(new ScalarRationalValue(new RationalNumber(1)));
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.ADD_ARRAY_SCALAR, e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.add(IRationalValue)'
	 */
	public void testAddArray() throws ProcessException {
		expected = new RationalArray(2, 3);
		expected.setValue(0, 0, new RationalNumber(2));
		expected.setValue(0, 1, new RationalNumber(4));
		expected.setValue(0, 2, new RationalNumber(6));
		expected.setValue(1, 0, new RationalNumber(8));
		expected.setValue(1, 1, new RationalNumber(10));
		expected.setValue(1, 2, new RationalNumber(12));

		IRationalValue res = arrayValue.add(arrayValue);
		assertTrue(res instanceof ArrayRationalValue);
		assertEquals(expected, ((ArrayRationalValue) res).getArray());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.add(IRationalValue)'
	 */
	public void testAddBadArray() {
		try {
			arrayValue.add(new ArrayRationalValue(new RationalArray(2, 2)));
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.ILLEGAL_ARGUMENT, e.getMessage());
		}
		try {
			arrayValue.add(new ArrayRationalValue(new RationalArray(3, 2)));
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.ILLEGAL_ARGUMENT, e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.subtract(IRationalValue)'
	 */
	public void testSubtractScalar() {
		try {
			arrayValue.subtract(new ScalarRationalValue(new RationalNumber(1)));
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.SUB_ARRAY_SCALAR, e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.subtract(IRationalValue)'
	 */
	public void testSubtractArray() throws ProcessException {
		expected = new RationalArray(2, 3);

		IRationalValue res = arrayValue.subtract(arrayValue);
		assertTrue(res instanceof ArrayRationalValue);
		assertEquals(expected, ((ArrayRationalValue) res).getArray());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.subtract(IRationalValue)'
	 */
	public void testSubtractBadArray() {
		try {
			arrayValue
					.subtract(new ArrayRationalValue(new RationalArray(2, 2)));
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.ILLEGAL_ARGUMENT, e.getMessage());
		}
		try {
			arrayValue
					.subtract(new ArrayRationalValue(new RationalArray(3, 2)));
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.ILLEGAL_ARGUMENT, e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.multiply(IRationalValue)'
	 */
	public void testMultiplyScalar() throws ProcessException {
		expected = new RationalArray(2, 3);
		expected.setValue(0, 0, new RationalNumber(2));
		expected.setValue(0, 1, new RationalNumber(4));
		expected.setValue(0, 2, new RationalNumber(6));
		expected.setValue(1, 0, new RationalNumber(8));
		expected.setValue(1, 1, new RationalNumber(10));
		expected.setValue(1, 2, new RationalNumber(12));

		IRationalValue res = arrayValue.multiply(new ScalarRationalValue(
				new RationalNumber(2)));
		assertTrue(res instanceof ArrayRationalValue);
		assertEquals(expected, ((ArrayRationalValue) res).getArray());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.multiply(IRationalValue)'
	 */
	public void testMultiplyArray() throws ProcessException {
		expected = new RationalArray(2, 2);
		expected.setValue(0, 0, new RationalNumber(14));
		expected.setValue(0, 1, new RationalNumber(32));
		expected.setValue(1, 0, new RationalNumber(32));
		expected.setValue(1, 1, new RationalNumber(77));

		IRationalValue res = arrayValue.multiply(arrayValue.transpose());
		assertTrue(res instanceof ArrayRationalValue);
		assertEquals(expected, ((ArrayRationalValue) res).getArray());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.multiply(IRationalValue)'
	 */
	public void testMultiplyBadArray() {
		try {
			arrayValue
					.multiply(new ArrayRationalValue(new RationalArray(2, 3)));
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.ILLEGAL_ARGUMENT, e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.divide(IRationalValue)'
	 */
	public void testDivideScalar() throws ProcessException {
		expected = new RationalArray(2, 3);
		expected.setValue(0, 0, new RationalNumber(2));
		expected.setValue(0, 1, new RationalNumber(4));
		expected.setValue(0, 2, new RationalNumber(6));
		expected.setValue(1, 0, new RationalNumber(8));
		expected.setValue(1, 1, new RationalNumber(10));
		expected.setValue(1, 2, new RationalNumber(12));

		IRationalValue res = arrayValue.divide(new ScalarRationalValue(
				new RationalNumber(1, 2)));
		assertTrue(res instanceof ArrayRationalValue);
		assertEquals(expected, ((ArrayRationalValue) res).getArray());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.divide(IRationalValue)'
	 */
	public void testDivideArray() throws ProcessException {
		RationalArray array = new RationalArray(2);
		array.setValue(0, 0, new RationalNumber(1));
		array.setValue(0, 1, new RationalNumber(2));
		array.setValue(1, 0, new RationalNumber(3));
		array.setValue(1, 1, new RationalNumber(4));
		arrayValue = new ArrayRationalValue(array);
		IRationalValue res = arrayValue.divide(arrayValue);
		assertTrue(res instanceof ArrayRationalValue);
		assertEquals(RationalArray.getIdentity(2), ((ArrayRationalValue) res)
				.getArray());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.divide(IRationalValue)'
	 */
	public void testDivideBadArray() {
		try {
			arrayValue.divide(new ArrayRationalValue(new RationalArray(2, 3)));
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.ILLEGAL_ARGUMENT, e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.divide(IRationalValue)'
	 */
	public void testDivideByNullArray() {
		RationalArray array = new RationalArray(2);
		array.setValue(0, 0, new RationalNumber(1));
		array.setValue(0, 1, new RationalNumber(2));
		array.setValue(1, 0, new RationalNumber(2));
		array.setValue(1, 1, new RationalNumber(4));
		arrayValue = new ArrayRationalValue(array);
		try {
			arrayValue.divide(arrayValue);
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.ILLEGAL_ARGUMENT, e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.transpose()'
	 */
	public void testTranspose() {
		expected = new RationalArray(3, 2);
		expected.setValue(0, 0, new RationalNumber(1));
		expected.setValue(1, 0, new RationalNumber(2));
		expected.setValue(2, 0, new RationalNumber(3));
		expected.setValue(0, 1, new RationalNumber(4));
		expected.setValue(1, 1, new RationalNumber(5));
		expected.setValue(2, 1, new RationalNumber(6));

		ArrayRationalValue val = new ArrayRationalValue(array);
		IRationalValue res = val.transpose();
		assertTrue(res instanceof ArrayRationalValue);
		assertEquals(expected, ((ArrayRationalValue) res).getArray());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ArrayRationalValue.getArray()'
	 */
	public void testGetArray() {
		expected = new RationalArray(2, 3);
		expected.setValue(0, 0, new RationalNumber(1));
		expected.setValue(0, 1, new RationalNumber(2));
		expected.setValue(0, 2, new RationalNumber(3));
		expected.setValue(1, 0, new RationalNumber(4));
		expected.setValue(1, 1, new RationalNumber(5));
		expected.setValue(1, 2, new RationalNumber(6));

		ArrayRationalValue val = new ArrayRationalValue(array);
		assertEquals(expected, val.getArray());
	}
}
