/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.fileManage.dao;

import com.tepusoft.common.persistence.CrudDao;
import com.tepusoft.common.persistence.annotation.MyBatisDao;
import com.tepusoft.modules.fileManage.entity.SysFile;

import java.util.List;

/**
 * 文件DAO接口
 * @author liy
 * @version 2017-07-09
 */
@MyBatisDao
public interface SysFileDao extends CrudDao<SysFile> {
    List<SysFile> findByParentIdsLike(SysFile sysFile);
}