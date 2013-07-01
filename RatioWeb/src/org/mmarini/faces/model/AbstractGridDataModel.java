package org.mmarini.faces.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author $Author: marco $
 * @version $Id: AbstractGridDataModel.java,v 1.1.2.1 2006/02/14 18:15:47 marco
 *          Exp $
 */
public abstract class AbstractGridDataModel implements GridDataModel {
	private List listener;
	private CellIndex index;

	/**
	 * @see org.mmarini.faces.model.GridDataModel#addGridDataModelListener(org.mmarini.faces.model.GridDataModelListener)
	 */
	public void addGridDataModelListener(GridDataModelListener listener) {
		if (this.listener == null)
			this.listener = new ArrayList();
		this.listener.add(listener);
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#removeGridDataModelListener(org.mmarini.faces.model.GridDataModelListener)
	 */
	public void removeGridDataModelListener(GridDataModelListener listener) {
		if (this.listener != null)
			this.listener.remove(listener);
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#isCellAvailable()
	 */
	public boolean isCellAvailable() {
		CellIndex index = getIndex();
		if (index == null)
			return false;
		int i = index.getRowIndex();
		if (i < 0 || i >= getRowCount())
			return false;
		i = index.getColIndex();
		if (i < 0 || i >= getColCount())
			return false;
		return true;
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#getIndex()
	 */
	public CellIndex getIndex() {
		return index;
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#setIndex(org.mmarini.faces.model.CellIndex)
	 */
	public void setIndex(CellIndex cellIndex) {
		this.index = cellIndex;
	}
}
