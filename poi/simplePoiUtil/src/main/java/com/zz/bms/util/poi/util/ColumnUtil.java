package com.zz.bms.util.poi.util;

import com.zz.bms.util.poi.vo.Column;
import com.zz.bms.util.spring.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Administrator
 */
public class ColumnUtil {


    /**
     * 获取所有的列
     * @param clz           类
     * @param isAll         是否所有的
     * @param isImport      导入
     * @param <T>
     * @return
     */
    public static  <T> List<Column> getColumn(final Class<T> clz, boolean isAll, boolean isImport) {
        //todo 增加缓存
        List<Field> fs = ReflectionUtil.getAllFields(T2.class);
        System.out.println( fs );

        fs = ReflectionUtil.getAllFields(T2.class);
        System.out.println( fs );

        return null;

    }

    public static void main(String[] args) {
        ColumnUtil.getColumn(null , false, false);
    }

}
