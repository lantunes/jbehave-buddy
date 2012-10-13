package org.bigtesting.jbehave.buddy.ui.widgets;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ExamplesTableModel implements TableModel {
	
	private DefaultTableModel delegate = new DefaultTableModel();

	private JTable table;
	
	public void setTable(JTable table) {
		this.table = table;
	}
	
	public void setData(String[][] rowData, String[] columnNames) {
		delegate = new DefaultTableModel(rowData, columnNames);
		table.setModel(delegate);
	}
	
	public void clear() {
		delegate.setRowCount(0);
	}
	
	@Override
	public void addTableModelListener(TableModelListener l) {
		delegate.addTableModelListener(l);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return delegate.getColumnClass(columnIndex);
	}

	@Override
	public int getColumnCount() {
		return delegate.getColumnCount();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return delegate.getColumnName(columnIndex);
	}

	@Override
	public int getRowCount() {
		return delegate.getRowCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return delegate.getValueAt(rowIndex, columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return delegate.isCellEditable(rowIndex, columnIndex);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		delegate.removeTableModelListener(l);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		delegate.setValueAt(aValue, rowIndex, rowIndex);
	}
	
}
