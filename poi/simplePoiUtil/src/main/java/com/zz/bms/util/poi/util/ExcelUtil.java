package com.zz.bms.util.poi.util;

import com.zz.bms.util.base.data.PubMethod;
import com.zz.bms.util.poi.exceptions.ExcelAbsenceException;
import com.zz.bms.util.poi.exceptions.ExcelFormatException;
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
    public static void row2Object(Object[] fieldVlaues , List<Column> columns , Object obj) throws ExcelAbsenceException,ExcelAbsenceException,ExcelTypeMatchingException{
        if(fieldVlaues == null || fieldVlaues.length == 0 || columns == null || columns.isEmpty() || obj == null){
            return ;
        }
        if(fieldVlaues.length < columns.size()){
            throw new ExcelAbsenceException();
        }
        int index = 0;
        for(Column column : columns){
            ReflectionUtil.makeAccessible(column.getField());
            try {
                Object val = PubMethod.getObject(column.getField() , fieldVlaues[index]);
                column.getField().set(obj, val);
            }catch(RuntimeException e){
                throw new ExcelFormatException(0,index+1);
            }catch(Exception e){
                throw new ExcelTypeMatchingException(0,index+1);
            }
            index++ ;
        }


    }
}
