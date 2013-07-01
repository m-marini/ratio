package org.mmarini.ratio.web;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author $Author: marco $
 * @version $Id: TestBean.java,v 1.2 2007/01/09 22:11:32 marco Exp $
 */
public class TestBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(TestBean.class);

	private String[][] data = new String[][] { {"A1", "B1"}, {"A2", "B2"}};
	private String[] value = new String[] {"a", "b"};
	private String[] value1 = new String[] {"a1", "b1"};

	/**
	 * 
	 */
	public TestBean() {}

	/**
	 * @return
	 */
	public String update() {
		log.debug("value=" + getValue()[0] + "," + getValue()[1]);
		return null;
	}

	/**
	 * @return Returns the test.
	 */
	public String[][] getData() {
		return data;
	}

	/**
	 * @return Returns the value.
	 */
	public String[] getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(String[] value) {
		this.value = value;
	}

	/**
	 * @return Returns the value1.
	 */
	public String[] getValue1() {
		return value1;
	}

	/**
	 * @param value1
	 *            The value1 to set.
	 */
	public void setValue1(String[] value1) {
		this.value1 = value1;
	}
}