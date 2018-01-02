/**
 * Copyright &copy; 2017-2017 <a href="https://tepusoft.com">tepusoft</a> All rights reserved.
 */
package com.tepusoft.modules.fileManage.entity;

import org.hibernate.validator.constraints.Length;


import com.tepusoft.common.persistence.DataEntity;

/**
 * 文件操作记录Entity
 * @author liy
 * @version 2017-07-08
 */
public class SysFileRecord extends DataEntity<SysFileRecord> {
	
	private static final long serialVersionUID = 1L;
	private String createName;		// 创建人名称
	private String folderId;		// 文件Id
	private String operation;		// 操作类型
	private String fileType;		// 文件类型(0，文件夹。1文件）
	
	public SysFileRecord() {
		super();
	}

	public SysFileRecord(String id){
		super(id);
	}

	@Length(min=0, max=100, message="创建人名称长度必须介于 0 和 100 之间")
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	@Length(min=0, max=64, message="文件Id长度必须介于 0 和 64 之间")
	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	@Length(min=0, max=5, message="操作类型长度必须介于 0 和 5 之间")
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Length(min=0, max=5, message="文件类型(0，文件夹。1文件）长度必须介于 0 和 5 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}