/*
 * Created on 21-gen-2006
 */
package org.mmarini.ratio.processor;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.mmarini.ratio.processor");
		//$JUnit-BEGIN$
		suite.addTestSuite(ArrayRationalValueTest.class);
		suite.addTestSuite(ScalarRationalValueTest.class);
		//$JUnit-END$
		return suite;
	}

}
