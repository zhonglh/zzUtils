package com.zz.bms.util.poi.excel;

import org.apache.poi.ss.usermodel.Workbook;
import com.zz.bms.util.poi.excel.html.ExcelToHtmlServer;

/**
 * Excel 变成界面
 * @author Administrator
 */
public final class ExcelToHtmlUtil {

    private ExcelToHtmlUtil() {
    }

    /**
     * 转换成为Table
     * @param wb Excel
     * @return
     */
    public static String toTableHtml(Workbook wb) {
        return new ExcelToHtmlServer(wb, false, 0).printPage();
    }

    /**
     * 转换成为Table
     * @param wb Excel
     * @param sheetNum sheetNum
     * @return
     */
    public static String toTableHtml(Workbook wb, int sheetNum) {
        return new ExcelToHtmlServer(wb, false, sheetNum).printPage();
    }

    /**
     * 转换成为完整界面
     * @param wb Excel
     * @return
     */
    public static String toAllHtml(Workbook wb) {
        return new ExcelToHtmlServer(wb, true, 0).printPage();
    }

    /**
     * 转换成为完整界面
     * @param wb Excel
     * @param sheetNum sheetNum
     * @return
     */
    public static String toAllHtml(Workbook wb, int sheetNum) {
        return new ExcelToHtmlServer(wb, true, sheetNum).printPage();
    }

}
