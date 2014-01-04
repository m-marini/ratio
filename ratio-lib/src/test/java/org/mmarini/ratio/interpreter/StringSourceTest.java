/**
 * 
 */
package org.mmarini.ratio.interpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * @author US00852
 * 
 */
public class StringSourceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.StringSource#consumeToken()}.
	 */
	@Test
	public void testConsumeToken() {
		final StringSource s = new StringSource("  +  1   ");
		assertThat(s.getToken(), equalTo("+"));
		s.consumeToken();
		assertThat(s.getToken(), equalTo("1"));
		s.consumeToken();
		assertThat(s.getToken(), nullValue());
	}

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.StringSource#fail(java.lang.String, java.lang.Object[])}
	 * .
	 */
	@Test
	public void testFail() {
		final String text = "  \n   +  1 a\n  ";
		final StringSource s = new StringSource(text);
		s.getToken();
		final ParserException e = s.fail("%s %d error", "aaa", 1);
		assertThat(e, hasProperty("row", equalTo(2)));
		assertThat(e, hasProperty("col", equalTo(4)));
		assertThat(e, hasProperty("message", equalTo("aaa 1 error")));
		assertThat(e, hasProperty("line", equalTo("   +  1 a")));
		assertThat(e, hasProperty("errorLocator", equalTo("   ^ aaa 1 error")));
		assertThat(
				e,
				hasProperty("description",
						equalTo("   +  1 a\n   ^ aaa 1 error")));
	}

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.StringSource#getToken()}.
	 */
	@Test
	public void testGetTokenAny() {
		final StringSource s = new StringSource("+1");
		assertThat(s.getToken(), equalTo("+"));
	}

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.StringSource#getToken()}.
	 */
	@Test
	public void testGetTokenDigit() {
		final StringSource s = new StringSource("123abc");
		assertThat(s.getToken(), equalTo("123"));
	}

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.StringSource#getToken()}.
	 */
	@Test
	public void testGetTokenEmpty() {
		final StringSource s = new StringSource("");
		assertThat(s.getToken(), nullValue());
	}

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.StringSource#getToken()}.
	 */
	@Test
	public void testGetTokenIdentifier() {
		final StringSource s = new StringSource("abc_123;");
		assertThat(s.getToken(), equalTo("abc_123"));
	}

	/**
	 * Test method for
	 * {@link org.mmarini.ratio.interpreter.StringSource#getToken()}.
	 */
	@Test
	public void testGetTokenSkipBlanks() {
		final StringSource s = new StringSource(" \t\na  ");
		assertThat(s.getToken(), equalTo("a"));
	}
}
