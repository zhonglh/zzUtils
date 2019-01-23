package com.zz.bms.util.poi.vo;

import java.lang.reflect.Field;

/**
 * 列实体类型
 * @author Administrator
 */
public class Column {


	/**
	 * 列序号 列所处的顺序号
	 * 对应Excel的列
	 * 比如12
	 */
	private int number;


	/**
	 * 对应列长度
	 */
	private int length;

	/**
	 * 对应列代码
	 * 例如 User 的  userName
	 */
	private String code;


	/**
	 * 对应列名称
	 * 例如 User 的  用户名
	 */
	private String name;


	/**
	 * 对应列属性
	 * 例如 User 实体类中的 String userName ;
	 */
	private Field field;





	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}


}
