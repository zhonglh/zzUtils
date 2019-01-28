package com.zz.bms.util.poi.imports;

import com.zz.bms.util.configs.AppConfig;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface ExcelImport {


    /**
     * 读取Excel第一个Sheet的数据
     * @return
     */
    public List<Object[]> readExcel() ;






    /**
     * 读取Excel所有Sheet 的数据
     * @return
     */
    public List<List<Object[]>> readExcelAllSheet() ;
}
