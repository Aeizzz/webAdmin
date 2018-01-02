/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tepusoft.modules.sys.dao;

import com.tepusoft.common.persistence.TreeDao;
import com.tepusoft.common.persistence.annotation.MyBatisDao;
import com.tepusoft.modules.sys.entity.Office;

import java.util.List;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	//根据多部门的id查询部门名
    List<Office> getDeparts(String[] idArr);

    List<Office> getByParentId(String parentId);

    List<Office> findByParentId(Office office);
}
