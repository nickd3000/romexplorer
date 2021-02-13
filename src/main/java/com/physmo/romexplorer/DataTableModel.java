package com.physmo.romexplorer;

import javax.swing.table.AbstractTableModel;

// https://docs.oracle.com/javase/tutorial/uiswing/components/table.html

@SuppressWarnings("serial")
public class DataTableModel extends AbstractTableModel {

    DataFile dataFile = null;
    String[] columnNames = {"Location", "HEX", "Character"};

    int bytesPerRow = 8;

    public DataTableModel(DataFile dataFile, int bytesPerRow) {
        this.dataFile = dataFile;
        this.bytesPerRow = bytesPerRow;
    }

    public String getColumnName(int id) {
        return columnNames[id];
    }

    @Override
    public int getRowCount() {
        return dataFile.size() / bytesPerRow;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (columnIndex == 0) {
            String lineNumber = String.format("%08d", rowIndex * bytesPerRow);
            return lineNumber;
        }

        if (columnIndex == 1) {
            String hexData = "";
            for (int i = 0; i < bytesPerRow; i++) {
                hexData += " " + String.format("%02X", dataFile.getValue(i + (rowIndex * bytesPerRow)));
            }
            return hexData;
        }

        if (columnIndex == 2) {
            String charData = "";
            for (int i = 0; i < bytesPerRow; i++) {
                int value = dataFile.getValue(i + (rowIndex * bytesPerRow));
                charData += " " + String.format("%c", processCharacter(value));
            }
            return charData;
        }

        return "dataaaaa";

    }


    public char processCharacter(int v) {
        if (v < 32) return '.';
        if (v > 126) return '.';
        return (char) v;
    }

}
