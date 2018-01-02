/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tepusoft.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import com.tepusoft.common.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tepusoft.modules.sys.dao.OfficeDao;
import com.tepusoft.modules.sys.entity.Office;
import com.tepusoft.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
	@Autowired
	private OfficeDao officeDao;

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}

	@Transactional(readOnly = true)
	public List<Office> findListByParent(Office office){
		if(office != null){
			return officeDao.findByParentId(office);
		}
		return  new ArrayList<Office>();
	}

	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	@Transactional(readOnly = false)
    public void saveList(List<Office> officeList) {
		for(Office office : officeList){
			super.save(office);
		}
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

	@Transactional(readOnly = false)
	public List<Office> getDeparts(String[] idsArr){
		List<Office> list=officeDao.getDeparts(idsArr);
		return list;
	}

	@Transactional(readOnly = false)
	public List<Office> getByParentId(String parentId){
		List<Office> list=officeDao.getByParentId(parentId);
		return list;
	}


}
