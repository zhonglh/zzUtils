package com.zz.bms.util.poi;

import com.zz.bms.util.poi.enums.EnumXlsFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
public abstract class AbstractXlsStyle {

    private static Map<String,CellStyle> styleMap = new ConcurrentHashMap<String,CellStyle>();
    private static CellStyle commonTitleStyle = null;
    private static CellStyle commonStyle = null;
    private static Font font = null;




    protected CellStyle getCellStyle(short alignment, boolean boldweight) {

        String key = String.valueOf(alignment) + "=" + String.valueOf(boldweight);
        CellStyle style = styleMap.get(key);
        if(style != null){
            return style;
        }else {
            CellStyle cellStyle = getWorkbook().createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);
            if (boldweight) {
                Font font = getFont();
                //选择需要用到的字体格式
                cellStyle.setFont(font);
            }

            cellStyle.setAlignment(alignment);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            setBorder(cellStyle, HSSFColor.BLACK.index, CellStyle.BORDER_THIN);
            styleMap.put(key ,cellStyle );
            return cellStyle;
        }
    }


    protected CellStyle getCellStyle(short alignment, boolean boldweight, EnumXlsFormat formatEm) {


        String key = String.valueOf(alignment) + "=" + String.valueOf(boldweight) + "=" + formatEm.name();


        CellStyle style = styleMap.get(key);
        if(style != null){
            return style;
        }else {

            CellStyle cellStyle = getWorkbook().createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);

            if (boldweight) {
                cellStyle.setFont(getFont());
            }

            cellStyle.setAlignment(alignment);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


            DataFormat format = getWorkbook().createDataFormat();
            cellStyle.setDataFormat(format.getFormat(formatEm.getPattern()));

            setBorder(cellStyle, HSSFColor.BLACK.index, CellStyle.BORDER_THIN);

            styleMap.put(key ,cellStyle );

            return cellStyle;
        }
    }




    protected CellStyle commonTitleStyle() {
        if(commonTitleStyle == null) {
            CellStyle cellStyle = getWorkbook().createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);

            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            setBorder(cellStyle, HSSFColor.BLACK.index, CellStyle.BORDER_THIN);

            cellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
            cellStyle.setFont(getFont());
            commonTitleStyle = cellStyle;
            return cellStyle;
        }else {
            return commonTitleStyle;
        }

    }




    protected CellStyle commonStyle() {
        if(commonStyle == null) {
            CellStyle cellStyle = getWorkbook().createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);

            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            setBorder(cellStyle, HSSFColor.BLACK.index, CellStyle.BORDER_THIN);
            commonStyle = cellStyle;
            return cellStyle;
        }else {
            return commonStyle;
        }

    }

    protected void setBorder(CellStyle style, short color, short borderType) {

        style.setBorderBottom(borderType);
        style.setBorderLeft(borderType);
        style.setBorderTop(borderType);
        style.setBorderRight(borderType);

        style.setRightBorderColor(color);
        style.setTopBorderColor(color);
        style.setLeftBorderColor(color);
        style.setBottomBorderColor(color);
    }

    protected Font getFont() {
        if(font != null){
            return font;
        }else {
            Font newFont = getWorkbook().createFont();
            //粗体显示
            newFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            font = newFont ;
            return newFont;
        }
    }


    public abstract Workbook getWorkbook();
}
