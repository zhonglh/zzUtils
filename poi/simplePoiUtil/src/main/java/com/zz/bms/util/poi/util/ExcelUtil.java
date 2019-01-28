package com.zz.bms.util.poi.util;

import com.zz.bms.util.base.data.PubMethod;
import com.zz.bms.util.poi.exceptions.ExcelAbsenceException;
import com.zz.bms.util.poi.exceptions.ExcelTypeMatchingException;
import com.zz.bms.util.poi.vo.Column;
import com.zz.bms.util.spring.ReflectionUtil;

import java.util.List;

/**
 * Excel工具类
 * @author Administrator
 */
public class ExcelUtil {

    /**
     * Excel Row 转为对应的对象
     * @param fieldVlaues
     * @param columns
     * @param obj
     * @return
     */
    public static void row2Object(Object[] fieldVlaues , List<Column> columns , Object obj){
        if(fieldVlaues == null || fieldVlaues.length == 0 || columns == null || columns.isEmpty() || obj == null){
            return ;
        }
        if(fieldVlaues.length < columns.size()){
            throw new ExcelAbsenceException();
        }
        int index = 0;
        for(Column column : columns){
            Object val = PubMethod.getObject(column.getField() , fieldVlaues[index]);
            ReflectionUtil.makeAccessible(column.getField());
            try {
                column.getField().set(obj, val);
            }catch(Exception e){
                throw new ExcelTypeMatchingException();
            }
            index++ ;
        }


    }
}
