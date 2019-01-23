package com.zz.bms.util.spring;


import com.zz.bms.util.configs.annotaions.*;
import com.zz.bms.util.configs.enums.EnumExcelType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 */
public class ReflectionUtil extends ReflectionUtils {

    //todo
    //所有的方法需要加缓存

    /**
     * 获取所有的业务属性(字段)
     * 有 EntityAttrPageAnnotation 注解的
     * @param clazz
     * @param stopClz
     * @return
     */

    public static List<Field> getBusinessAllFields(Class<?> clazz , Class stopClz) {
        final List<Field> fieldList = new ArrayList<Field>();
        FieldCallback fc = new FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException,
                    IllegalAccessException {
                fieldList.add(field);
            }
        };

        FieldFilter ff = new FieldFilter() {
            @Override
            public boolean matches(Field field) {
                EntityAttrPageAnnotation pageAnnotation = field.getAnnotation(EntityAttrPageAnnotation.class);
                return pageAnnotation != null ;
            }

        };
        ReflectionUtils.doWithFields(clazz, stopClz ,fc , ff);
        return fieldList;
    }

    /**
     * 获取所有的业务属性(字段)
     * 界面上可显示的
     * 可用于日志记录
     * @param clazz
     * @param stopClz
     * @return
     */
    public static List<Field> getBusinessFields(Class<?> clazz , Class stopClz) {
        final List<Field> fieldList = new ArrayList<Field>();
        final Set<String> groupSet = new HashSet<String>();
        FieldCallback fc = new FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                fieldList.add(field);
            }
        };
        FieldFilter ff = new FieldFilter(){
            @Override
            public boolean matches(Field field) {
                EntityAttrExcelAnnotation excelAnnotation = field.getAnnotation(EntityAttrExcelAnnotation.class);
                EntityAttrPageAnnotation pageAnnotation = field.getAnnotation(EntityAttrPageAnnotation.class);
                EntityAttrDictAnnotation dictAnnotation = field.getAnnotation(EntityAttrDictAnnotation.class);
                EntityAttrFkAnnotation fkAnnotation = field.getAnnotation(EntityAttrFkAnnotation.class);


                //导入或导出的
                if(excelAnnotation != null){
                    if(!excelAnnotation.excelProcess().equals(EnumExcelType.NONE.getVal())){
                        return true;
                    }
                }

                if(pageAnnotation != null){
                    if(pageAnnotation.listShowable()) {
                        return true;
                    }

                    if(pageAnnotation.existEditPage() && !pageAnnotation.hidden()){
                        if(dictAnnotation != null){
                            groupSet.add(dictAnnotation.group());
                            if(dictAnnotation.isValueField()){
                                return false;
                            }else {
                                return true;
                            }
                        }else if(fkAnnotation != null){
                            groupSet.add(fkAnnotation.group());
                            if(fkAnnotation.isFkId()){
                                return false;
                            }else {
                                return true;
                            }
                        }else {
                            return true;
                        }
                    }

                }


                if(dictAnnotation != null){
                    if(groupSet.contains(dictAnnotation.group())) {
                        if (dictAnnotation.isValueField()) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }else if(fkAnnotation != null){
                    if(groupSet.contains(fkAnnotation.group())) {
                        if (fkAnnotation.isFkId()) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
                return false;

            }
        };
        ReflectionUtils.doWithFields(clazz, stopClz ,fc , ff);
        return fieldList;
    }


    /**
     * 获取业务属性(字段)
     * Excel处理的
     * @param clazz
     * @param importFlag
     * @return
     */

    public static List<Field> getExcelFields(Class<?> clazz , boolean importFlag ) {
        return getExcelFields(clazz , null , importFlag );
    }
    public static List<Field> getExcelFields(Class<?> clazz , Class stopClz , boolean importFlag) {
        final List<Field> fieldList = new ArrayList<Field>();

        FieldCallback fc = new FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                fieldList.add(field);
            }
        };
        FieldFilter ff = new FieldFilter(){

            @Override
            public boolean matches(Field field) {

                EntityAttrPageAnnotation pageAnnotation = field.getAnnotation(EntityAttrPageAnnotation.class);
                if(pageAnnotation == null){
                    return false;
                }

                EntityAttrExcelAnnotation excelAnnotation = field.getAnnotation(EntityAttrExcelAnnotation.class);
                if(excelAnnotation != null){

                    if(excelAnnotation.excelProcess().equals(EnumExcelType.IMPORT_EXPORT)){
                        return true;
                    }

                    if(importFlag) {
                        if (excelAnnotation.excelProcess().equals(EnumExcelType.ONLY_IMPORT)){
                            return true;
                        }
                    }else {
                        if (excelAnnotation.excelProcess().equals(EnumExcelType.ONLY_EXPORT)){
                            return true;
                        }
                    }
                }

                return false;

            }
        };
        ReflectionUtils.doWithFields(clazz, stopClz ,fc , ff);
        return fieldList;
    }

    /**
     * 获得一个类里所有的属性(字段)
     * 仅去除 stopClz (BaseBusinessSimpleExEntity/BaseBusinessSimpleEntity/BaseBusinessExEntity/BaseBusinessEntity)里的属性
     * @param clazz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz , Class stopClz) {
        final List<Field> fieldList = new ArrayList<Field>();
        FieldCallback fc = new FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException,
                    IllegalAccessException {
                fieldList.add(field);
            }
        };
        ReflectionUtils.doWithFields(clazz,stopClz,fc);
        return fieldList;
    }





}
