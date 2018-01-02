/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.sysform.service;



import com.tepusoft.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.service.CrudService;
import com.tepusoft.modules.sysform.entity.SysForm;
import com.tepusoft.modules.sysform.dao.SysFormDao;

import java.util.List;

/**
 * 表单Service
 * @author ly
 * @version 2017-06-29
 */
@Service
@Transactional(readOnly = true)
public class SysFormService extends CrudService<SysFormDao, SysForm> {

	@Autowired
	private SysFormDao  sysFormDao;
	public SysForm get(String id) {
		return super.get(id);
	}
	
	public List<SysForm> findList(SysForm sysForm) {
		return super.findList(sysForm);
	}
	
	public Page<SysForm> findPage(Page<SysForm> page, SysForm sysForm) {
		return super.findPage(page, sysForm);
	}
	@Transactional(readOnly = false)
	public void save(SysForm sysForm) {
		if (StringUtils.isBlank(sysForm.getId())){
			sysForm.preInsert();
			sysFormDao.insert(sysForm);
		}else{
			sysForm.preUpdate();
			sysFormDao.update(sysForm);
		}
	}
	@Transactional(readOnly = false)
	public void delete(SysForm sysForm) {
		super.delete(sysForm);
	}

}