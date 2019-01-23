package com.zz.bms.util.poi;

import com.zz.bms.util.poi.cell.CellOperation;
import com.zz.bms.util.poi.enums.EnumXlsFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
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


    private static CellStyle headerCellStyle1 = null;
    private static CellStyle headerCellStyle2 = null;




    /**
     * 用于处理特殊的样式处理 (处理工资表， 表头是从Excel文件读取出来的)
     * 内容和标题的样式一样 , 颜色 背景色等和标题一样， 对齐方式和格式化不一样
     * @param cellStyle
     * @param index
     * @param clz
     * @param val
     * @param operationModel
     * @param <T>
     * @return
     */
    public <T> CellStyle firstCommonStyle(CellStyle cellStyle ,int index, Class<T> clz ,  Object val , String operationModel){

        String key = "firstStyle=" + clz.getName() + "=" + index;

        CellStyle firstCommonStyle = styleMap.get(key);

        if(firstCommonStyle != null) {

            EnumXlsFormat formatEm = null;
            short alignment = 0;
            if (operationModel.equals(CellOperation.OPERATION_MODEL_DEFAULT)) {
                if (val instanceof Date || val instanceof Timestamp) {
                    formatEm = EnumXlsFormat.DATE;
                    alignment = CellStyle.ALIGN_CENTER;
                } else if (val instanceof Integer || val instanceof Long) {
                    formatEm = EnumXlsFormat.DIGITS;
                    alignment = CellStyle.ALIGN_RIGHT;
                } else if (val instanceof Float || val instanceof Double || val instanceof BigDecimal) {
                    formatEm = EnumXlsFormat.CURRENCY;
                    alignment = CellStyle.ALIGN_RIGHT;
                } else {
                    formatEm = null;
                    alignment = CellStyle.ALIGN_LEFT;
                }
            } else {
                if (val instanceof Date || val instanceof Timestamp) {
                    formatEm = null;
                    alignment = CellStyle.ALIGN_CENTER;
                } else if (val instanceof Integer || val instanceof Long) {
                    formatEm = null;
                    alignment = CellStyle.ALIGN_RIGHT;
                } else if (val instanceof Float || val instanceof Double || val instanceof BigDecimal) {
                    formatEm = EnumXlsFormat.NUMBER;
                    alignment = CellStyle.ALIGN_RIGHT;
                } else {
                    formatEm = null;
                    alignment = CellStyle.ALIGN_LEFT;
                }
            }

            //todo , 如果有问题，  需要做深拷贝
            CellStyle newCellStyle = cellStyle;
            if (formatEm != null) {
                DataFormat format = getWorkbook().createDataFormat();
                newCellStyle.setDataFormat(format.getFormat(formatEm.getPattern()));
                newCellStyle.setAlignment(alignment);
            }

            firstCommonStyle = newCellStyle;
            return newCellStyle;
        }else {
            return firstCommonStyle;
        }
    }


    public CellStyle getHeaderCellStyle1(){

        if(headerCellStyle1 == null) {

            CellStyle cellStyle = getWorkbook().createCellStyle();

            // 设置背景色
            short index = 0x9;
            cellStyle.setFillForegroundColor(index);

            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            //下边框
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            //左边框
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            //上边框
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            //右边框
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);

            //居中
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

            //居中
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            Font font = getHeaderFont1();
            cellStyle.setFont(font);


            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);

            headerCellStyle1 = cellStyle;
            return cellStyle;
        }else {
            return headerCellStyle1;
        }
    }



    public CellStyle getHeaderCellStyle2(){

        if(headerCellStyle2 == null) {


            CellStyle cellStyle = getWorkbook().createCellStyle();

            //设置背景色
            short index = 0x9;
            cellStyle.setFillForegroundColor(index);


            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


            //下边框
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            //左边框
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            //上边框
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            //右边框
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            // 居中
            cellStyle.setAlignment(CellStyle.ALIGN_LEFT);


            Font font = getHeaderFont2();
            cellStyle.setFont(font);

            headerCellStyle2 = cellStyle;
            return cellStyle;
        }else {
            return headerCellStyle2;
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


        String key = String.valueOf(alignment) + "=" + String.valueOf(boldweight) + "=" + (formatEm==null?"":formatEm.name());


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

            if(formatEm != null) {
                DataFormat format = getWorkbook().createDataFormat();
                cellStyle.setDataFormat(format.getFormat(formatEm.getPattern()));
            }


            setBorder(cellStyle, HSSFColor.BLACK.index, CellStyle.BORDER_THIN);

            styleMap.put(key ,cellStyle );

            return cellStyle;
        }
    }




    protected CellStyle commonStyle(Object val , String operationModel){

        if(val == null){
            return getCellStyle(CellStyle.ALIGN_LEFT, false, null);
        }


        EnumXlsFormat formatEm = null;
        short alignment = 0;
        if(operationModel.equals(CellOperation.OPERATION_MODEL_DEFAULT)){
            if(val instanceof Date || val instanceof Timestamp){
                formatEm = EnumXlsFormat.DATE;
                alignment = CellStyle.ALIGN_CENTER;
            }else if(val instanceof Integer || val instanceof Long){
                formatEm = EnumXlsFormat.DIGITS;
                alignment = CellStyle.ALIGN_RIGHT;
            }else if(val instanceof Float || val instanceof Double || val instanceof BigDecimal){
                formatEm = EnumXlsFormat.CURRENCY;
                alignment = CellStyle.ALIGN_RIGHT;
            }else{
                formatEm = null;
                alignment = CellStyle.ALIGN_LEFT;
            }
        }else {
            if(val instanceof Date || val instanceof Timestamp){
                formatEm = null;
                alignment = CellStyle.ALIGN_CENTER;
            }else if(val instanceof Integer || val instanceof Long){
                formatEm = null;
                alignment = CellStyle.ALIGN_RIGHT;
            }else if(val instanceof Float || val instanceof Double || val instanceof BigDecimal){
                formatEm = EnumXlsFormat.NUMBER;
                alignment = CellStyle.ALIGN_RIGHT;
            }else{
                formatEm = null;
                alignment = CellStyle.ALIGN_LEFT;
            }
        }

        return getCellStyle(alignment, false, formatEm);

    }




    protected CellStyle commonStyle(EnumXlsFormat formatEm) {

        String key = "cs="+ (formatEm == null ? "" : formatEm.name());
        CellStyle cs = styleMap.get(key);
        if(cs == null) {
            CellStyle cellStyle = getWorkbook().createCellStyle();
            cellStyle.setBorderBottom((short) 1);
            cellStyle.setBorderTop((short) 1);
            cellStyle.setBorderLeft((short) 1);
            cellStyle.setBorderRight((short) 1);

            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            if(formatEm != null && formatEm != EnumXlsFormat.DATE){
                cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
            }
            cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            setBorder(cellStyle, HSSFColor.BLACK.index, CellStyle.BORDER_THIN);
            cs = cellStyle;
            return cellStyle;
        }else {
            return cs;
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
            newFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font = newFont ;
            return newFont;
        }
    }


    private Font getHeaderFont1() {
        Font font = getWorkbook().createFont();
        font.setFontName("黑体");
        //设置字体大小
        font.setFontHeightInPoints((short) 16);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        return font;


    }


    private Font getHeaderFont2() {
        Font font = getWorkbook().createFont();
        font.setFontName("黑体");
        //设置字体大小
        font.setFontHeightInPoints((short) 10);
        return font;
    }



    public abstract Workbook getWorkbook();
}
