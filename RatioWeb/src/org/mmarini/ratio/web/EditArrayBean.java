package org.mmarini.ratio.web;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.faces.component.html.HtmlGrid;
import org.mmarini.ratio.RationalArray;
import org.mmarini.ratio.interpreter.AddExpression;
import org.mmarini.ratio.interpreter.IVariableResolver;
import org.mmarini.ratio.interpreter.StringContextInterpreter;
import org.mmarini.ratio.interpreter.SyntaxException;
import org.mmarini.ratio.processor.ArrayRationalValue;
import org.mmarini.ratio.processor.IRationalValue;
import org.mmarini.ratio.processor.ProcessException;
import org.mmarini.ratio.processor.ScalarRationalValue;

/**
 * @author $Author: marco $
 * @version $Id: EditArrayBean.java,v 1.2 2007/01/09 22:11:32 marco Exp $
 */
public class EditArrayBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(EditArrayBean.class);
	private static IVariableResolver variableResolver = new IVariableResolver() {
		/**
		 * @see org.mmarini.ratio.interpreter.IVariableResolver#put(java.lang.String,
		 *      org.mmarini.ratio.processor.IRationalValue)
		 */
		public void put(String assignName, IRationalValue a) {}

		/**
		 * @see org.mmarini.ratio.interpreter.IVariableResolver#get(java.lang.String)
		 */
		public IRationalValue get(String name) {
			return null;
		}
	};

	private ArrayRationalValue array;
	private HtmlGrid grid;
	private String name;

	/**
	 * 
	 */
	public EditArrayBean() {}

	/**
	 * @return
	 */
	private String[][] createExpression() {
		ArrayRationalValue value = getArray();
		log.debug("createExpression(): value=" + value);
		if (value == null)
			return null;
		RationalArray array = value.getArray();
		int nr = array.getRowsCount();
		int nc = array.getColumnsCount();
		String[][] exp = new String[nr][nc];
		for (int i = 0; i < nr; ++i)
			for (int j = 0; j < nc; ++j) {
				exp[i][j] = array.getValue(i, j).toString();
			}
		return exp;
	}

	/**
	 * @return
	 * @throws SyntaxException
	 * @throws ProcessException
	 */
	public String createArray() throws SyntaxException, ProcessException {
		String[][] array = (String[][]) getGrid().getValue();
		int nr = array.length;
		int nc = array[0].length;
		RationalArray matrix = new RationalArray(nr, nc);
		for (int i = 0; i < nr; ++i) {
			for (int j = 0; j < nc; ++j) {
				StringContextInterpreter sci = new StringContextInterpreter(
						array[i][j], variableResolver);
				AddExpression.getInstance().interpret(sci);
				sci.execute();
				ScalarRationalValue value = (ScalarRationalValue) sci.pop();
				matrix.setValue(i, j, value.getNumber());
			}
		}
		ArrayRationalValue value = new ArrayRationalValue(matrix);
		setArray(value);
		FacesContext ctx = FacesContext.getCurrentInstance();
		RatioBean ratioBean = (RatioBean) ctx.getApplication()
				.getVariableResolver().resolveVariable(ctx, "ratioBean");
		ratioBean.setValue(getName(), value);
		return "success";
	}

	/**
	 * @param ctx
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateCell(FacesContext ctx, UIComponent component,
			Object value) throws ValidatorException {
		String cmd = (String) value;
		StringContextInterpreter sci = new StringContextInterpreter(cmd,
				variableResolver);
		try {
			AddExpression.getInstance().interpret(sci);
			sci.execute();
		} catch (SyntaxException e) {
			throw createException(ctx, "syntaxError", new Object[] {e
					.getMessage()});
		} catch (ProcessException e) {
			throw createException(ctx, "processError", new Object[] {e
					.getMessage()});
		}
	}

	/**
	 * @return Returns the array.
	 */
	public ArrayRationalValue getArray() {
		return array;
	}

	/**
	 * @param array
	 *            The array to set.
	 */
	public void setArray(ArrayRationalValue array) {
		this.array = array;
		initGrid();
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the grid.
	 */
	public HtmlGrid getGrid() {
		return grid;
	}

	/**
	 * @param grid
	 *            The grid to set.
	 */
	public void setGrid(HtmlGrid grid) {
		this.grid = grid;
		initGrid();
	}

	/**
	 * 
	 *
	 */
	private void initGrid() {
		log.debug("initGrid()");
		HtmlGrid grid = getGrid();
		if (grid == null)
			return;
		String[][] array = createExpression();
		if (array != null)
			grid.setValue(array);
	}
}
