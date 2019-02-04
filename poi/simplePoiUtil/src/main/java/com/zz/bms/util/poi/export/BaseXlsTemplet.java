package com.zz.bms.util.poi.export;

import com.zz.bms.util.configs.annotaions.EntityAnnotation;
import com.zz.bms.util.poi.vo.Column;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Excel 模板
 * @author Administrator
 */
public class BaseXlsTemplet<T> extends BaseXlsExport<T> implements ExcelExport<T> {


    private static Map<String,CellStyle> templetStyleMap = new ConcurrentHashMap<String,CellStyle>();
    private static Map<String,Font> templetFontMap = new ConcurrentHashMap<String,Font>();


    @Override
    public String getExcelFileName(){
        if(entityClz == null || entityClz == Object.class){
            return "模板";
        }else {

            EntityAnnotation ea = entityClz.getAnnotation(EntityAnnotation.class);
            if(ea == null){
                return "模板";
            }else {
                return ea.value()+"模板";
            }
        }
    }

    @Override
    protected CellStyle commonTitleStyle(Column column) {
        String columnKey = "";
        if(column != null){
            columnKey = column.getField().getName();
        }
        String key = "commonTitleStyle="+ this.getWorkbook().getClass().getName()+  columnKey ;

        CellStyle commonTitleStyle = templetStyleMap.get(key);

        if(commonTitleStyle == null) {
            System.out.println(key+"false");
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
            cellStyle.setFont(getTitleFont(column));


            templetStyleMap.put(key,cellStyle);
            return cellStyle;
        }else {
            System.out.println(key+"true");
            return commonTitleStyle;
        }

    }



    @Override
    protected Font getTitleFont(Column column) {

        String columnKey = "";
        if(column != null && column.isRequired()){
            columnKey = "required";
        }
        String key = "font="+ this.getWorkbook().getClass().getName()+ columnKey;


        Font font = templetFontMap.get(key);

        if(font != null){
            return font;
        }else {
            Font newFont = getWorkbook().createFont();
            //粗体显示
            newFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            if(column != null && column.isRequired()) {
                newFont.setColor(HSSFColor.RED.index);
            }
            templetFontMap.put(key , newFont);
            return newFont;
        }
    }


}




