package com.zz.bms.util.poi.excel;

import com.zz.bms.util.poi.ExcelExport;
import com.zz.bms.util.poi.vo.Column;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class XmlExport<T> implements ExcelExport<T> {


    @Override
    public void exportTitles(int headers, T t, boolean isAddNumber) {

    }

    @Override
    public void exportTitles(int headers, T t, List<Column> columns, boolean isAddNumber) {

    }

    @Override
    public void exportHeaders(List<String> headers) {

    }

    @Override
    public void exportContent(List<T> contents, int rowIndex, boolean isAddNumber) {

    }

    @Override
    public void exportContent(List<T> contents, int rowIndex, List<Column> columns, boolean isAddNumber) {

    }

    @Override
    public void exportXls(HttpServletResponse response) throws RuntimeException {

    }

    @Override
    public void exportXls(String xlsFileName) throws RuntimeException {

    }
}
