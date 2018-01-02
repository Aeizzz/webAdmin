/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.fileManage.service;

import java.util.List;

import com.tepusoft.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.service.CrudService;
import com.tepusoft.modules.fileManage.entity.SysFile;
import com.tepusoft.modules.fileManage.dao.SysFileDao;

/**
 * dfService
 * @author liy
 * @version 2017-07-09
 */
@Service
@Transactional(readOnly = true)
public class SysFileService extends CrudService<SysFileDao, SysFile> {
	@Autowired
	private SysFileDao sysFileDao;

	public SysFile get(String id) {
		return super.get(id);
	}
	
	public List<SysFile> findList(SysFile sysFile) {
		return super.findList(sysFile);
	}
	
	public Page<SysFile> findPage(Page<SysFile> page, SysFile sysFile) {
		return super.findPage(page, sysFile);
	}
	
	@Transactional(readOnly = false)
	public void save(SysFile sysFile) {

		if (StringUtils.isBlank(sysFile.getId())) {
			sysFile.preInsert();
			sysFileDao.insert(sysFile);
		} else {
			if(sysFile.getParent()!=null&&sysFile.getParent().getId()!=null){
				sysFile.setParentId(sysFile.getParent().getId());
				sysFile.setParentIds(sysFile.getParent().getParentIds()+sysFile.getParentId()+",");
			}
			sysFile.preUpdate();
			sysFileDao.update(sysFile);
		}
	}
}