package com.tepusoft.modules.bookmanage.dao;

import com.tepusoft.common.persistence.CrudDao;
import com.tepusoft.common.persistence.annotation.MyBatisDao;
import com.tepusoft.modules.bookmanage.entity.Book;

/**
 * @author XuYunXuan
 * @ClassName: BookDao
 * @Description:
 * @date 2017-12-05 15:49
 */
@MyBatisDao
public interface BookDao extends CrudDao<Book> {

}
