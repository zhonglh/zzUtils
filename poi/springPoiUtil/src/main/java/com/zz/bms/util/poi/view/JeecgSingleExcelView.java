/**
 * Copyright 2013-2015 JueYue (qrb.jueyue@gmail.com)
 *   
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zz.bms.util.poi.view;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import com.zz.bms.util.poi.excel.ExcelExportUtil;
import com.zz.bms.util.poi.excel.entity.ExportParams;
import com.zz.bms.util.poi.excel.entity.vo.NormalExcelConstants;
import com.zz.bms.util.poi.excel.export.ExcelExportServer;
import com.zz.bms.util.poi.util.ThreadLocalHolder;
import org.springframework.stereotype.Controller;

/**
 * Excel 生成解析器,减少用户操作
 */
@SuppressWarnings("unchecked")
@Controller(NormalExcelConstants.JEECG_EXCEL_VIEW)
public class JeecgSingleExcelView extends MiniAbstractExcelView {

    public JeecgSingleExcelView() {
        super();
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        String codedFileName = "临时文件";
        Workbook workbook = null;
        
       
        
        if (model.containsKey(NormalExcelConstants.MAP_LIST)) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) model
                .get(NormalExcelConstants.MAP_LIST);
            if (list.size() == 0) {
                throw new RuntimeException("MAP_LIST IS NULL");
            }
            
            
            if(list.get(0).containsKey(NormalExcelConstants.DYNAMIC_COLUMN)){
            	ThreadLocalHolder.setDynamicMap((Map<String,Map<String,String>>)list.get(0).get(NormalExcelConstants.DYNAMIC_COLUMN));
            }
            workbook = ExcelExportUtil.exportExcel(
                (ExportParams) list.get(0).get(NormalExcelConstants.PARAMS), (Class<?>) list.get(0)
                    .get(NormalExcelConstants.CLASS),
                (Collection<?>) list.get(0).get(NormalExcelConstants.DATA_LIST));
            if(list.get(0).containsKey(NormalExcelConstants.DYNAMIC_COLUMN)){   	
            	ThreadLocalHolder.setDynamicMap(null);
            }
            
            for (int i = 1; i < list.size(); i++) {
                if(list.get(i).containsKey(NormalExcelConstants.DYNAMIC_COLUMN)){
                	ThreadLocalHolder.setDynamicMap((Map<String,Map<String,String>>)list.get(i).get(NormalExcelConstants.DYNAMIC_COLUMN));
                }
            	
                new ExcelExportServer().createSheet(workbook,
                    (ExportParams) list.get(i).get(NormalExcelConstants.PARAMS), (Class<?>) list
                        .get(i).get(NormalExcelConstants.CLASS),
                    (Collection<?>) list.get(i).get(NormalExcelConstants.DATA_LIST));

                if(list.get(i).containsKey(NormalExcelConstants.DYNAMIC_COLUMN)){
                	ThreadLocalHolder.setDynamicMap(null);
                }                
                
            }
        } else {
        	 if (model.containsKey(NormalExcelConstants.DYNAMIC_COLUMN)) {        	
             	ThreadLocalHolder.setDynamicMap((Map<String,Map<String,String>>)model.get(NormalExcelConstants.DYNAMIC_COLUMN));
             }
            workbook = ExcelExportUtil.exportExcel(
                (ExportParams) model.get(NormalExcelConstants.PARAMS),
                (Class<?>) model.get(NormalExcelConstants.CLASS),
                (Collection<?>) model.get(NormalExcelConstants.DATA_LIST));
            if (model.containsKey(NormalExcelConstants.DYNAMIC_COLUMN)) {        	
            	ThreadLocalHolder.setDynamicMap(null);
            }
        }


        
        
        if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
            codedFileName = (String) model.get(NormalExcelConstants.FILE_NAME);
        }
        if (workbook instanceof HSSFWorkbook) {
            codedFileName += HSSF;
        } else {
            codedFileName += XSSF;
        }
        if (isIE(request)) {
            codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF8");
        } else {
            codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
    }
}
