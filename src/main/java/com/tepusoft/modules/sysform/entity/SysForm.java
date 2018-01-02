/**
 * Copyright &copy; 2017-2017 <a href="https://tepusoft.com">tepusoft</a> All rights reserved.
 */
package com.tepusoft.modules.sysform.entity;

import org.apache.xerces.xs.StringList;
import org.hibernate.validator.constraints.Length;

import com.tepusoft.common.persistence.DataEntity;

/**
 * 表单Entity
 * @author ly
 * @version 2017-06-29
 */
public class SysForm extends DataEntity<SysForm> {
	
	private static final long serialVersionUID = 1L;
//	private String delFlag;		// 删除标记
	private String templateNo;		// 模板编号
	private String templateName;		// 模板名称
	private String  ftlContent;//模版内容
	private String isActivate;		// 激活状态
	private String path;		// word路径

	public SysForm() {
		super();
	}

	public SysForm(String id){
		super(id);
	}

//	@Length(min=0, max=10, message="删除标记长度必须介于 0 和 10 之间")
//	public String getDelFlag() {
//		return delFlag;
//	}

//	public void setDelFlag(String delFlag) {
//		this.delFlag = delFlag;
//	}
	
	@Length(min=1, max=10, message="模板编号长度必须介于 1 和 10 之间")
	public String getTemplateNo() {
		return templateNo;
	}

	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
	
	@Length(min=0, max=64, message="模板名称长度必须介于 0 和 64 之间")
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	@Length(min=0, max=5, message="激活状态长度必须介于 0 和 5 之间")
	public String getIsActivate() {
		return isActivate;
	}

	public void setIsActivate(String isActivate) {
		this.isActivate = isActivate;
	}
	
	@Length(min=0, max=200, message="word路径长度必须介于 0 和 200 之间")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFtlContent() {
		return ftlContent;
	}

	public void setFtlContent(String ftlContent) {
		this.ftlContent = ftlContent;
	}
}