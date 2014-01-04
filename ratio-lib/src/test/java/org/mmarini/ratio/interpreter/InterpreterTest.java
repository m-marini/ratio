/**
 * 
 */
package org.mmarini.ratio.interpreter;

import static org.junit.Assert.assertThat;
import static org.mmarini.ratio.RatioMatchers.hasInterpreterValue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * @author US00852
 * 
 */
public class InterpreterTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testProcess1aug2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1,2");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[1, 2]"));
	}

	@Test
	public void testProcess1aug2_3aug4() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2),(3,4)");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[1, 2; 3, 4]"));
	}

	@Test
	public void testProcess1aug2_3aug4_5aug6() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2),(3,4),(5,6)");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[1, 2; 3, 4; 5, 6]"));
	}

	@Test
	public void testProcess1aug2_3aug4_5aug6_7aug8() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "((1,2),(3,4)),((5,6),(7,8))");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[1, 2; 3, 4; 5, 6; 7, 8]"));
	}

	@Test
	public void testProcess1aug2aug3() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1,2,3");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[1, 2, 3]"));
	}

	@Test
	public void testProcess1diva1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1 / ((1,2),(2,3))");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[-3, 2; 2, -1]"));
	}

	@Test
	public void testProcess1plus2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1+2");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "3"));
	}

	@Test
	public void testProcess2by3() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "2 * 3");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "6"));
	}

	@Test
	public void testProcess3sub2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "3-2");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "1"));
	}

	@Test
	public void testProcess4div6() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "4 / 6");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "2/3"));
	}

	@Test
	public void testProcessa1bya1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "((1,2),(2,3)) * ((1,2),(2,3))");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[5, 8; 8, 13]"));
	}

	@Test
	public void testProcessa1byinva1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "((1,2),(2,3)) * inv((1,2),(2,3))");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[1, 0; 0, 1]"));
	}

	@Test
	public void testProcessa1div2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "((1,2),(2,3)) / 2");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[1/2, 1; 1, 3/2]"));
	}

	@Test
	public void testProcessa1diva1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "((1,2),(2,3))/((1,2),(2,3))");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[1, 0; 0, 1]"));
	}

	@Test
	public void testProcessa1plusa1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "((1,2),(2,3)) + ((1,2),(2,3))");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[2, 4; 4, 6]"));
	}

	@Test
	public void testProcessa1suba1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "((1,2),(2,3))-((1,2),(2,3))");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[0, 0; 0, 0]"));
	}

	@Test
	public void testProcessDet() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "det 2");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "2"));
	}

	@Test
	public void testProcessDetArray() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "det((1,3),(2,4))");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "-2"));
	}

	@Test
	public void testProcessInv() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "inv 2");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "1/2"));
	}

	@Test
	public void testProcessInvArray() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "inv ((1,2),(2,3))");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[-3, 2; 2, -1]"));
	}

	@Test
	public void testProcessNeg() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "-1");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "-1"));
	}

	@Test
	public void testProcessNegArray() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "-((1,2),(3,4))");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[-1, -2; -3, -4]"));
	}

	@Test
	public void testProcessNegVect() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "-(1,2)");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[-1, -2]"));
	}

	@Test
	public void testProcessNumber() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "1"));
	}

	@Test
	public void testProcessPlus() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "+1");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "1"));
	}

	@Test
	public void testProcessPrio1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1)");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "1"));
	}

	@Test
	public void testProcessTrans() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "trans 2");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "2"));
	}

	@Test
	public void testProcessTransArray() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "trans ((1,2),(3,4))");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1, 3; 2, 4]"));
	}

	@Test
	public void testProcessTransVect() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "trans (1,2)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[1; 2]"));
	}

	@Test
	public void testProcessv1byv2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2) * trans(2,3)");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[8]"));
	}

	@Test
	public void testProcessv1plusv1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2)+(1,2)");
		final Interpreter i = new Interpreter(map);
		assertThat(i, hasInterpreterValue("a", "[2, 4]"));
	}

	@Test
	public void testProcessv1subv1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2)-(1,2)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[0, 0]"));
	}
}
