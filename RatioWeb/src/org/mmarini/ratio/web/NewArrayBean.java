package org.mmarini.ratio.web;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.mmarini.ratio.RationalArray;
import org.mmarini.ratio.interpreter.VariableNameValidator;
import org.mmarini.ratio.processor.ArrayRationalValue;

/**
 * @author $Author: marco $
 * @version $Id: NewArrayBean.java,v 1.2 2007/01/09 22:11:32 marco Exp $
 */
public class NewArrayBean extends AbstractBean implements Serializable {
	// private static Log log = LogFactory.getLog(NewArrayBean.class);
	private static final long serialVersionUID = 1L;
	private HtmlInputText variableName;
	private HtmlInputText colsCount;
	private HtmlInputText rowsCount;

	/**
	 * 
	 */
	public NewArrayBean() {}

	/**
	 * @return
	 */
	public String execute() {
		int nr = ((Long) getRowsCount().getValue()).intValue();
		int nc = ((Long) getColsCount().getValue()).intValue();
		RationalArray ratioArray = new RationalArray(nr, nc);
		FacesContext ctx = FacesContext.getCurrentInstance();
		EditArrayBean editArrayBean = (EditArrayBean) ctx.getApplication()
				.getVariableResolver().resolveVariable(ctx, "editArrayBean");
		editArrayBean.setArray(new ArrayRationalValue(ratioArray));
		editArrayBean.setName((String) getVariableName().getValue());
		return "success";
	}

	/**
	 * @param ctx
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateName(FacesContext ctx, UIComponent component,
			Object value) throws ValidatorException {
		String name = (String) value;
		if (!VariableNameValidator.getInstance().isName(name)) {
			throw createException(ctx, "notVariableNameError",
					new Object[] {name});
		}
	}

	/**
	 * @return Returns the command.
	 */
	public HtmlInputText getVariableName() {
		return variableName;
	}

	/**
	 * @param command
	 *            The command to set.
	 */
	public void setVariableName(HtmlInputText command) {
		this.variableName = command;
	}

	/**
	 * @return Returns the colsCount.
	 */
	public HtmlInputText getColsCount() {
		return colsCount;
	}

	/**
	 * @return Returns the rowsCount.
	 */
	public HtmlInputText getRowsCount() {
		return rowsCount;
	}

	/**
	 * @param colsCount
	 *            The colsCount to set.
	 */
	public void setColsCount(HtmlInputText colsCount) {
		this.colsCount = colsCount;
	}

	/**
	 * @param rowsCount
	 *            The rowsCount to set.
	 */
	public void setRowsCount(HtmlInputText rowsCount) {
		this.rowsCount = rowsCount;
	}
}
