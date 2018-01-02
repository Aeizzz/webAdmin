/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.fileManage.dao;

import com.tepusoft.common.persistence.CrudDao;
import com.tepusoft.common.persistence.annotation.MyBatisDao;
import com.tepusoft.modules.fileManage.entity.SysFileRecord;

/**
 * 文件操作记录DAO接口
 * @author liy
 * @version 2017-07-08
 */
@MyBatisDao
public interface SysFileRecordDao extends CrudDao<SysFileRecord> {
	
}