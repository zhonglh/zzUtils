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
package com.zz.bms.util.poi.export.excel.export.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.zz.bms.util.poi.export.excel.annotation.Excel;
import com.zz.bms.util.poi.export.excel.annotation.ExcelCollection;
import com.zz.bms.util.poi.export.excel.annotation.ExcelEntity;
import com.zz.bms.util.poi.export.excel.entity.params.ExcelExportEntity;
import com.zz.bms.util.poi.handler.inter.IExcelDataHandler;
import com.zz.bms.util.poi.util.PoiPublicUtil;
import com.zz.bms.util.poi.util.ThreadLocalHolder;

/**
 * 导出基础处理,不设计POI,只设计对象,保证复用性
 * @author Administrator
 */
public class ExportBase {

    protected IExcelDataHandler dataHanlder;

    protected List<String>      needHanlderList;

    /**
     * 创建导出实体对象
     * 
     * @param field
     * @param targetId
     * @param pojoClass
     * @param getMethods
     * @return
     * @throws Exception
     */
    private ExcelExportEntity createExcelExportEntity(Field field, String targetId,
            Class<?> pojoClass, List<Method> getMethods) throws Exception {
    	return this.createExcelExportEntity(field, targetId, pojoClass, getMethods, false);
    }
    private ExcelExportEntity createExcelExportEntity(Field field, String targetId,
               Class<?> pojoClass, List<Method> getMethods,boolean isDynamic) throws Exception {
        Excel excel = field.getAnnotation(Excel.class);
        ExcelExportEntity excelEntity = new ExcelExportEntity();
        excelEntity.setType(excel.type());
        getExcelField(targetId, field, excelEntity, excel, pojoClass,isDynamic);
        if (getMethods != null) {
            List<Method> newMethods = new ArrayList<Method>();
            newMethods.addAll(getMethods);
            newMethods.add(excelEntity.getMethod());
            excelEntity.setMethods(newMethods);
        }
        return excelEntity;
    }

    private Object formatValue(Object value, ExcelExportEntity entity) throws Exception {
        Date temp = null;
        if (value instanceof String) {
            SimpleDateFormat format = new SimpleDateFormat(entity.getDatabaseFormat());
            temp = format.parse(value.toString());
        } else if (value instanceof Date) {
            temp = (Date) value;
        }
        if (temp != null) {
            SimpleDateFormat format = new SimpleDateFormat(entity.getFormat());
            value = format.format(temp);
        }
        return value;
    }

    /**
     * 获取需要导出的全部字段
     * 
     * @param exclusions
     * @param targetId
     *            目标ID
     * @param fields
     * @throws Exception
     */
    

    public void getAllExcelField(String[] exclusions, String targetId, Field[] fields,
                                 List<ExcelExportEntity> excelParams, Class<?> pojoClass,
                                 List<Method> getMethods) throws Exception {
    	this.getAllExcelField(exclusions, targetId, fields, excelParams, pojoClass, getMethods, false);    	
    }
    public void getAllExcelField(String[] exclusions, String targetId, Field[] fields,
                                 List<ExcelExportEntity> excelParams, Class<?> pojoClass,
                                 List<Method> getMethods,boolean isDynamic) throws Exception {
        List<String> exclusionsList = exclusions != null ? Arrays.asList(exclusions) : null;
        ExcelExportEntity excelEntity;
        // 遍历整个filed
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 先判断是不是collection,在判断是不是java自带对象,之后就是我们自己的对象了
            if (PoiPublicUtil.isNotUserExcelUserThis(exclusionsList, field, targetId)) {
                continue;
            }
            // 首先判断Excel 可能一下特殊数据用户回自定义处理
            if (field.getAnnotation(Excel.class) != null) {
            	ExcelExportEntity excelExportEntity = 
            		createExcelExportEntity(field, targetId, pojoClass, getMethods,isDynamic);
            	if(excelExportEntity != null && excelExportEntity.getName() != null)
            		excelParams.add(excelExportEntity);
            } else if (PoiPublicUtil.isCollection(field.getType())) {
                ExcelCollection excel = field.getAnnotation(ExcelCollection.class);
                boolean dynamic = excel.isDynamic();
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                Class<?> clz = (Class<?>) pt.getActualTypeArguments()[0];
                
                if(dynamic){
                	Map<String,Map<String,String>> maps = ThreadLocalHolder.getDynamicMap();
        			if(maps == null) continue;
        			Map<String,String> map = maps.get(clz.getSimpleName());
        			if(map == null || map.isEmpty()) continue;
                }
                
                List<ExcelExportEntity> list = new ArrayList<ExcelExportEntity>();
                getAllExcelField(exclusions, StringUtils.isNotEmpty(excel.id()) ? excel.id()
                    : targetId, PoiPublicUtil.getClassFields(clz), list, clz, null,dynamic);
                excelEntity = new ExcelExportEntity();
                excelEntity.setName(getExcelName(excel.name(), targetId));
                excelEntity.setOrderNum(getCellOrder(excel.orderNum(), targetId));
                excelEntity.setMethod(PoiPublicUtil.getMethod(field.getName(), pojoClass));
                excelEntity.setList(list);
                excelParams.add(excelEntity);
            } else {
                List<Method> newMethods = new ArrayList<Method>();
                if (getMethods != null) {
                    newMethods.addAll(getMethods);
                }
                newMethods.add(PoiPublicUtil.getMethod(field.getName(), pojoClass));
                ExcelEntity excel = field.getAnnotation(ExcelEntity.class);
                getAllExcelField(exclusions, StringUtils.isNotEmpty(excel.id()) ? excel.id()
                    : targetId, PoiPublicUtil.getClassFields(field.getType()), excelParams,
                    field.getType(), newMethods);
            }
        }
    }

    /**
     * 获取这个字段的顺序
     * 
     * @param orderNum
     * @param targetId
     * @return
     */
    public int getCellOrder(String orderNum, String targetId) {
        if (isInteger(orderNum) || targetId == null) {
            return Integer.valueOf(orderNum);
        }
        String[] arr = orderNum.split(",");
        String[] temp;
        for (String str : arr) {
            temp = str.split("_");
            if (targetId.equals(temp[1])) {
                return Integer.valueOf(temp[0]);
            }
        }
        return 0;
    }

    /**
     * 获取填如这个cell的值,提供一些附加功能
     * 
     * @param entity
     * @param obj
     * @return
     * @throws Exception
     */
    public Object getCellValue(ExcelExportEntity entity, Object obj) throws Exception {
        Object value;
        if (obj instanceof Map) {
            value = ((Map<?, ?>) obj).get(entity.getKey());
        } else {
            value = entity.getMethods() != null ? getFieldBySomeMethod(entity.getMethods(), obj)
                : entity.getMethod().invoke(obj, new Object[] {});
        }
        if (StringUtils.isNotEmpty(entity.getFormat())) {
            value = formatValue(value, entity);
        }
        if (entity.getReplace() != null && entity.getReplace().length > 0) {
            value = replaceValue(entity.getReplace(), String.valueOf(value));
        }
        if (needHanlderList != null && needHanlderList.contains(entity.getName())) {
            value = dataHanlder.exportHandler(obj, entity.getName(), value);
        }
        if (StringUtils.isNotEmpty(entity.getSuffix()) && value != null) {
            value = value + entity.getSuffix();
        }
        return value == null ? "" : value.toString();
    }

    /**
     * 获取集合的值
     * @param entity
     * @param obj
     * @return
     * @throws Exception
     */
    public Collection<?> getListCellValue(ExcelExportEntity entity, Object obj) throws Exception {
        Object value;
        if (obj instanceof Map) {
            value = ((Map<?, ?>) obj).get(entity.getKey());
        } else {
            value = (Collection<?>) entity.getMethod().invoke(obj, new Object[] {});
        }
        return (Collection<?>) value;
    }

    /**
     * 注解到导出对象的转换
     * 
     * @param targetId
     * @param field
     * @param excelEntity
     * @param excel
     * @param pojoClass
     * @throws Exception
     */
    private void getExcelField(String targetId, Field field, ExcelExportEntity excelEntity,
            Excel excel, Class<?> pojoClass) throws Exception {
    	this.getExcelField(targetId, field,  excelEntity, excel, pojoClass, false);
    }
    private void getExcelField(String targetId, Field field, ExcelExportEntity excelEntity,
                               Excel excel, Class<?> pojoClass,boolean isDynamic) throws Exception {
               
        if(isDynamic) {
			Map<String,Map<String,String>> maps = ThreadLocalHolder.getDynamicMap();
			if(maps == null) return ;
			Map<String,String> map = maps.get(pojoClass.getSimpleName());
			String name = null;
			if(map != null) {
				name = map.get(excel.name());
			}       
		
			if(name == null) return ;
			else excelEntity.setName(name);
        }else {
        	excelEntity.setName(getExcelName(excel.name(), targetId)); 
        }
        
        excelEntity.setWidth(excel.width());
        excelEntity.setHeight(excel.height());
        excelEntity.setNeedMerge(excel.needMerge());
        excelEntity.setMergeVertical(excel.mergeVertical());
        excelEntity.setMergeRely(excel.mergeRely());
        excelEntity.setReplace(excel.replace());
        excelEntity.setOrderNum(getCellOrder(excel.orderNum(), targetId));
        excelEntity.setWrap(excel.isWrap());
        excelEntity.setExportImageType(excel.imageType());
        excelEntity.setSuffix(excel.suffix());
        excelEntity.setDatabaseFormat(excel.databaseFormat());
        excelEntity.setFormat(StringUtils.isNotEmpty(excel.exportFormat()) ? excel.exportFormat()
            : excel.format());
        excelEntity.setStatistics(excel.isStatistics());
        String fieldname = field.getName();
        excelEntity.setMethod(PoiPublicUtil.getMethod(fieldname, pojoClass));
    }

    /**
     * 判断在这个单元格显示的名称
     * 
     * @param exportName
     * @param targetId
     * @return
     */
    public String getExcelName(String exportName, String targetId) {
        if (exportName.indexOf(",") < 0) {
            return exportName;
        }
        String[] arr = exportName.split(",");
        for (String str : arr) {
            if (str.indexOf(targetId) != -1) {
                return str.split("_")[0];
            }
        }
        return null;
    }

    /**
     * 多个反射获取值
     * 
     * @param list
     * @param t
     * @return
     * @throws Exception
     */
    public Object getFieldBySomeMethod(List<Method> list, Object t) throws Exception {
        for (Method m : list) {
            if (t == null) {
                t = "";
                break;
            }
            t = m.invoke(t, new Object[] {});
        }
        return t;
    }

    /**
     * 根据注解获取行高
     * 
     * @param excelParams
     * @return
     */
    public short getRowHeight(List<ExcelExportEntity> excelParams) {
        double maxHeight = 0;
        for (int i = 0; i < excelParams.size(); i++) {
            maxHeight = maxHeight > excelParams.get(i).getHeight() ? maxHeight : excelParams.get(i)
                .getHeight();
            if (excelParams.get(i).getList() != null) {
                for (int j = 0; j < excelParams.get(i).getList().size(); j++) {
                    maxHeight = maxHeight > excelParams.get(i).getList().get(j).getHeight() ? maxHeight
                        : excelParams.get(i).getList().get(j).getHeight();
                }
            }
        }
        return (short) (maxHeight * 50);
    }

    /**
     * 判断字符串是否是整数
     */
    public boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Object replaceValue(String[] replace, String value) {
        String[] temp;
        for (String str : replace) {
            temp = str.split("_");
            if (value.equals(temp[1])) {
                value = temp[0];
                break;
            }
        }
        return value;
    }

    /**
     * 对字段根据用户设置排序
     */
    public void sortAllParams(List<ExcelExportEntity> excelParams) {
        Collections.sort(excelParams);
        for (ExcelExportEntity entity : excelParams) {
            if (entity.getList() != null) {
                Collections.sort(entity.getList());
            }
        }
    }

}
