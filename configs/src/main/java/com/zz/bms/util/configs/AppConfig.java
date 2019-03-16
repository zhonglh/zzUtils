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
     * 比如 Vue
     */
    @Deprecated
    public static boolean USE_SASS = false;

    /**
     * 是否有面包屑(导航)功能
     */
    public static boolean USE_CRUMB = true;

    /**
     * 菜单的最大层级
     */
    public static int MENU_MAX_LEVEL = 4;

    //
    //DefaultCellOperation

    /**
     * Excel操作模式
     * DefaultCellOperation     默认的操作， 导入导出使用和相应的类型， 比如Java属性为日期， Excel中Cell的类型也是日期
     * StringCellOperation      在Excel中所有的列属性都是文本类型
     */
    public static String EXCEL_OPERATION_MODEL = "DefaultCellOperation";


    /**
     * Excel 是否有序号列
     */
    public static boolean EXCEL_ADD_NUMBER = true;



    /**
     * Excel 是否导出头信息
     */
    public static boolean EXCEL_EXPORT_HEADER = true;


    /**
     * Excel 导入导出列是否相同
     * 如果设置为相同， 导出的数据可以用于导入
     */
    public static boolean EXCEL_EXPORT_IMPORT_SAME = false;

    /**
     * 导入所有的Sheet数据
     */
    public static boolean EXCEL_IMPORT_ALLSHEET = false;

    /**
     * 表头占用的列数
     */
    public static int HEADER_DEFAULT_CELLS = 13;


    /**
     * 导入的数据都正确才入库
     */
    public static boolean NO_ERROR_SAVE_DB = false;




}
