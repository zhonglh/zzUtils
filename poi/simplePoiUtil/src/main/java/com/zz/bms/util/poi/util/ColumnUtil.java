package com.zz.bms.util.poi.util;

import com.zz.bms.util.base.sorts.SortComparator;
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


    /**
     * 获取所有的列
     * @param clz           类
     * @param isImport      导入
     * @param <T>
     * @return
     */

    public static  <T> List<Column> getColumn(Class<T> clz, boolean isImport) {
        String key = clz.getName() + String.valueOf(isImport);
        List<Column> list = columnsMap.get(key);
        if(list == null){
            list = getColumn0(clz, isImport);
            if(list != null) {
                columnsMap.put(key, list);
            }
        }
        return list;
    }
    private static  <T> List<Column> getColumn0(Class<T> clz, boolean isImport) {

        List<Field> fs = ReflectionUtil.getExcelFields(clz , isImport);
        if(fs == null || fs.isEmpty()){
            return null;
        }

        List<Column> list = new ArrayList<Column>(fs.size());
        for(Field f : fs ){

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

            list.add(column);

        }


        Collections.sort(list , new SortComparator<Column>());

        int index = 0;
        for(Column column : list){
            column.setNumber(index);
            index ++ ;
        }

        return list;

    }

}
