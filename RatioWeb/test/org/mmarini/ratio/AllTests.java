/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author US00852
 * @version $Id: AllTests.java,v 1.2 2006/01/31 22:31:30 marco Exp $
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for it.mmarini.ratio");
		//$JUnit-BEGIN$
		suite.addTestSuite(RationalArrayTest.class);
		suite.addTestSuite(EratosteneTest.class);
		suite.addTestSuite(RationalNumberTest.class);
		suite.addTestSuite(FactorizerTest.class);
		suite.addTestSuite(PoweredNumberTest.class);
		//$JUnit-END$
		return suite;
	}
}
