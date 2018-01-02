package com.tepusoft.modules.processformdefine.entity;

import com.tepusoft.common.persistence.DataEntity;


/**
 * 表和字段关联表
 * @author wxp
 * @version 2017-05-06
 */
public class TableColumnEntity extends DataEntity<TableColumnEntity>{

	private static final long serialVersionUID = 1L;
	
	//表名
	private String tableName;
	//数据库中字段名
	private String columnName;
	//字段名对应的页面属性名
	private String pageColumnName;
	//字段类型
	private String columnType;
	//字段中文名
	private String chineseName;
	//字段中文名
	private Object columnValue;
	
	public TableColumnEntity() {
		super();
	}

	public TableColumnEntity(String tableName, String columnName,
			String pageColumnName, String columnType, String chineseName,
			Object columnValue) {
		super();
		this.tableName = tableName;
		this.columnName = columnName;
		this.pageColumnName = pageColumnName;
		this.columnType = columnType;
		this.chineseName = chineseName;
		this.columnValue = columnValue;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getPageColumnName() {
		return pageColumnName;
	}

	public void setPageColumnName(String pageColumnName) {
		this.pageColumnName = pageColumnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public Object getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
	}

	



	
}
