package com.tepusoft.modules.book.service;

import com.tepusoft.common.service.CrudService;
import com.tepusoft.modules.book.dao.LHLCategoryDao;
import com.tepusoft.modules.book.entity.LHLCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class LHLCategoryService extends CrudService<LHLCategoryDao,LHLCategory> {
}
