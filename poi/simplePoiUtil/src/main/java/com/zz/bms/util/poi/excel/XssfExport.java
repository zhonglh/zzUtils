package com.zz.bms.util.poi.excel;

import com.zz.bms.util.poi.BaseXlsExport;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel2007 格式
 * 不适用大数据量
 * @author Administrator
 */
public class XssfExport extends BaseXlsExport {


    public XssfExport() {
        this.workbook = new XSSFWorkbook();
    }

}
