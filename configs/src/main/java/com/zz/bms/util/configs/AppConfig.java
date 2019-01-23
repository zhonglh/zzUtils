package com.zz.bms.util.configs;

/**
 * 系统配置类
 * @author Administrator
 */
public final class AppConfig {




    /**
     * 是否调试模式
     * 在Spring容器(或Servlet容器) 启动完后根据实际情况修改
     */
    public static boolean DEBUG_MODE = false;


    /**
     * 部署时是否使用了动静分离  SASS:Static and static separation
     */
    public static boolean USE_SASS = false;

    /**
     * 是否有面包屑(导航)功能
     */
    public static boolean USE_CRUMB = true;




}
