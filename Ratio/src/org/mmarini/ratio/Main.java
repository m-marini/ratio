package org.mmarini.ratio;

/**
 * @author US00852
 * @version $Id: Main.java,v 1.2 2006/01/15 12:21:17 marco Exp $
 */
public class Main {
	public static void main(String[] arg) throws Exception {
		RationalArray array = new RationalArray(2);
		array.setValue(0, 0, new RationalNumber(1));
		array.setValue(0, 1, new RationalNumber(2));
		array.setValue(1, 0, new RationalNumber(3));
		array.setValue(1, 1, new RationalNumber(4));

		RationalVector vector = new RationalVector(2);
		vector.setValue(0, new RationalNumber(1));
		vector.setValue(1, new RationalNumber(1));
		
		RationalArray inverse = array.inverse();
		
		System.out.println(array);
		System.out.println(inverse);
		System.out.println(vector);
		System.out.println(inverse.multiply(vector));
	}
}
