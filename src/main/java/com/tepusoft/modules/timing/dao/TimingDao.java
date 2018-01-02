/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.timing.dao;

import com.tepusoft.common.persistence.CrudDao;
import com.tepusoft.common.persistence.annotation.MyBatisDao;
import com.tepusoft.modules.timing.entity.Timing;

/**
 * 增删改查DAO接口
 * @author liy
 * @version 2017-07-01
 */
@MyBatisDao
public interface TimingDao extends CrudDao<Timing> {
	
}