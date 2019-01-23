package com.zz.bms.util.poi.excel;

import com.zz.bms.util.poi.ExcelExport;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CsvExport implements ExcelExport {

    @Override
    public void exportTitles(int headers, Object o, boolean isAddNumber) {

    }

    @Override
    public void exportXls(HttpServletResponse response) throws RuntimeException {

    }

    @Override
    public void exportXls(String xlsFileName) throws RuntimeException {

    }

    @Override
    public void exportContent(List contents, int rowIndex, List list, boolean isAddNumber) {

    }

    @Override
    public void exportHeaders(List headers) {

    }
}
