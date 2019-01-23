package com.zz.bms.util.poi;

import com.zz.bms.util.poi.vo.Column;

import java.util.List;

/**
 * @author Administrator
 */
public interface IExport<T> {


    /**
     * 导出标题信息
     * @param headers
     * @param t
     * @param isAddNumber
     */
    public void exportTitles(int headers , T t , boolean isAddNumber);

    /**
     * 导出头信息
     * @param headers
     */
    public void exportHeaders(List<String> headers);


    /**
     * 导出内容
     * @param contents      内容
     * @param rowIndex      行索引
     * @param columns       列设置
     * @param isAddNumber   是否增加序号
     */
    public void exportContent(List<T> contents, int rowIndex, List<Column> columns, boolean isAddNumber ) ;



}
