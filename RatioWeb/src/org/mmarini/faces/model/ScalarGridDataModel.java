package org.mmarini.faces.model;

/**
 * @author $Author: marco $
 * @version $Id: ScalarGridDataModel.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class ScalarGridDataModel extends AbstractGridDataModel {
	private Object wrappedData;

	/**
	 * 
	 */
	public ScalarGridDataModel() {
		super();
	}

	/**
	 * @param data
	 */
	public ScalarGridDataModel(Object data) {
		wrappedData = data;
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#getRowCount()
	 */
	public int getRowCount() {
		return 1;
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#getColCount()
	 */
	public int getColCount() {
		return 1;
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#getCellData()
	 */
	public Object getCellData() {
		if (!isCellAvailable())
			return null;
		return getWrappedData();
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#getWrappedData()
	 */
	public Object getWrappedData() {
		return wrappedData;
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#setWrappedData(java.lang.Object)
	 */
	public void setWrappedData(Object data) {
		wrappedData = data;
	}
}
