/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.sysform.dao;

import com.tepusoft.common.persistence.CrudDao;
import com.tepusoft.common.persistence.annotation.MyBatisDao;
import com.tepusoft.modules.sysform.entity.SysForm;

/**
 * 表单DAO接口
 * @author ly
 * @version 2017-06-29
 */
@MyBatisDao
public interface SysFormDao extends CrudDao<SysForm> {
	
}