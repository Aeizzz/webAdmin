package com.tepusoft.modules.demo.dao;
import com.tepusoft.common.persistence.annotation.MyBatisDao;
import com.tepusoft.modules.demo.entity.DesginTable;
import com.tepusoft.common.persistence.CrudDao;
import com.tepusoft.modules.gen.entity.GenTable;

import java.util.List;

/**
 * Created by liying on 2017/6/20.
 */
@MyBatisDao
public interface DesginTableDao extends CrudDao<DesginTable>{

    List<DesginTable> findTableList(DesginTable desginTable);
//    创建表
   void createTable(DesginTable desginTable);
//   void updateIsDbsynch(DesginTable desginTable);
   void  dropTable(DesginTable desginTable);
   //删除以前备份的表
    void  dropReTable(DesginTable desginTable);
//    备份表
   void  reTable (DesginTable desginTable);

}
