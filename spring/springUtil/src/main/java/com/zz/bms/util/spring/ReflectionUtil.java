package com.zz.bms.util.spring;

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class ReflectionUtil extends ReflectionUtils {



    /**
     * 获取所有的业务属性(字段)
     * @param clazz
     * @param businessAnnotation 例 EntityAttrPageAnnotation
     * @return
     */
    public static List<Field> getBusinessFields(Class<?> clazz , Class businessAnnotation) {
        final List<Field> fieldList = new ArrayList<Field>();
        FieldCallback fc = new FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException,
                    IllegalAccessException {
                fieldList.add(field);
            }
        };
        FieldFilter ff = new FieldFilter(){
            @Override
            public boolean matches(Field field) {
                if(field.isAnnotationPresent(businessAnnotation)) {
                    Annotation annotation = field.getAnnotation(businessAnnotation);
                    return (annotation != null);
                }else {
                    return false;
                }
            }
        };
        ReflectionUtils.doWithFields(clazz,fc , ff);
        return fieldList;
    }

    /**
     * 获得一个类里所有的属性(字段)
     * @param clazz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        final List<Field> fieldList = new ArrayList<Field>();
        FieldCallback fc = new FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException,
                    IllegalAccessException {
                fieldList.add(field);
            }
        };
        ReflectionUtils.doWithFields(clazz,fc);
        return fieldList;
    }


}
