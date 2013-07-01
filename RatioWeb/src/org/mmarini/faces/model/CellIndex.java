package org.mmarini.faces.model;

import java.io.Serializable;

/**
 * @author $Author: marco $
 * @version $Id: CellIndex.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class CellIndex implements Serializable {
	private static final long serialVersionUID = 1L;
	private int rowIndex;
	private int colIndex;

	/**
	 * @param rowIndex
	 * @param colIndex
	 */
	public CellIndex(int rowIndex, int colIndex) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
	}

	/**
	 * @param index
	 */
	public CellIndex(CellIndex index) {
		this(index.getRowIndex(), index.getColIndex());
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof CellIndex))
			return false;
		if (getRowIndex() != ((CellIndex) obj).getRowIndex())
			return false;
		if (getColIndex() != ((CellIndex) obj).getColIndex())
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getColIndex() + 31 * getRowIndex();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer bfr = new StringBuffer();
		bfr.append(getClass().getName());
		bfr.append("(");
		bfr.append(getRowIndex());
		bfr.append(",");
		bfr.append(getColIndex());
		bfr.append(")");
		return bfr.toString();
	}

	/**
	 * @return Returns the colIndex.
	 */
	public int getColIndex() {
		return colIndex;
	}

	/**
	 * @return Returns the rowIndex.
	 */
	public int getRowIndex() {
		return rowIndex;
	}
}
