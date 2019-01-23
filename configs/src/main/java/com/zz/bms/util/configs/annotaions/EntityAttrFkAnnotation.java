package com.zz.bms.util.configs.annotaions;

import java.lang.annotation.*;

/**
 * 实体属性是字典的注解类
 */


@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityAttrFkAnnotation {

    /**
     * 组编号
     * 例如 project
     * 比如 project_id , project_name , project_code 是一组
     * @return
     */
    public String group() ;

    /**
     * 组名称
     * 例如 项目
     * @return
     */
    public String groupName();




    /**
     * 是否为外键值 ,  比如 project_id 为 true
     * @return
     */
    public boolean isFkId() default false;


    /**
     * 是否为外键名称 ,  比如 project_name 为 true
     * @return
     */
    public boolean isFkBusinessName() default false;

    /**
     * 是否为业务识别字段 ，
     * 比如人员， 不能通过姓名识别， 需要通过工号或者身份证 号码识别
     * 如果识别字段为多个联合， 有多个字段值为 true
     * @return
     */
    public boolean isFkBusinessKey() default false;

    /**
     * 组对应的列名称 ,
     * 例如 project_id ,  project_code , project_name
     * @return
     */
    public String dbColumnName() default "id";


    /**
     * 对应列的类型
     * 如 char , varchar , date
     * @return
     */
    public String dbColumnType() default "char";

    /**
     * 对应列的长度
     * @return
     */
    public int dbColumnLength() default 32;

    /**
     * 小数点长度
     * @return
     */
    public int dbColumnDecimals() default 0;

    /**
     * 对应列是否可以为空
     * @return
     */
    public boolean dbColumnNotNull() default false;



    /**
     * 外键对应的实体类全称
     * 例： com.zz.bms.system.bo.TsUserBO
     * @return
     */
    public String fkEntity() ;

}


