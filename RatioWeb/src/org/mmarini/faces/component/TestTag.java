package org.mmarini.faces.component;

import javax.faces.webapp.UIComponentTag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author $Author: marco $
 * @version $Id: TestTag.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class TestTag extends UIComponentTag {
	private static Log log = LogFactory.getLog(TestTag.class);

	/**
	 * 
	 */
	public TestTag() {
		super();
		log.debug("GridTag created");
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return "org.mmarini.faces.Test";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		return "org.mmarini.faces.Test";
	}
}
