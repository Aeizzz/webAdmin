/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.fileManage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tepusoft.common.service.CrudService;
import com.tepusoft.modules.fileManage.entity.SysFileRecord;
import com.tepusoft.modules.fileManage.dao.SysFileRecordDao;

/**
 * 文件操作记录Service
 * @author liy
 * @version 2017-07-08
 */
@Service
@Transactional(readOnly = true)
public class SysFileRecordService extends CrudService<SysFileRecordDao, SysFileRecord> {

	public SysFileRecord get(String id) {
		return super.get(id);
	}

//	@Transactional(readOnly = false)
//	public void save(SysFileRecord sysFileRecord) {
//		super.save(sysFileRecord);
//	}

	@Transactional(readOnly = false)
	public void addRecord(String Id,String type,String operation) {
		SysFileRecord sysFileRecord = new SysFileRecord();
		sysFileRecord.setFileType(type);
		sysFileRecord.setOperation(operation);
		sysFileRecord.setFolderId(Id);
		super.save(sysFileRecord);
	}
	

	
}