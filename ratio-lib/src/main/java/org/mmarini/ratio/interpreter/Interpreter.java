/**
 * 
 */
package org.mmarini.ratio.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.mmarini.ratio.RationalArray;
import org.mmarini.ratio.RationalNumber;

/**
 * <pre>
 * parse := exp
 * exp := mul { "+" mul | "-" mul }
 * mul := unary { "*" unary | "/" unary }
 * unary * := plus | negate | determiner | trans | inv |augment 
 * plus := "+" unary 
 * negate := "-" unary 
 * determiner := "det" unary 
 * trans:= "trans" unary 
 * inv := "inv" unary 
 * augment := term { "," term }
 * term := integer | identifier | "(" exp ")"
 * 
 * </pre>
 * 
 * @author US00852
 * 
 */
public class Interpreter {
	private final Map<String, String> expressions;
	private final Map<String, Value> values;

	/**
	 * @param expressions
	 * 
	 */
	public Interpreter(final Map<String, String> expressions) {
		this.expressions = expressions;
		values = new HashMap<>();
		for (final String id : expressions.keySet())
			evaluate(id);
	}

	/**
	 * <pre>
	 * augment := term { "|" term }
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value augment(final ParserSource s) throws ParserException {
		Value e = term(s);
		if (e == null)
			return null;
		for (;;) {
			if (!litteral(s, ","))
				return e;
			final Value a = term(s);
			if (a == null)
				throw s.fail("<augment> ::= <term> { \",\" <term> }");
			e = computeAugment(s, e, a);
		}
	}

	/**
	 * @param <T>
	 * @param s
	 * @param va
	 * @param vb
	 * @throws ParserException
	 */
	private final Value computeAdd(final ParserSource s, final Value va,
			final Value vb) throws ParserException {
		try {
			return va.apply(new ValueVisitor<Value>() {

				@Override
				public Value visit(final ArrayValue va1) throws ParserException {
					final RationalArray a = va1.getValue();
					return vb.apply(new ValueVisitor<Value>() {

						@Override
						public Value visit(final ArrayValue vb1)
								throws ParserException {
							return new ArrayValue(a.add(vb1.getValue()));
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue vb1)
								throws ParserException {
							throw s.fail("array expected");
						}
					});
				}

				@Override
				public Value visit(final ErrorValue value)
						throws ParserException {
					throw value.getException();
				}

				@Override
				public Value visit(final ScalarValue va) throws ParserException {
					final RationalNumber a = va.getValue();
					return vb.apply(new ValueVisitor<Value>() {

						@Override
						public Value visit(final ArrayValue vb1)
								throws ParserException {
							throw s.fail("scla expected");
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue vb1)
								throws ParserException {
							return new ScalarValue(vb1.getValue().add(a));
						}
					});
				}
			});
		} catch (final IllegalArgumentException e) {
			throw s.fail(e, e.getMessage());
		}
	}

	/**
	 * @param <T>
	 * @param s
	 * @param a
	 * @param b
	 * @throws ParserException
	 */
	private final Value computeAugment(final ParserSource s, final Value a,
			final Value b) throws ParserException {
		try {
			return a.apply(new ValueVisitor<Value>() {

				@Override
				public Value visit(final ArrayValue va) throws ParserException {
					final RationalArray mx = va.getValue();
					return b.apply(new ValueVisitor<Value>() {

						@Override
						public Value visit(final ArrayValue vb)
								throws ParserException {
							return new ArrayValue(mx.agumentRow(vb.getValue()));
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue value)
								throws ParserException {
							return new ArrayValue(mx.agumentCol(value
									.getValue()));
						}
					});
				}

				@Override
				public Value visit(final ErrorValue value)
						throws ParserException {
					throw value.getException();
				}

				@Override
				public Value visit(final ScalarValue va) throws ParserException {
					if (!(b instanceof ScalarValue))
						throw s.fail("scalar expected");
					return new ArrayValue(va.getValue().augment(
							((ScalarValue) b).getValue()));
				}
			});
		} catch (final IllegalArgumentException e) {
			throw s.fail(e, e.getMessage());
		}
	}

	/**
	 * @param ss
	 * @param unary
	 * @return
	 * @throws ParserException
	 */
	private Value computeDet(final ParserSource s, final Value unary)
			throws ParserException {
		return unary.apply(new ValueVisitor<Value>() {

			@Override
			public Value visit(final ArrayValue value) throws ParserException {
				return new ScalarValue(value.getValue().det());
			}

			@Override
			public Value visit(final ErrorValue value) throws ParserException {
				throw value.getException();
			}

			@Override
			public Value visit(final ScalarValue value) throws ParserException {
				return value;
			}
		});
	}

	/**
	 * @param <T>
	 * @param s
	 * @param va
	 * @param vb
	 * @throws ParserException
	 */
	private final Value computeDiv(final ParserSource s, final Value va,
			final Value vb) throws ParserException {
		try {
			return va.apply(new ValueVisitor<Value>() {

				@Override
				public Value visit(final ArrayValue va1) throws ParserException {
					final RationalArray a = va1.getValue();
					return vb.apply(new ValueVisitor<Value>() {

						@Override
						public Value visit(final ArrayValue vb1)
								throws ParserException {
							return new ArrayValue(a.mul(vb1.getValue().inv()));
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue vb1)
								throws ParserException {
							return new ArrayValue(a.mul(vb1.getValue().inv()));
						}
					});
				}

				@Override
				public Value visit(final ErrorValue value)
						throws ParserException {
					throw value.getException();
				}

				@Override
				public Value visit(final ScalarValue va) throws ParserException {
					final RationalNumber a = va.getValue();
					return vb.apply(new ValueVisitor<Value>() {

						@Override
						public Value visit(final ArrayValue vb1)
								throws ParserException {
							return new ArrayValue(vb1.getValue().inv().mul(a));
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue vb1)
								throws ParserException {
							return new ScalarValue(a.div(vb1.getValue()));
						}
					});
				}
			});
		} catch (final IllegalArgumentException e) {
			throw s.fail(e, e.getMessage());
		}
	}

	/**
	 * @param s
	 * @param v
	 * @return
	 * @throws ParserException
	 */
	private Value computeInv(final ParserSource s, final Value v)
			throws ParserException {
		try {
			return v.apply(new ValueVisitor<Value>() {

				@Override
				public Value visit(final ArrayValue value)
						throws ParserException {
					return new ArrayValue(value.getValue().inv());
				}

				@Override
				public Value visit(final ErrorValue value)
						throws ParserException {
					throw value.getException();
				}

				@Override
				public Value visit(final ScalarValue value)
						throws ParserException {
					return new ScalarValue(value.getValue().inv());
				}
			});
		} catch (final IllegalArgumentException e) {
			throw s.fail(e, e.getMessage());
		}
	}

	/**
	 * @param <T>
	 * @param s
	 * @param va
	 * @param vb
	 * @throws ParserException
	 */
	private final Value computeMul(final ParserSource s, final Value va,
			final Value vb) throws ParserException {
		try {
			return va.apply(new ValueVisitor<Value>() {

				@Override
				public Value visit(final ArrayValue va1) throws ParserException {
					final RationalArray a = va1.getValue();
					return vb.apply(new ValueVisitor<Value>() {

						@Override
						public Value visit(final ArrayValue vb1)
								throws ParserException {
							return new ArrayValue(a.mul(vb1.getValue()));
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue vb1)
								throws ParserException {
							return new ArrayValue(a.mul(vb1.getValue()));
						}
					});
				}

				@Override
				public Value visit(final ErrorValue value)
						throws ParserException {
					throw value.getException();
				}

				@Override
				public Value visit(final ScalarValue va) throws ParserException {
					final RationalNumber a = va.getValue();
					return vb.apply(new ValueVisitor<Value>() {

						@Override
						public Value visit(final ArrayValue vb1)
								throws ParserException {
							return new ArrayValue(vb1.getValue().mul(a));
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue vb1)
								throws ParserException {
							return new ScalarValue(vb1.getValue().mul(a));
						}
					});
				}
			});
		} catch (final IllegalArgumentException e) {
			throw s.fail(e, e.getMessage());
		}
	}

	/**
	 * @param s
	 * @param v
	 * @return
	 * @throws ParserException
	 */
	private Value computeNeg(final ParserSource s, final Value v)
			throws ParserException {
		return v.apply(new ValueVisitor<Value>() {

			@Override
			public Value visit(final ArrayValue value) throws ParserException {
				return new ArrayValue(value.getValue().neg());
			}

			@Override
			public Value visit(final ErrorValue value) throws ParserException {
				throw value.getException();
			}

			@Override
			public Value visit(final ScalarValue value) throws ParserException {
				return new ScalarValue(value.getValue().neg());
			}
		});
	}

	/**
	 * @param <T>
	 * @param s
	 * @param va
	 * @param vb
	 * @throws ParserException
	 */
	private final Value computeSub(final ParserSource s, final Value va,
			final Value vb) throws ParserException {
		try {
			return va.apply(new ValueVisitor<Value>() {

				@Override
				public Value visit(final ArrayValue va1) throws ParserException {
					final RationalArray a = va1.getValue();
					return vb.apply(new ValueVisitor<Value>() {

						@Override
						public Value visit(final ArrayValue vb1)
								throws ParserException {
							return new ArrayValue(a.sub(vb1.getValue()));
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue vb1)
								throws ParserException {
							throw s.fail("array expected");
						}
					});
				}

				@Override
				public Value visit(final ErrorValue value)
						throws ParserException {
					throw value.getException();
				}

				@Override
				public Value visit(final ScalarValue va) throws ParserException {
					return vb.apply(new ValueVisitor<Value>() {

						@Override
						public Value visit(final ArrayValue vb1)
								throws ParserException {
							throw s.fail("scalar expected");
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue vb1)
								throws ParserException {
							return new ScalarValue(va.getValue().sub(
									vb1.getValue()));
						}
					});
				}
			});
		} catch (final IllegalArgumentException e) {
			throw s.fail(e, e.getMessage());
		}
	}

	/**
	 * @param s
	 * @param v
	 * @return
	 * @throws ParserException
	 */
	private Value computeTrans(final ParserSource s, final Value v)
			throws ParserException {
		return v.apply(new ValueVisitor<Value>() {

			@Override
			public Value visit(final ArrayValue value) throws ParserException {
				return new ArrayValue(value.getValue().transpose());
			}

			@Override
			public Value visit(final ErrorValue value) throws ParserException {
				throw value.getException();
			}

			@Override
			public Value visit(final ScalarValue value) throws ParserException {
				return value;
			}
		});
	}

	/**
	 * <pre>
	 * determiner := "det" unary
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value det(final ParserSource s) throws ParserException {
		if (!litteral(s, "det"))
			return null;
		return computeDet(s, unary(s));
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ParserException
	 */
	private Value evaluate(final ParserSource s, final String id)
			throws ParserException {
		final Value f = values.get(id);
		if (f != null) {
			if (f.equals(Value.UNDEFINED))
				throw s.fail("cycle reference on %s", id);
			return f;
		}
		final Value v = evaluate(id);
		if (v == null)
			throw s.fail("undefined variable %s", id);
		return v;
	}

	/**
	 * @param id
	 * @return
	 * @throws ParserException
	 */
	private Value evaluate(final String id) {
		final Value f = values.get(id);
		if (f != null)
			return f;
		final String e = expressions.get(id);
		if (e == null)
			return null;
		values.put(id, Value.UNDEFINED);
		Value v;
		try {
			v = parse(new StringSource(e));
		} catch (final ParserException e1) {
			values.put(id, new ErrorValue(e1));
			return null;
		}
		values.put(id, v);
		return v;
	}

	/**
	 * <pre>
	 * exp := mul { "+" mul | "-" mul }
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value exp(final ParserSource s) throws ParserException {
		Value e = mul(s);
		if (e == null)
			return null;
		for (;;) {
			if (litteral(s, "+")) {
				final Value a = mul(s);
				if (a == null)
					throw s.fail("<exp> := <mul> { \"+\" <mul> | \"-\" <mul> }");
				e = computeAdd(s, e, a);
			} else if (litteral(s, "-")) {
				final Value a = mul(s);
				if (a == null)
					throw s.fail("<exp> := <mul> { \"+\" <mul> | \"-\" <mul> }");
				e = computeSub(s, e, a);
			} else
				break;
		}
		return e;
	}

	/**
	 * @return the values
	 */
	public Map<String, Value> getValues() {
		return values;
	}

	/**
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value identifier(final ParserSource s) throws ParserException {
		final String id = s.getToken();
		if (id == null || !Character.isJavaIdentifierStart(id.charAt(0)))
			return null;
		s.consumeToken();
		return evaluate(s, id);
	}

	/**
	 * <pre>
	 * inverse:= "inv" unary
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value inv(final ParserSource s) throws ParserException {
		if (!litteral(s, "inv"))
			return null;
		return computeInv(s, unary(s));
	}

	/**
	 * 
	 * @param s
	 * @param litteral
	 * @return
	 */
	private boolean litteral(final ParserSource s, final String litteral) {
		if (!litteral.equals(s.getToken()))
			return false;
		s.consumeToken();
		return true;
	}

	/**
	 * <pre>
	 * mul := unary { "*" unary | "/" unary }
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value mul(final ParserSource s) throws ParserException {
		Value e = unary(s);
		if (e == null)
			return null;
		for (;;) {
			if (litteral(s, "*")) {
				final Value a = unary(s);
				if (a == null)
					throw s.fail("<mul> ::= <unary> { \"*\" <unary> | \"/\" <unary> }");
				e = computeMul(s, e, a);
			} else if (litteral(s, "/")) {
				final Value a = unary(s);
				if (a == null)
					throw s.fail("<mul> ::= <unary> { \"*\" <unary> | \"/\" <unary> }");
				e = computeDiv(s, e, a);
			} else
				break;
		}
		return e;
	}

	/**
	 * <pre>
	 * negate := "-" unary
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value neg(final ParserSource s) throws ParserException {
		if (!litteral(s, "-"))
			return null;
		return computeNeg(s, unary(s));
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private Value number(final ParserSource s) {
		final String t = s.getToken();
		if (t != null) {
			try {
				final int n = Integer.parseInt(t);
				s.consumeToken();
				return new ScalarValue(n);
			} catch (final NumberFormatException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * <pre>
	 * parse := exp
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value parse(final ParserSource s) throws ParserException {
		final Value e = exp(s);
		if (e == null)
			throw s.fail("<exp> ::= <mul> [ addSuffix | subSuffix ]");
		if (s.getToken() != null)
			throw s.fail("empty");
		return e;
	}

	/**
	 * <pre>
	 * plus := "+" unary
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value plus(final ParserSource s) throws ParserException {
		return litteral(s, "+") ? unary(s) : null;
	}

	/**
	 * <pre>
	 * term := integer | identifier | "(" exp ")"
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value term(final ParserSource s) throws ParserException {
		final Value n = number(s);
		if (n != null)
			return n;
		final Value i = identifier(s);
		if (i != null)
			return i;
		if (litteral(s, "(")) {
			final Value e = exp(s);
			if (e == null)
				throw s.fail("<term> := <integer> | <identifier> | \"(\" <exp> \")\"");
			if (!litteral(s, ")"))
				throw s.fail("\")\"");
			return e;
		} else
			return null;
	}

	/**
	 * <pre>
	 * trans:= "transpose" unary
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value trans(final ParserSource s) throws ParserException {
		if (!litteral(s, "trans"))
			return null;
		return computeTrans(s, unary(s));
	}

	/**
	 * <pre>
	 * unary * := plus | negate | determiner | transpose | inverse | augment
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value unary(final ParserSource s) throws ParserException {
		final Value o1 = plus(s);
		if (o1 != null)
			return o1;
		final Value o2 = neg(s);
		if (o2 != null)
			return o2;
		final Value o3 = det(s);
		if (o3 != null)
			return o3;
		final Value o4 = trans(s);
		if (o4 != null)
			return o4;
		final Value o5 = inv(s);
		if (o5 != null)
			return o5;
		return augment(s);
	}
}
