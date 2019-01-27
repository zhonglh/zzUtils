package com.zz.bms.util.poi.excel;

import com.zz.bms.util.poi.ExcelExport;
import com.zz.bms.util.poi.enums.EnumExcelFileType;
import com.zz.bms.util.poi.vo.Column;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * CSV 格式导出
 * @author Administrator
 */
public class CsvExport<T> implements ExcelExport<T> {

    @Override
    public void exportTitles(int headers, T t, boolean isAddNumber) {

    }

    @Override
    public void exportTitles(int headers, T t, List<Column> columns, boolean isAddNumber) {

    }

    @Override
    public void exportHeaders(List<String> headers , int maxCellLength) {
    }

    @Override
    public void exportContent(List<T> contents, int rowIndex, boolean isAddNumber) {

    }

    @Override
    public void exportContent(List<T> contents, int rowIndex, List<Column> columns, boolean isAddNumber) {

    }

    @Override
    public void exportXls(HttpServletResponse response ) throws RuntimeException {

    }


    @Override
    public void exportXls(HttpServletResponse response , String fileName ) throws RuntimeException {

    }

    @Override
    public void exportXls(String xlsFileName) throws RuntimeException {

    }
}
