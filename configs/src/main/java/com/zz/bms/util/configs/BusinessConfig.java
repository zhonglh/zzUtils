package com.zz.bms.util.configs;


/**
 * 业务设置
 * @author Administrator
 */
public class BusinessConfig {


    /**
     * 是否启用租户模式，即多企业
     * 该属性的内容如果要修改，在需求开始阶段就要确定, 在设计表时需要
     */
    public static boolean USE_TENANT = true;



    /**
     * 是否使用机构
     * 该属性的内容如果要修改，在需求开始阶段就要确定, 在设计表时需要
     */
    public static boolean USE_ORGAN = true;


    /**
     * 是否要启动租户拦截器
     */
    public static boolean IS_CHECK_Tenant = true;




    /**
     * 是否要启动机构拦截器
     */
    public static boolean IS_CHECK_Organ = true;

}
