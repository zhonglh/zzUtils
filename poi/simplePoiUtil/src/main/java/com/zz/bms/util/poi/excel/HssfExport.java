package com.zz.bms.util.poi.excel;

import com.zz.bms.util.poi.BaseXlsExport;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel 2003 格式
 * 不适用于大数据量
 * @author Administrator
 */
public class HssfExport<T> extends BaseXlsExport<T> {

    public HssfExport() {
        this.workbook = new HSSFWorkbook();
    }
}
