package com.zz.bms.util.annotaions;

import java.lang.annotation.*;

/**
 * 实体属性数据库的注解类
 */


@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityAttrDBAnnotation {


    //属性名称
    public String attrName();

    //属性长度
    public int attrLength() default 0;

    //属性小数点长度
    public int attrDecimals() default 0;


    //属性是否不能为空
    public boolean notNull() default false;

    //属性序号
    public int sort() default  9999;

}


