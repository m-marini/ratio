package org.mmarini.ratio.processor;

import org.mmarini.ratio.RationalArray;
import org.mmarini.ratio.RationalNumber;

import junit.framework.TestCase;

public class ScalarRationalValueTest extends TestCase {
	private static final RationalNumber VALUE12 = new RationalNumber(1, 2);
	private static final RationalNumber VALUE1 = new RationalNumber(1);
	private static final RationalNumber VALUE3 = new RationalNumber(3);
	private static final RationalNumber VALUE14 = new RationalNumber(1, 4);
	private static final RationalNumber VALUE34 = new RationalNumber(3, 4);
	private static final RationalNumber VALUE316 = new RationalNumber(3, 16);
	private static final RationalNumber VALUE_34 = new RationalNumber(-3, 4);
	private static final RationalArray MULTIPLY = RationalArray.getIdentity(2)
			.multiply(VALUE34);
	private static final RationalArray DIVIDE_ARRAY = new RationalArray(2);
	private static RationalArray RESULT_DIVIDE_ARRAY = null;

	ScalarRationalValue value14;
	ScalarRationalValue value34;
	ArrayRationalValue array;
	ArrayRationalValue array1;

	/**
	 * 
	 */
	public ScalarRationalValueTest() {
		DIVIDE_ARRAY.setValue(0, 0, new RationalNumber(1));
		DIVIDE_ARRAY.setValue(0, 1, new RationalNumber(2));
		DIVIDE_ARRAY.setValue(1, 0, new RationalNumber(3));
		DIVIDE_ARRAY.setValue(1, 1, new RationalNumber(4));
		RESULT_DIVIDE_ARRAY = DIVIDE_ARRAY.inverse().multiply(
				new RationalNumber(3, 4));
	}

	protected void setUp() throws Exception {
		super.setUp();
		value34 = new ScalarRationalValue(VALUE34);
		value14 = new ScalarRationalValue(VALUE14);
		array = new ArrayRationalValue(RationalArray.getIdentity(2));
		array1 = new ArrayRationalValue(DIVIDE_ARRAY);
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.toString()'
	 */
	public void testToString() {
		assertEquals("3/4", value34.toString());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.negate()'
	 */
	public void testNegate() {
		IRationalValue neg = value34.negate();
		assertTrue(neg instanceof ScalarRationalValue);
		assertEquals(VALUE_34, ((ScalarRationalValue) neg).getNumber());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.add(IRationalValue)'
	 */
	public void testAddScalar() throws ProcessException {
		IRationalValue add = value14.add(value34);
		assertTrue(add instanceof ScalarRationalValue);
		assertEquals(VALUE1, ((ScalarRationalValue) add).getNumber());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.add(IRationalValue)'
	 */
	public void testAddArray() {
		try {
			value14.add(array);
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.ADD_SCALAR_ARRAY, e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.subtract(IRationalValue)'
	 */
	public void testSubtractScalar() throws ProcessException {
		IRationalValue sub = value34.subtract(value14);
		assertTrue(sub instanceof ScalarRationalValue);
		assertEquals(VALUE12, ((ScalarRationalValue) sub).getNumber());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.subtract(IRationalValue)'
	 */
	public void testSubtractArray() {
		try {
			value14.subtract(array);
			fail("Exception not thrown");
		} catch (ProcessException e) {
			assertEquals(ProcessException.SUB_SCALAR_ARRAY, e.getMessage());
		}
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.multiply(IRationalValue)'
	 */
	public void testMultiplyScalar() throws ProcessException {
		IRationalValue mul = value34.multiply(value14);
		assertTrue(mul instanceof ScalarRationalValue);
		assertEquals(VALUE316, ((ScalarRationalValue) mul).getNumber());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.multiply(IRationalValue)'
	 */
	public void testMultiplyArray() throws ProcessException {
		IRationalValue mul = value34.multiply(array);
		assertTrue(mul instanceof ArrayRationalValue);
		assertEquals(MULTIPLY, ((ArrayRationalValue) mul).getArray());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.divide(IRationalValue)'
	 */
	public void testDivideScalar() throws ProcessException {
		IRationalValue div = value34.divide(value14);
		assertTrue(div instanceof ScalarRationalValue);
		assertEquals(VALUE3, ((ScalarRationalValue) div).getNumber());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.divide(IRationalValue)'
	 */
	public void testDivideArray() throws ProcessException {
		IRationalValue div = value34.divide(array1);
		assertTrue(div instanceof ArrayRationalValue);
		assertEquals(RESULT_DIVIDE_ARRAY, ((ArrayRationalValue) div).getArray());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.transpose()'
	 */
	public void testTranspose() {
		IRationalValue tran = value34.transpose();
		assertTrue(tran instanceof ScalarRationalValue);
		assertEquals(VALUE34, ((ScalarRationalValue) tran).getNumber());
	}

	/*
	 * Test method for
	 * 'org.mmarini.ratio.processor.ScalarRationalValue.getNumber()'
	 */
	public void testGetNumber() {
		assertEquals(VALUE34, value34.getNumber());
	}

}
