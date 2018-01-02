/**
 * Copyright &copy; 2017-2017 <a href="https://tepusoft.com">tepusoft</a> All rights reserved.
 */
package com.tepusoft.modules.fileManage.entity;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.tepusoft.common.persistence.TreeEntity;

/**
 * 文件夹Entity
 *
 * @author liy
 * @version 2017-07-06
 */
public class SysFolder extends TreeEntity<SysFolder> {

    private static final long serialVersionUID = 1L;
    //	private String folderName;		// 文件名称
    private String parentId;        // 所有父文件
    private String createName;        // 创建人名称
    private int clickNum;
    private int downloadNum;
    private String type;
    private Long fileSize;		// 文件大小

    public SysFolder() {
        super();
    }

    public SysFolder(String id) {
        super(id);
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min = 0, max = 2000, message = "所有父文件长度必须介于 0 和 2000 之间")
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @JsonBackReference
    public SysFolder getParent() {
        return parent;
    }

    public void setParent(SysFolder parent) {
        this.parent = parent;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
    public Long getFileSize() {
        return fileSize;
    }
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}