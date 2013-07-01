package org.mmarini.faces.model;

/**
 * @author $Author: marco $
 * @version $Id: GridDataModel.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public interface GridDataModel {
	/**
	 * @param listener
	 */
	public abstract void addGridDataModelListener(GridDataModelListener listener);

	/**
	 * @param listener
	 */
	public abstract void removeGridDataModelListener(
			GridDataModelListener listener);

	/**
	 * @return
	 */
	public abstract int getRowCount();

	/**
	 * @return
	 */
	public abstract int getColCount();

	/**
	 * @return
	 */
	public abstract CellIndex getIndex();

	/**
	 * @param colIndex
	 */
	public abstract void setIndex(CellIndex colIndex);

	/**
	 * @return
	 */
	public abstract Object getCellData();

	/**
	 * @return
	 */
	public abstract Object getWrappedData();

	/**
	 * @return
	 */
	public abstract boolean isCellAvailable();

	/**
	 * @param data
	 */
	public abstract void setWrappedData(Object data);
}
