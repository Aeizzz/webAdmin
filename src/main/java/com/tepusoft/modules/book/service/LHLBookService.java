package com.tepusoft.modules.book.service;

import com.tepusoft.common.service.CrudService;
import com.tepusoft.modules.book.dao.LHLBookDao;
import com.tepusoft.modules.book.entity.LHLBook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class LHLBookService extends CrudService<LHLBookDao, LHLBook> {
}
