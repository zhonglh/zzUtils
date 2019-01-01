package com.zz.bms.util.annotaions;


import java.lang.annotation.*;

/**
 * 实体属性Excel的注解类
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityAttrExcelAnnotation {





    /**
     * Excel 处理逻辑
     * 0：不导入也不导出
     * 1: 只导入
     * 2: 只导出
     * 3: 导入导出
     * @return
     */
    public int excelProcess() default 3;


}
