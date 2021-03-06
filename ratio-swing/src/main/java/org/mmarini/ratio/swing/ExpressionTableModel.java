/**
 * 
 */
package org.mmarini.ratio.swing;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author US00852
 * 
 */
public class ExpressionTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 594306141901592641L;
	private static final String[] COLUMN_NAME = {
			Messages.getString("ExpressionTableModel.id.text"), Messages.getString("ExpressionTableModel.expression.text"), Messages.getString("ExpressionTableModel.value.text") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	private List<ExpressionDef> expressions;

	/**
	 * 
	 */
	public ExpressionTableModel() {
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 3;
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int column) {
		return COLUMN_NAME[column];
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return expressions != null ? expressions.size() : 0;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final ExpressionDef e = expressions.get(rowIndex);
		final Object v;
		switch (columnIndex) {
		case 0:
			v = e.getId();
			break;
		case 1:
			v = e.getExpression();
			break;
		case 2:
			v = e.getValue();
			break;
		default:
			v = "?"; //$NON-NLS-1$
			break;
		}
		return v;
	}

	/**
	 * @param expressions
	 *            the expressions to set
	 */
	public void setExpressions(final List<ExpressionDef> expressions) {
		this.expressions = expressions;
		fireTableDataChanged();
	}

}
