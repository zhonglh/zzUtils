package com.zz.bms.util.poi.cell;

import com.zz.bms.util.base.data.DateKit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * 导出CELL
 * 全部转换为字符串导出， 字符的导出格式为文本， 日期的导出格式为日期
 * @author Administrator
 */
public class StringCellExport implements CellExport {


    @Override
    public String getOperationModel() {
        return CellOperation.OPERATION_MODEL_STRING;
    }

    @Override
    public void setCell(int index, Row row, CellStyle cellStyle, String value) {
        Cell cell = row.createCell(index);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    @Override
    public void setCell(int index, Row row, CellStyle cellStyle, Integer value) {
        setCell(index, row, cellStyle, value.toString());
    }

    @Override
    public void setCell(int index, Row row, CellStyle cellStyle, Long value) {
        setCell(index, row, cellStyle, value.toString());
    }

    @Override
    public void setCell(int index, Row row, CellStyle cellStyle, Double value) {
        setCell(index, row, cellStyle, value.toString());
    }

    @Override
    public void setCell(int index, Row row, CellStyle cellStyle, Float value) {
        setCell(index, row, cellStyle, value.toString());
    }

    @Override
    public void setCell(int index, Row row, CellStyle cellStyle, Boolean value) {
        setCell(index, row, cellStyle, value.toString());

    }

    @Override
    public void setCell(int index, Row row, CellStyle cellStyle, BigDecimal value) {
        setCell(index, row, cellStyle, value.toString());
    }

    @Override
    public void setCell(int index, Row row, CellStyle cellStyle, Date value) {
        setCell(index, row, cellStyle, DateKit.toShortDate(value));
    }

    @Override
    public void setCell(int index, Row row, CellStyle cellStyle, Timestamp value) {
        setCell(index, row, cellStyle, DateKit.dateTo19String(value));
    }

    @Override
    public void setCell(int index, Row row, CellStyle cellStyle, Object value) {
        setCell(index , row , cellStyle , value.toString());
    }

}
