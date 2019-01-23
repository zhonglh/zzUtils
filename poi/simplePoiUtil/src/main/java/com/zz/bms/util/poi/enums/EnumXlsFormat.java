package com.zz.bms.util.poi.enums;

/**
 * @author Administrator
 */

public enum EnumXlsFormat {


    DATE("yyyy/MM/dd"),
    NUMBER("0.00"),
    DOUBLE("##0.00"),
    CURRENCY("#,##0.00"),
    PERCENT("0.00%");

    private final String pattern;

    EnumXlsFormat(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return this.pattern;
    }
}
