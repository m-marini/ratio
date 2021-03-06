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
 * expression ::= slice
 * slice := composeCol{ | "row" composeCol | "col" composeCol}
 * composeCol := composeRow { ";" composeRow}
 * composeRow := add { "," add}
 * add := mul { "+" mul | "-" mul }
 * mul := unary { "*" unary | "/" unary }
 * unary * := plus | negate | determiner | trans | inv | reduce | trace | lcm | term 
 * reduce := "reduce" unary
 * plus := "+" unary 
 * negate := "-" unary 
 * determiner := "det" unary 
 * trans ::= "trans" unary 
 * trace ::= "trace" unary 
 * lcm ::= "lcm" unary 
 * inv ::= "inv" unary 
 * term ::= integer | identifier | "(" slice ")"
 * 
 * </pre>
 * 
 * @author US00852
 * 
 */
public class Interpreter {
	private static final String EXPRESSION_FAILURE = "<expression> ::= <slice>";
	private static final String COMPOSE_ROW_FAILURE = "<composeRow> ::= <add> { \",\" <add> }";
	private static final String COMPOSE_COL_FAILURE = "<composeCol> ::= <composeRow> { \";\" <composeRow> }";
	private static final String MUL_FAILURE = "<mul> ::= <unary> { \"*\" <unary> | \"/\" <unary> }";
	private static final String ADD_FAILURE = "<add> ::= <mul> { \"+\" <mul> | \"-\" <mul> }";
	private static final String CLOSE_BRACKET_FAILURE = "expected \")\"";
	private static final String SLICE_FAILURE = "<slice> ::= <composeCol> { | \"row\" <composeCol> | \"col\" <composeCol> }";
	private static final String UNARY_FAILURE = "<unary> ::= <unary-operator> <unary> | <term>";

	private final Map<String, String> expressions;
	private final Map<String, Value> values;
	private final Map<String, Functor3<Value, ParserSource, Value, Value>> sliceOperMap;
	private final Map<String, Functor3<Value, ParserSource, Value, Value>> addOperMap;
	private final Map<String, Functor3<Value, ParserSource, Value, Value>> mulOperMap;
	private final Map<String, Functor2<Value, ParserSource, Value>> unaryOperMap;

	/**
	 * @param expressions
	 * 
	 */
	public Interpreter(final Map<String, String> expressions) {
		this.expressions = expressions;
		values = new HashMap<>();
		unaryOperMap = new HashMap<>();
		mulOperMap = new HashMap<>();
		sliceOperMap = new HashMap<>();
		addOperMap = new HashMap<>();

		sliceOperMap.put("col",
				new Functor3<Value, ParserSource, Value, Value>() {

					@Override
					public Value apply(final ParserSource p0, final Value p1,
							final Value p2) throws ParserException {
						return computeSliceCol(p0, p1, p2);
					}
				});
		sliceOperMap.put("row",
				new Functor3<Value, ParserSource, Value, Value>() {

					@Override
					public Value apply(final ParserSource p0, final Value p1,
							final Value p2) throws ParserException {
						return computeSliceRow(p0, p1, p2);
					}
				});

		addOperMap.put("+", new Functor3<Value, ParserSource, Value, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1,
					final Value p2) throws ParserException {
				return computeAdd(p0, p1, p2);
			}
		});
		addOperMap.put("-", new Functor3<Value, ParserSource, Value, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1,
					final Value p2) throws ParserException {
				return computeSub(p0, p1, p2);
			}
		});

		mulOperMap.put("*", new Functor3<Value, ParserSource, Value, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1,
					final Value p2) throws ParserException {
				return computeMul(p0, p1, p2);
			}
		});
		mulOperMap.put("/", new Functor3<Value, ParserSource, Value, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1,
					final Value p2) throws ParserException {
				return computeDiv(p0, p1, p2);
			}
		});

		unaryOperMap.put("+", new Functor2<Value, ParserSource, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1) {
				return p1;
			}
		});
		unaryOperMap.put("-", new Functor2<Value, ParserSource, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1)
					throws ParserException {
				return computeNeg(p0, p1);
			}
		});
		unaryOperMap.put("trace", new Functor2<Value, ParserSource, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1)
					throws ParserException {
				return computeTrace(p0, p1);
			}
		});
		unaryOperMap.put("trans", new Functor2<Value, ParserSource, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1)
					throws ParserException {
				return computeTrans(p0, p1);
			}
		});
		unaryOperMap.put("det", new Functor2<Value, ParserSource, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1)
					throws ParserException {
				return computeDet(p0, p1);
			}
		});
		unaryOperMap.put("I", new Functor2<Value, ParserSource, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1)
					throws ParserException {
				return computeIdentity(p0, p1);
			}
		});
		unaryOperMap.put("inv", new Functor2<Value, ParserSource, Value>() {
			@Override
			public Value apply(final ParserSource p0, final Value p1)
					throws ParserException {
				return computeInv(p0, p1);
			}
		});
		unaryOperMap.put("lcm", new Functor2<Value, ParserSource, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1)
					throws ParserException {
				return computeLcm(p0, p1);
			}
		});
		unaryOperMap.put("reduce", new Functor2<Value, ParserSource, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1)
					throws ParserException {
				return computeReduce(p0, p1);
			}
		});
		unaryOperMap.put("bases", new Functor2<Value, ParserSource, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1)
					throws ParserException {
				return computeBases(p0, p1);
			}
		});
		unaryOperMap.put("rank", new Functor2<Value, ParserSource, Value>() {

			@Override
			public Value apply(final ParserSource p0, final Value p1)
					throws ParserException {
				return computeRank(p0, p1);
			}
		});
		for (final String id : expressions.keySet())
			evaluate(id);
	}

	/**
	 * <pre>
	 * add := mul { "+" mul | "-" mul }
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value add(final ParserSource s) throws ParserException {
		Value e = mul(s);
		if (e == null)
			return null;
		for (;;) {
			final Functor3<Value, ParserSource, Value, Value> f = mapLitteral(
					s, addOperMap);
			if (f == null)
				break;
			final Value a = mul(s);
			if (a == null)
				throw s.fail(MUL_FAILURE);
			e = f.apply(s, e, a);
		}
		return e;
	}

	/**
	 * <pre>
	 * composeCol := composeRow { ";" composeRow}
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value composeCol(final ParserSource s) throws ParserException {
		Value e = composeRow(s);
		if (e == null)
			return null;
		for (;;) {
			if (litteral(s, ";")) {
				final Value a = composeRow(s);
				if (a == null)
					throw s.fail(COMPOSE_ROW_FAILURE);
				e = computeAugmentRow(s, e, a);
			} else
				return e;
		}
	}

	/**
	 * <pre>
	 * composeRow := add { "," add}
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value composeRow(final ParserSource s) throws ParserException {
		Value e = add(s);
		if (e == null)
			return null;
		for (;;) {
			if (litteral(s, ",")) {
				final Value a = add(s);
				if (a == null)
					throw s.fail(ADD_FAILURE);
				e = computeAugmentCol(s, e, a);
			} else
				return e;
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
	private final Value computeAugmentCol(final ParserSource s, final Value a,
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
							return new ArrayValue(mx.agumentCol(vb.getValue()));
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
					return new ArrayValue(va.getValue().augmentCol(
							((ScalarValue) b).getValue()));
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
	private final Value computeAugmentRow(final ParserSource s, final Value a,
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
							return new ArrayValue(mx.augmentRow(vb.getValue()));
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue value)
								throws ParserException {
							return new ArrayValue(mx.augmentRow(value
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
					return new ArrayValue(va.getValue().augmentRow(
							((ScalarValue) b).getValue()));
				}
			});
		} catch (final IllegalArgumentException e) {
			throw s.fail(e, e.getMessage());
		}
	}

	/**
	 * @param ss
	 * @param v
	 * @return
	 * @throws ParserException
	 */
	private Value computeBases(final ParserSource s, final Value v)
			throws ParserException {
		return v.apply(new ValueVisitor<Value>() {

			@Override
			public Value visit(final ArrayValue value) throws ParserException {
				return new ArrayValue(value.getValue().bases());
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
	private Value computeIdentity(final ParserSource s, final Value v)
			throws ParserException {
		return v.apply(new ValueVisitor<Value>() {

			@Override
			public Value visit(final ArrayValue value) throws ParserException {
				throw s.fail("scalar expected");
			}

			@Override
			public Value visit(final ErrorValue value) throws ParserException {
				throw value.getException();
			}

			@Override
			public Value visit(final ScalarValue value) throws ParserException {
				final RationalNumber r = value.getValue();
				if (!r.isInteger())
					throw s.fail("%s is not an integer", r.toString());
				final int i = r.intValue();
				if (i <= 0)
					throw s.fail("%d invalid value", i);
				return new ArrayValue(RationalArray.identity(i));
			}
		});
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
	 * @param ss
	 * @param v
	 * @return
	 * @throws ParserException
	 */
	private Value computeLcm(final ParserSource s, final Value v)
			throws ParserException {
		return v.apply(new ValueVisitor<Value>() {

			@Override
			public Value visit(final ArrayValue value) throws ParserException {
				return new ScalarValue(value.getValue().lcm());
			}

			@Override
			public Value visit(final ErrorValue value) throws ParserException {
				throw value.getException();
			}

			@Override
			public Value visit(final ScalarValue value) throws ParserException {
				return new ScalarValue(value.getValue().getLower());
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
	 * @param ss
	 * @param v
	 * @return
	 * @throws ParserException
	 */
	private Value computeRank(final ParserSource s, final Value v)
			throws ParserException {
		return v.apply(new ValueVisitor<Value>() {

			@Override
			public Value visit(final ArrayValue value) throws ParserException {
				return new ScalarValue(value.getValue().rank());
			}

			@Override
			public Value visit(final ErrorValue value) throws ParserException {
				throw value.getException();
			}

			@Override
			public Value visit(final ScalarValue value) throws ParserException {
				return new ScalarValue(RationalNumber.ONE);
			}
		});
	}

	/**
	 * @param ss
	 * @param v
	 * @return
	 * @throws ParserException
	 */
	private Value computeReduce(final ParserSource s, final Value v)
			throws ParserException {
		return v.apply(new ValueVisitor<Value>() {

			@Override
			public Value visit(final ArrayValue value) throws ParserException {
				return new ArrayValue(value.getValue().reduce());
			}

			@Override
			public Value visit(final ErrorValue value) throws ParserException {
				throw value.getException();
			}

			@Override
			public Value visit(final ScalarValue value) throws ParserException {
				return new ScalarValue(RationalNumber.ONE);
			}
		});
	}

	/**
	 * @param <T>
	 * @param s
	 * @param a
	 * @param b
	 * @throws ParserException
	 */
	private final Value computeSliceCol(final ParserSource s, final Value a,
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
							final RationalArray mb = vb.getValue();
							if (mb.getColumnCount() != 2
									|| mb.getRowCount() != 1)
								throw s.fail("array 1 x 2 expected");
							final RationalNumber ri0 = mb.getValues()[0][0];
							if (!ri0.isInteger())
								throw s.fail("%s is not an integer",
										ri0.toString());
							final RationalNumber ri1 = mb.getValues()[0][1];
							if (!ri1.isInteger())
								throw s.fail("%s is not an integer",
										ri1.toString());
							final int i = ri0.intValue();
							final int j = ri1.intValue();
							if (i > j)
								throw s.fail("%d to %d invalid range", i, j);
							if (i < 0 || i >= mx.getColumnCount())
								throw s.fail("%d index out of range", i);
							if (j < 0 || j >= mx.getColumnCount())
								throw s.fail("%d index out of range", j);
							return new ArrayValue(mx.sliceCol(i, j - i + 1));
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue value)
								throws ParserException {
							final RationalNumber sb = value.getValue();
							if (!sb.isInteger())
								throw s.fail("%s is not an integer",
										sb.toString());
							final int i = sb.intValue();
							if (i < 0 || i >= mx.getColumnCount())
								throw s.fail("%d index out of range", i);
							return new ArrayValue(mx.sliceCol(i, 1));
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
					throw s.fail("array expected");
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
	private final Value computeSliceRow(final ParserSource s, final Value a,
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
							final RationalArray mb = vb.getValue();
							if (mb.getColumnCount() != 2
									|| mb.getRowCount() != 1)
								throw s.fail("array 1 x 2 expected");
							final RationalNumber ri0 = mb.getValues()[0][0];
							if (!ri0.isInteger())
								throw s.fail("%s is not an integer",
										ri0.toString());
							final RationalNumber ri1 = mb.getValues()[0][1];
							if (!ri1.isInteger())
								throw s.fail("%s is not an integer",
										ri1.toString());
							final int i = ri0.intValue();
							final int j = ri1.intValue();
							if (i > j)
								throw s.fail("%d to %d invalid range", i, j);
							if (i < 0 || i >= mx.getRowCount())
								throw s.fail("%d index out of range", i);
							if (j < 0 || j >= mx.getRowCount())
								throw s.fail("%d index out of range", j);
							return new ArrayValue(mx.sliceRow(i, j - i + 1));
						}

						@Override
						public Value visit(final ErrorValue value)
								throws ParserException {
							throw value.getException();
						}

						@Override
						public Value visit(final ScalarValue value)
								throws ParserException {
							final RationalNumber sb = value.getValue();
							if (!sb.isInteger())
								throw s.fail("%s is not an integer",
										sb.toString());
							final int i = sb.intValue();
							if (i < 0 || i >= mx.getRowCount())
								throw s.fail("%d index out of range", i);
							return new ArrayValue(mx.sliceRow(i, 1));
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
					throw s.fail("array expected");
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
	 * @param ss
	 * @param v
	 * @return
	 * @throws ParserException
	 */
	private Value computeTrace(final ParserSource s, final Value v)
			throws ParserException {
		return v.apply(new ValueVisitor<Value>() {

			@Override
			public Value visit(final ArrayValue value) throws ParserException {
				return new ScalarValue(value.getValue().trace());
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
				throw s.fail("cyclic reference on %s", id);
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
			v = expression(new StringSource(e));
		} catch (final ParserException e1) {
			values.put(id, new ErrorValue(e1));
			return null;
		}
		values.put(id, v);
		return v;
	}

	/**
	 * <pre>
	 * expression := slice
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value expression(final ParserSource s) throws ParserException {
		final Value e = slice(s);
		if (e == null)
			s.fail(SLICE_FAILURE);
		if (s.getToken() != null)
			throw s.fail(EXPRESSION_FAILURE);
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
	 * 
	 * @param s
	 * @param map
	 * @return
	 */
	private <F> F mapLitteral(final ParserSource s, final Map<String, F> map) {
		final F f = map.get(s.getToken());
		if (f != null)
			s.consumeToken();
		return f;
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
			final Functor3<Value, ParserSource, Value, Value> f = mapLitteral(
					s, mulOperMap);
			if (f == null)
				break;
			final Value a = unary(s);
			if (a == null)
				throw s.fail(UNARY_FAILURE);
			e = f.apply(s, e, a);
		}
		return e;
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
	 * slice := composeCol{ | "row" composeCol | "col" composeCol}
	 * </pre>
	 * 
	 * @param s
	 * @return
	 * @throws ParserException
	 */
	private Value slice(final ParserSource s) throws ParserException {
		Value e = composeCol(s);
		if (e == null)
			return null;
		for (;;) {
			final Functor3<Value, ParserSource, Value, Value> f = mapLitteral(
					s, sliceOperMap);
			if (f == null)
				break;
			final Value a = composeCol(s);
			if (a == null)
				throw s.fail(COMPOSE_COL_FAILURE);
			e = f.apply(s, e, a);
		}
		return e;
	}

	/**
	 * <pre>
	 * term := integer | identifier | "(" slice ")"
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
			final Value e = slice(s);
			if (e == null)
				throw s.fail(SLICE_FAILURE);
			if (!litteral(s, ")"))
				throw s.fail(CLOSE_BRACKET_FAILURE);
			return e;
		} else
			return null;
	}

	/**
	 * <pre>
	 * unary ::= unary-oper unary |term
	 * </pre>
	 * 
	 * @param t
	 * @return
	 * @throws ParserException
	 */
	private Value unary(final ParserSource s) throws ParserException {
		final Functor2<Value, ParserSource, Value> f = mapLitteral(s,
				unaryOperMap);
		if (f != null) {
			final Value e = unary(s);
			if (e == null)
				throw s.fail(UNARY_FAILURE);
			return f.apply(s, e);
		} else
			return term(s);
	}
}
