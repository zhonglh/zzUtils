package com.zz.bms.util.poi.enums;

/**
 * Excel 文件类型
 * @author Administrator
 */

public enum EnumExcelFileType {

    XML("xml" , "xls"),
    HSSF("hssf" , "xls"),
    XSSF("xssf" , "xlsx"),
    SXSSF("sxssf" , "xlsx"),
    CSV("csv" , "csv"),

    ;



    private String code;
    private String fileType;

    EnumExcelFileType(String code, String fileType) {
        this.code = code;
        this.fileType = fileType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
