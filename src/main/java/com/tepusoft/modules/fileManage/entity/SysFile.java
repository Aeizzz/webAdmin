/**
 * Copyright &copy; 2017-2017 <a href="https://tepusoft.com">tepusoft</a> All rights reserved.
 */
package com.tepusoft.modules.fileManage.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.tepusoft.common.persistence.DataEntity;

/**
 * dfEntity
 * @author liy
 * @version 2017-07-09
 */
public class SysFile extends DataEntity<SysFile> {
	
	private static final long serialVersionUID = 1L;
	private SysFolder parent;
	private String parentId;
	private String parentIds;		// 所有父文件夹
	private String name;		// 文件名称
	private String filePath;		// 文件路径
	private Date pastDate;		// 过期时间
	private Long fileSize;		// 文件大小
	private String version;		// 版本号
	private int clickNum;		// 点击数量
	private int downloadNum;		// 下载数量
	private String type;		// 文件类型
	private String createName;		// 创建人名称
	private String updateName;		// 更新人名称
	private String fileType;   //文件格式
	public SysFile() {
		super();
	}

	public SysFile(String id){
		super(id);
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public SysFolder getParent() {
		return parent;
	}

	public void setParent(SysFolder parent) {
		this.parent = parent;
	}

	public String getParentId() {
	return parentId;
}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Length(min=0, max=2000, message="所有父文件夹长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=100, message="文件名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=300, message="文件路径长度必须介于 0 和 300 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPastDate() {
		return pastDate;
	}

	public void setPastDate(Date pastDate) {
		this.pastDate = pastDate;
	}
	
	@Length(min=0, max=100, message="文件大小长度必须介于 0 和 100 之间")
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	@Length(min=0, max=30, message="版本号长度必须介于 0 和 30 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}

	public int getDownloadNum() {
		return downloadNum;
	}

	public void setDownloadNum(int downloadNum) {
		this.downloadNum = downloadNum;
	}
	
	@Length(min=0, max=5, message="文件类型长度必须介于 0 和 5 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=100, message="创建人名称长度必须介于 0 和 100 之间")
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	@Length(min=0, max=64, message="更新人名称长度必须介于 0 和 64 之间")
	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	
}