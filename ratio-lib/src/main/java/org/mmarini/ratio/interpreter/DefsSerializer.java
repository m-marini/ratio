/**
 * 
 */
package org.mmarini.ratio.interpreter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * @author US00852
 * 
 */
public class DefsSerializer implements XmlConstants {

	private static final Attributes EMPTY_ATTRIBUTES = null;

	/**
	 * @return
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private SAXParser createParser() throws SAXException,
			ParserConfigurationException {
		final SAXParserFactory f = SAXParserFactory.newInstance();
		f.setNamespaceAware(true);
		f.setSchema(SchemaFactory.newInstance(
				XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
				getClass().getResource("/defs-0.1.0.xsd")));
		return f.newSAXParser();
	}

	/**
	 * 
	 * @param result
	 * @return
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws SAXException
	 */
	private TransformerHandler createTransformerHandler(final Result result)
			throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, SAXException {
		final SAXTransformerFactory tf = (SAXTransformerFactory) TransformerFactory
				.newInstance();
		final TransformerHandler handler = tf.newTransformerHandler();
		final Transformer tr = handler.getTransformer();
		tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		handler.setResult(result);
		return handler;
	}

	/**
	 * @param f
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public Map<String, String> load(final File f) throws SAXException,
			IOException, ParserConfigurationException {
		final DefsHandler h = new DefsHandler();
		createParser().parse(f, h);
		return h.getDefs();
	}

	/**
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public Map<String, String> load(final InputStream stream)
			throws SAXException, IOException, ParserConfigurationException {
		final DefsHandler h = new DefsHandler();
		createParser().parse(stream, h);
		return h.getDefs();
	}

	/**
	 * @param f
	 * @param defs
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 */
	public void save(final File f, final Map<String, String> defs)
			throws IOException, TransformerConfigurationException,
			TransformerFactoryConfigurationError, SAXException {
		final FileWriter w = new FileWriter(f);
		save(createTransformerHandler(new StreamResult(w)), defs);
		w.close();
	}

	/**
	 * 
	 * @param stream
	 * @param defs
	 * @throws IOException
	 * @throws TransformerConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws SAXException
	 */
	public void save(final OutputStream stream, final Map<String, String> defs)
			throws IOException, TransformerConfigurationException,
			TransformerFactoryConfigurationError, SAXException {
		save(createTransformerHandler(new StreamResult(stream)), defs);
	}

	/**
	 * 
	 * @param t
	 * @param defs
	 * @throws SAXException
	 */
	private void save(final TransformerHandler t, final Map<String, String> defs)
			throws SAXException {
		t.startDocument();
		t.startElement(XMLNS, "", DEFS_ELEM, EMPTY_ATTRIBUTES);
		for (final Entry<String, String> e : defs.entrySet()) {
			final AttributesImpl a = new AttributesImpl();
			a.addAttribute(XMLNS, "", ID_ATTR, "CDATA",
					String.valueOf(e.getKey()));
			t.startElement(XMLNS, "", EXP_ELEM, a);
			final char[] chars = e.getValue().toCharArray();
			t.characters(chars, 0, chars.length);
			t.endElement(XMLNS, "", EXP_ELEM);
		}
		t.endElement(XMLNS, "", DEFS_ELEM);
		t.endDocument();
	}
}