package org.mmarini.faces.component.html;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author $Author: marco $
 * @version $Id: GridTag.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class GridTag extends UIComponentTag {
	private static Log log = LogFactory.getLog(GridTag.class);

	private String value;
	private String rowIndexVar;
	private String colIndexVar;
	private String styleClass;

	/**
	 * 
	 */
	public GridTag() {
		super();
		log.debug("GridTag created");
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#release()
	 */
	public void release() {
		super.release();
		setValue(null);
		setColIndexVar(null);
		setRowIndexVar(null);
		setStyleClass(null);
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		HtmlGrid grid = (HtmlGrid) component;
		FacesContext context = getFacesContext();
		Application appl = context.getApplication();

		String value = getValue();
		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = appl.createValueBinding(value);
				grid.setValueBinding("value", vb);
				log.debug("Setting property value of " + grid + " = " + vb);
			} else {
				grid.setValue(value);
				log.debug("Setting property value of " + grid + " = " + value);
			}
		}

		value = getColIndexVar();
		if (value != null) {
			grid.setColIndexVar(value);
		}
		value = getRowIndexVar();
		if (value != null) {
			grid.setRowIndexVar(value);
		}
		value = getStyleClass();
		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = appl.createValueBinding(value);
				grid.setValueBinding("styleClass", vb);
			} else {
				grid.setStyleClass(value);
			}
		}
	}

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return "org.mmarini.faces.HtmlGrid";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		return "org.mmarini.faces.Grid";
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return Returns the colIndexVar.
	 */
	public String getColIndexVar() {
		return colIndexVar;
	}

	/**
	 * @param colIndexVar
	 *            The colIndexVar to set.
	 */
	public void setColIndexVar(String colIndexVar) {
		this.colIndexVar = colIndexVar;
	}

	/**
	 * @return Returns the rowIndexVar.
	 */
	public String getRowIndexVar() {
		return rowIndexVar;
	}

	/**
	 * @param rowIndexVar
	 *            The rowIndexVar to set.
	 */
	public void setRowIndexVar(String rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	/**
	 * @return Returns the styleClass.
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * @param styleClass
	 *            The styleClass to set.
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
}
