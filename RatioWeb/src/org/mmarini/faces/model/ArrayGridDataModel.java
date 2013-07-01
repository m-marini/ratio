package org.mmarini.faces.model;

/**
 * @author $Author: marco $
 * @version $Id: ArrayGridDataModel.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class ArrayGridDataModel extends AbstractGridDataModel {
	private Object[][] data;

	/**
	 * 
	 *
	 */
	public ArrayGridDataModel() {}

	/**
	 * @param data
	 */
	public ArrayGridDataModel(Object[][] data) {
		setData(data);
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#getRowCount()
	 */
	public int getRowCount() {
		return getData().length;
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#getColCount()
	 */
	public int getColCount() {
		if (getRowCount() == 0)
			return 0;
		return getData()[0].length;
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#getCellData()
	 */
	public Object getCellData() {
		if (!isCellAvailable())
			return null;
		CellIndex index = getIndex();
		return getData()[index.getRowIndex()][index.getColIndex()];
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#getWrappedData()
	 */
	public Object getWrappedData() {
		return getData();
	}

	/**
	 * @see org.mmarini.faces.model.GridDataModel#setWrappedData(java.lang.Object)
	 */
	public void setWrappedData(Object data) {
		if (! (data instanceof Object[][])) {
			throw new IllegalArgumentException("It is not an Object[][]");
		}
		setData((Object[][]) data);
	}

	/**
	 * @return Returns the data.
	 */
	private Object[][] getData() {
		return data;
	}

	/**
	 * @param data
	 *            The data to set.
	 */
	private void setData(Object[][] data) {
		this.data = data;
		setIndex(null);
	}
}
