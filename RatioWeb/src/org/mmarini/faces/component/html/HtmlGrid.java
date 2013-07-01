package org.mmarini.faces.component.html;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

import org.mmarini.faces.component.UIGrid;
import org.mmarini.faces.model.CellIndex;

/**
 * @author $Author: marco $
 * @version $Id: HtmlGrid.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class HtmlGrid extends UIGrid {
//	private static Log log = LogFactory.getLog(HtmlGrid.class);
	private String styleClass;

	/**
	 * 
	 */
	public HtmlGrid() {
		super();
	}

	/**
	 * @see org.mmarini.faces.component.UIGrid#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context) {
		Object[] state = new Object[2];
		state[0] = super.saveState(context);
		state[1] = styleClass;
		return state;
	}

	/**
	 * @see org.mmarini.faces.component.UIGrid#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object state) {
		super.restoreState(context, ((Object[]) state)[0]);
		styleClass = (String) ((Object[]) state)[1];
	}

	/**
	 * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	public void encodeBegin(FacesContext facesContext) throws IOException {
		super.encodeBegin(facesContext);
		ResponseWriter writer = facesContext.getResponseWriter();
		writer.startElement("table", this);
		writer.write("\n");
	}

	/**
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	public void encodeChildren(FacesContext facesContext) throws IOException {
		ResponseWriter writer = facesContext.getResponseWriter();
		writer.startElement("tbody", this);
		writer.write("\n");

		encodeGrid(facesContext);

		writer.endElement("tbody");
		writer.write("\n");
	}

	/**
	 * @param facesContext
	 * @throws IOException
	 */
	private void encodeGrid(FacesContext facesContext) throws IOException {
		int rowCount = getRowCount();
		ResponseWriter writer = facesContext.getResponseWriter();
		int colCount = getColCount();
		String styleClass = getStyleClass();

		for (int i = 0; i < rowCount; i++) {
			writer.startElement("tr", this);
			writer.write("\n");

			for (int j = 0; j < colCount; j++) {
				setIndex(new CellIndex(i, j));

				writer.startElement("td", this);
				if (styleClass != null)
					writer.writeAttribute("class", styleClass, null);
				writer.write("\n");

				encodeChildrenCell(facesContext);

				writer.endElement("td");
				writer.write("\n");
			}

			writer.endElement("tr");
			writer.write("\n");
		}
	}

	/**
	 * @param facesContext
	 * @throws IOException
	 */
	private void encodeChildrenCell(FacesContext facesContext)
			throws IOException {
		if (getChildCount() == 0)
			return;
		for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
			UIComponent component = (UIComponent) iter.next();
			component.encodeBegin(facesContext);
			if (component.getRendersChildren()) {
				component.encodeChildren(facesContext);
			}
			component.encodeEnd(facesContext);
		}
	}

	/**
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	public void encodeEnd(FacesContext facesContext) throws IOException {
		super.encodeEnd(facesContext);
		ResponseWriter writer = facesContext.getResponseWriter();
		writer.endElement("table");
		writer.write("\n");
	}

	/**
	 * @return Returns the styleClass.
	 */
	public String getStyleClass() {
		if (styleClass != null)
			return styleClass;
		ValueBinding vb = getValueBinding("styleClass");
		return vb != null ? (String) vb.getValue(getFacesContext()) : null;
	}

	/**
	 * @param styleClass
	 *            The styleClass to set.
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
}
