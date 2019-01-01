package com.zz.bms.util.annotaions;

import java.lang.annotation.*;

/**
 * 实体属性验证的注解类
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityAttrCheckAnnotation {


    //检查规则  正则表达式
    //检查内容是否符合该表达式
    public String checkRule() default "";

    //字典类型
    public String dictType() default "";

    //高级校验， 在Excel和普通方式都会有体现
    public boolean customCheck() default false;

}
