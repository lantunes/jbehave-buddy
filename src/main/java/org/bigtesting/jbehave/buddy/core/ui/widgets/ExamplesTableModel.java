package org.bigtesting.jbehave.buddy.core.ui.widgets;

import java.util.Arrays;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ExamplesTableModel implements TableModel {

    private DefaultTableModel delegate = new DefaultTableModel();

    public void setData(String[][] examples) {
        String[] columnNames = examples[0];
        String[][] rowData = Arrays.copyOfRange(examples, 1, examples.length);
        delegate.setDataVector(rowData, columnNames);
        delegate.fireTableRowsInserted(0, getRowCount()-1);
    }

    public String[][] getCurrentExamples() {

        String[][] examples = new String[getRowCount() + 1][getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++) {
            examples[0][i] = getColumnName(i);
        }
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                examples[i + 1][j] = (String) getValueAt(i, j);
            }
        }
        return examples;
    }

    public void addNewRow() {
        String[] row = new String[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++)
            row[i] = "";
        delegate.addRow(row);
    }

    public void removeRows(int[] rows) {
        Arrays.sort(rows);
        for (int i = rows.length - 1; i >= 0; i--)
            delegate.removeRow(rows[i]);
    }

    public void clear() {
        delegate.setRowCount(0);
    }

    public void addTableModelListener(TableModelListener l) {
        delegate.addTableModelListener(l);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return delegate.getColumnClass(columnIndex);
    }

    public int getColumnCount() {
        return delegate.getColumnCount();
    }

    public String getColumnName(int columnIndex) {
        return delegate.getColumnName(columnIndex);
    }

    public int getRowCount() {
        return delegate.getRowCount();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return delegate.getValueAt(rowIndex, columnIndex);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return delegate.isCellEditable(rowIndex, columnIndex);
    }

    public void removeTableModelListener(TableModelListener l) {
        delegate.removeTableModelListener(l);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        delegate.setValueAt(aValue, rowIndex, columnIndex);
    }

    public boolean hasRows() {
        return delegate.getRowCount() > 0;
    }
}
