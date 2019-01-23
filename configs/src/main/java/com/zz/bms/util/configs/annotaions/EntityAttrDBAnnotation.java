package com.zz.bms.util.configs.annotaions;

import java.lang.annotation.*;

/**
 * 实体属性数据库的注解类
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityAttrDBAnnotation {


    /**
     * 属性名称
     * @return
     */
    public String attrName();

    /**
     * 属性类型(字段类型)
     * @return
     */
    public String type() ;

    /**
     * 属性长度
     * @return
     */
    public int attrLength() ;

    /**
     * 属性小数点长度
     * @return
     */
    public int attrDecimals() default 0;

    /**
     * 属性是否不能为空
     * @return
     */
    public boolean notNull() default false;

    /**
     * 属性序号
     * @return
     */
    public int sort() default  9999;

}


