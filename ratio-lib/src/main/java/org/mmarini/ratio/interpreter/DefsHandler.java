/**
 * 
 */
package org.mmarini.ratio.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author US00852
 * 
 */
public class DefsHandler extends DefaultHandler implements XmlConstants {
	private Map<String, String> defs;
	private Locator locator;
	private final StringBuilder text;
	private String id;

	/**
	 * 
	 */
	public DefsHandler() {
		text = new StringBuilder();
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(final char[] ch, final int start, final int length)
			throws SAXException {
		text.append(ch, start, length);
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		if (defs == null)
			throw new SAXParseException("Missing " + DEFS_ELEM + " element",
					locator);
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(final String uri, final String localName,
			final String qName) throws SAXException {
		if (XMLNS.equals(uri)) {
			switch (qName) {
			case EXP_ELEM:
				defs.put(id, text.toString());
				break;
			}
		}
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#error(org.xml.sax.SAXParseException)
	 */
	@Override
	public void error(final SAXParseException e) throws SAXException {
		throw e;
	}

	/**
	 * @return the defs
	 */
	public Map<String, String> getDefs() {
		return defs;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#setDocumentLocator(org.xml.sax.Locator
	 *      )
	 */
	@Override
	public void setDocumentLocator(final Locator locator) {
		this.locator = locator;
	}

	/**
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(final String uri, final String localName,
			final String qName, final Attributes attributes)
			throws SAXException {
		text.setLength(0);
		if (XMLNS.equals(uri)) {
			switch (qName) {
			case DEFS_ELEM:
				defs = new HashMap<>();
				break;
			case EXP_ELEM:
				id = attributes.getValue(ID_ATTR);
				break;
			}
		}
	}

}
