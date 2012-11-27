/*
 * UNC Charlotte ITCS 6162 Knowledge Discovery in Databases - KDD  Class, Final Project
 * 
 * by Yongkang Liu, 11/27/2012
 */
package kdd.ui;

import javax.swing.table.AbstractTableModel;

/**
 * The table model class for JTable.
 */
public class DataTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 3939466186911495692L;

    /**
     * The column names.
     */
    private String[] columnNames;

    /**
     * The table values.
     */
    private Object[][] data;

    /**
     * Constructor
     * 
     * @param col
     *            The number of JTable columns.
     * @param row
     *            The number of JTable rows.
     */
    public DataTableModel(int col, int row) {
        this.columnNames = new String[col];
        for (int i = 0; i < this.columnNames.length; i++) {
            this.columnNames[i] = "Col " + i;
        }
        this.data = new Object[row][col];

    }

    /**
     * Constructor
     * 
     * @param columnNames
     *            The JTable columns.
     * @param data
     *            The JTable cell values.
     */
    public DataTableModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    /**
     * Return total number of column.
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Return total number of rows.
     */
    public int getRowCount() {
        return this.data.length;
    }

    /**
     * Get the value of a cell
     * 
     * @param row
     *            The row number in the table.
     * @param col
     *            The column number in the table.
     * @return Return the cell value.
     */
    public Object getValueAt(int row, int col) {
        return this.data[row][col];
    }

    /**
     * Check if the cell is editable.
     * 
     * @param row
     *            The row number in the table.
     * @param col
     *            The column number in the table.
     * @return Return true if the cell is editable, otherwise return false.
     */
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /**
     * Get the column name.
     * 
     * @param col
     *            The column
     * @return Return the column name.
     */
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Set value at a cell.
     * 
     * @param value
     *            The new value
     * @param row
     *            The row number in the table.
     * @param col
     *            The column number in the table.
     */
    public void setValueAt(Object value, int row, int col) {
        this.data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
