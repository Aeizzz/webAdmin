/**
 * Copyright &copy; 2017-2017 <a href="https://tepusoft.com">tepusoft</a> All rights reserved.
 */
package com.tepusoft.modules.timing.entity;

import org.hibernate.validator.constraints.Length;

import com.tepusoft.common.persistence.DataEntity;

/**
 * 增删改查Entity
 * @author liy
 * @version 2017-07-01
 */
public class Timing extends DataEntity<Timing> {
	
	private static final long serialVersionUID = 1L;
	private String isEffect;		// 是否生效
	private String cron;		// cron表达式
	private String status;		// 运行状态
	private String content;		// 任务描述
	private String workId;		// 任务Id
	private String createName;		// 创建人名称
	private String updateName;		// 更新人名称
	
	public Timing() {
		super();
	}

	public Timing(String id){
		super(id);
	}

	@Length(min=0, max=10, message="是否生效长度必须介于 0 和 10 之间")
	public String getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}
	
	@Length(min=0, max=64, message="cron表达式长度必须介于 0 和 64 之间")
	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}
	
	@Length(min=0, max=64, message="运行状态长度必须介于 0 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="任务描述长度必须介于 0 和 64 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=64, message="任务Id长度必须介于 0 和 64 之间")
	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}
	
	@Length(min=0, max=50, message="创建人名称长度必须介于 0 和 50 之间")
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	@Length(min=0, max=50, message="更新人名称长度必须介于 0 和 50 之间")
	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	
}