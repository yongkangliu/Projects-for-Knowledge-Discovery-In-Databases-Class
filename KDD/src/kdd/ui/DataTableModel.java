package kdd.ui;

import javax.swing.table.AbstractTableModel;

public class DataTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 3939466186911495692L;

    private String[] columnNames;
    private Object[][] data;

    public DataTableModel(int numOfIntervals, int numOfUnits) {
        this.columnNames = new String[numOfIntervals];
        for (int i = 0; i < this.columnNames.length; i++) {
            this.columnNames[i] = "Col " + i;
        }
        this.data = new Object[numOfUnits][numOfIntervals];

    }

    public DataTableModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public void setValueAt(Object value, int row, int col) {
        this.data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
