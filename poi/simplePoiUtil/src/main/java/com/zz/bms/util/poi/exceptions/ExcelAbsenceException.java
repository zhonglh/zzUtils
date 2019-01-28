package com.zz.bms.util.poi.exceptions;

/**
 * 数据缺少异常
 * @author Administrator
 */
public class ExcelAbsenceException extends RuntimeException {

    int rowIndex = 0 ;

    public ExcelAbsenceException() {
    }
    public ExcelAbsenceException(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

}
