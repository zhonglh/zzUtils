package com.zz.bms.util.poi;

import com.zz.bms.util.base.data.DateKit;
import com.zz.bms.util.poi.enums.EnumXlsFormat;
import com.zz.bms.util.poi.vo.Column;
import org.apache.poi.ss.usermodel.*;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
public abstract class AbstractXlsExport<T> extends AbstractXlsStyle {

	public static int defaultColumnWidth = 15;

	public static int defaultColumnHeight = 400;
	public static int titleColumnHeight = 400;

	protected Workbook workbook;

	/**
	 * 当前Sheet
	 */
	protected Sheet sheet;

	/**
	 * 当前行
	 */
	protected Row row;

	/**
	 * 头信息
	 */
	protected Row[] headerRow;

	/**
	 * 标题信息
	 */
	protected Row[] titleRow;

	/**
	 * 是否自定义标题信息
	 * 例如  标题的信息是从其它excel获取的
	 */
	private boolean isCustomTitleInfo = false ;

	public boolean isWriteTitle(){
		return true;
	}



	public String getNumberName(){
		return "序号";
	}





	public void createSheet(T t ) {
		createSheet(t , defaultColumnWidth);
	}


	public void createSheet(T t  , int columnWidth) {
		createSheet(t ,columnWidth , 0 , 1);
	}


	public void createSheet(T t,  int cols, int rows) {
		createSheet(t ,defaultColumnWidth , cols , rows);
	}

	public void createSheet(T t, int columnWidth , int cols, int rows) {
		this.sheet = workbook.createSheet();
		this.sheet.setDefaultColumnWidth(columnWidth);
		this.sheet.createFreezePane(cols, rows);
	}	


	/**
	 * 增加一行
	 * @param index 行号
	 */
	public void createRow(Sheet sheet, int index) {
		setCurrRow(sheet.createRow(index));
	}
	
	
	
	
	
	


	public void setTitleCell(int index, String value) {
		Cell cell = getCurrRow().createCell(index);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		CellStyle cellStyle = commonTitleStyle();
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}	
	

	/**
	 * 设置单元格
	 * 
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setCell(int index, String value) {
		Cell cell = getCurrRow().createCell(index);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellStyle(commonStyle(index));
		cell.setCellValue(value);
	}
	

	public void setCell(int index, Date value) {
		Cell cell = getCurrRow().createCell(index);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellStyle(commonStyle(index));
		if (value != null) {
			cell.setCellValue(DateKit.fmtDateToYMD(value));
		}
	}

	public void setCell(int index, java.sql.Timestamp value) {
		Cell cell = getCurrRow().createCell(index);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellStyle(commonStyle(index));
		if (value != null) {
			cell.setCellValue(DateKit.fmtDateToYMD(value));
		}
	}

	public void setCell(int index, int value) {
		Cell cell = getCurrRow().createCell(index);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellStyle(commonStyle(index));
		cell.setCellValue(value);
	}

	private void setCell(int index, double value, EnumXlsFormat formatEm) {
		Cell cell = getCurrRow().createCell(index);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		
		// 建立新的cell样式
		CellStyle cellStyle = commonStyle(index); 
		DataFormat format = getWorkbook().createDataFormat();
		
		// 设置cell样式为定制的浮点数格式
		cellStyle.setDataFormat(format.getFormat(formatEm.getPattern())); 

		// 设置该cell浮点数的显示格式
		cell.setCellStyle(cellStyle); 
	}

	public void setCell(int index, double value) {
		setCell(index, value, EnumXlsFormat.NUMBER);
	}

	public void setCurrency(int index, double value) {
		setCell(index, value, EnumXlsFormat.CURRENCY);
	}

	public void setPercent(int index, double value) {
		setCell(index, value, EnumXlsFormat.PERCENT);
	}
	

	

	
	public CellStyle firstCommonStyle(int cellIndex) {
		
		try{
			if(titleRow != null && isCustomTitleInfo()){
				return this.titleRow[titleRow.length-1].getCell(cellIndex).getCellStyle();
			}
		}catch(Exception e){
			;
		}		
		return commonStyle();
	}	
	
	
	public abstract CellStyle commonStyle(int cellIndex) ;

	
	
	
	
	/**
	 * 设置单元格内容
	 * @param index
	 * @param value
	 * @param alignment
	 * @param boldweight
	 */
	public void setCell(int index, String value, short alignment, boolean boldweight) {
		
		Cell cell = getCurrRow().createCell(index);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		CellStyle cellStyle = getCellStyle(alignment, boldweight);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}



	/**
	 * 设置单元格内容
	 * @param index
	 * @param value
	 * @param alignment
	 * @param boldweight
	 * @param formatEm
	 */
	public void setCell(int index, Double value, short alignment, boolean boldweight, EnumXlsFormat formatEm) {
		
		Cell cell = getCurrRow().createCell(index);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		CellStyle cellStyle = getCellStyle(alignment, boldweight, formatEm);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}


	public void setNumberCell(int index, Double value, short alignment, boolean boldweight) {
		this.setCell(index, value, alignment,boldweight , EnumXlsFormat.NUMBER);
	}

	public void setCurrencyCell(int index, Double value, short alignment, boolean boldweight) {
		this.setCell(index, value, alignment,boldweight , EnumXlsFormat.CURRENCY);
	}

	public void setPercentCell(int index, Double value, short alignment, boolean boldweight) {
		this.setCell(index, value, alignment,boldweight , EnumXlsFormat.PERCENT);
	}

	
	
	public <T> boolean specialHand(T t, List<Column> columns, boolean addNumber){
		return false;
	}


	public Sheet getCurrSheet() {
		return this.sheet;
	}

	public void setCurrSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Row getCurrRow() {
		return this.row;
	}

	public void setCurrRow(Row row) {
		this.row = row;
	}

	@Override
	public Workbook getWorkbook() {
		return workbook;
	}

	public Row[] getTitleRow() {
		return titleRow;
	}

	public void setTitleRow(Row[] titleRow) {
		this.titleRow = titleRow;
	}

	public Row[] getHeaderRow() {
		return headerRow;
	}

	public void setHeaderRow(Row[] headerRow) {
		this.headerRow = headerRow;
	}

	public boolean isCustomTitleInfo() {
		return isCustomTitleInfo;
	}
}
