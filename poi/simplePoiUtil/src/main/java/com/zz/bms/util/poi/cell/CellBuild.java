package com.zz.bms.util.poi.cell;

import com.zz.bms.util.configs.AppConfig;

/**
 * @author Administrator
 */
public class CellBuild {

    /**
     * 生成一个Excel导出处理对象
     * @return
     */
    public static CellExport buildCellExport(){
        if(AppConfig.EXCEL_OPERATION_MODEL.equals(CellOperation.OPERATION_MODEL_DEFAULT)) {
            return new DefaultCellExport();
        }else {
            return new StringCellExport();
        }
    }


    public static CellImport buildCellImport(){
        //todo
       return null;
    }
}
