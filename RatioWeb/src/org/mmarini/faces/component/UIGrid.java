package org.mmarini.faces.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.faces.model.AbstractGridDataModel;
import org.mmarini.faces.model.ArrayGridDataModel;
import org.mmarini.faces.model.CellIndex;
import org.mmarini.faces.model.GridDataModel;
import org.mmarini.faces.model.ScalarGridDataModel;

/**
 * @author $Author: marco $
 * @version $Id: UIGrid.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class UIGrid extends UINamingContainer {
	private static final String COMPONENT_FAMILY = "org.mmarini.faces.Grid";
	private static final GridDataModel EMPTY_DATA_MODEL = new AbstractGridDataModel() {
		/**
		 * @see org.mmarini.faces.model.GridDataModel#getRowCount()
		 */
		public int getRowCount() {
			return 0;
		}

		/**
		 * @see org.mmarini.faces.model.GridDataModel#getColCount()
		 */
		public int getColCount() {
			return 0;
		}

		/**
		 * @see org.mmarini.faces.model.GridDataModel#getCellData()
		 */
		public Object getCellData() {
			return null;
		}

		/**
		 * @see org.mmarini.faces.model.GridDataModel#getWrappedData()
		 */
		public Object getWrappedData() {
			return null;
		}

		/**
		 * @see org.mmarini.faces.model.GridDataModel#setWrappedData(java.lang.Object)
		 */
		public void setWrappedData(Object data) {}

	};
	private static Log log = LogFactory.getLog(UIGrid.class);
	private Map dataModelMap = new HashMap();
	private Map cellState = new HashMap();
	private Object value;
	private String rowIndexVar;
	private String colIndexVar;
	private Object initialDescendantState;

	/**
	 * 
	 */
	public UIGrid() {
		super();
		log.debug("UIGrid created");
	}

	/**
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		return true;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext arg0) throws IOException {
		setIndex(null);
	}

	/**
	 * @see javax.faces.component.UIComponentBase#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext arg0) throws IOException {
		setIndex(null);
	}

	/**
	 * @see javax.faces.component.UIComponent#processValidators(javax.faces.context.FacesContext)
	 */
	public void processValidators(FacesContext context) {
		if (context == null)
			throw new NullPointerException("context");
		if (!isRendered())
			return;
		setIndex(null);
		processCellChildren(context, new ProcessValidatorsCommand(context));
		setIndex(null);
	}

	/**
	 * @see javax.faces.component.UIComponent#processUpdates(javax.faces.context.FacesContext)
	 */
	public void processUpdates(FacesContext context) {
		if (context == null)
			throw new NullPointerException("context");
		if (!isRendered())
			return;
		setIndex(null);
		processCellChildren(context, new ProcessUpdatesCommand(context));
		setIndex(null);
	}

	/**
	 * @see javax.faces.component.UIComponent#processDecodes(javax.faces.context.FacesContext)
	 */
	public void processDecodes(FacesContext context) {
		if (context == null)
			throw new NullPointerException("context");
		if (!isRendered())
			return;
		setIndex(null);
		processCellChildren(context, new ProcessDecodesCommand(context));
		setIndex(null);
		try {
			decode(context);
		} catch (RuntimeException e) {
			context.renderResponse();
			throw e;
		}
	}

	/**
	 * @param context
	 */
	protected void processCellChildren(FacesContext context,
			AbstractComponentCommand command) {
		int rowCount = getRowCount();
		int colCount = getColCount();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				setIndex(new CellIndex(i, j));
				if (isCellAvailable()) {
					for (Iterator it = getChildren().iterator(); it.hasNext();) {
						UIComponent child = (UIComponent) it.next();
						command.setComponent(child);
						command.execute();
					}
				}
			}
		}
	}

	/**
	 * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context) {
		Object[] values = new Object[4];
		values[0] = super.saveState(context);
		values[1] = getValue();
		values[2] = getRowIndexVar();
		values[3] = getColIndexVar();
		return values;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object state) {
		Object[] values = (Object[]) state;
		super.restoreState(context, values[0]);
		this.value = values[1];
		this.rowIndexVar = (String) values[2];
		this.colIndexVar = (String) values[3];
	}

	/**
	 * @return
	 */
	protected GridDataModel getDataModel() {
		GridDataModel dataModel = null;
		String clientID = "";

		UIComponent parent = getParent();
		if (parent != null) {
			clientID = parent.getClientId(getFacesContext());
		}
		Map dataModelMap = getDataModelMap();
		dataModel = (GridDataModel) dataModelMap.get(clientID);
		if (dataModel == null) {
			dataModel = createDataModel();
			dataModelMap.put(clientID, dataModel);
		}
		return dataModel;
	}

	/**
	 * @return
	 */
	protected CellIndex getIndex() {
		return getDataModel().getIndex();
	}

	/**
	 * @param index
	 */
	protected void setIndex(CellIndex index) {
		if (index == null) {
			if (getIndex() == null)
				return;
		} else if (index.equals(getIndex()))
			return;

		saveDescendantComponentStates();
		getDataModel().setIndex(index);
		updateRequestVars();
		restoreDescendantComponentStates();
	}

	/**
	 * Sets the index request variables
	 */
	private void updateRequestVars() {
		FacesContext ctx = getFacesContext();
		String colIndexVar = getColIndexVar();
		String rowIndexVar = getRowIndexVar();
		Map requestMap = ctx.getExternalContext().getRequestMap();
		CellIndex index = getIndex();
		if (index == null) {
			log.debug("removing request var \"" + colIndexVar + "\"");
			requestMap.remove(colIndexVar);
			log.debug("removing request var \"" + rowIndexVar + "\"");
			requestMap.remove(rowIndexVar);
		} else {
			log.debug("putting request var \"" + colIndexVar + "\"="
					+ index.getColIndex());
			requestMap.put(colIndexVar, new Integer(index.getColIndex()));
			log.debug("putting request var \"" + rowIndexVar + "\"="
					+ index.getRowIndex());
			requestMap.put(rowIndexVar, new Integer(index.getRowIndex()));
		}
	}

	/**
	 * 
	 *
	 */
	protected void saveDescendantComponentStates() {
		CellIndex index = getIndex();
		if (index == null) {
			if (getInitialDescendantState() == null) {
				Object state = saveDescendantComponentStates(getChildren()
						.iterator(), false);
				setInitialDescendantState(state);
			}
			return;
		}
		Object[][] gridState = getGridState();
		Object cellState = saveDescendantComponentStates(getChildren()
				.iterator(), false);
		gridState[index.getRowIndex()][index.getColIndex()] = cellState;
	}

	/**
	 * @return
	 */
	private Object[][] getGridState() {
		String clientId = getParent().getClientId(getFacesContext());
		Map cellStateMap = getCellState();
		Object[][] gridState = (Object[][]) cellStateMap.get(clientId);
		if (gridState == null) {
			gridState = new Object[getRowCount()][getColCount()];
			cellStateMap.put(clientId, gridState);
		}
		return gridState;
	}

	/**
	 * @param iter
	 * @param saveChildFacets
	 * @return
	 */
	protected Object saveDescendantComponentStates(Iterator iter,
			boolean saveChildFacets) {
		Collection childStates = new ArrayList();
		while (iter.hasNext()) {
			UIComponent child = (UIComponent) iter.next();
			if (!child.isTransient()) {
				Iterator childsIterator;
				if (saveChildFacets) {
					childsIterator = child.getFacetsAndChildren();
				} else {
					childsIterator = child.getChildren().iterator();
				}
				Object descendantState = saveDescendantComponentStates(
						childsIterator, true);
				Object state = null;
				if (child instanceof EditableValueHolder) {
					state = new EditableValueHolderState(
							(EditableValueHolder) child);
				}
				childStates.add(new Object[] {state, descendantState});
			}
		}
		return childStates;
	}

	/**
	 * 
	 *
	 */
	private void restoreDescendantComponentStates() {
		Object[][] gridState = getGridState();
		CellIndex index = getIndex();
		Object state = null;
		if (index != null)
			state = gridState[index.getRowIndex()][index.getColIndex()];
		if (state == null)
			state = getInitialDescendantState();
		restoreDescendantComponentStates(getChildren().iterator(), state, false);
	}

	/**
	 * @param iter
	 * @param state
	 * @param restoreChildFacets
	 */
	private void restoreDescendantComponentStates(Iterator iter, Object state,
			boolean restoreChildFacets) {
		Iterator descendantStateIterator = null;
		while (iter.hasNext()) {
			if (descendantStateIterator == null && state != null) {
				descendantStateIterator = ((Collection) state).iterator();
			}
			UIComponent component = (UIComponent) iter.next();
			// reset the client id (see spec 3.1.6)
			component.setId(component.getId());
			if (!component.isTransient()) {
				Object childState = null;
				Object descendantState = null;
				if (descendantStateIterator != null
						&& descendantStateIterator.hasNext()) {
					Object[] object = (Object[]) descendantStateIterator.next();
					childState = object[0];
					descendantState = object[1];
				}
				if (component instanceof EditableValueHolder) {
					((EditableValueHolderState) childState)
							.restoreState((EditableValueHolder) component);
				}
				Iterator childsIterator;
				if (restoreChildFacets) {
					childsIterator = component.getFacetsAndChildren();
				} else {
					childsIterator = component.getChildren().iterator();
				}
				restoreDescendantComponentStates(childsIterator,
						descendantState, true);
			}
		}
	}

	/**
	 * @see javax.faces.component.UIComponent#setValueBinding(java.lang.String,
	 *      javax.faces.el.ValueBinding)
	 */
	public void setValueBinding(String name, ValueBinding binding) {
		if (name == null) {
			throw new NullPointerException("name");
		} else if (name.equals("value")) {
			getDataModelMap().clear();
		}
		super.setValueBinding(name, binding);
	}

	/**
	 * @return
	 */
	protected GridDataModel createDataModel() {
		Object value = getValue();
		if (value == null) {
			return EMPTY_DATA_MODEL;
		} else if (value instanceof GridDataModel) {
			return (GridDataModel) value;
		} else if (value instanceof Object[][]) {
			return new ArrayGridDataModel((Object[][]) value);
		} else {
			return new ScalarGridDataModel(value);
		}
	}

	/**
	 * @see javax.faces.component.UIData#getClientId(javax.faces.context.FacesContext)
	 */
	public String getClientId(FacesContext context) {
		String clientId = super.getClientId(context);
		CellIndex index = getIndex();
		if (index == null)
			return clientId;
		return clientId + "_" + index.getRowIndex() + "_" + index.getColIndex();
	}

	/**
	 * @return
	 */
	public int getRowCount() {
		return getDataModel().getRowCount();
	}

	/**
	 * @return Returns the colCount.
	 */
	public int getColCount() {
		return getDataModel().getColCount();
	}

	/**
	 * @return
	 */
	public Object getCellData() {
		return getDataModel().getCellData();
	}

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	/**
	 * @return Returns the dataModelMap.
	 */
	private Map getDataModelMap() {
		return dataModelMap;
	}

	/**
	 * @return Returns the value.
	 */
	public Object getValue() {
		if (value != null)
			return value;
		ValueBinding vb = getValueBinding("value");
		return vb != null ? (Object) vb.getValue(getFacesContext()) : null;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(Object value) {
		this.value = value;
		getDataModelMap().clear();
		getCellState().clear();
	}

	/**
	 * @return
	 */
	public boolean isCellAvailable() {
		return getDataModel().isCellAvailable();
	}

	/**
	 * @see javax.faces.component.UIComponentBase#decode(javax.faces.context.FacesContext)
	 */
	public void decode(FacesContext ctx) {
		if (ctx == null)
			throw new NullPointerException("context");
		Renderer renderer = getRenderer(ctx);
		if (renderer != null) {
			renderer.decode(ctx, this);
		}
	}

	/**
	 * @return Returns the initialDesendantState.
	 */
	private Object getInitialDescendantState() {
		return initialDescendantState;
	}

	/**
	 * @param initialDesendantState
	 *            The initialDesendantState to set.
	 */
	private void setInitialDescendantState(Object initialDesendantState) {
		this.initialDescendantState = initialDesendantState;
	}

	/**
	 * @return Returns the colIndexVar.
	 */
	public String getColIndexVar() {
		return colIndexVar;
	}

	/**
	 * @return Returns the rowIndexVar.
	 */
	public String getRowIndexVar() {
		return rowIndexVar;
	}

	/**
	 * @param colIndexVar
	 *            The colIndexVar to set.
	 */
	public void setColIndexVar(String colIndexVar) {
		this.colIndexVar = colIndexVar;
	}

	/**
	 * @param rowIndexVar
	 *            The rowIndexVar to set.
	 */
	public void setRowIndexVar(String rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	/**
	 * @return Returns the cellState.
	 */
	private Map getCellState() {
		return cellState;
	}
}
