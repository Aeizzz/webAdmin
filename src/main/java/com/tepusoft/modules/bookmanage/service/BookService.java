package com.tepusoft.modules.bookmanage.service;

import com.tepusoft.common.service.CrudService;
import com.tepusoft.modules.bookmanage.dao.BookDao;
import com.tepusoft.modules.bookmanage.entity.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author XuYunXuan
 * @ClassName: BookService
 * @Description:
 * @date 2017-12-05 15:49
 */
@Service
@Transactional(readOnly = true)
public class BookService extends CrudService<BookDao,Book> {
}
