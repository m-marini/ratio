/**
 * 
 */
package org.mmarini.ratio.interpreter;

import static org.hamcrest.Matchers.containsString;
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
	public void testErrorAdd() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1+?");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", containsString("Error: <add> ::=")));
	}

	@Test
	public void testErrorAdd1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2)+3");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: array expected")));
	}

	@Test
	public void testErrorAdd2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1+(2,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: scalar expected")));
	}

	@Test
	public void testErrorAdd3() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2)+(1,2,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: Add of different dimensions")));
	}

	@Test
	public void testErrorAdd4() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2)+trans (1,2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: Add of different dimensions")));
	}

	@Test
	public void testErrorAdd5() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(trans (1,2))+(1,2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: Add of different dimensions")));
	}

	@Test
	public void testErrorAugment1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1,(2,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: scalar expected")));
	}

	@Test
	public void testErrorAugment2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "trans(1,2),2");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue(
						"a",
						containsString("Error: Augment of different dimensions")));
	}

	@Test
	public void testErrorAugmentRow3() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,2);(1,2,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue(
						"a",
						containsString("Error: Augment of different dimensions")));
	}

	@Test
	public void testErrorAugumentRow() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1,?");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", containsString("Error: <add> ::=")));
	}

	@Test
	public void testErrorCyclic() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", " - a");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: cyclic reference")));
	}

	@Test
	public void testErrorDet() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2,3) * (1,2,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue(
						"a",
						containsString("Error: Multiply of different dimensions")));
	}

	@Test
	public void testErrorDiv1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1/?");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue(
						"a",
						containsString("Error: <mul> ::= <unary> { \"*\" <unary> | \"/\" <unary> }")));
	}

	@Test
	public void testErrorDivArrayBy0() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2;2,4) * inv (1,2;2,4)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", containsString("Error: divide by 0")));
	}

	@Test
	public void testErrorDivBy0() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1/0");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", containsString("Error: divide by 0")));
	}

	@Test
	public void testErrorIdentity() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "I(1/2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 1/2 is not an integer")));
	}

	@Test
	public void testErrorIdentity1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "I 0");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 0 invalid value")));
	}

	@Test
	public void testErrorIdentity2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "I -1");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: -1 invalid value")));
	}

	@Test
	public void testErrorInv() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "inv((1,2,3),(1,2,3))");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: Inverse of not square array")));
	}

	@Test
	public void testErrorInvalidReference() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", " - b");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: undefined variable b")));
	}

	@Test
	public void testErrorInvArrayBy0() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "inv (1,2;2,4)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", containsString("Error: divide by 0")));
	}

	@Test
	public void testErrorInvBy0() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "inv 0");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", containsString("Error: divide by 0")));
	}

	@Test
	public void testErrorMul1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1*?");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue(
						"a",
						containsString("Error: <mul> ::= <unary> { \"*\" <unary> | \"/\" <unary> }")));
	}

	@Test
	public void testErrorParse1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "?");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: <expression> ::=")));
	}

	@Test
	public void testErrorParse2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1 a");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: <expression> ::=")));
	}

	@Test
	public void testErrorSliceCol() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1 col ?");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: <composeCol> ::=")));
	}

	@Test
	public void testErrorSliceCol1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3)col(0,1,2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: array 1 x 2 expected")));
	}

	@Test
	public void testErrorSliceCol10() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) col -1");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: -1 index out of range")));
	}

	@Test
	public void testErrorSliceCol11() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) col 3");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 3 index out of range")));
	}

	@Test
	public void testErrorSliceCol12() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1 col (0,1)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: array expected")));
	}

	@Test
	public void testErrorSliceCol2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) col (1/2,1)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 1/2 is not an integer")));
	}

	@Test
	public void testErrorSliceCol3() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) col (1,1/2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 1/2 is not an integer")));
	}

	@Test
	public void testErrorSliceCol4() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) col (2,1)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 2 to 1 invalid range")));
	}

	@Test
	public void testErrorSliceCol5() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) col (-1,1)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: -1 index out of range")));
	}

	@Test
	public void testErrorSliceCol6() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) col (3,4)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 3 index out of range")));
	}

	@Test
	public void testErrorSliceCol7() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) col (0,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 3 index out of range")));
	}

	@Test
	public void testErrorSliceCol8() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) col (0,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 3 index out of range")));
	}

	@Test
	public void testErrorSliceCol9() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) col (1/2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 1/2 is not an integer")));
	}

	@Test
	public void testErrorSliceRow() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1 row ?");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: <composeCol> ::=")));
	}

	@Test
	public void testErrorSliceRow1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3)row(0,1,2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: array 1 x 2 expected")));
	}

	@Test
	public void testErrorSliceRow10() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) row -1");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: -1 index out of range")));
	}

	@Test
	public void testErrorSliceRow11() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) row 3");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 3 index out of range")));
	}

	@Test
	public void testErrorSliceRow12() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1 row (0,1)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: array expected")));
	}

	@Test
	public void testErrorSliceRow2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) row (1/2,1)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 1/2 is not an integer")));
	}

	@Test
	public void testErrorSliceRow3() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) row (1,1/2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 1/2 is not an integer")));
	}

	@Test
	public void testErrorSliceRow4() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) row (2,1)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 2 to 1 invalid range")));
	}

	@Test
	public void testErrorSliceRow5() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) row (-1,1)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: -1 index out of range")));
	}

	@Test
	public void testErrorSliceRow6() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) row (3,4)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 3 index out of range")));
	}

	@Test
	public void testErrorSliceRow7() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) row (0,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 3 index out of range")));
	}

	@Test
	public void testErrorSliceRow8() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) row (0,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 3 index out of range")));
	}

	@Test
	public void testErrorSliceRow9() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(2,3) row (1/2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: 1/2 is not an integer")));
	}

	@Test
	public void testErrorSub() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1-?");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", containsString("Error: <add> ::=")));
	}

	@Test
	public void testErrorSub1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2)-3");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: array expected")));
	}

	@Test
	public void testErrorSub2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1-(2,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						containsString("Error: scalar expected")));
	}

	@Test
	public void testErrorSub3() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2)-(1,2,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue(
						"a",
						containsString("Error: Subtract of different dimensions")));
	}

	@Test
	public void testErrorSub4() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2)-trans (1,2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue(
						"a",
						containsString("Error: Subtract of different dimensions")));
	}

	@Test
	public void testErrorSub5() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(trans (1,2))-(1,2)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue(
						"a",
						containsString("Error: Subtract of different dimensions")));
	}

	@Test
	public void testErrorTerm1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "( ^ )");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", containsString("<slice> ::=")));
	}

	@Test
	public void testErrorTerm2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "( 1 a");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", containsString("Error: \")\"")));
	}

	@Test
	public void testProcess1diva1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1 / (1,2;2,3)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[-3, 2; 2, -1]"));
	}

	@Test
	public void testProcess1plus2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1+2");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "3"));
	}

	@Test
	public void testProcess2by3() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "2 * 3");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "6"));
	}

	@Test
	public void testProcess3sub2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "3-2");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "1"));
	}

	@Test
	public void testProcess4div6() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "4 / 6");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "2/3"));
	}

	@Test
	public void testProcessa1bya1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2;2,3)*(1,2;2,3)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[5, 8; 8, 13]"));
	}

	@Test
	public void testProcessa1byinva1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2;2,3) * inv((1,2;2,3))");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1, 0; 0, 1]"));
	}

	@Test
	public void testProcessa1div2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2;2,3) / 2");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1/2, 1; 1, 3/2]"));
	}

	@Test
	public void testProcessa1diva1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2;2,3)/(1,2;2,3)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1, 0; 0, 1]"));
	}

	@Test
	public void testProcessa1plusa1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2;2,3)+(1,2;2,3)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[2, 4; 4, 6]"));
	}

	@Test
	public void testProcessa1suba1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2;2,3)-(1,2;2,3)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[0, 0; 0, 0]"));
	}

	@Test
	public void testProcessComposeArray2_2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1,2;3,4");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1, 2; 3, 4]"));
	}

	@Test
	public void testProcessComposeArray3_2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1,2;3,4;5,6");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1, 2; 3, 4; 5, 6]"));
	}

	@Test
	public void testProcessComposeArray4_2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2;3,4);(5,6;7,8)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1, 2; 3, 4; 5, 6; 7, 8]"));
	}

	@Test
	public void testProcessComposeCol1_1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1;2");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[1; 2]"));
	}

	@Test
	public void testProcessComposeCol2_2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1;2);(3;4)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1; 2; 3; 4]"));
	}

	@Test
	public void testProcessComposeCol3() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1;2;3");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[1; 2; 3]"));
	}

	@Test
	public void testProcessComposeRow1_1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1,2");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[1, 2]"));
	}

	@Test
	public void testProcessComposeRow2_2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2),(3,4)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1, 2, 3, 4]"));
	}

	@Test
	public void testProcessComposeRow3() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1,2,3");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[1, 2, 3]"));
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
		map.put("a", "det((1,3;2,4))");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "-2"));
	}

	@Test
	public void testProcessIdentity() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "I(3)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1, 0, 0; 0, 1, 0; 0, 0, 1]"));
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
		map.put("a", "inv (1,2;2,3)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[-3, 2; 2, -1]"));
	}

	@Test
	public void testProcessLcm() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "lcm(1,1/2,1/3,1/10)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "30"));
	}

	@Test
	public void testProcessLcm1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "lcm(1,1/2;1/3,1/10)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "30"));
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
		map.put("a", "-(1,2;3,4)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[-1, -2; -3, -4]"));
	}

	@Test
	public void testProcessNegVect() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "-(1,2)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[-1, -2]"));
	}

	@Test
	public void testProcessNumber() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "1"));
	}

	@Test
	public void testProcessPlus() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "+1");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "1"));
	}

	@Test
	public void testProcessPrio1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "1"));
	}

	@Test
	public void testProcessPriority() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "1/2,2/3,3/4");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[1/2, 2/3, 3/4]"));
	}

	@Test
	public void testProcessReduce() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "reduce(1,2,3,1;1,3,4,2;3,2,1,3)");
		assertThat(
				new Interpreter(map),
				hasInterpreterValue("a",
						"[1, 0, 0, 0; 0, 1, 0, 2; 0, 0, 1, -1]"));
	}

	@Test
	public void testProcessSliceCol() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2,3;2,3,4;3,4,5) col 1");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[2; 3; 4]"));
	}

	@Test
	public void testProcessSliceCol1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "((1,2) col 0)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[1]"));
	}

	@Test
	public void testProcessSliceCols() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2,3;2,3,4;3,4,5) col (1,2)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[2, 3; 3, 4; 4, 5]"));
	}

	@Test
	public void testProcessSliceRow() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2,3;2,3,4;3,4,5) row 1");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[2, 3, 4]"));
	}

	@Test
	public void testProcessSliceRows() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2,3;2,3,4;3,4,5) row (1,2)");
		assertThat(new Interpreter(map),
				hasInterpreterValue("a", "[2, 3, 4; 3, 4, 5]"));
	}

	@Test
	public void testProcessTrace() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "trace(1,2;3,4)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "5"));
	}

	@Test
	public void testProcessTrace1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "trace((1,2) col 0)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "1"));
	}

	@Test
	public void testProcessTrace2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "trace 1");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "1"));
	}

	@Test
	public void testProcessTrace4() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "trace(1,2,3;3,4,5)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "5"));
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
		map.put("a", "trans ((1,2;3,4))");
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
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[8]"));
	}

	@Test
	public void testProcessv1plusv1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2)+(1,2)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[2, 4]"));
	}

	@Test
	public void testProcessv1subv1() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("a", "(1,2)-(1,2)");
		assertThat(new Interpreter(map), hasInterpreterValue("a", "[0, 0]"));
	}
}
