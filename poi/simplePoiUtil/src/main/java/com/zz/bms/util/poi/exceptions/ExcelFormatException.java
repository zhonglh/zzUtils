package com.zz.bms.util.poi.exceptions;


/**
 * Excel 格式错误
 * @author Administrator
 */
public class ExcelFormatException extends RuntimeException {
    int rowIndex = 0 ;
    int cellIndex = 0;

    public ExcelFormatException(int rowIndex, int cellIndex) {
        this.rowIndex = rowIndex;
        this.cellIndex = cellIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getCellIndex() {
        return cellIndex;
    }

    public void setCellIndex(int cellIndex) {
        this.cellIndex = cellIndex;
    }
}
