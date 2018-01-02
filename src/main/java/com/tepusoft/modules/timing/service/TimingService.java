/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a>  All rights reserved.
 */
package com.tepusoft.modules.timing.service;

import java.util.List;

import com.tepusoft.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.service.CrudService;
import com.tepusoft.modules.timing.entity.Timing;
import com.tepusoft.modules.timing.dao.TimingDao;

/**
 * 增删改查Service
 * @author liy
 * @version 2017-07-01
 */
@Service
@Transactional(readOnly = true)
public class TimingService extends CrudService<TimingDao, Timing> {
	@Autowired
	private TimingDao timingDao;
	public Timing get(String id) {
		return super.get(id);
	}
	
	public List<Timing> findList(Timing timing) {
		return super.findList(timing);
	}
	
	public Page<Timing> findPage(Page<Timing> page, Timing timing) {
		return super.findPage(page, timing);
	}
	
	@Transactional(readOnly = false)
	public void save(Timing timing) {
		if (StringUtils.isBlank(timing.getId())){
			timing.setIsEffect("0");
			timing.setStatus("1");
			timing.preInsert();
			timingDao.insert(timing);
		}else{
			timing.preUpdate();
			timingDao.update(timing);
		}

		super.save(timing);
	}
	
	@Transactional(readOnly = false)
	public void delete(Timing timing) {
		super.delete(timing);
	}
	
}