package com.tepusoft.modules.book.service;

import com.tepusoft.common.service.CrudService;
import com.tepusoft.modules.book.dao.LHLPublishingDao;
import com.tepusoft.modules.book.entity.LHLPublishing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LHLPublishingService extends CrudService<LHLPublishingDao,LHLPublishing> {

}
