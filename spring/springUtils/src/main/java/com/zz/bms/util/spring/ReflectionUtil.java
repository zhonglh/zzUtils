package com.zz.bms.util.spring;

import com.zz.bms.util.annotaions.EntityAttrPageAnnotation;
import org.springframework.util.ReflectionUtils;

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
     * @return
     */
    public static List<Field> getBusinessFields(Class<?> clazz) {
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
                if(field.isAnnotationPresent(EntityAttrPageAnnotation.class)) {
                    EntityAttrPageAnnotation pa = field.getAnnotation(EntityAttrPageAnnotation.class);
                    return (pa != null);
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
