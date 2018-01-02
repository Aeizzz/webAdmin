/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.fileManage.dao;

import com.tepusoft.common.persistence.TreeDao;
import com.tepusoft.common.persistence.annotation.MyBatisDao;
import com.tepusoft.modules.fileManage.entity.SysFolder;

import java.util.List;

/**
 * 文件夹DAO接口
 * @author liy
 * @version 2017-07-06
 */
@MyBatisDao
public interface SysFolderDao extends TreeDao<SysFolder> {
    List<SysFolder> find(SysFolder sysFolder);
    List<SysFolder> findByName(SysFolder sysFolder);

}