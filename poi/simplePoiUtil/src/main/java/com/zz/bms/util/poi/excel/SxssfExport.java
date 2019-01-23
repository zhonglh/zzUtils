package com.zz.bms.util.poi.excel;

import com.zz.bms.util.poi.BaseXlsExport;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * Excel2007 格式
 * 用于大数据量
 * @author Administrator
 */
public class SxssfExport<T> extends BaseXlsExport<T> {

    //内存中保留的行数，超出后会写到磁盘
    //应该是在窗口中的行数
    private static int rowAccessWindowSize = 1000;

    //每个sheet 10w条
    private static int maxSheetRows = 100000;

    public SxssfExport() {

        this.workbook = new SXSSFWorkbook(rowAccessWindowSize);
        //生成的临时文件将进行gzip压缩
        ((SXSSFWorkbook)workbook).setCompressTempFiles(true);
    }
}
