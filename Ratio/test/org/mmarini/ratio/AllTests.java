/*
 * Created on Jul 11, 2004
 */
package org.mmarini.ratio;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author US00852
 * @version $Id: AllTests.java,v 1.1 2005/02/10 22:29:26 marco Exp $
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for it.mmarini.ratio");
		//$JUnit-BEGIN$
		suite.addTestSuite(RationalNumberTest.class);
		suite.addTestSuite(PoweredNumberTest.class);
		suite.addTestSuite(EratosteneTest.class);
		suite.addTestSuite(FactorizerTest.class);
		suite.addTestSuite(RationalArrayTest.class);
		suite.addTestSuite(RationalVectorTest.class);
		//$JUnit-END$
		return suite;
	}
}
