/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import java.util.List;

import org.mmarini.ratio.Factorizer;

import junit.framework.TestCase;

/**
 * @author US00852
 * @version $Id: FactorizerTest.java,v 1.1 2005/02/10 22:29:26 marco Exp $
 */
public class FactorizerTest extends TestCase {

	public final void testGetInstance() {
		assertNotNull(Factorizer.getInstance());
	}

	public final void testCreateFactors() {
		List list = Factorizer.getInstance().createFactors(0);
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("0^1", list.get(0).toString());

		list = Factorizer.getInstance().createFactors(1);
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("1^1", list.get(0).toString());

		list = Factorizer.getInstance().createFactors(900);
		assertNotNull(list);
		assertEquals(3, list.size());
		assertEquals("2^2", list.get(0).toString());
		assertEquals("3^2", list.get(1).toString());
		assertEquals("5^2", list.get(2).toString());

		list = Factorizer.getInstance().createFactors(2200);
		assertNotNull(list);
		assertEquals(3, list.size());
		assertEquals("2^3", list.get(0).toString());
		assertEquals("5^2", list.get(1).toString());
		assertEquals("11^1", list.get(2).toString());
	}
	
	public final void testGetMCD() {
		int mcd = Factorizer.getInstance().getMCD(10, 9);
		assertEquals(1, mcd);
		mcd = Factorizer.getInstance().getMCD(10, 4);
		assertEquals(2, mcd);
		mcd = Factorizer.getInstance().getMCD(20, 40);
		assertEquals(20, mcd);
		mcd = Factorizer.getInstance().getMCD(60, 100);
		assertEquals(20, mcd);
	}
}
