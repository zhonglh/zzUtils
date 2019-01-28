package com.zz.bms.util.poi.util;

import com.zz.bms.util.base.sorts.SortComparator;
import com.zz.bms.util.configs.AppConfig;
import com.zz.bms.util.configs.annotaions.*;
import com.zz.bms.util.configs.util.AnnotaionEntityUtil;
import com.zz.bms.util.poi.vo.Column;
import com.zz.bms.util.spring.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
public class ColumnUtil {

    private static Map<String,List<Column>> columnsMap = new ConcurrentHashMap<String,List<Column>>();


    public static <T> List<Column> getColumn(Class<T> mclz , boolean isImport) {
        List<Column> columns;
        if(AppConfig.EXCEL_EXPORT_IMPORT_SAME){
            columns = ColumnUtil.getAllColumns(mclz);
        }else {
            columns = ColumnUtil.getAllColumns(mclz, isImport);
        }
        return columns;
    }


    /**
     * 获取类型所有的Excel列， 包括导入或者导出类型的
     * @param clz       Class 类型
     * @param <T>
     * @return
     */
    public static  <T> List<Column> getAllColumns(Class<T> clz) {

        String key = clz.getName();
        List<Column> list = columnsMap.get(key);
        if(list == null){
            list = getAllColumns0(clz, true, false);
            if (list != null) {
                columnsMap.put(key, list);
            }
        }
        return list;
    }


    /**
     * 获取所有的Excel列
     * @param clz           类
     * @param isImport      是否导入
     * @param <T>
     * @return
     */
    public static  <T> List<Column> getAllColumns(Class<T> clz, boolean isImport) {
        String key = clz.getName() + String.valueOf(isImport);
        List<Column> list = columnsMap.get(key);

        if(list == null){
            list = getAllColumns0(clz, false, isImport);
            if(list != null) {
                columnsMap.put(key, list);
            }
        }
        return list;
    }
    private static  <T> List<Column> getAllColumns0(Class<T> clz, boolean isAll, boolean isImport) {

        synchronized (clz) {

            List<Field> fs = ReflectionUtil.getExcelFields(clz, isAll, isImport);
            if (fs == null || fs.isEmpty()) {
                return null;
            }

            List<Column> list = new ArrayList<Column>(fs.size());
            for (Field f : fs) {
                Column column = field2Column(f);
                list.add(column);
            }

            Collections.sort(list, new SortComparator<Column>());
            int index = 0;
            for (Column column : list) {
                column.setNumber(index);
                index++;
            }


            return list;
        }

    }

    private static Column field2Column(Field f) {
        EntityAttrPageAnnotation pageAnnotation = f.getAnnotation(EntityAttrPageAnnotation.class);
        EntityAttrDBAnnotation dbAnnotation = f.getAnnotation(EntityAttrDBAnnotation.class);
        EntityAttrDictAnnotation dictAnnotation = f.getAnnotation(EntityAttrDictAnnotation.class);
        EntityAttrFkAnnotation fkAnnotation = f.getAnnotation(EntityAttrFkAnnotation.class);

        int maxLength = AnnotaionEntityUtil.maxLength(dbAnnotation, fkAnnotation, dictAnnotation, pageAnnotation);

        Column column = new Column();
        column.setNumber(pageAnnotation.sort());
        column.setCode(f.getName());
        column.setLength(maxLength);
        column.setName(pageAnnotation.title());
        column.setField(f);
        return column;
    }

}
